package com.zerokol.kanban.daos;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zerokol.kanban.models.Project;
import com.zerokol.kanban.utils.KanbanHelper;
import com.zerokol.kanban.utils.KanbanSQLiteHelper;

public class ProjectDAO {
	public static final String TABLE = "projects";
	public static final String TABLE_ID = "id";
	public static final String TABLE_NAME = "name";
	public static final String TABLE_CREATED_AT = "created_at";
	public static final String[] ALL_COLUMNS = { TABLE_ID, TABLE_NAME,
			TABLE_CREATED_AT };

	private SQLiteDatabase database;
	private KanbanSQLiteHelper dbHelper;

	public ProjectDAO(Context context) {
		super();
		dbHelper = new KanbanSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void create(Project project) {
		database.insert(TABLE, null, buildArguments(project));
	}

	private ContentValues buildArguments(Project project) {
		ContentValues values = new ContentValues();

		values.put(TABLE_NAME, project.getName());
		values.put(TABLE_CREATED_AT,
				KanbanHelper.getFormatedData(project.getCreatedAt()));
		return values;
	}

	public ArrayList<Project> selectAll() {
		ArrayList<Project> projects = new ArrayList<Project>();

		Cursor cursor = database.query(TABLE, ALL_COLUMNS, null, null, null,
				null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Project project = cursorToList(cursor);
			projects.add(project);
			cursor.moveToNext();
		}
		cursor.close();

		return projects;
	}

	private Project cursorToList(Cursor c) {
		Project p = new Project();

		p.setId(c.getInt(0));
		p.setName(c.getString(1));
		p.setCreatedAt(KanbanHelper.convertStringDateToDate(c.getString(2)));

		return p;
	}
}
