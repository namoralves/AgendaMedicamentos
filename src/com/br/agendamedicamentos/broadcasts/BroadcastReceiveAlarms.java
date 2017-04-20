package com.br.agendamedicamentos.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.dao.MedicamentosDAO;
import com.br.agendamedicamentos.dialogs.ActivityDialog;
import com.br.agendamedicamentos.utils.Utils;

public class BroadcastReceiveAlarms extends BroadcastReceiver {

	private MedicamentosDAO medicamentoDao;

	@Override
	public void onReceive(Context context, Intent intent) {
		medicamentoDao = MedicamentosDAO.getInstance(context);

		int id = intent.getIntExtra("idMed", intent.getExtras().getInt("id"));
		if (id != 0) {
			Medicamento medicamento = medicamentoDao.select(id);
			Log.i("alarm", "depois da query: " + medicamento.getNome());
			Utils.playAlarme(context);
			Utils.notificacao(context, new ActivityDialog(),
					medicamento.getId(), medicamento.getNome(),
					"Hora do remédio " + medicamento.getNome(),
					"Hora do remédio " + medicamento.getNome());
		}
	}
}
