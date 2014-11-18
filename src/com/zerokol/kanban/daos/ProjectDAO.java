package com.zerokol.kanban.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zerokol.kanban.models.Project;
import com.zerokol.kanban.utils.KanbanSQLiteHelper;

public class ProjectDAO {
	private final String TAB_NAME = "projects";
	private final String FIELD_NAME = "name";
	private final String[] cols = { "id", "name" };

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
		database.insert(TAB_NAME, null, buildArguments(project));
	}

	private ContentValues buildArguments(Project p) {
		ContentValues cv = new ContentValues();

		cv.put(FIELD_NAME, p.getName());

		return cv;
	}

	public Project selectFirst() {
		Cursor c = database.query(TAB_NAME, cols, null, null, null, null, null,
				null);

		c.moveToFirst();
		Project p = cursorToList(c);
		c.close();
		return p;
	}

	private Project cursorToList(Cursor c) {
		Project p = new Project();
		p.setId(c.getInt(0));
		p.setName(c.getString(1));

		return p;
	}
}
