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
				.filter("fase", "=", fase.toString(), true)
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
		if(torneioPartidaList.size()>0) {
			return torneioPartidaList;
		}else {
			return null;
		}
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
		this.insertInto("torneio_partidas", "id_torneio, id_time_1, id_time_2 "+(torneioPartida.getFase() > 0 ? ", fase ": "" ))
		.values(torneioPartida.getIdTorneio().toString()+", "+
				torneioPartida.getIdTime1().toString()+", "+
				torneioPartida.getIdTime2().toString()+
				((torneioPartida.getFase() > 0) ? ", "+torneioPartida.getFase().toString() : ""))
		.commit();
	}

	public void updateTorneioPartida(TorneioPartidaModel torneioPartida, TimeDAO timeDAO) throws SQLException {
		this.update("torneio_partidas")
		.setValue(
				"  pontos_1 = "+torneioPartida.getPontos1()+
				", pontos_2 = "+torneioPartida.getPontos2()
				)
		.where("id", "=", torneioPartida.getId().toString())
		.commit();
		
		if(torneioPartida.getPontos1() > torneioPartida.getPontos2()) {			
			timeDAO.addVitoria(torneioPartida.getIdTime1());		
			timeDAO.addDerrota(torneioPartida.getIdTime2());		
		}else {
			timeDAO.addVitoria(torneioPartida.getIdTime2());		
			timeDAO.addDerrota(torneioPartida.getIdTime1());					
		}
		
		
	}
	
	public void deleteTorneioPartida(Integer idTorneio) throws SQLException {
		this.delete()
		.from("torneio_partidas")		
		.where("id_torneio", "=", idTorneio.toString())
		.commit();
	}
}
