package model;

public class JogoModel {
	private Integer id, idCategoria;
	private String nome;
	
	public JogoModel() {
		setId(0);
		setIdCategoria(0);
		setNome("");
	}
	
	public JogoModel(JogoModel jogo) {
		setId(jogo.getId());
		setIdCategoria(jogo.getIdCategoria());
		setNome(jogo.getNome());
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public JogoModel setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
		return this;
	}

	public JogoModel setId(int id) {
		this.id = id;
		return this;
	}

	public JogoModel setNome(String nome) {
		this.nome = nome;
		return this;
	}
	
	
}
