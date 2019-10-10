package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TorneioTimeModel;

public class TorneioTimeDAO extends BaseDAO {
	
	public TorneioTimeDAO(Connection conn) {
		super(conn);
	}

	public ArrayList<TorneioTimeModel> getAllTorneioTimes(Integer idTorneio) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneio_times")
				.where("id_torneio", "=", idTorneio.toString())
				.apply();
		ArrayList<TorneioTimeModel> torneioTimeList = new ArrayList<TorneioTimeModel>();
		while(result.next()) {
			torneioTimeList.add(new TorneioTimeModel()
							.setIdTorneio(result.getInt("id_torneio"))
							.setIdTime(result.getInt("id_time"))
							);
		}
		return torneioTimeList;
	}

	public TorneioTimeModel getOneTorneioTime(Integer idTorneio, Integer idTime) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneio_times")
				.filter("id_torneio", "=", idTorneio.toString(), true)
				.filter("id_time", "=", idTime.toString(), true)				
				.where()
				.apply();
		
		if(result.next()) {
			TorneioTimeModel torneioTime = new TorneioTimeModel();
			return torneioTime.setIdTorneio(result.getInt("id_torneio"))
					.setIdTime(result.getInt("id_time"));
		}else {
			return null;
		}
	}

	public void createTorneioTime(TorneioTimeModel torneioTime) throws SQLException {
		this.insertInto("torneio_times", "id_torneio, id_time")
		.values(torneioTime.getIdTorneio().toString()+", "+torneioTime.getIdTime().toString())
		.commit();
	}

	/*
	public void updateTorneioTime(TorneioTimeModel torneioTime) throws SQLException {
		this.update("torneio_times")
		.setValue(
				"id_torneio = "+torneioTime.getIdTorneio().toString()+
				", id_time= "+torneioTime.getIdTime().toString()
				)
		.filter("id_torneio", "=", torneioTime.getIdTorneio().toString(), true)
		.filter("id_time", "=", torneioTime.getIdTime().toString(), true)				
		.where()
		.commit();
	}
	*/

	public void deleteTorneioTime(Integer idTorneio, Integer idTime) throws SQLException {
		this.delete()
		.from("torneioTimes")
		.filter("id_torneio", "=", idTorneio.toString(), true)
		.filter("id_time", "=", idTime.toString(), false)				
		.where()
		.commit();
	}
}
