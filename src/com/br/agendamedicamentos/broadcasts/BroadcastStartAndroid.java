package com.br.agendamedicamentos.broadcasts;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.agendamedicamentos.utils.AlarmesAndroid;

public class BroadcastStartAndroid extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("broadcast",
				"Hora: " + new SimpleDateFormat("HH:mm").format(new Date()));
		AlarmesAndroid alarmesAndroid = new AlarmesAndroid(context);
		alarmesAndroid.agendarAlarmesLogica();
	}

}
