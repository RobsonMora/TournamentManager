package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.GanhadorModel;

public class GanhadorDAO extends BaseDAO {
	
	public GanhadorDAO(Connection conn) {
		super(conn);
	}

	public ArrayList<GanhadorModel> getAllGanhadores() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("ganhadores")
				.apply();
		ArrayList<GanhadorModel> ganhadorList = new ArrayList<GanhadorModel>();
		while(result.next()) {
			ganhadorList.add(new GanhadorModel()
							.setIdTorneio(result.getInt("id_torneio"))
							.setIdTime(result.getInt("id_time"))
							);
		}
		return ganhadorList;
	}
	
	public GanhadorModel getOneGanhador(Integer idTorneio, Integer idTime) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("ganhadores")
				.filter("id_torneio", "=", idTorneio.toString(), true)
				.filter("id_time", "=", idTime.toString(), true)				
				.where()
				.apply();
		
		if(result.next()) {
			GanhadorModel ganhador = new GanhadorModel();
			return ganhador.setIdTorneio(result.getInt("id_torneio"))
					.setIdTime(result.getInt("id_time"));
		}else {
			return null;
		}
	}

	public void createGanhador(GanhadorModel ganhador) throws SQLException {
		this.insertInto("ganhadores", "id_torneio, id_time")
		.values(ganhador.getIdTorneio().toString()+", "+ganhador.getIdTime().toString())
		.commit();
	}

	public void deleteGanhador(Integer idTorneio, Integer idTime) throws SQLException {
		this.delete()
		.from("ganhadores")
		.filter("id_torneio", "=", idTorneio.toString(), true)
		.filter("id_time", "=", idTime.toString(), false)				
		.where()
		.commit();
	}


}
