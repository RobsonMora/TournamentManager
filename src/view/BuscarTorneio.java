package view;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dao.TorneioDAO;
import model.TorneioModel;

public class BuscarTorneio extends MasterBuscar {
	
	TorneioModel torneioReturn;
	private ArrayList<TorneioModel> torneioList;
	
	public BuscarTorneio(Connection conn) {
		super(conn);
		model = new DefaultTableModel(new String[]{"id","Nome"}, 0);
		updateComp(new String[] {"Torneio"});
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		torneioReturn = null;
	}

	protected void buscar() {
		System.out.println("buscar");
		try {
			System.out.println("buscar");
			clean();
				torneioList = new TorneioDAO(conn).getAllTorneios();
				for(int i = 0; i < torneioList.size(); i++) {

					if(utils.containsIgnoreCase(torneioList.get(i).getNome(), jTxtBusca.getText())) {						
						InsertRow(Integer.toString((torneioList.get(i).getId())), torneioList.get(i).getNome());
					}else {
						torneioList.remove(i);
						i--;
					}
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void setReturn() {
		torneioReturn = torneioList.get(table.getSelectedRow());
	}

}