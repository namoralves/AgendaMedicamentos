package com.br.agendamedicamentos.bean;

import java.sql.Timestamp;

public class Alarme {

	private int id;
	private int idMedicamento;
	private String ultimoTomado;
	private boolean ativo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMedicamento() {
		return idMedicamento;
	}

	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public String getUltimoTomado() {
		return ultimoTomado;
	}

	public void setUltimoTomado(String ultimoTomado) {
		this.ultimoTomado = ultimoTomado;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
