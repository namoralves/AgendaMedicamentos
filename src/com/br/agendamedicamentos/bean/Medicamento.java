package com.br.agendamedicamentos.bean;

public class Medicamento {

	private int id;
	private Usuario usuario;
	private String nome;
	private int quantidadeCartelaFrasco;
	private int periodoHoras;
	private int quantidadeTomar;
	private String dataInicial;
	private String dataFinal;
	private String medidaUnidade;
	private Alarme alarme;
	private boolean ativo;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Alarme getAlarme() {
		return alarme;
	}

	public void setAlarme(Alarme alarme) {
		this.alarme = alarme;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeCartelaFrasco() {
		return quantidadeCartelaFrasco;
	}

	public void setQuantidadeCartelaFrasco(int quantidadeCartelaFrasco) {
		this.quantidadeCartelaFrasco = quantidadeCartelaFrasco;
	}

	public int getPeriodoHoras() {
		return periodoHoras;
	}

	public void setPeriodoHoras(int periodoHoras) {
		this.periodoHoras = periodoHoras;
	}

	public int getQuantidadeTomar() {
		return quantidadeTomar;
	}

	public void setQuantidadeTomar(int quantidadeTomar) {
		this.quantidadeTomar = quantidadeTomar;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getMedidaUnidade() {
		return medidaUnidade;
	}

	public void setMedidaUnidade(String medidaUnidade) {
		this.medidaUnidade = medidaUnidade;
	}

}
