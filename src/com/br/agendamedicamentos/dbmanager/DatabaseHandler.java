package com.br.agendamedicamentos.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "agendaMedicamentos.db";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		String CREATE_USUARIO_TABLE = "CREATE TABLE usuario ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nome TEXT NOT NULL, "
				+ "dataNascimento DATE NOT NULL, "
				+ "sexo CHAR(1) NOT NULL )";

		String CREATE_MEDICAMENTOS_TABLE = "CREATE TABLE medicamento ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " // id
				+ "idUsuario INTEGER, " // id do usuário
				+ "nome TEXT NOT NULL, " // nome
				+ "quantidadeCartelaFrasco INTEGER NOT NULL, " // quantidade
																// cartela
				+ "periodoHoras INTEGER NOT NULL, " // periodoHoras
				+ "quantidadeTomar INTEGER NOT NULL, " // quantidade a tomar
				+ "medidaUnidade TEXT NOT NULL, " // medida unidade
				+ "dataInicial DATETIME NOT NULL, " // data inicial
				+ "dataFinal DATETIME NOT NULL, " // data final
				+ "status INTEGER NOT NULL )"; // Ativo

		String CREATE_ALARME_TABLE = "CREATE TABLE alarme ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "idMedicamento INTEGER, "
				+ "ultimoTomado DATETIME, "
				+ "status INTEGER )";

		db.execSQL(CREATE_USUARIO_TABLE);
		db.execSQL(CREATE_MEDICAMENTOS_TABLE);
		db.execSQL(CREATE_ALARME_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Sem implementação
	}

}
