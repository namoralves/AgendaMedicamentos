package com.br.agendamedicamentos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.br.agendamedicamentos.bean.Alarme;
import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.bean.Usuario;
import com.br.agendamedicamentos.broadcasts.BroadcastReceiveAlarms;
import com.br.agendamedicamentos.dao.AlarmeDAO;
import com.br.agendamedicamentos.dao.MedicamentosDAO;
import com.br.agendamedicamentos.dao.UsuarioDAO;
import com.br.agendamedicamentos.utils.Utils;

public class CadastroMedicamentoActivity extends Activity {

	private static final int DATE_DIALOG_INICIAL = 0;
	private static final int DATE_DIALOG_FINAL = 1;

	private int HORA_MILISEGUNDOS = 3600000; // 60 * 60 * 1000
	private int MINUTOS_MILISEGUNDOS = 60000;

	private Button btnCadastrar;
	private Button btnCancelar;

	private EditText txtNome;
	private EditText txtQuantidadeCartelaFrasco;
	private EditText txtPeriodoHoras;
	private EditText txtQuantidadeTomar;
	private Button btnDataInicial;
	private Button btnDataFinal;

	private Spinner spnMedidaUnidade;

	private MedicamentosDAO medicamentoDao;
	private AlarmeDAO alarmeDao;

	private int parametro = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastre);
		medicamentoDao = MedicamentosDAO.getInstance(this);
		alarmeDao = AlarmeDAO.getInstance(this);
		initializeComponents();
	}

	private void initializeComponents() {
		Utils.verificaUsuario(this);
		txtNome = (EditText) findViewById(R.cadastre.txtNome);
		txtQuantidadeCartelaFrasco = (EditText) findViewById(R.cadastre.txtQuantidadeCartelaFrasco);
		txtPeriodoHoras = (EditText) findViewById(R.cadastre.txtPeriodoHoras);
		txtQuantidadeTomar = (EditText) findViewById(R.cadastre.txtQuantidadeTomar);

		ArrayAdapter<CharSequence> arrayTipoMedicamento = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		arrayTipoMedicamento.add("COMPRIMIDO");
		arrayTipoMedicamento.add("DOSE");
		arrayTipoMedicamento.add("INJEÇÃO");

		spnMedidaUnidade = (Spinner) findViewById(R.cadastre.spnMedidaUnidade);
		spnMedidaUnidade.setAdapter(arrayTipoMedicamento);

		btnDataInicial = (Button) findViewById(R.cadastre.btnDataInicial);
		btnDataInicial.setOnClickListener(onClickDataInicial);

		btnDataFinal = (Button) findViewById(R.cadastre.btnDataFinal);
		btnDataFinal.setOnClickListener(onClickDataFinal);

		btnCadastrar = (Button) findViewById(R.cadastre.btnCadastrar);
		btnCadastrar.setOnClickListener(onClickCadastrar);

		btnCancelar = (Button) findViewById(R.cadastre.btnCancelar);
		btnCancelar.setOnClickListener(onClickCancelar);

		// Recebe os parâmetros passados
		Bundle b = getIntent().getExtras();
		if (b != null) {
			parametro = b.getInt("idMed");

			Medicamento m = medicamentoDao.select(parametro);
			txtNome.setText(m.getNome());
			txtQuantidadeCartelaFrasco.setText(String.valueOf(m
					.getQuantidadeCartelaFrasco()));
			txtPeriodoHoras.setText(String.valueOf(m.getPeriodoHoras()));
			txtQuantidadeTomar.setText(String.valueOf(m.getQuantidadeTomar()));
			btnDataInicial.setText(String.valueOf(m.getDataInicial()));
			btnDataFinal.setText(String.valueOf(m.getDataFinal()));
			spnMedidaUnidade.setSelection(selecionarUnidade(m
					.getMedidaUnidade()));
			btnCadastrar.setOnClickListener(onClickEditar);
			btnCadastrar.setText("Atualizar");
		}
	}

	private int selecionarUnidade(String medidaUnidade) {
		if ("COMPRIMIDO".equals(medidaUnidade)) {
			return 0;
		} else if ("DOSE".equals(medidaUnidade)) {
			return 1;
		} else {
			return 2;
		}
	}

	private View.OnClickListener onClickDataInicial = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(DATE_DIALOG_INICIAL);
		}
	};

	private View.OnClickListener onClickDataFinal = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(DATE_DIALOG_FINAL);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar calendario = Calendar.getInstance();

		int ano = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case DATE_DIALOG_INICIAL:
			return new DatePickerDialog(this, listenerDateInicial, ano, mes,
					dia);
		case DATE_DIALOG_FINAL:
			return new DatePickerDialog(this, listenerDateFinal, ano, mes, dia);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener listenerDateInicial = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String data = Utils.formataData(dayOfMonth, monthOfYear, year);
			btnDataInicial.setText(data);
		}
	};

	private DatePickerDialog.OnDateSetListener listenerDateFinal = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String data = Utils.formataData(dayOfMonth, monthOfYear, year);
			btnDataFinal.setText(data);
		}
	};

	private View.OnClickListener onClickCadastrar = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!verificaNulos()) {
				Medicamento m = new Medicamento();
				m.setNome(txtNome.getText().toString());
				m.setQuantidadeCartelaFrasco(Integer
						.parseInt(txtQuantidadeCartelaFrasco.getText()
								.toString()));
				m.setPeriodoHoras(Integer.parseInt(txtPeriodoHoras.getText()
						.toString()));
				m.setQuantidadeTomar(Integer.parseInt(txtQuantidadeTomar
						.getText().toString()));
				m.setMedidaUnidade(spnMedidaUnidade.getSelectedItem()
						.toString());
				m.setDataInicial(btnDataInicial.getText().toString());
				m.setDataFinal(btnDataFinal.getText().toString());
				m.setAtivo(true);
				Usuario usuario = UsuarioDAO.getInstance(
						getApplicationContext()).select(1);
				m.setUsuario(usuario);

				int id = medicamentoDao.insert(m);
				m.setId(id);

				Alarme alarme = new Alarme();
				alarme.setAtivo(true);
				alarme.setIdMedicamento(id);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				String data = sdf.format(new Date());
				alarme.setUltimoTomado(data);

				int idAlarme = alarmeDao.insert(alarme);
				alarme.setId(idAlarme);
				// Adiciona o próximo Alarme
				m.setAlarme(alarme);

				Log.d("med", "id: " + id);
				Log.d("med", "nome: " + m.getNome());
				Log.d("med",
						"quantidadeCartelaFrasco: "
								+ m.getQuantidadeCartelaFrasco());
				Log.d("med", "periodoHoras: " + m.getPeriodoHoras());
				Log.d("med", "dataInicial: " + m.getDataInicial());
				Log.d("med", "dataFinal: " + m.getDataFinal());
				Log.d("med", "medidaUnidade: " + m.getMedidaUnidade());
				Log.d("med", "alarm>id: " + idAlarme);
				Log.d("med", "alarm>idMedicamento: "
						+ m.getAlarme().getIdMedicamento());
				Log.d("med", "alarm>ultimoMedicamento: "
						+ m.getAlarme().getUltimoTomado());

				startAlarm(m);
				Toast.makeText(getApplicationContext(),
						"Remedio cadastrado com sucesso!", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Por favor, preencha todos os campos.",
						Toast.LENGTH_LONG).show();
			}
		}

		private void startAlarm(Medicamento med) {

			int hora = med.getPeriodoHoras();

			// ID do Alarm Manager
			Bundle bundle = new Bundle();
			bundle.putInt("id", med.getId());

			DateTime dt = new DateTime();
			// [TODO] Mudar para plsuHours
			dt = dt.plusMinutes(hora);
			Long miliSec = dt.getMillis();
			Log.d("teste", "final Joda cad: " + dt.getMillis());
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("dd/MM/yyyy HH:mm:ss");
			Log.d("teste", "dt: " + formatter.print(dt));

			// Calendar calendar = Calendar.getInstance();
			// calendar.setTimeInMillis(System.currentTimeMillis());
			// calendar.add(Calendar.MINUTE, hora); // Segundos
			// // apartir
			// // de
			// // agora

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, miliSec, hora
					* MINUTOS_MILISEGUNDOS, // [TODO] Mudar para HORAS_MILISEGUNDOS
					getPendingIntent(bundle, med.getId()));
		}

		private PendingIntent getPendingIntent(Bundle bundle, int id) {
			Intent it = new Intent(CadastroMedicamentoActivity.this,
					BroadcastReceiveAlarms.class);
			it.putExtras(bundle);
			return PendingIntent.getBroadcast(CadastroMedicamentoActivity.this,
					id, it, PendingIntent.FLAG_UPDATE_CURRENT);
		}
	};

	private View.OnClickListener onClickEditar = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!verificaNulos()) {
				Medicamento m = new Medicamento();
				m.setId(parametro);
				m.setNome(txtNome.getText().toString());
				m.setQuantidadeCartelaFrasco(Integer
						.parseInt(txtQuantidadeCartelaFrasco.getText()
								.toString()));
				m.setPeriodoHoras(Integer.parseInt(txtPeriodoHoras.getText()
						.toString()));
				m.setQuantidadeTomar(Integer.parseInt(txtQuantidadeTomar
						.getText().toString()));
				m.setMedidaUnidade(spnMedidaUnidade.getSelectedItem()
						.toString());
				m.setDataInicial(btnDataInicial.getText().toString());
				m.setDataFinal(btnDataFinal.getText().toString());

				Log.i("sql2", "Data Inicial: " + m.getDataInicial());
				Log.i("sql2", "Data Final: " + m.getDataFinal());

				Usuario usuario = UsuarioDAO.getInstance(
						getApplicationContext()).select(1);
				m.setUsuario(usuario);

				Alarme alarme = alarmeDao.selectByIdMed(m.getId());
				// Adiciona o próximo Alarme
				m.setAlarme(alarme);
				
				medicamentoDao.update(m);
				Toast.makeText(getApplicationContext(),
						"Remedio '" + m.getNome() + "' editado!",
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Por favor, preencha todos os campos.",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	private boolean verificaNulos() {
		if ("".equals(txtNome.getText().toString().trim())) {
			return true;
		} else if ("".equals(txtQuantidadeTomar.getText().toString().trim())) {
			return true;
		} else if ("".equals(txtPeriodoHoras.getText().toString().trim())) {
			return true;
		} else if ("".equals(txtQuantidadeCartelaFrasco.getText().toString()
				.trim())) {
			return true;
		} else if ("".equals(btnDataInicial.getText().toString().trim())
				|| "Data Inicial".equals(btnDataInicial.getText().toString()
						.trim())) {
			return true;
		} else if ("".equals(btnDataFinal.getText().toString().trim())
				|| "Data Final".equals(btnDataInicial.getText().toString()
						.trim())) {
			return true;
		} else {
			// Caso tudo esteja preenchido
			return false;
		}
	}

	private View.OnClickListener onClickCancelar = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

}
