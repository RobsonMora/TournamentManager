package model;

import java.io.File;

public class TimeModel {
	
	private Integer id, vitorias, derrotas, pontos;
	private String nome;
	private File logo;
	
	public TimeModel() {
		id = 0;
		nome = "";
		logo = null;
		vitorias = 0;
		derrotas = 0;
		pontos = 1000;
	}
		
	public TimeModel(TimeModel time) {
		id = time.getId();
		nome = time.getNome();
		vitorias = time.getVitorias();
		derrotas = time.getDerrotas();
		pontos = time.getPontos();		
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
	
	public Integer getVitorias() {
		return vitorias;
	}

	public TimeModel setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
		return this;
	}

	public Integer getDerrotas() {
		return derrotas;
	}

	public TimeModel setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
		return this;
	}

	public Integer getPontos() {
		return pontos;
	}

	public TimeModel setPontos(Integer pontos) {
		this.pontos = pontos;
		return this;
	}


}
