package view;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import dao.TimeDAO;
import model.TimeModel;


@SuppressWarnings("serial")
public class Ranking extends JInternalFrame {
		
	
	private JTable tblRank;
	protected DefaultTableModel model;
	private TimeDAO timeDAO;
	
	public Ranking(Connection conn) {

		setSize(330, 350);
		setTitle("Ranking");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		timeDAO = new TimeDAO(conn);
		
		
		calcRank();
		fillTable();
		
		setVisible(true);
	}
		
	public void calcRank() {
		
		String colunas1[] = { "Posicao", "Nome do Time", "Pontos", "KD" };
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
	
	private void fillTable() {
		
		try {
			ArrayList<TimeModel> timeModels = timeDAO.getAllTimes();
			model.setRowCount(0);
			for(TimeModel timeModel : timeModels) {				
				if(timeModel != null) {
					model.addRow(new String[] {timeModel.getId().toString(),
											   timeModel.getNome(), 
										       timeModel.getPontos().toString(), 
										       timeModel.getVitorias().toString() + "/" + timeModel.getDerrotas().toString()});
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
	
