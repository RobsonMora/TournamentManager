package dao;

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
	
	public TimeModel getOneTime(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("times")
				.where("id", "=", id.toString())
				.apply();
		
		if(result.next()) {
			TimeModel time = new TimeModel();
			return time.setId(result.getInt("id"))
					.setNome(result.getString("nome"));
		}else {
			return null;
		}
	}

	public void createTime(TimeModel time) throws SQLException {
		this.insertInto("times", "nome")
		.values(quoteStr(time.getNome()))
		.commit();
	}

	public void updateTime(TimeModel time) throws SQLException {
		this.update("times")
		.setValue(
				" nome = "+ quoteStr(time.getNome()))
		.where("id", "=", time.getId().toString())
		.commit();
	}

	public void deleteTime(Integer id) throws SQLException {
		this.delete()
		.from("times")
		.where("id", "=", id.toString())
		.commit();
	}

}
