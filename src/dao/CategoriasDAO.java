package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CategoriaModel;

public class CategoriasDAO extends BaseDAO{

	public CategoriasDAO(Connection conn) {
		super(conn);
	}
	
	public ArrayList<CategoriaModel> getAllCategorias() throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("categorias")
				.orderBy("id")
				.apply();
		ArrayList<CategoriaModel> categoriaList = new ArrayList<CategoriaModel>();
		while(result.next()) {
			categoriaList.add(new CategoriaModel()
							.setId(result.getInt("id"))
							.setNome(result.getString("nome"))
							);
		}
		return categoriaList;
	}

	public CategoriaModel getOneCategoria(Integer id) throws SQLException {
		ResultSet result = null;
		result = this.select("*")
				.from("categorias")
				.where("id", "=", id.toString())
				.apply();
		
		if(result.next()) {
			CategoriaModel categoria = new CategoriaModel();
			return categoria.setId(result.getInt("id"))
					.setNome(result.getString("nome"));
		}else {
			return null;
		}
	}

	public void createCategoria(CategoriaModel categoria) throws SQLException {
		this.insertInto("categorias", "nome")
		.values(quoteStr(categoria.getNome()))
		.commit();
	}

	public void updateCategoria(CategoriaModel categoria) throws SQLException {
		this.update("categorias")
		.setValue(
				"  nome = "+ quoteStr(categoria.getNome()))
		.where("id", "=", categoria.getId().toString())
		.commit();
	}

	public void deleteCategoria(Integer id) throws SQLException {
		this.delete()
		.from("categorias")
		.where("id", "=", id.toString())
		.commit();
	}
	
}
