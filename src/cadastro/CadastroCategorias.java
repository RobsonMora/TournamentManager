package cadastro;

import java.awt.Dimension;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Buscas.BuscarCategoria;
import dao.CategoriasDAO;
import model.CategoriaModel;
@SuppressWarnings("serial")
public class CadastroCategorias extends MasterDialogCad {

	private JLabel LblCodigoID, LblCategoria;
	private JTextField txtFCodigoID, txtFCategoria;
	private CategoriasDAO categoriaDao;
	private BuscarCategoria busca;
	private CategoriaModel categoria, categoriaChange;

	private void create() {

		setSize(550, 154);
		setTitle("Categorias");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public CadastroCategorias(Connection conn) {

		super(conn);
		categoriaDao = new CategoriasDAO(conn);
		create();
	}
	
	protected void actionSearch() {
		busca = new BuscarCategoria(conn);
		try {
			busca.addWindowListener(eventWindowSearchClosed);
		} catch (Exception e2) {
			busca = null;
		}
	}

	@Override
	protected boolean afterSearch() {
		if (busca.categoriaReturn != null) {
			categoria = busca.categoriaReturn;
			return true;
		}
		return false;
	}

	@Override
	protected boolean actionDelete() {
		if ((categoria != null) && (!isInserting)) {
			try {
				if(!categoriaDao.existsDependencies(categoria.getId())) {
					categoriaDao.deleteCategoria(categoria.getId());
					return true;
				}else {
					JOptionPane.showMessageDialog(null, "Existem jogos cadastrados com essa categoria");
					return false;
				}
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
			if(txtFCategoria.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Campo vazio!");
				return false;
			}
			getFields();
			if (isInserting) {
				categoriaDao.createCategoria(categoriaChange);
				JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
			} else {
				categoriaDao.updateCategoria(categoriaChange);
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
				categoria = new CategoriaModel();
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
				categoria = null;
			}
			categoriaChange = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	protected void fillFields() {

		txtFCodigoID.setText(categoria.getId().toString());
		txtFCategoria.setText(categoria.getNome());


		categoriaChange = new CategoriaModel(categoria);
	}
	
	private void getFields() {
		categoriaChange.setNome(txtFCategoria.getText());
	}

	protected void subComponents() {

		LblCodigoID = new JLabel("Código da categoria:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);
		
		LblCategoria = new JLabel("Nome da categoria:");
		LblCategoria.setBounds(18, 50, 200, 100);
		getContentPane().add(LblCategoria);	
		
		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(140, 57, 387, 26);
		txtFCodigoID.setName("ignore");
		txtFCodigoID.setEnabled(false);
		txtFCodigoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtFCategoria.requestFocus();
				}

			}
		});
		getContentPane().add(txtFCodigoID);

		txtFCategoria = new JTextField();
		txtFCategoria.setBounds(140, 87, 387, 26);
		txtFCategoria.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnSave.doClick();
				}

			}
		});
		getContentPane().add(txtFCategoria);
		
		childContainer = getContentPane();
	
	}
	
	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
