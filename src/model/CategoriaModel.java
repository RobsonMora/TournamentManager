package model;

public class CategoriaModel {
	
	private Integer id;
	private String nome;
	
	public CategoriaModel() {
		setId(0);
		setNome("");
	}
	
	public CategoriaModel(CategoriaModel categoria) {
		setId(categoria.getId());
		setNome(categoria.getNome());
	}

	public Integer getId() {
		return id;
	}

	public CategoriaModel setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public CategoriaModel setNome(String nome) {
		this.nome = nome;
		return this;
	}	

}
