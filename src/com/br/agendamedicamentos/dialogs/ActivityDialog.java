package com.br.agendamedicamentos.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.agendamedicamentos.R;
import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.dao.AlarmeDAO;
import com.br.agendamedicamentos.dao.MedicamentosDAO;
import com.br.agendamedicamentos.utils.HandlerAdiar;
import com.br.agendamedicamentos.utils.Utils;

public class ActivityDialog extends Activity {

	private TextView textNomePaciente;
	private Button btnTomar;
	private TextView textNome;
	private Button btnAdiar;
	private TextView dataHora;
	private ImageView imgMed;
	private MedicamentosDAO medDao;
	private AlarmeDAO alarmeDao;
	private HandlerAdiar hAdiar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_notification);

		medDao = MedicamentosDAO.getInstance(getBaseContext());
		alarmeDao = AlarmeDAO.getInstance(getBaseContext());
		hAdiar = new HandlerAdiar();

		textNomePaciente = (TextView) findViewById(R.dialog_notification.paciente);
		btnTomar = (Button) findViewById(R.dialog_notification.btn_tomar);
		textNome = (TextView) findViewById(R.dialog_notification.nome_medicamento);
		btnAdiar = (Button) findViewById(R.dialog_notification.btn_adiar);
		dataHora = (TextView) findViewById(R.dialog_notification.data_hora);
		imgMed = (ImageView) findViewById(R.dialog_notification.img);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			int id = b.getInt("idMed");
			createDialog(id);
		}
	}

	private void createDialog(int idMed) {

		final Medicamento med = medDao.select(idMed);
		textNomePaciente.setText(med.getUsuario().getNome());
		dataHora.setText(med.getDataInicial());
		imgMed.setImageResource(Utils.selecionarUnidade(med.getMedidaUnidade()));
		textNome.setText(med.getNome());

		btnTomar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				medDao.updateQuantidade(med);
				alarmeDao.updateUltimoTomado(med);
				Utils.cancelSound();
				hAdiar.pararSchedule();
				finish();
			}
		});

		btnAdiar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				Utils.cancelSound();
				hAdiar.adiarMedicamento(med, getApplicationContext());
			}
		});
	}
}
