package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TimeModel;

public class TimeDAO extends BaseDAO{

	public TimeDAO(Connection conn) {
		super(conn);
	}

	public ArrayList<TimeModel> getAllTimes() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("times")
				.orderBy(" pontos desc, id ")
				.apply();
		ArrayList<TimeModel> timeList = new ArrayList<TimeModel>();
		File logo = null;
		while(result.next()) {	
			if (result.getInt("id") > 0) {
				logo = null;
				if(result.getBytes("logo") != null) {			
					FileOutputStream fos;
					logo = new File(System.getProperty("user.dir") + "\\images\\Logos\\"+result.getInt("id")+result.getString("nome"));
					try {
						fos = new FileOutputStream(logo);
						fos.write(result.getBytes("logo"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}			
				}

				timeList.add(new TimeModel()
						.setId(result.getInt("id"))
						.setNome(result.getString("nome"))
						.setDerrotas(result.getInt("derrotas"))
						.setVitorias(result.getInt("vitorias"))
						.setPontos(result.getInt("pontos"))
						.setLogo((logo!=null) ? new File(logo.getPath()) : null)
						);
			}

		}

		return timeList;
	}

	public TimeModel getOneTime(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("times")
				.where("id", "=", id.toString())
				.apply();

		if(result.next()) {
			TimeModel time = new TimeModel()
					.setId(result.getInt("id"))
					.setNome(result.getString("nome"))
					.setDerrotas(result.getInt("derrotas"))
					.setVitorias(result.getInt("vitorias"))
					.setPontos(result.getInt("pontos"));
			if(result.getBytes("logo") != null) {
				try {
					FileOutputStream fos;
					File logo = new File(System.getProperty("user.dir") + "\\images\\Logos\\"+time.getId()+time.getNome());
					fos = new FileOutputStream(logo);
					fos.write(result.getBytes("logo"));
					time.setLogo((logo!=null) ? new File(logo.getPath()) : null);
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return time;
		}else {
			return null;
		}
	}

	public void createTime(TimeModel time) throws SQLException {
		this.insertInto("times", ((time.getLogo()!=null)?"logo, ":"")+"nome, pontos")
		.values(((time.getLogo()!=null)?"? , ":"")+quoteStr(time.getNome())+", 1000")
		.commit(time.getLogo());
	}

	public void updateTime(TimeModel time) throws SQLException {
		this.update("times")
		.setValue(
				((time.getLogo()!=null)?"logo = ?, ":"")+"nome = "+ quoteStr(time.getNome()))
		.where("id", "=", time.getId().toString())
		.commit(time.getLogo());
	}

	public void addVitoria(Integer id) {
		this.update("times")
		.setValue(" vitorias = coalesce(vitorias, 0) + 1 ")
		.where("id", "=", id.toString())
		.commit();
		updatePontos(id, 10);		
	}

	public void addDerrota(Integer id) {
		this.update("times")
		.setValue(" derrotas = coalesce(derrotas, 0) + 1 ")
		.where("id", "=", id.toString())
		.commit();
		updatePontos(id, -10);		
	}

	//TODO Definir esquema de pontos
	//     Atualmente o pontos sao reduzidos e adicionados de forma fixa
	public void updatePontos(Integer id, Integer pontos) {
		this.update("times")
		.setValue(" pontos = coalesce(pontos, 0) + (" + pontos.toString() + ") ")
		.where("id", "=", id.toString())
		.commit();
	}

	public void deleteTime(Integer id) throws SQLException {
		this.delete()
		.from("times")
		.where("id", "=", id.toString())
		.commit();
	}

}
