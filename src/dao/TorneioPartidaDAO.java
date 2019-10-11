package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TorneioPartidaModel;

public class TorneioPartidaDAO extends BaseDAO {
	
	public TorneioPartidaDAO(Connection conn) {
		super(conn);
	}

	public ArrayList<TorneioPartidaModel> getAllTorneioPartidas(Integer idTorneio, Integer fase) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneio_partidas")
				.filter("id_torneio", "=", idTorneio.toString(), true)
				.filter("fase", "=", fase.toString(), false)
				.where()
				.orderBy("id")
				.apply();
		ArrayList<TorneioPartidaModel> torneioPartidaList = new ArrayList<TorneioPartidaModel>();
		while(result.next()) {
			torneioPartidaList.add(new TorneioPartidaModel()
							.setId(result.getInt("id"))
							.setIdTorneio(result.getInt("id_torneio"))
							.setIdTime1(result.getInt("id_time_1"))
							.setIdTime2(result.getInt("id_time_2"))
							.setPontos1(result.getInt("pontos_1"))
							.setPontos2(result.getInt("pontos_2"))
							.setFase(result.getInt("fase"))
							);
		}
		return torneioPartidaList;
	}

	public TorneioPartidaModel getOneTorneioPartida(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneio_partidas")
				.where("id", "=", id.toString())
				.apply();
		
		if(result.next()) {
			TorneioPartidaModel torneioPartida = new TorneioPartidaModel();
			return torneioPartida.setId(result.getInt("id"))
					.setIdTorneio(result.getInt("id_torneio"))
					.setIdTime1(result.getInt("id_time_1"))
					.setIdTime2(result.getInt("id_time_2"))
					.setPontos1(result.getInt("pontos_1"))
					.setPontos2(result.getInt("pontos_2"))
					.setFase(result.getInt("fase"));
		}else {
			return null;
		}
	}

	public void createTorneioPartida(TorneioPartidaModel torneioPartida) throws SQLException {
		this.insertInto("torneio_partidas", "id_torneio, id_time_1, id_time_2, fase")
		.values(torneioPartida.getIdTorneio().toString()+", "+
				torneioPartida.getIdTime1().toString()+", "+
				torneioPartida.getIdTime2().toString()+
				((torneioPartida.getFase() == 0) ? ", "+torneioPartida.getFase().toString() : ""))
		.commit();
	}

	public void updateTorneioPartida(TorneioPartidaModel torneioPartida) throws SQLException {
		this.update("torneio_partidas")
		.setValue(
				"  pontos_1 = "+torneioPartida.getPontos1()+
				", pontos_2 = "+torneioPartida.getPontos2()
				)
		.where("id", "=", torneioPartida.getId().toString())
		.commit();
	}
	
	public void deleteTorneioPartida(Integer id) throws SQLException {
		this.delete()
		.from("torneio_partidas")
		.where("id", "=", id.toString())
		.commit();
	}

}
