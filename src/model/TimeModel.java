package model;

public class TimeModel {
	
	private Integer id;
	private String nome;
	
	public TimeModel() {
		id = 0;
		nome = "";
	}
	
	public TimeModel(TimeModel time) {
		id = time.getId();
		nome = time.getNome();
	}
	
	
	public Integer getId() {
		return id;
	}
	public TimeModel setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getNome() {
		return nome;
	}
	public TimeModel setNome(String nome) {
		this.nome = nome;
		return this;
	}

}
