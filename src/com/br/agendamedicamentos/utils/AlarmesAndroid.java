package com.br.agendamedicamentos.utils;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.broadcasts.BroadcastReceiveAlarms;
import com.br.agendamedicamentos.dao.MedicamentosDAO;

public class AlarmesAndroid {

	private Context context;
	private int HORA_MILISEGUNDOS = 3600000;
	private int MINUTOS_MILISEGUNDOS = 60000;

	public AlarmesAndroid(Context context) {
		this.context = context;
	}

	/**
	 * @author namor Lógica do agendamento dos alarmes só é executado no
	 *         BOOT_COMPLETED
	 */
	public void agendarAlarmesLogica() {
		MedicamentosDAO medDao = MedicamentosDAO.getInstance(context);
		List<Medicamento> listMed = medDao.selectAll();

		for (int i = 0; i < listMed.size(); i++) {
			Medicamento med = listMed.get(i);
			if (med.isAtivo()) {

				DateTime ha = new DateTime();
				DateTimeFormatter formatter = DateTimeFormat
						.forPattern("dd/MM/yyyy HH:mm:ss");
				DateTime ht = formatter.parseDateTime(med.getAlarme()
						.getUltimoTomado());

//				Hours horasEntre = Hours.hoursBetween(ht, ha);
//				Minutes minutosEntre = Minutes.minutesBetween(ht, ha);
				Seconds segundosEntre = Seconds.secondsBetween(ht, ha);
//				int horas = horasEntre.getHours();
//				int minutos = minutosEntre.getMinutes();
				int segundos = segundosEntre.getSeconds();
				Log.d("teste", "segundos: " + String.valueOf(segundos));
				Log.d("teste", "med.periodo: " + med.getPeriodoHoras());
				Log.d("teste", "divisão: " + segundos / med.getPeriodoHoras());
				Log.d("teste", "UT: "+med.getAlarme().getUltimoTomado());
				int resto = (segundos % med.getPeriodoHoras());
				Log.d("teste", "resto: " + resto);
				int resultado;
				if (resto >= 0) {
					Log.d("teste", "if");
					resultado = med.getPeriodoHoras() - resto;
				} else {
					Log.d("teste", "else");
					// caso menor que 0 (zero)
					resultado = resto * -1;
				}

				Log.d("teste", "resultado: " + resultado);

				// Só depois de tudo
				startAlarm(med, resultado);
			}
			med = null;
		}
	}

	private void startAlarm(Medicamento med, int firstAlarm) {
		// ID do Alarm Manager
		Bundle bundle = new Bundle();
		bundle.putInt("id", med.getId());
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("dd/MM/yyyy HH:mm:ss");
		Log.d("teste", "entrou no alarm");
		Log.d("teste", "l: "+med.getAlarme().getUltimoTomado());
		DateTime dt = formatter.parseDateTime(med.getAlarme().getUltimoTomado());
		dt = dt.plusHours(firstAlarm);
		Long miliSec = dt.getMillis();
		Log.d("teste", "final Joda: " + dt.getMillis());
		Log.d("teste", "data no BD: "+med.getAlarme().getUltimoTomado());
		Log.d("teste", "dt agendada: "+formatter.print(dt));

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, miliSec,
				med.getPeriodoHoras() * HORA_MILISEGUNDOS, // Horas
				getPendingIntent(bundle, med.getId()));
	}

	private PendingIntent getPendingIntent(Bundle bundle, int id) {
		Intent it = new Intent(context, BroadcastReceiveAlarms.class);
		it.putExtras(bundle);
		return PendingIntent.getBroadcast(context, id, it,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
