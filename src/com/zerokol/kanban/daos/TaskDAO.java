package com.zerokol.kanban.daos;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.zerokol.kanban.models.Task;
import com.zerokol.kanban.utils.KanbanHelper;
import com.zerokol.kanban.utils.KanbanSQLiteHelper;

public class TaskDAO {
	public static final String TABLE = "tasks";
	public static final String TABLE_ID = "id";
	public static final String TABLE_DESCRIPTION = "description";
	public static final String TABLE_SITUATION = "situation";
	public static final String TABLE_CREATED_AT = "created_at";
	public static final String[] ALL_COLUMNS = { TABLE_ID, TABLE_DESCRIPTION,
			TABLE_SITUATION, TABLE_CREATED_AT };

	private SQLiteDatabase database;
	private KanbanSQLiteHelper dbHelper;

	public TaskDAO(Context context) {
		dbHelper = new KanbanSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		// Para ativar o uso de chave estrangeira e seus gatilhos
		// database.execSQL("PRAGMA foreign_keys=ON;");
	}

	public void close() {
		dbHelper.close();
	}

	public long create(Task task) {
		return database.insert(TABLE, null, buildArguments(task));
	}

	public int update(Task task) {
		ContentValues values = buildArguments(task);
		return database.update(TABLE, values, TABLE_ID + " = '" + task.getId()
				+ "'", null);
	}

	private ContentValues buildArguments(Task task) {
		ContentValues values = new ContentValues();

		values.put(TABLE_ID, task.getId());
		values.put(TABLE_DESCRIPTION, task.getDescription());
		values.put(TABLE_SITUATION, task.getSituation());
		values.put(TABLE_CREATED_AT,
				KanbanHelper.getFormatedData(task.getCreatedAt()));

		return values;
	}

	public void delete(long id) {
		database.delete(TABLE, TABLE_ID + " = '" + id + "'", null);
	}

	public ArrayList<Task> selectAll() {
		ArrayList<Task> tasks = new ArrayList<Task>();

		Cursor cursor = database.query(TABLE, ALL_COLUMNS, null, null, null,
				null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Task task = cursorToList(cursor);
			tasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();

		return tasks;
	}

	private Task cursorToList(Cursor cursor) {
		Task task = new Task();

		task.setId(cursor.getInt(0));
		task.setDescription(cursor.getString(1));
		task.setSituation(cursor.getInt(2));
		task.setCreatedAt(KanbanHelper.convertStringDateToDate(cursor
				.getString(3)));

		return task;
	}
}