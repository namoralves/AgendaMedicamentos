package com.br.agendamedicamentos.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.br.agendamedicamentos.R;

public class AboutDialog extends Activity {

	private Button btnContinuar;
	private TextView textSobre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		initComponents();
	}

	private void initComponents() {
		textSobre = (TextView) findViewById(R.welcome.textMensagem);
		textSobre
				.setText("O aplicativo Agenda de Medicamentos foi desenvolvido para que auxiliar você a gerenciar "
						+ "melhor seus medicamentos, ajudando-o você a se lembrar de tomar os medicamsntos"
						+ " e facilitando a gerência de tempo, quantidade e tipo de remédios a tomar!");

		btnContinuar = (Button) findViewById(R.welcome.continuar);
		btnContinuar.setText("Fechar");
		btnContinuar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
