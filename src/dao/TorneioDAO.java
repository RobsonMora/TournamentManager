package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.TorneioModel;

public class TorneioDAO extends BaseDAO {

	public ArrayList<TorneioModel> getAllTorneios() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("alunos")
				.apply();
		ArrayList<TorneioModel> torneioList = new ArrayList<TorneioModel>();
		while(result.next()) {
			torneioList.add(new TorneioModel()
							.setId(result.getInt("id"))
							.setNome(result.getString("nome"))
							.setInicio(result.getDate("inicio"))
							.setFim(result.getDate("fim"))							
							);
		}
		return torneioList;
	}

	public TorneioModel getOneAluno(Integer id) throws SQLException {
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
					.setFim(result.getDate("fim"))	;
		}else {
			return null;
		}
	}

	public void createAluno(TorneioModel torneio) throws SQLException {
		String fields = " nome "+ ((torneio.getInicioDate().compareTo(new Date(0))==0)? "" : ", inicio ")+ ((torneio.getFimDate().compareTo(new Date(0))==0)? "" : ", fim ");
		this.insertInto("torneios", fields)
		.values("'"+torneio.getNome()+
				((torneio.getInicioDate().compareTo(new Date(0))==0)? "" : ("','" + torneio.getInicioDate()))+
				((torneio.getFimDate().compareTo(new Date(0))==0)? "" : ("','" + torneio.getFimDate()))
				)
		.commit();
	}

	public void updateAluno(TorneioModel torneio) throws SQLException {
		this.update("torneios")
		.setValue(
				"nome = '"+torneio.getNome()+"' "+
						((torneio.getInicioDate().compareTo(new Date(0))==0)? "' '" : ("', inicio = '"+torneio.getInicioDate()+"' "))+
						((torneio.getFimDate().compareTo(new Date(0))==0)? "' '" : ("', fim = '"+torneio.getFimDate()+"' "))
				)
		.where("id", "=", Integer.toString((torneio.getId())))
		.commit();
	}

	public void deleteAluno(Integer id) throws SQLException {
		this.delete()
		.from("torneios")
		.where("id", "=", id.toString())
		.commit();
	}

}
