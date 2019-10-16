package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TimeModel;
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
				.orderBy("id_time")
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
	
	public ArrayList<TimeModel> getAllTimes(Integer idTorneio) throws SQLException {
		ResultSet result = null;
		result = this.select("t.id, t.nome")
				.from("torneio_times tt")
				.leftJoin(" times t on t.id = tt.id_time")
				.where("tt.id_torneio", "=", idTorneio.toString())
				.apply();
		ArrayList<TimeModel> timeList = new ArrayList<TimeModel>();
		while(result.next()) {
			timeList.add(new TimeModel()
							.setId(result.getInt("id"))
							.setNome(result.getString("nome"))
							);
		}
		return timeList;
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

	public void createTorneioTime(ArrayList<TimeModel> torneioTimes, Integer torneio) throws SQLException {
		deleteTorneioTime(torneio, 0);

		for(TimeModel torneioTime : torneioTimes) {
			this.insertInto("torneio_times", "id_torneio, id_time")
			.values(torneio.toString() +", "+torneioTime.getId().toString())
			.setSql(getSql() + ";");			
		}
		commit();
	}

	public void deleteTorneioTime(Integer idTorneio, Integer idTime) throws SQLException {
		this.delete()
		.from("torneio_times")
		.filter("id_torneio", "=", idTorneio.toString(), true)
		.filter("id_time", "=", idTime.toString(), false)				
		.where()
		.commit();
	}


}
