package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import dao.GanhadorDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.GanhadorModel;
import model.TorneioPartidaModel;

public class Ranking extends JInternalFrame {
		
	private TorneioPartidaDAO torneioPartidaDAO;
	private TorneioTimeDAO torneioTimeDAO;
	private GanhadorDAO ganhadorDAO;
	private TimeDAO timeDAO;
	private TorneioDAO torneioDAO;
	private JTable tblRank;
	protected DefaultTableModel model;
	
	public Ranking(Connection conn) {

		setSize(330, 350);
		setTitle("Ranking");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		
		torneioDAO = new TorneioDAO(conn);
		torneioPartidaDAO = new TorneioPartidaDAO(conn);
		torneioTimeDAO = new TorneioTimeDAO(conn);
		ganhadorDAO = new GanhadorDAO(conn);
		timeDAO = new TimeDAO(conn);
		
		calcRank();
		
		setVisible(true);
	}
		
	public void calcRank() {
		
		String colunas1[] = { "Posição", "Nome do Time", "Pontos" };
		model = new DefaultTableModel(colunas1, 20);
		tblRank = new JTable(model);
		tblRank.setBorder(BorderFactory.createLineBorder(Color.black));
		// pra editar a coluna = setEnable(true) //
		tblRank.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblRank);
		scrollPane.setBounds(10, 10, 300, 300);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.getContentPane().add(scrollPane);
		tblRank.getTableHeader().setEnabled(false);
	
		}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
	
