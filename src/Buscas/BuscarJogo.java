package Buscas;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.JogoDAO;
import model.JogoModel;
@SuppressWarnings("serial")
public class BuscarJogo extends MasterBuscar {

	public JogoModel jogoReturn;
	private ArrayList<JogoModel> jogoList;
	private JogoDAO jogoDao;

	public BuscarJogo(Connection conn) {
		super(conn);
		model = new DefaultTableModel(new String[] { "id", "Nome" }, 0);
		updateComp(new String[] { "Jogo" });
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		jogoReturn = null;
	}

	protected void buscar() {
		try {
			clean();
			if (jTxtBusca.getText().isEmpty()) {
				jogoList = new JogoDAO(conn).getAllJogos();
				for (int i = 0; i < jogoList.size(); i++) {

					if (utils.containsIgnoreCase(jogoList.get(i).getNome(), jTxtBusca.getText())) {
						InsertRow(Integer.toString(jogoList.get(i).getId()), jogoList.get(i).getNome());
					} else {
						jogoList.remove(i);
						i--;
					}

				}
			} else {
				if (jTxtBusca.getText().matches("[0123456789]+")) {
					JogoModel jogoReturn = new JogoModel();
					jogoReturn = new JogoDAO(conn).getOneJogo(Integer.parseInt(jTxtBusca.getText()));
					if (jogoReturn != null) {
						InsertRow(Integer.toString(jogoReturn.getId()), jogoReturn.getNome());
					}
				} else {
					System.out.println("numeros");
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void setReturn() {
		jogoReturn = jogoList.get(table.getSelectedRow());
	}

}
