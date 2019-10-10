package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.JogoModel;

public class JogoDAO extends BaseDAO {

	public JogoDAO(Connection conn) {
		super(conn);
	}
	
	public ArrayList<JogoModel> getAllJogos() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("jogos")
				.apply();
		ArrayList<JogoModel> jogoList = new ArrayList<JogoModel>();
		while(result.next()) {
			jogoList.add(new JogoModel()
							.setId(result.getInt("id"))
							.setNome(result.getString("nome"))
							.setIdCategoria(result.getInt("id_categoria"))
							);
		}
		return jogoList;
	}

	public JogoModel getOneJogo(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("jogos")
				.where("id", "=", id.toString())
				.apply();
		
		if(result.next()) {
			JogoModel jogo = new JogoModel();
			return jogo.setId(result.getInt("id"))
					.setNome(result.getString("nome"))
					.setIdCategoria(result.getInt("id_categoria"));
		}else {
			return null;
		}
	}

	public void createJogo(JogoModel jogo) throws SQLException {
		this.insertInto("jogos", "nome, id_categoria")
		.values(quoteStr(jogo.getNome()) + ", "+
				jogo.getIdCategoria())
		.commit();
	}

	public void updateJogo(JogoModel jogo) throws SQLException {
		this.update("jogos")
		.setValue(
				"  nome = "+ quoteStr(jogo.getNome()) +
				", id_categoria = "+ jogo.getIdCategoria()
				)
		.where("id", "=", jogo.getId().toString())
		.commit();
	}

	public void deleteTime(Integer id) throws SQLException {
		this.delete()
		.from("jogos")
		.where("id", "=", id.toString())
		.commit();
	}
	
}
