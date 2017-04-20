package com.br.agendamedicamentos.utils;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.agendamedicamentos.R;

public class ItemListAdicionar extends BaseAdapter {

	private List<String> list;
	private Activity activity;

	public ItemListAdicionar(Activity activity, List<String> list) {
		super();
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	public void remove(int position) {
		this.list.remove(position);
	}

	public void add(String texto) {
		this.list.add(texto);
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Declaro um objeto do tipo ListRow
		ListRow listRow;

		// Crio um Inflater para poder adicionar a lista na activity
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.list_row.text, null);

			listRow = new ListRow();

			listRow.texto = (TextView) convertView
					.findViewById(R.list_row.text);

			convertView.setTag(listRow);
		} else {
			listRow = (ListRow) convertView.getTag();
		}

		listRow.texto.setText(list.get(position));

		return convertView;
	}

	private class ListRow {
		TextView texto;
	}

}
