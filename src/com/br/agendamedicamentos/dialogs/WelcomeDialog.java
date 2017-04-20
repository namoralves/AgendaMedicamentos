package com.br.agendamedicamentos.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.br.agendamedicamentos.R;

public class WelcomeDialog extends Activity {

	private Button btnContinuar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		initComponents();
	}

	private void initComponents() {
		btnContinuar = (Button) findViewById(R.welcome.continuar);

		btnContinuar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(WelcomeDialog.this,
						CadastroUsuario.class);
				startActivity(it);
				finish();
			}
		});
	}

}
