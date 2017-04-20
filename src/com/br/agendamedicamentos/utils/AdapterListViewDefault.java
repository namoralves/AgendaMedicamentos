package com.br.agendamedicamentos.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListViewDefault extends BaseAdapter implements
		OnItemClickListener {

	private LayoutInflater mInflater;
	private ArrayList<String> itens;
	private Context context;

	public AdapterListViewDefault(Context context, ArrayList<String> itens) {
		this.itens = itens;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itens.size();
	}

	public String getItem(int position) {
		return itens.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		String item = itens.get(position);
		view = mInflater.inflate(android.R.layout.simple_list_item_1, null);

		((TextView) view.findViewById(android.R.id.text1)).setText(item);
		((TextView) view.findViewById(android.R.id.text1)).setTextColor(Color.WHITE);
		
		return view;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

}