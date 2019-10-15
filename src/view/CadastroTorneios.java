package view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.table.DefaultTableModel;
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

	private JLabel LblTime, LblJogo, LblCodTorneio, LblNomeTorneio, LblObs, lblTip;
	private JTextField txtTime, txtFCodTorneio, txtFNomeTorneio;
	private JTextArea txtAObs;
	private JComboBox<String> ComboJogo;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnAddTime;
	private TimeDAO timeDao;
	private JogoDAO jogoDao;
	private TorneioDAO torneioDao;
	private TorneioTimeDAO torneioTimeDao;
	private TorneioModel torneio, torneioChange;
	private String oldTorneio;
	private BuscarTorneio busca;
	private BuscarTime buscaTime;
	private ArrayList<TorneioTimeModel> torneioTimes, torneioTimesChange;
	private TorneioTimeModel torneioTimeAux;
	
	protected WindowAdapter eventWindowSearchTimeClosed = new WindowAdapter() {

		@Override
		public void windowClosed(WindowEvent e) {
			
			torneioTimeAux = new TorneioTimeModel();
			if(buscaTime.timeReturn!=null) {
				if(!utils.tableContains(table, 0, buscaTime.timeReturn.getNome())){
					torneioTimeAux.setIdTime(buscaTime.timeReturn.getId());
					torneioTimeAux.setIdTorneio(0);
					torneioTimesChange.add(torneioTimeAux);
					insertTable(buscaTime.timeReturn);
				}else {
					JOptionPane.showMessageDialog(null, "Já existe");
				}
			}

		}

	};

	private void create() {

		setSize(550, 654);
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
		if (busca.torneioReturn != null) {
			new TorneioTimeModel();
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
				torneioTimeDao.deleteTorneioTime(torneioChange.getId(), 0);
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
			getFields();
			if (torneioTimesChange.size() >= 0) {
				int torneioId = torneioChange.getId();
				if (isInserting) {
					torneioId = torneioDao.createTorneio(torneioChange);
				} else {
					if (torneio.getNome().trim().equals(oldTorneio.trim())) {
						torneioDao.updateTorneio(torneioChange);
					}
					torneioTimeDao.createTorneioTime(torneioTimesChange, torneioId);
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
				new TorneioTimeModel();
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

		txtAObs.setText(torneio.getObservacao());
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

		if (jogos != null) {
			for (JogoModel jogo : jogos) {
				ComboJogo.addItem(jogo.getNome());
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void findGrad() {
		try {
			if (!torneio.getNome().trim().isEmpty()) {
				torneioTimes = torneioTimeDao.getAllTorneioTimes(torneio.getId());
				torneioTimesChange = (ArrayList<TorneioTimeModel>)torneioTimes.clone();
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
			model.addRow(new String[] { timeAux.getId().toString(),timeAux.getNome() });
		}
	}

	private void getFields() throws SQLException {
		
		torneioChange.setNome(txtFNomeTorneio.getText());
		ArrayList<JogoModel> jogos = jogoDao.getAllJogos();
		int i = 0;
		for (JogoModel jogo : jogos) {
			i++;
			if (ComboJogo.getSelectedItem().toString().equalsIgnoreCase(jogo.getNome())) {
				torneioChange.setIdJogo(i);
			}
		}
		torneioChange.setObservacao(txtAObs.getText());
		TorneioTimeModel auxTime = new TorneioTimeModel();
		/*for (i = 0; i < model.getRowCount(); i++) {
			auxTime.setIdTime(Integer.parseInt((String) model.getValueAt(i, 0)));
			auxTime.setIdTorneio(torneioChange.getId());
			torneioTimesChange.add(auxTime);
		}*/
		torneioChange.setInicio(new Date(0));
		torneioChange.setFim(new Date(0));

	}
	
	private void insertTable(TimeModel time) {

		model.addRow(new String[] {time.getId().toString(), time.getNome()});		

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
		LblJogo.setBounds(99, 80, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtAObs.requestFocus();
				}

			}
		});
		ComboJogo.setBounds(140, 117, 387, 26);
		getContentPane().add(ComboJogo);

		LblObs = new JLabel("Observação:");
		LblObs.setBounds(59, 110, 200, 100);
		getContentPane().add(LblObs);

		txtFCodTorneio = new JTextField();
		txtFCodTorneio.setBounds(140, 57, 387, 26);
		txtFCodTorneio.setName("ignore");
		txtFCodTorneio.setEnabled(false);
		txtFCodTorneio.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		getContentPane().add(txtFCodTorneio);

		txtFNomeTorneio = new JTextField();
		txtFNomeTorneio.setBounds(140, 87, 387, 26);
		txtFNomeTorneio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ComboJogo.requestFocus();
				}

			}
		});
		getContentPane().add(txtFNomeTorneio);

		txtAObs = new JTextArea();
		getContentPane().add(txtAObs);
		txtAObs.setLineWrap(true);

		btnAddTime = new JButton(new AbstractAction("Adicionar Time") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buscaTime = new BuscarTime(conn);
				buscaTime.addWindowListener(eventWindowSearchTimeClosed);
				
			}
		});
		btnAddTime.setBounds(392, 255, 135, 26);
		btnAddTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAdd.requestFocus();
					btnAdd.doClick();
					
				}

			}
		});
		getContentPane().add(btnAddTime);

		txtTime = new JTextField();
		txtTime.setBounds(100, 255, 280, 26);
		txtTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAddTime.requestFocus();
				}

			}
		});
		getContentPane().add(txtTime);

		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setBounds(9, 177, 519, 70);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtAObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtTime.requestFocus();
					
				}

			}
		});
		
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
