
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import dao.CategoriasDAO;
import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioTimeDAO;

@SuppressWarnings("serial")
public class NovoTorneio extends MasterDialogCad {

	private JLabel LblTime, LblJogo, LblCodTorneio, LblNomeTorneio, LblQtdTimes, LblObs, lblTip;
	private JTextField txtTime, txtFCodTorneio, txtFNomeTorneio, txtFQtdTimes, txtFObs;
	private JTextArea txtAObs;
	private JComboBox<String> ComboJogo;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnAdd;
	private TimeDAO timeDao;
	private JogoDAO jogoDao;
	private TorneioDAO torneioDao;
	private TorneioTimeDAO torneioTimeDao;

	private void create() {

		setSize(550,684);
		setTitle("Novo Torneio");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public NovoTorneio(Connection conn) {

		super(conn);
		jogoDao = new JogoDAO(conn);
		timeDao = new TimeDAO(conn);
		torneioDao = new TorneioDAO(conn);
		torneioTimeDao = new TorneioTimeDAO(conn);
		create();

	}

	@Override
	protected void subComponents() {

		LblTime = new JLabel("Nome do time:");
		LblTime.setBounds(11, 247, 200, 100);
		getContentPane().add(LblTime);
		
		LblCodTorneio = new JLabel("Código do torneio:");
		LblCodTorneio.setBounds(30, 20, 200, 100);
		getContentPane().add(LblCodTorneio);

		LblNomeTorneio = new JLabel("Nome do Torneio:");
		LblNomeTorneio.setBounds(32, 50, 200, 100);
		getContentPane().add(LblNomeTorneio);

		LblQtdTimes = new JLabel("Quantidade de Times:");
		LblQtdTimes.setBounds(10, 80, 200, 100);
		getContentPane().add(LblQtdTimes);

		LblJogo = new JLabel("Tipo de jogo:");
		LblJogo.setBounds(58, 110, 200, 100);
		getContentPane().add(LblJogo);

		
		
		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(140, 147, 387, 26);
		getContentPane().add(ComboJogo);

		LblObs = new JLabel("Observação:");
		LblObs.setBounds(59, 140, 200, 100);
		getContentPane().add(LblObs);

		txtFCodTorneio = new JTextField();
		txtFCodTorneio.setBounds(140, 57, 387, 26);
		getContentPane().add(txtFCodTorneio);

		txtFNomeTorneio = new JTextField();
		txtFNomeTorneio.setBounds(140, 87, 387, 26);
		getContentPane().add(txtFNomeTorneio);

		txtFQtdTimes = new JTextField();
		txtFQtdTimes.setBounds(140, 117, 387, 26);
		getContentPane().add(txtFQtdTimes);

		txtAObs = new JTextArea();
		getContentPane().add(txtAObs);
		txtAObs.setLineWrap(true);
		
		btnAdd = new JButton("Adicionar time");
		btnAdd.setBounds(420, 285, 107, 26);
		getContentPane().add(btnAdd);
		
		txtTime = new JTextField();
		txtTime.setBounds(100, 285, 310, 26);
		getContentPane().add(txtTime);
		
		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setBounds(9, 207, 519, 70);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		String colunas[] = { "Código ", "time" };

		model = new DefaultTableModel(colunas, 0);

		table = new JTable(model);
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setEnabled(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(9, 320, 519, 300);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollPane);
		table.getTableHeader().setEnabled(false);
		
		lblTip = new JLabel("Duplo clique na linha para remové-la.");
		lblTip.setBounds(11, 350, 350, 570);
		getContentPane().add(lblTip);
		
		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
