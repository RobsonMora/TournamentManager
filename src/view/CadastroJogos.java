package view;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.CategoriasDAO;
import dao.JogoDAO;
import model.CategoriaModel;
import model.JogoModel;
import model.TorneioModel;
import model.TorneioTimeModel;

public class CadastroJogos extends MasterDialogCad {

	private JLabel LblCodigoID, LblCategoria, LblJogo;
	private JTextField txtFCodigoID, txtFNome;
	private JComboBox<String> ComboJogo;
	private JogoDAO jogoDao;
	private BuscarJogo busca;
	private JogoModel jogo, jogoChange;
	private CategoriasDAO categoriasDao;

	private void create() {

		setSize(550, 184);
		setTitle("Cadastro de Jogos");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);
	}

	public CadastroJogos(Connection conn) {
		super(conn);
		jogoDao = new JogoDAO(conn);
		categoriasDao = new CategoriasDAO(conn);
		create();
	}

	protected void actionSearch() {
		busca = new BuscarJogo(conn);
		try {
			busca.addWindowListener(eventWindowSearchClosed);
		} catch (Exception e2) {
			busca = null;
		}
	}

	@Override
	protected boolean afterSearch() {
		if (busca.jogoReturn != null) {
			jogo = busca.jogoReturn;
			return true;
		}
		return false;
	}

	@Override
	protected boolean actionDelete() {
		if ((jogo != null) && (!isInserting)) {
			try {
				jogoDao.deleteJogo(jogo.getId());
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
			if (isInserting) {
				jogoDao.createJogo(jogoChange);

			} else {
				jogoDao.updateJogo(jogoChange);
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	protected boolean actionAdd() {
		if (!isInserting) {
			try {
				jogo = new JogoModel();
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
				jogo = null;
			}
			jogoChange = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	protected void fillFields() {

		txtFCodigoID.setText(jogo.getId().toString());
		txtFNome.setText(jogo.getNome());

		fillCategorias();

		jogoChange = new JogoModel(jogo);
	}

	public void fillCategorias() {

		ComboJogo.removeAllItems();
		ComboJogo.addItem("--Selecione--");

		ArrayList<CategoriaModel> categorias = null;
		try {
			categorias = categoriasDao.getAllCategorias();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!categorias.isEmpty()) {
			for (CategoriaModel categoria : categorias) {
				ComboJogo.addItem(categoria.getNome());
			}
		}
	}

	private void getFields() throws SQLException {
		jogoChange.setNome(txtFNome.getText());
		ArrayList<CategoriaModel> categorias = categoriasDao.getAllCategorias();
		int i = 0;
		for (CategoriaModel categoria : categorias) {
			i++;
			if (ComboJogo.getSelectedItem().toString().equalsIgnoreCase(categoria.getNome())) {
				jogoChange.setIdCategoria(i);
			}
		}
	}

	protected void subComponents() {

		LblCodigoID = new JLabel("Código do jogo:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);

		LblCategoria = new JLabel("Nome do jogo:");
		LblCategoria.setBounds(18, 50, 200, 100);
		getContentPane().add(LblCategoria);

		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(120, 57, 407, 26);
		txtFCodigoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					txtFNome.requestFocus();
				}

			}
		});
		getContentPane().add(txtFCodigoID);

		txtFNome = new JTextField();
		txtFNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					ComboJogo.requestFocus();
				}

			}
		});
		txtFNome.setBounds(120, 87, 407, 26);

		getContentPane().add(txtFNome);

		LblJogo = new JLabel("Tipo de jogo:");
		LblJogo.setBounds(27, 80, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(120, 117, 407, 26);
		ComboJogo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					btnSave.doClick();
				}

			}
		});
		getContentPane().add(ComboJogo);
		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
