package com.br.agendamedicamentos.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.br.agendamedicamentos.ContextAgendaMedicamentos;
import com.br.agendamedicamentos.R;
import com.br.agendamedicamentos.bean.Usuario;
import com.br.agendamedicamentos.dao.UsuarioDAO;
import com.br.agendamedicamentos.dialogs.WelcomeDialog;

public class Utils {

	private static SoundPool soundP;
	private static Timer timer;
	private static Handler h;
	private static int MINUTE_IN_MILISECONDS = 1000 * 60;

	public static void ScreenChange(Context activity, Object o) {
		activity.startActivity(new Intent(activity, o.getClass()));
	}

	public static String formataData(int dia, int mes, int ano) {
		String diaString;
		String mesString;
		String anoString;

		mes++;

		if (dia < 10) {
			diaString = "0" + dia;
		} else {
			diaString = String.valueOf(dia);
		}

		if (mes < 10) {
			mesString = "0" + mes;
		} else {
			mesString = String.valueOf(mes);
		}

		if (ano < 10) {
			anoString = "0" + ano;
		} else {
			anoString = String.valueOf(ano);
		}

		return diaString + "/" + mesString + "/" + anoString;
	}

	public static void notificacao(Context context, Object goTo, int uId,
			String title, String msgBarStatus, String mensagem) {
		final String mensagemBarraStatus = msgBarStatus;
		final String titulo = title;

		NotificationManager nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

		Notification notificacao = new Notification(R.drawable.ic_launcher,
				mensagemBarraStatus, System.currentTimeMillis());

		Intent intentMensagem = new Intent(context, goTo.getClass());

		// Passar parâmetros
		Bundle b = new Bundle();
		b.putInt("idMed", uId);
		intentMensagem.putExtras(b);
		PendingIntent p = PendingIntent.getActivity(context, uId,
				intentMensagem, 0);

		notificacao.setLatestEventInfo(context, titulo, mensagem, p);

		notificacao.defaults = Notification.DEFAULT_VIBRATE;
		notificacao.ledARGB = Color.argb(100, 255, 255, 255);
		notificacao.ledOnMS = 500;
		notificacao.ledOffMS = 0;
		// notificacao.defaults |= Notification.DEFAULT_SOUND;
		notificacao.flags |= Notification.FLAG_SHOW_LIGHTS;
		notificacao.flags |= Notification.FLAG_AUTO_CANCEL;
		Log.d("utils",
				"Hora: " + new SimpleDateFormat("HH:mm").format(new Date()));
		nm.notify(uId, notificacao);
	}

	/**
	 * @author namor
	 * @param numero
	 * @return Valor int para boolean (se 1 = true, se não = false)
	 */
	public static boolean integer2Boolean(int numero) {
		if (numero == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author namor
	 * @param medidaUnidade
	 * @return Valor int da class R
	 */
	public static int selecionarUnidade(String medidaUnidade) {
		if ("COMPRIMIDO".equals(medidaUnidade)) {
			return R.drawable.comprimido;
		} else if ("DOSE".equals(medidaUnidade)) {
			return R.drawable.dose;
		} else {
			return R.drawable.injecao;
		}
	}

	/**
	 * @author namor
	 * @param context
	 * @return Verifica se existe usuário cadastrado no sistema
	 */
	public static void verificaUsuario(Context context) {
		UsuarioDAO usuarioDao = UsuarioDAO.getInstance(context);

		List<Usuario> listaUsuario = usuarioDao.selectAll();
		if (listaUsuario.size() < 1) {
			Intent it = new Intent(context, WelcomeDialog.class);
			context.startActivity(it);
		}

	}

	public static void playAlarme(Context context) {
		try {
			MediaPlayer mp = MediaPlayer.create(context, R.raw.alarm);
			mp.start();
			Toast.makeText(ContextAgendaMedicamentos.getAppContext(), "Tocando Som", Toast.LENGTH_SHORT).show();
			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
		} catch (Exception e) {
			Log.e("[Utils]", "Erro no Media Player " + e.getMessage());
		}
	}

	public static void playAlarme2(Context context) {
		try {
			soundP = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
			int soundIdAlarm = soundP.load(context, R.raw.alarm, 1);
			int streamIdSound = soundP.play(soundIdAlarm, 1f, 1f, 0, 3, 1f);
			Toast.makeText(context, "Tocando Som", Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
			Log.e("[Utils]", "Erro no Alarme2 " + ex.getMessage());
		}
	}

	public static void cancelSound() {
		if (soundP != null) {
			soundP.release();
			soundP = null;
		}
	}

}
