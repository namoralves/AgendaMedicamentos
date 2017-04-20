package com.br.agendamedicamentos.dialogs;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.br.agendamedicamentos.R;
import com.br.agendamedicamentos.bean.Usuario;
import com.br.agendamedicamentos.dao.UsuarioDAO;
import com.br.agendamedicamentos.utils.Utils;

public class CadastroUsuario extends Activity {

	private static final int DIALOG_DATA_NASCIMENTO = 0;

	private TextView textNome;
	private Button btnDataNascimento;
	private RadioGroup radioGroupSexo;
	private Button btnCadastrar;
	private RadioButton radioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usuario_cadastre);
		initComponents();
	}

	private void initComponents() {
		textNome = (TextView) findViewById(R.usuario_cadastre.text_nome);
		btnDataNascimento = (Button) findViewById(R.usuario_cadastre.btn_data_nascimento);
		radioGroupSexo = (RadioGroup) findViewById(R.usuario_cadastre.radiogroup);
		btnCadastrar = (Button) findViewById(R.usuario_cadastre.btn_cadastrar);

		btnDataNascimento.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DIALOG_DATA_NASCIMENTO);
			}
		});

		btnCadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome = textNome.getText().toString();
				String dataNascimento = btnDataNascimento.getText().toString();
				int selectedId = radioGroupSexo.getCheckedRadioButtonId();
				radioButton = (RadioButton) findViewById(selectedId);
				String sexo = radioButton.getText().toString();

				Usuario usuario = new Usuario();
				usuario.setNome(nome);
				usuario.setDataNascimento(dataNascimento);
				usuario.setSexo(sexo.charAt(0));

				int id = UsuarioDAO.getInstance(getBaseContext()).insert(
						usuario);
				finish();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar calendario = Calendar.getInstance();

		int ano = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case DIALOG_DATA_NASCIMENTO:
			return new DatePickerDialog(this, listenerDataNascimento, ano, mes,
					dia);
		}
		return null;
	}

	// Action do datepicker
	private DatePickerDialog.OnDateSetListener listenerDataNascimento = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String data = Utils.formataData(dayOfMonth, monthOfYear, year);
			btnDataNascimento.setText(data);
		}
	};

}
