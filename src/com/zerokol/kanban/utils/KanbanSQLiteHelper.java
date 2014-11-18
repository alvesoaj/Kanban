package com.zerokol.kanban.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class KanbanSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "kanban.db";
	private static final int DATABASE_VERSION = 3;

	private static final String CREATE_PROJECTS_TABLE = "CREATE TABLE [projects] ("
			+ " [id] INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ " [name] VARCHAR(255) NOT NULL);";

	private static final String CREATE_TASKS_TABLE = "CREATE TABLE [tasks] ( "
			+ " [id] INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ " [description] VARCHAR(255) NOT NULL,"
			+ " [situation] INTEGER(10) NOT NULL,"
			+ " [created_at] DATETIME NOT NULL);";

	private static final String ADD_COLUMN_TO_TASKS = "ALTER TABLE [tasks]"
			+ " ADD project_id INTEGER(10);";

	public KanbanSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// v1
		database.execSQL(CREATE_TASKS_TABLE);
		// v2
		database.execSQL(CREATE_PROJECTS_TABLE);
		// v3
		database.execSQL(ADD_COLUMN_TO_TASKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(KanbanSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ".");

		for (int i = oldVersion; i < newVersion; i++) {
			switch (i) {
			case 1:
				database.execSQL(CREATE_PROJECTS_TABLE);
				break;
			case 2:
				database.execSQL(ADD_COLUMN_TO_TASKS);
				break;
			}
		}
	}
}
