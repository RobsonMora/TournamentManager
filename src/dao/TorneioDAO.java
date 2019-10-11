package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.TorneioModel;

public class TorneioDAO extends BaseDAO {

	public TorneioDAO(Connection conn) {
		super(conn);
	}

	public ArrayList<TorneioModel> getAllTorneios() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneios")
				.apply();
		ArrayList<TorneioModel> torneioList = new ArrayList<TorneioModel>();
		while(result.next()) {
			torneioList.add(new TorneioModel()
							.setId(result.getInt("id"))
							.setNome(result.getString("nome"))
							.setInicio(result.getDate("inicio"))
							.setFim(result.getDate("fim"))		
							.setIdJogo(result.getInt("id_jogo"))
							.setObservacao(result.getString("observacao"))
							);
		}
		return torneioList;
	}

	public TorneioModel getOneTorneio(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("torneios")
				.where("id", "=", id.toString())
				.apply();
		
		if(result.next()) {
			TorneioModel torneio = new TorneioModel();
			return torneio.setId(result.getInt("id"))
					.setNome(result.getString("nome"))
					.setInicio(result.getDate("inicio"))
					.setFim(result.getDate("fim"))	
					.setIdJogo(result.getInt("id_jogo"))
					.setObservacao(result.getString("observacao"));
		}else {
			return null;
		}
	}

	public void createTorneio(TorneioModel torneio) throws SQLException {
		String fields = " nome, id_jogo, observacao "+ ((torneio.getInicioDate().compareTo(new Date(0))==0)? "" : ", inicio ")+ ((torneio.getFimDate().compareTo(new Date(0))==0)? "" : ", fim ");
		this.insertInto("torneios", fields)
		.values(quoteStr(torneio.getNome())+", "+
				torneio.getIdJogo()+", "+
				quoteStr(torneio.getObservacao()) + 			
				((torneio.getInicioDate().compareTo(new Date(0))==0)? "" : ("," + quoteStr(torneio.getInicio())))+
				((torneio.getFimDate().compareTo(new Date(0))==0)? "" : ("," + quoteStr(torneio.getFim())))
				)
		.commit();
	}

	public void updateTorneio(TorneioModel torneio) throws SQLException {
		this.update("torneios")
		.setValue(
				" nome = " + quoteStr(torneio.getNome())+
				" id_jogo = " + torneio.getIdJogo().toString()+
				" observacao = " + quoteStr(torneio.getObservacao())+ 
						((torneio.getInicioDate().compareTo(new Date(0))==0)? "" : (" , inicio = '"+torneio.getInicioDate()+"' "))+
						((torneio.getFimDate().compareTo(new Date(0))==0)? "" : (" , fim = '"+torneio.getFimDate()+"' "))
				)
		.where("id", "=", Integer.toString((torneio.getId())))
		.commit();
	}

	public void deleteTorneio(Integer id) throws SQLException {
		this.delete()
		.from("torneios")
		.where("id", "=", id.toString())
		.commit();
	}

}
