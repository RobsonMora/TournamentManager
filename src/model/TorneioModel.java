package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TorneioModel{
	
	private Integer id, idJogo; 
	private String nome, observacao;
	
	private Date inicio;
	private Date fim;
	
	public TorneioModel() {
		setId(0);
		setIdJogo(0);
		setNome("");
		setObservacao("");
	}
	
	public TorneioModel(TorneioModel torneio) {
		setId(torneio.getId());
		setIdJogo(torneio.getIdJogo());
		setNome(torneio.getNome());
		setObservacao(torneio.getObservacao());
	}
	
	public Integer getId() {
		return id;
	}

	public TorneioModel setId(Integer id) {
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
	
	public Integer getIdJogo() {
		return idJogo;
	}

	public TorneioModel setIdJogo(Integer idJogo) {
		this.idJogo = idJogo;
		return this;
	}

	public String getObservacao() {
		return observacao;
	}

	public TorneioModel setObservacao(String observacao) {
		this.observacao = observacao;
		return this;
	}


}
