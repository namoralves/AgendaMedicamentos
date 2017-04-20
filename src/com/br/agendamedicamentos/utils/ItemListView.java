package com.br.agendamedicamentos.utils;

public class ItemListView {

	private Object texto;
	private Object tag;
	private Object iconeMedicamento;
	private Object dataInicial;
	private Object dataFinal;
	private Object qtdRestante;
	private Object qtdTomar;

	public ItemListView() {
	}

	public ItemListView(Object texto, Object iconeMedicamento, Object id,
			Object dataInicial, Object dataFinal, Object qtdRestante, Object qtdTomar) {
		this.texto = texto;
		this.iconeMedicamento = iconeMedicamento;
		this.tag = id;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.qtdRestante = qtdRestante;
		this.qtdTomar = qtdTomar;
	}

	public Object getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Object dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Object getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Object dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Object getIconeMedicamento() {
		return iconeMedicamento;
	}

	public void setIconeMedicamento(Object iconeMedicamento) {
		this.iconeMedicamento = iconeMedicamento;
	}

	public Object getTexto() {
		return texto;
	}

	public void setTexto(Object texto) {
		this.texto = texto;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Object getQtdRestante() {
		return qtdRestante;
	}

	public void setQtdRestante(Object qtdRestante) {
		this.qtdRestante = qtdRestante;
	}

	public Object getQtdTomar() {
		return qtdTomar;
	}

	public void setQtdTomar(Object qtdTomar) {
		this.qtdTomar = qtdTomar;
	}

}