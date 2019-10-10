package model;

public class TorneioPartidaModel {
	
	private Integer id, idTorneio, idTime1, idTime2, pontos1, pontos2, fase;

	public Integer getId() {
		return id;
	}

	public TorneioPartidaModel setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getIdTorneio() {
		return idTorneio;
	}

	public TorneioPartidaModel setIdTorneio(Integer idTorneio) {
		this.idTorneio = idTorneio;
		return this;
	}

	public Integer getIdTime1() {
		return idTime1;
	}

	public TorneioPartidaModel setIdTime1(Integer idTime1) {
		this.idTime1 = idTime1;
		return this;
	}

	public Integer getIdTime2() {
		return idTime2;
	}

	public TorneioPartidaModel setIdTime2(Integer idTime2) {
		this.idTime2 = idTime2;
		return this;
	}

	public Integer getPontos1() {
		return pontos1;
	}

	public TorneioPartidaModel setPontos1(Integer pontos1) {
		this.pontos1 = pontos1;
		return this;
	}

	public Integer getPontos2() {
		return pontos2;
	}

	public TorneioPartidaModel setPontos2(Integer pontos2) {
		this.pontos2 = pontos2;
		return this;
	}

	public Integer getFase() {
		return fase;
	}

	public TorneioPartidaModel setFase(Integer fase) {
		this.fase = fase;
		return this;
	}

}
