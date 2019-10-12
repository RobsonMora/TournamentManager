package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
	private JTextField txtTime, txtFCodTorneio, txtFNomeTorneio, txtFQtdTimes;
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
	private TorneioTimeModel torneioTime;
	private String oldTorneio;

	private JogoModel jogo;
	private BuscarTorneio busca;
	private BuscarTime buscaTime;
	private ArrayList<TorneioTimeModel> torneioTimes, torneioTimesChange;

	private void create() {

		setSize(550, 654);
		setTitle("Cadastro Torneio");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		for(Component c : getComponents()){
		        if (c instanceof BasicInternalFrameTitlePane){
		        		for (MouseMotionListener m : c.getMouseMotionListeners()){
		        			  c.removeMouseMotionListener(m);
		        		}
		        		break;
		        }
		}
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
		if (busca.torneioReturn != null) {
			torneioTime = new TorneioTimeModel();
			torneio = busca.torneioReturn;
			oldTorneio = torneio.getNome();
			return true;
		}
		return false;
	}

	@Override
	protected boolean actionDelete() {
		if ((torneio != null) && (!isInserting)) {
			try {
				torneioDao.deleteTorneio(torneio.getId());
				for (TorneioTimeModel torneioTime : torneioTimesChange) {
					torneioTimeDao.deleteTorneioTime(torneioChange.getId(), torneioTime.getIdTime());
				}
				return true;
			} catch (SQLException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean actionSave() {
		try {
			TorneioTimeModel tor = new TorneioTimeModel();
			tor.setIdTime(1);
			tor.setIdTorneio(0);
			torneioTimesChange.add(tor);
			getFields();
			if (torneioTimesChange.size() >= 0) {
				if (isInserting) {
					torneioDao.createTorneio(torneioChange);
				} else {
					if (torneio.getNome().trim().equals(oldTorneio.trim())) {
						torneioDao.updateTorneio(torneioChange);
					}
				}
				for (TorneioTimeModel torneioTime : torneioTimes) {
					torneioTimeDao.createTorneioTime(torneioTime);
				}
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Insira ao um time");
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	protected boolean actionAdd() {
		if (!isInserting) {
			try {
				torneio = new TorneioModel();
				torneioTime = new TorneioTimeModel();
				torneioTimes = new ArrayList<TorneioTimeModel>();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean actionCancel() {
		try {
			if (isInserting) {
				torneio = null;
			}
			torneioChange = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	protected void fillFields() {

		txtFCodTorneio.setText(Integer.toString(torneio.getId()));
		txtFNomeTorneio.setText(torneio.getNome());

		String stq = torneio.getObservacao();
		txtAObs.setText(stq);
		fillJogos();
		findGrad();

		torneioChange = new TorneioModel(torneio);
	}

	public void fillJogos() {

		ComboJogo.removeAllItems();
		ComboJogo.addItem("--Selecione--");

		ArrayList<JogoModel> jogos = null;
		try {
			jogos = jogoDao.getAllJogos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		findGrad();
		if (jogos != null) {
			for (JogoModel jogo : jogos) {
				ComboJogo.addItem(jogo.getNome());
			}
		}

	}

	private void findGrad() {
		try {
			if (!torneio.getNome().trim().isEmpty()) {
				torneioTimes = torneioTimeDao.getAllTorneioTimes(torneio.getId());
				torneioTimesChange = torneioTimes;
				fillTable();
			}
		} catch (SQLException e) {
		}
	}

	private void fillTable() throws SQLException {
		model.setRowCount(0);
		TimeModel timeAux = new TimeModel();
		for (TorneioTimeModel timTorneioTimeModel : torneioTimesChange) {
			timeAux = timeDao.getOneTime(timTorneioTimeModel.getIdTime());
			model.addRow(new String[] { timeAux.getNome() });
		}
	}
	
	private void getFields() {
		torneioChange.setId(Integer.parseInt(txtFCodTorneio.getText()));
		torneioChange.setNome(txtFNomeTorneio.getText());
		ArrayList<JogoModel> jogos = null;
		int i=0;
		if (jogos != null) {
			for (JogoModel jogo : jogos) {
				i++;
				if(ComboJogo.getSelectedItem().toString().equalsIgnoreCase(jogo.getNome())) {
					torneioChange.setIdJogo(i);
				}
			}
		}
		torneioChange.setObservacao(txtAObs.getText());
		TorneioTimeModel aux = new TorneioTimeModel();
		for(i=0; i < model.getRowCount(); i++) {
			aux.setIdTime(Integer.parseInt((String) model.getValueAt(i, 0)));
			aux.setIdTorneio(torneioChange.getId());
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

		LblJogo = new JLabel("Jogo:");
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

		btnAdd = new JButton(new AbstractAction("Adicionar Time") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buscaTime = new BuscarTime(conn);
				if (buscaTime.timeReturn != null) {
					model.addRow(new String[] { Integer.toString(buscaTime.timeReturn.getId()), buscaTime.timeReturn.getNome()});
				}
			}
		});
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
