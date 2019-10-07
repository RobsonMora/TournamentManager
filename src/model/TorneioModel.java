package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TorneioModel{
	
	private int id; 
	private String nome;
	private Date inicio;
	private Date fim;
	
	public TorneioModel() {
				
	}
	
	
	public int getId() {
		return id;
	}

	public TorneioModel setId(int id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public TorneioModel setNome(String nome) {
		this.nome = nome;
		return this;
	}
	
	public Date getInicioDate() {
		return inicio;
	}
	
	public String getInicio() {
		return new SimpleDateFormat("dd/MM/yyyy").format(inicio);
	}	

	public TorneioModel setInicio(Date inicio) {
		this.inicio = inicio;
		return this;
	}	
	
	public Date getFimDate() {
		return fim;
	}
	
	public String getFim() {
		return new SimpleDateFormat("dd/MM/yyyy").format(fim);
	}
	
	public TorneioModel setFim(Date fim) {
		this.fim = fim;
		return this;
	}

}
