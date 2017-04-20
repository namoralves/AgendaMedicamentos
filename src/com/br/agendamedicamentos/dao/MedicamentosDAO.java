package com.br.agendamedicamentos.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.agendamedicamentos.bean.Alarme;
import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.bean.Usuario;
import com.br.agendamedicamentos.dbmanager.DatabaseHandler;
import com.br.agendamedicamentos.utils.Utils;

public class MedicamentosDAO {

	private static final String TABELA_MEDICAMENTO = "medicamento";
	private DatabaseHandler dbHendler;
	private static MedicamentosDAO medicamentosDao;
	private static AlarmeDAO alarmeDao;
	private static UsuarioDAO usuarioDao;

	public MedicamentosDAO(Context ctx) {
		if (dbHendler == null) {
			dbHendler = new DatabaseHandler(ctx);

		}
		alarmeDao = AlarmeDAO.getInstance(ctx);
		usuarioDao = UsuarioDAO.getInstance(ctx);
	}

	public static MedicamentosDAO getInstance(Context ctx) {
		if (medicamentosDao == null) {
			medicamentosDao = new MedicamentosDAO(ctx);
		}
		return medicamentosDao;
	}

	public List<Medicamento> selectAll() {
		List<Medicamento> listaMed = new ArrayList<Medicamento>();
		// Verifica se pode ser lido
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_MEDICAMENTO, null, null, null, null, null,
				null);
		Log.i("alarm", String.valueOf(c.getCount()));
		if (c.moveToFirst()) {
			do {
				Medicamento medicamento = new Medicamento();
				medicamento.setId(c.getInt(c.getColumnIndex("id")));
				Usuario usuario = usuarioDao.select(medicamento.getId());
				medicamento.setUsuario(usuario);
				medicamento.setNome(c.getString(c.getColumnIndex("nome")));
				medicamento.setQuantidadeCartelaFrasco(c.getInt(c
						.getColumnIndex("quantidadeCartelaFrasco")));
				medicamento.setPeriodoHoras(c.getInt(c
						.getColumnIndex("periodoHoras")));
				medicamento.setQuantidadeTomar(c.getInt(c
						.getColumnIndex("quantidadeTomar")));
				medicamento.setMedidaUnidade(c.getString(c
						.getColumnIndex("medidaUnidade")));
				medicamento.setDataInicial(c.getString(c
						.getColumnIndex("dataInicial")));
				medicamento.setDataFinal(c.getString(c
						.getColumnIndex("dataFinal")));
				medicamento.setAtivo(Utils.integer2Boolean(c.getInt(c
						.getColumnIndex("status"))));

				Alarme alarme = new Alarme();
				alarme = alarmeDao.selectByIdMed(1);
				medicamento.setAlarme(alarme);

				// Adiciona a lista
				listaMed.add(medicamento);

				Log.i("sql", "SelectAll id: " + medicamento.getId()
						+ ", nome: " + medicamento.getNome() + ", qtdCartela: "
						+ medicamento.getQuantidadeCartelaFrasco()
						+ ", periodo: " + medicamento.getPeriodoHoras()
						+ ", qtdTomar: " + medicamento.getQuantidadeTomar()
						+ ", data inicial: " + medicamento.getDataInicial()
						+ ", data final: " + medicamento.getDataFinal()
						+ ", ativo: " + medicamento.isAtivo());
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return listaMed;
	}

	public Medicamento select(int id) {
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_MEDICAMENTO, null, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		Medicamento medicamento = new Medicamento();
		if (c.moveToFirst()) {
			do {
				Log.d("sql", "selectAll: " + c.getInt(0));
				Log.d("sql", "selectAll: " + c.getString(1));
				Log.d("sql", "selectAll: " + c.getInt(2));
				Log.d("sql", "selectAll: " + c.getInt(3));
				Log.d("sql", "selectAll: " + c.getInt(4));
				Log.d("sql", "selectAll: " + c.getString(5));
				Log.d("sql", "selectAll: " + c.getString(6));
				Log.d("sql", "selectAll: " + c.getString(7));
				Log.d("sql", "selectAll: " + c.getInt(8));
				Log.d("sql", "+++++++++++ ");
				medicamento.setId(c.getInt(c.getColumnIndex("id")));
				Usuario usuario = usuarioDao.select(1);
				medicamento.setUsuario(usuario);
				medicamento.setNome(c.getString(c.getColumnIndex("nome")));
				medicamento.setQuantidadeCartelaFrasco(c.getInt(c
						.getColumnIndex("quantidadeCartelaFrasco")));
				medicamento.setPeriodoHoras(c.getInt(c
						.getColumnIndex("periodoHoras")));
				medicamento.setQuantidadeTomar(c.getInt(c
						.getColumnIndex("quantidadeTomar")));
				medicamento.setMedidaUnidade(c.getString(c
						.getColumnIndex("medidaUnidade")));
				medicamento.setDataInicial(c.getString(c
						.getColumnIndex("dataInicial")));
				medicamento.setDataFinal(c.getString(c
						.getColumnIndex("dataFinal")));
				medicamento.setAtivo(Utils.integer2Boolean(c.getInt(c
						.getColumnIndex("status"))));
				Alarme alarme = new Alarme();
				alarme = alarmeDao.selectByIdMed(medicamento.getId());
				medicamento.setAlarme(alarme);

				Log.i("alarm", "Select id: " + medicamento.getId() + ", nome: "
						+ medicamento.getNome());
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return medicamento;
	}

	public int insert(Medicamento medicamento) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("idUsuario", medicamento.getUsuario().getId());
		cv.put("nome", medicamento.getNome());
		cv.put("quantidadeCartelaFrasco",
				medicamento.getQuantidadeCartelaFrasco());
		cv.put("periodoHoras", medicamento.getPeriodoHoras());
		cv.put("quantidadeTomar", medicamento.getQuantidadeTomar());
		cv.put("medidaUnidade", medicamento.getMedidaUnidade());
		cv.put("dataInicial", medicamento.getDataInicial());
		cv.put("dataFinal", medicamento.getDataFinal());
		cv.put("status", medicamento.isAtivo() ? 1 : 0);

		Long retorno = db.insert(TABELA_MEDICAMENTO, null, cv);
		db.close();
		return retorno.intValue();
	}

	public int update(Medicamento medicamento) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();
		int retorno;
		ContentValues cv = new ContentValues();
		cv.put("idUsuario", medicamento.getUsuario().getId());
		cv.put("nome", medicamento.getNome());
		cv.put("quantidadeCartelaFrasco",
				medicamento.getQuantidadeCartelaFrasco());
		cv.put("periodoHoras", medicamento.getPeriodoHoras());
		cv.put("quantidadeTomar", medicamento.getQuantidadeTomar());
		cv.put("medidaUnidade", medicamento.getMedidaUnidade());
		cv.put("dataInicial", medicamento.getDataInicial());
		cv.put("dataFinal", medicamento.getDataFinal());
		cv.put("status", medicamento.isAtivo() ? 1 : 0);

		Log.i("sql",
				"Update id: " + medicamento.getId() + ", nome: "
						+ medicamento.getNome() + ", qtdCartela: "
						+ medicamento.getQuantidadeCartelaFrasco()
						+ ", periodo: " + medicamento.getPeriodoHoras()
						+ ", qtdTomar: " + medicamento.getQuantidadeTomar()
						+ ", data inicial: " + medicamento.getDataInicial()
						+ ", data final: " + medicamento.getDataFinal());

		retorno = db.update(TABELA_MEDICAMENTO, cv, "id = ?",
				new String[] { String.valueOf(medicamento.getId()) });
		db.close();
		return retorno;
	}

	public void updateStatus(int id, boolean status) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("status", (status ? 1 : 0));

		db.update(TABELA_MEDICAMENTO, cv, "id = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void delete(int id) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();
		alarmeDao.selectByIdMed(id);
		// Tabela Medicamento
		db.delete(TABELA_MEDICAMENTO, "id = ?",
				new String[] { String.valueOf(id) });
		// Tabela Alarme
		// [TODO] arrumar
		Alarme alarme = alarmeDao.selectByIdMed(id);
		alarmeDao.delete(alarme.getId());
		db.close();
	}

	public void updateQuantidade(Medicamento med) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		Log.d("remedio",
				"quantidade antes: " + med.getQuantidadeCartelaFrasco());
		double qtdNova = med.getQuantidadeCartelaFrasco()
				- med.getQuantidadeTomar();
		Log.d("remedio", "quantidade depois: " + qtdNova);

		ContentValues cv = new ContentValues();
		cv.put("quantidadeCartelaFrasco", qtdNova);

		db.update(TABELA_MEDICAMENTO, cv, "id = ?",
				new String[] { String.valueOf(med.getId()) });
		db.close();
	}

}
