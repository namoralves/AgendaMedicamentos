package com.br.agendamedicamentos.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.agendamedicamentos.bean.Usuario;
import com.br.agendamedicamentos.dbmanager.DatabaseHandler;

public class UsuarioDAO {

	private DatabaseHandler dbHendler;
	private static UsuarioDAO usuarioDao;

	// Tabela
	private String TABELA_USUARIO = "usuario";

	// Colunas
	private String COLUNA_ID = "id";
	private String COLUNA_NOME = "nome";
	private String COLUNA_DATA_NASCIMENTO = "dataNascimento";
	private String COLUNA_SEXO = "sexo";

	public UsuarioDAO(Context ctx) {
		if (dbHendler == null) {
			dbHendler = new DatabaseHandler(ctx);

		}
	}

	public static UsuarioDAO getInstance(Context ctx) {
		if (usuarioDao == null) {
			usuarioDao = new UsuarioDAO(ctx);
		}
		return usuarioDao;
	}

	public Usuario select(int id) {
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		Cursor c = db.query(TABELA_USUARIO, null, COLUNA_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);

		Usuario usuario = new Usuario();
		if (c.moveToFirst()) {
			do {
				usuario.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
				usuario.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));

				usuario.setDataNascimento(c.getString(c
						.getColumnIndex(COLUNA_DATA_NASCIMENTO)));

				usuario.setSexo(c.getString(c.getColumnIndex(COLUNA_SEXO))
						.charAt(0));
			} while (c.moveToNext());
		}
		db.close();
		Log.d("sql", "usuario select(): " + usuario.getNome());
		Log.d("sql", "usuario select(): " + usuario.getDataNascimento());
		Log.d("sql", "usuario select(): " + usuario.getSexo());
		return usuario;
	}

	public List<Usuario> selectAll() {
		SQLiteDatabase db = dbHendler.getReadableDatabase();

		List<Usuario> listUsuarios = new ArrayList<Usuario>();
		Cursor c = db.query(TABELA_USUARIO, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				Usuario usuario = new Usuario();
				usuario.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
				usuario.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));
				usuario.setDataNascimento(c.getString(c
						.getColumnIndex(COLUNA_DATA_NASCIMENTO)));
				usuario.setSexo(c.getString(c.getColumnIndex(COLUNA_SEXO))
						.charAt(0));
				listUsuarios.add(usuario);
			} while (c.moveToNext());
		}
		db.close();
		return listUsuarios;
	}

	public void delete(int id) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		db.delete(TABELA_USUARIO, COLUNA_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public int update(Usuario usuario) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(COLUNA_NOME, usuario.getNome());
		cv.put(COLUNA_DATA_NASCIMENTO, usuario.getDataNascimento().toString());
		cv.put(COLUNA_SEXO, usuario.getSexo().toString());

		int retorno = db.update(TABELA_USUARIO, cv, COLUNA_ID + " = ?",
				new String[] { String.valueOf(usuario.getId()) });
		db.close();
		return retorno;
	}

	public int insert(Usuario usuario) {
		SQLiteDatabase db = dbHendler.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(COLUNA_NOME, usuario.getNome());
		cv.put(COLUNA_DATA_NASCIMENTO, usuario.getDataNascimento());
		cv.put(COLUNA_SEXO, usuario.getSexo().toString());

		Long id = db.insert(TABELA_USUARIO, null, cv);
		db.close();
		return id.intValue();
	}

}
