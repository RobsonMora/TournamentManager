package model;

import java.io.File;

public class TimeModel {
	
	private Integer id;
	private String nome;
	private File logo;
	
	public TimeModel() {
		id = 0;
		nome = "";
		logo = null;
	}
	
	public TimeModel(TimeModel time) {
		id = time.getId();
		nome = time.getNome();
		if(time.getLogo()!=null) {
			logo = new File(time.getLogo().getPath());
		}
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
	
	public File getLogo() {
		return logo;
	}

	public TimeModel setLogo(File logo) {
		this.logo = logo;
		return this;
	}

}
