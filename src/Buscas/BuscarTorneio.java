package Buscas;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dao.JogoDAO;
import dao.TorneioDAO;
import model.TorneioModel;
@SuppressWarnings("serial")
public class BuscarTorneio extends MasterBuscar {

	public TorneioModel torneioReturn;
	private ArrayList<TorneioModel> torneioList;
	private TorneioDAO torneioDao;

	public BuscarTorneio(Connection conn) {
		super(conn);
		model = new DefaultTableModel(new String[] { "id", "Nome" }, 0);
		updateComp(new String[] { "Torneio" });
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		torneioReturn = null;
	}

	protected void buscar() {
		try {
			clean();
			if (jTxtBusca.getText().isEmpty()) {
				torneioList = new TorneioDAO(conn).getAllTorneios();
				for (int i = 0; i < torneioList.size(); i++) {

					if (utils.containsIgnoreCase(torneioList.get(i).getNome(), jTxtBusca.getText())) {
						InsertRow(Integer.toString(torneioList.get(i).getId()), torneioList.get(i).getNome());
					} else {
						torneioList.remove(i);
						i--;
					}

				}
			} else {
				if (jTxtBusca.getText().matches("[0123456789]+")) {
					torneioList = new ArrayList<TorneioModel>();
					TorneioModel torneiomodel = new TorneioModel();
					torneiomodel = new TorneioDAO(conn).getOneTorneio(Integer.parseInt(jTxtBusca.getText()));
					if (torneiomodel != null) {
						torneioList.add(torneiomodel);
						InsertRow(Integer.toString(torneioList.get(0).getId()), torneioList.get(0).getNome());
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
		torneioReturn = torneioList.get(table.getSelectedRow());
	}

}