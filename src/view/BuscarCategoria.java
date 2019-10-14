package view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dao.CategoriasDAO;
import model.CategoriaModel;
@SuppressWarnings("serial")
public class BuscarCategoria extends MasterBuscar {

	CategoriaModel categoriaReturn;
	private ArrayList<CategoriaModel> categoriaList;
	private CategoriasDAO categoriaDao;

	public BuscarCategoria(Connection conn) {
		super(conn);
		model = new DefaultTableModel(new String[] { "id", "Nome" }, 0);
		updateComp(new String[] { "Categoria" });
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		categoriaReturn = null;
	}

	protected void buscar() {
		System.out.println("buscar");
		try {
			clean();
			if (jTxtBusca.getText().isEmpty()) {
				categoriaList = new CategoriasDAO(conn).getAllCategorias();
				for (int i = 0; i < categoriaList.size(); i++) {

					if (utils.containsIgnoreCase(categoriaList.get(i).getNome(), jTxtBusca.getText())) {
						InsertRow(Integer.toString(categoriaList.get(i).getId()), categoriaList.get(i).getNome());
					} else {
						categoriaList.remove(i);
						i--;
					}

				}
			} else {
				if (jTxtBusca.getText().matches("[0123456789]+")) {
					categoriaList = new ArrayList<CategoriaModel>();
					CategoriaModel categoriaResult = categoriaDao.getOneCategoria(Integer.parseInt(jTxtBusca.getText().trim()));
					if (categoriaResult != null) {
						categoriaList.add(categoriaResult);
						InsertRow(Integer.toString(categoriaList.get(0).getId()), categoriaList.get(0).getNome());
					}
				} else {
					System.out.println("numeros");
				}
			}

		} catch (SQLException e1) {
			// TODO: handle exception
		}
	}

	protected void setReturn() {
		categoriaReturn = categoriaList.get(table.getSelectedRow());
	}

}

