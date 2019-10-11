package view;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
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
import model.JogoModel;
import model.TimeModel;
import model.TorneioModel;
import model.TorneioTimeModel;

@SuppressWarnings("serial")
public class CadastroTorneios extends MasterDialogCad {

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
	private TorneioModel torneio, torneioChange;
	private JogoModel jogo;
	private BuscarTorneio busca;
	private ArrayList<TorneioTimeModel> times, timesChange;

	private void create() {


		setSize(550,654);
		setTitle("Cadastro Torneio");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public CadastroTorneios(Connection conn) {

		super(conn);
		jogoDao = new JogoDAO(conn);
		timeDao = new TimeDAO(conn);
		torneioDao = new TorneioDAO(conn);
		torneioTimeDao = new TorneioTimeDAO(conn);
		create();

	}

	@Override
	protected void actionSearch() {
		busca = new BuscarTorneio(conn);
		try {
			busca.addWindowListener(eventWindowSearchClosed);
		} catch (Exception e2) {
			busca = null;
		}
	}
	
	@Override
	protected boolean afterSearch() {
		if(busca.torneioReturn!=null) {
			torneio = busca.torneioReturn;
			return true;
		}
		return false;
	}

	@Override
	protected boolean actionDelete() {
		if ((torneio != null) && (!isInserting)) {
			try {
				torneioDao.deleteTorneio(torneio.getId());
				return true;
			} catch (SQLException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean actionAdd() {
		if(!isInserting) {
			try {
				torneio = new TorneioModel();
				return true;
			} catch (Exception e) {
				return false;
			}			
		}else {
			return false;
		}
	}
	
	@Override
	protected boolean actionSave() {
		if(torneioChange!=null) {
			try {
				if(isInserting) {
					torneioDao.createTorneio(torneioChange);
					System.out.println("salvoo");
				}else {
					torneioDao.updateTorneio(torneioChange);
				}
			} catch (SQLException e) {
				return false;
			}

			return true;
		}else {
			return false;
		}
	}
	
	@Override
	protected boolean actionCancel() {
		try {		
			if(isInserting) {
				torneio = null;
			}
			torneioChange = null;			
		}catch (Exception e) {
			return false;
		}				
		return true;	
	}
	
	@Override
	protected void fillFields() {
		// TODO Auto-generated method stub
		/*private JLabel LblTime, LblJogo, LblCodTorneio, LblNomeTorneio, LblQtdTimes, LblObs, lblTip;
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
		private TorneioModel torneio, torneioChange;
		private BuscarTorneio busca;*/
		
		txtFCodTorneio.setText(Integer.toString(torneio.getId()));
		txtFNomeTorneio.setText(torneio.getNome());
		//txtFObs.setText(torneio.getObservacao);
		//ComboJogo.add(torneio.get);
		findGrad();
	}
	
	private void findGrad() {
		try {
			if(!torneio.getNome().trim().isEmpty()) {
				times = torneioTimeDao.getAllTorneioTimes(torneio.getId());				
				timesChange = times;
				fillTable();
			}
		} catch (SQLException e) {
		}		
	}

	private void fillTable() throws SQLException {
		model.setRowCount(0);
		TimeModel timeAux = new TimeModel();
		for(TorneioTimeModel timTorneioTimeModel : timesChange) {
			timeAux = timeDao.getOneTime(timTorneioTimeModel.getIdTime());
			model.addRow(new String[] {timeAux.getNome()});
		}
	}

	@Override
	protected void subComponents() {

		LblTime = new JLabel("Nome do time:");
		LblTime.setBounds(11, 217, 200, 100);
		getContentPane().add(LblTime);

		LblCodTorneio = new JLabel("Código do torneio:");
		LblCodTorneio.setBounds(30, 20, 200, 100);
		getContentPane().add(LblCodTorneio);

		LblNomeTorneio = new JLabel("Nome do Torneio:");
		LblNomeTorneio.setBounds(32, 50, 200, 100);
		getContentPane().add(LblNomeTorneio);

		LblJogo = new JLabel("Tipo de jogo:");
		LblJogo.setBounds(58, 80, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(140, 117, 387, 26);
		getContentPane().add(ComboJogo);

		LblObs = new JLabel("Observação:");
		LblObs.setBounds(59, 110, 200, 100);
		getContentPane().add(LblObs);

		txtFCodTorneio = new JTextField();
		txtFCodTorneio.setBounds(140, 57, 387, 26);
		getContentPane().add(txtFCodTorneio);

		txtFNomeTorneio = new JTextField();
		txtFNomeTorneio.setBounds(140, 87, 387, 26);
		getContentPane().add(txtFNomeTorneio);

		txtAObs = new JTextArea();
		getContentPane().add(txtAObs);
		txtAObs.setLineWrap(true);

		btnAdd = new JButton("Adicionar time");
		btnAdd.setBounds(420, 255, 107, 26);
		getContentPane().add(btnAdd);

		txtTime = new JTextField();
		txtTime.setBounds(100, 255, 310, 26);
		getContentPane().add(txtTime);

		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setBounds(9, 177, 519, 70);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);

		String colunas[] = { "Código ", "Time" };

		model = new DefaultTableModel(colunas, 0);

		table = new JTable(model);
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setEnabled(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(9, 290, 519, 300);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollPane);
		table.getTableHeader().setEnabled(false);

		lblTip = new JLabel("Duplo clique na linha para remové-la.");
		lblTip.setBounds(11, 320, 350, 570);
		getContentPane().add(lblTip);

		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
