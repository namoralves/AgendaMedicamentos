package com.br.agendamedicamentos;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.br.agendamedicamentos.dialogs.AboutDialog;
import com.br.agendamedicamentos.utils.Utils;

public class MainActivity extends Activity {

	private Button btnCadastre;
	private Button btnAgenda;
	private Button btnConfig;
	private Button btnExit;

	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ContextAgendaMedicamentos.getAppContext();
		initializeComponents();
	}

	public void start() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 10);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initializeComponents() {
		Utils.verificaUsuario(this);
		btnCadastre = (Button) findViewById(R.activity_main.btnCadastre);
		btnCadastre.setOnClickListener(onClickCadastre);

		btnAgenda = (Button) findViewById(R.activity_main.btnAgenda);
		btnAgenda.setOnClickListener(onClickAgenda);

		btnConfig = (Button) findViewById(R.activity_main.btnConfig);
		btnConfig.setText("Sobre");
		btnConfig.setOnClickListener(onClickConfig);

		btnExit = (Button) findViewById(R.activity_main.btnExit);
		btnExit.setOnClickListener(onClickExit);

	}

	private View.OnClickListener onClickCadastre = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(MainActivity.this,
					CadastroMedicamentoActivity.class));
		}
	};

	private View.OnClickListener onClickAgenda = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(MainActivity.this,
					ListaMedicamentosActivity.class));
		}
	};

	private View.OnClickListener onClickConfig = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent it = new Intent(MainActivity.this, AboutDialog.class);
			startActivity(it);
		}
	};

	private View.OnClickListener onClickExit = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onDestroy();
			System.exit(0);
		}
	};

}
