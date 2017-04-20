package com.br.agendamedicamentos.dao;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.agendamedicamentos.bean.Alarme;
import com.br.agendamedicamentos.bean.Medicamento;
import com.br.agendamedicamentos.dbmanager.DatabaseHandler;

public class AlarmeDAO {

	private static final String TABELA_ALARME = "alarme";
	private DatabaseHandler dbHendler;
	private static AlarmeDAO alarmeDao;

	public AlarmeDAO(Context ctx) {
		if (dbHendler == null) {
			dbHendler = new DatabaseHandler(ctx);
		}
	}

	public static AlarmeDAO getInstance(Context ctx) {
		if (alarmeDao == null) {
			alarmeDao = new AlarmeDAO(ctx);
		}
		return alarmeDao;
	}

	public List<Alarme> selectAll() {
		List<Alarme> listaAlarme = new ArrayList<Alarme>();

		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_ALARME, null, null, null, null, null, null);

		if (c.moveToFirst()) {
			do {
				Alarme alarme = new Alarme();
				alarme.setId(c.getInt(c.getColumnIndex("id")));
				alarme.setIdMedicamento(c.getInt(c
						.getColumnIndex("idMedicamento")));
				alarme.setUltimoTomado(c.getString(c
						.getColumnIndex("ultimoTomado")));
				alarme.setAtivo(c.getInt(c.getColumnIndex("status")) == 1 ? true
						: false);
				listaAlarme.add(alarme);
				Log.d("med", "alarm:: " + alarme.getId());
				Log.d("med", "alarm:: " + alarme.getUltimoTomado());
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return listaAlarme;
	}

	public Alarme select(int id) {
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_ALARME, null, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		Alarme alarme = new Alarme();
		if (c.moveToFirst()) {
			do {
				alarme.setId(c.getInt(c.getColumnIndex("id")));
				alarme.setIdMedicamento(c.getInt(c
						.getColumnIndex("idMedicamento")));
				alarme.setUltimoTomado(c.getString(c
						.getColumnIndex("ultimoTomado")));
				alarme.setAtivo(c.getInt(c.getColumnIndex("status")) == 1 ? true
						: false);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return alarme;
	}

	public int insert(Alarme alarme) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("idMedicamento", alarme.getIdMedicamento());
		cv.put("ultimoTomado", alarme.getUltimoTomado());
		cv.put("status", alarme.isAtivo() ? 1 : 0);

		Long id = db.insert(TABELA_ALARME, null, cv);
		Log.d("med", "alarm:: " + id);
		Log.d("med", "alarm:: " + alarme.getUltimoTomado());
		db.close();
		return id.intValue();
	}

	public int update(Alarme alarme) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();
		int retorno;

		ContentValues cv = new ContentValues();
		cv.put("idMedicamento", alarme.getIdMedicamento());
		cv.put("proximoAlarme", alarme.getUltimoTomado());
		cv.put("status", alarme.isAtivo() ? 1 : 0);
		Log.d("med", "alarm Update:: " + alarme.getUltimoTomado());

		retorno = db.update(TABELA_ALARME, cv, "id = ?",
				new String[] { String.valueOf(alarme.getId()) });
		db.close();
		return retorno;
	}

	public int updateStatus(int idAlarm, boolean status) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();
		int retorno;

		ContentValues cv = new ContentValues();
		cv.put("status", status ? 1 : 0);

		retorno = db.update(TABELA_ALARME, cv, "id = ?",
				new String[] { String.valueOf(idAlarm) });
		db.close();
		return retorno;
	}

	public void delete(int id) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();
		db.delete(TABELA_ALARME, "idMedicamento = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public Alarme selectByIdMed(int idMed) {
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_ALARME, null, "idMedicamento = ?",
				new String[] { String.valueOf(idMed) }, null, null, null, null);

		Alarme alarme = new Alarme();
		if (c.moveToFirst()) {
			do {
				alarme.setId(c.getInt(c.getColumnIndex("id")));
				alarme.setIdMedicamento(c.getInt(c
						.getColumnIndex("idMedicamento")));
				alarme.setUltimoTomado(c.getString(c
						.getColumnIndex("ultimoTomado")));
				alarme.setAtivo(c.getInt(c.getColumnIndex("status")) == 1 ? true
						: false);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return alarme;
	}

	public void updateUltimoTomado(Medicamento med) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		DateTime dataN = new DateTime().plusSeconds(med.getPeriodoHoras());
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		String data = fmt.print(dataN);
		ContentValues cv = new ContentValues();
		Log.d("date", "update: " + data);
		cv.put("ultimoTomado", data);

		db.update(TABELA_ALARME, cv, "idMedicamento = ?",
				new String[] { String.valueOf(med.getId()) });
		db.close();
	}

}
