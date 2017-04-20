package com.br.agendamedicamentos.utils;

import android.content.Context;
import android.os.Handler;

import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.dialogs.ActivityDialog;

public class HandlerAdiar {

	private Handler handler;
	private Context context;
	private Medicamento medicamento;
	private long timerSchedule = 1000 * 30; // 30 seconds

	private Runnable adiarTimer = new Runnable() {
		public void run() {
			Utils.notificacao(context, new ActivityDialog(),
					medicamento.getId(), medicamento.getNome(),
					"Hora do remédio " + medicamento.getNome(),
					"Hora do remédio " + medicamento.getNome());
			Utils.playAlarme(context);
			handler.postDelayed(adiarTimer, timerSchedule);
		}
	};

	public void adiarMedicamento(Medicamento med, Context context) {
		this.medicamento = med;
		this.context = context;
		handler = new Handler();
		handler.postDelayed(adiarTimer, timerSchedule);
	}// end method

	public void pararSchedule() {
		if (handler != null) { 
			handler.removeCallbacks(adiarTimer);
		}
		// handler.removeCallbacks(Disabler);
	}// end method

}
