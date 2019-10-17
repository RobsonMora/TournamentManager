package Buscas;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dao.TimeDAO;
import model.TimeModel;
@SuppressWarnings("serial")
public class BuscarTime extends MasterBuscar {

	public TimeModel timeReturn;
	private ArrayList<TimeModel> timeList;
	private TimeDAO timeDao;

	public BuscarTime(Connection conn) {
		super(conn);
		model = new DefaultTableModel(new String[] { "id", "Nome" }, 0);
		updateComp(new String[] { "Time" });
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		timeReturn = null;
	}

	protected void buscar() {
		try {
			clean();
			if (jTxtBusca.getText().isEmpty()) {
				timeList = new TimeDAO(conn).getAllTimes();
				for (int i = 0; i < timeList.size(); i++) {

					if (utils.containsIgnoreCase(timeList.get(i).getNome(), jTxtBusca.getText())) {
						InsertRow(Integer.toString(timeList.get(i).getId()), timeList.get(i).getNome());
					} else {
						timeList.remove(i);
						i--;
					}
				}
			} else {
				if (jTxtBusca.getText().matches("[0123456789]+")) {
					timeList = new ArrayList<TimeModel>();
					TimeModel timemodel = new TimeModel();
					timemodel = new TimeDAO(conn).getOneTime(Integer.parseInt(jTxtBusca.getText()));
					if (timemodel != null) {
						timeList.add(timemodel);
						InsertRow(Integer.toString(timeList.get(0).getId()), timeList.get(0).getNome());
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
		timeReturn = timeList.get(table.getSelectedRow());
	}

}
