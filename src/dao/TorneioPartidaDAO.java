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

	public ArrayList<TorneioPartidaModel> getAllTorneioPartidas(Integer idTorneio) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneio_partidas")
				.where("id_torneio", "=", idTorneio.toString())
				.orderBy("id")
				.apply();
		ArrayList<TorneioPartidaModel> torneioPartidaList = new ArrayList<TorneioPartidaModel>();
		while(result.next()) {
			torneioPartidaList.add(new TorneioPartidaModel()
							.setId(result.getInt("id"))
							.setIdTorneio(result.getInt("id_torneio"))
							.setIdTime1(result.getInt("id_time1"))
							.setIdTime2(result.getInt("id_time2"))
							.setPontos1(result.getInt("pontos1"))
							.setPontos2(result.getInt("pontos2"))
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
					.setIdTime1(result.getInt("id_time1"))
					.setIdTime2(result.getInt("id_time2"))
					.setPontos1(result.getInt("pontos1"))
					.setPontos2(result.getInt("pontos2"))
					.setFase(result.getInt("fase"));
		}else {
			return null;
		}
	}

	public void createTorneioPartida(TorneioPartidaModel torneioPartida) throws SQLException {
		this.insertInto("torneio_partidas", "id_torneio, id_time1, id_time2, fase")
		.values(torneioPartida.getIdTorneio().toString()+", "+
				torneioPartida.getIdTime1().toString()+", "+
				torneioPartida.getIdTime2().toString()+", "+
//				torneioPartida.getPontos1().toString()+", "+
//				torneioPartida.getPontos2().toString()+", "+
				torneioPartida.getFase().toString())
		.commit();
	}

	public void updateTorneioPartida(TorneioPartidaModel torneioPartida) throws SQLException {
		this.update("torneio_partidas")
		.setValue(
				"  pontos1 = "+torneioPartida.getPontos1()+
				", pontos2 = "+torneioPartida.getPontos2()
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
