package com.br.agendamedicamentos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.dao.MedicamentosDAO;
import com.br.agendamedicamentos.utils.AdapterListViewIcon;
import com.br.agendamedicamentos.utils.ItemListView;
import com.br.agendamedicamentos.utils.Utils;

public class ListaMedicamentosActivity extends Activity {

	private ListView listView;
	private AdapterListViewIcon adapterListView;
	private ArrayList<ItemListView> itens;

	private MedicamentosDAO medicamentoDao;

	private List<Medicamento> listMedicamento = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_medicamentos);
		Log.i("alarm", "onCreate");
		Utils.verificaUsuario(this);
		// [TODO] Teste tirar daqui
		createListView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// [TODO] Verificar no cliclo de vida da activity, ERRO: chamando 3
		// vezes select * medicamentos
		Log.i("alarm", "onStart");
		createListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// [TODO] Verificar no cliclo de vida da activity, ERRO: chamando 3
		// vezes select * medicamentos
		Log.i("alarm", "onResume");
		createListView();
	};

	private void createListView() {
		sendBroadcast(new Intent("BOOT_COMPLETED"));
		if (itens == null || itens.isEmpty()) {
			itens = new ArrayList<ItemListView>();
			medicamentoDao = MedicamentosDAO.getInstance(this);
			Log.i("alarm", "antes");
			listMedicamento = medicamentoDao.selectAll();
			Log.i("alarm", "depois");

			for (Medicamento medicamento : listMedicamento) {
				ItemListView item1 = new ItemListView(
						medicamento.getNome(),
						Utils.selecionarUnidade(medicamento.getMedidaUnidade()),
						medicamento.getId(), medicamento.getDataInicial(),
						medicamento.getDataFinal(), "Restante: "
								+ medicamento.getQuantidadeCartelaFrasco(),
						"Qt. Tomar: " + medicamento.getQuantidadeTomar());
				itens.add(item1);
			}

			adapterListView = new AdapterListViewIcon(this, itens);
		}
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapterListView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				LinearLayout objectLayout = (LinearLayout) parent
						.getChildAt(position);
				objectLayout = (LinearLayout) objectLayout.getChildAt(0);
				// Passa para a intent do cadastro
				Intent i = new Intent(getApplicationContext(),
						CadastroMedicamentoActivity.class);
				i.putExtra("idMed", listMedicamento.get(position).getId());
				startActivity(i);
			}

		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> param, View view,
					final int position, long id) {
				CharSequence[] options = { "Editar Medicamento",
						"Excluir Medicamento" };

				AlertDialog.Builder alert = new AlertDialog.Builder(
						ListaMedicamentosActivity.this);
				alert.setItems(options, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							// Editar
							Intent i = new Intent(getApplicationContext(),
									CadastroMedicamentoActivity.class);
							i.putExtra("idMed", listMedicamento.get(position)
									.getId());
							startActivity(i);
							break;

						case 1:
							// Excluir
							itens.remove(position);
							medicamentoDao.delete(listMedicamento.get(position)
									.getId());
							adapterListView.notifyDataSetChanged();

							// Verificar os medicamentos existentes
							for (Medicamento med : medicamentoDao.selectAll()) {
								Log.i("alarm", medicamentoDao.selectAll()
										.toString());
								Log.i("alarm",
										med.getId() + ", " + med.getNome());
							}
							break;
						}
					}
				});
				alert.show();

				return false;
			}
		});
	}

	@Override
	protected void onPause() {
		Log.i("alarm", "onPause");
		itens.removeAll(itens);
		super.onPause();
	}
}
