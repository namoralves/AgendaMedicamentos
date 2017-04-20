package com.br.agendamedicamentos.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.agendamedicamentos.R;

public class AdapterListViewIcon extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<ItemListView> itens;

	public AdapterListViewIcon(Context context, ArrayList<ItemListView> itens) {
		this.itens = itens;
		this.mInflater = LayoutInflater.from(context);
	} 

	public int getCount() {
		return itens.size();
	}

	public ItemListView getItem(int position) {
		return itens.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		ItemListView item = itens.get(position);
		view = mInflater.inflate(R.layout.list_row_medicamentos, null);

		((TextView) view.findViewById(R.list_row_medicamento.nome)).setText(item.getTexto().toString());
		((ImageView) view.findViewById(R.list_row_medicamento.imagemview)).setImageResource((Integer)item.getIconeMedicamento());
		((TextView) view.findViewById(R.list_row_medicamento.dataInicial)).setText(item.getDataInicial().toString());
		((TextView) view.findViewById(R.list_row_medicamento.dataFinal)).setText(item.getDataFinal().toString());
		((TextView) view.findViewById(R.list_row_medicamento.textQtdRestante)).setText(String.valueOf(item.getQtdRestante().toString()));
		((TextView) view.findViewById(R.list_row_medicamento.textQtdTomar)).setText(String.valueOf(item.getQtdTomar().toString()));
		
		return view;
	}

}