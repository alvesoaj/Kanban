package com.zerokol.kanban.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zerokol.kanban.R;
import com.zerokol.kanban.adapters.ProjectAdapter;
import com.zerokol.kanban.daos.ProjectDAO;
import com.zerokol.kanban.models.Project;

public class MainActivity extends ActionBarActivity {
	private static final int CREATE_PROJECT_REQUEST = 1;
	private ListView projectsListView;
	private ArrayList<Project> projects;
	private ProjectDAO projectdao;
	private ProjectAdapter projectAdapter;

	private int selectedItemPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		projectsListView = (ListView) findViewById(R.id.main_projects_listView);

		// Registrando a listView para receber um menu de contexto
		registerForContextMenu(projectsListView);

		projectdao = new ProjectDAO(this);
		projectdao.open();
		projects = projectdao.selectAll();
		projectdao.close();

		projectAdapter = new ProjectAdapter(this, projects);

		projectsListView.setAdapter(projectAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_create_project) {
			Intent intent = new Intent(MainActivity.this,
					CreateProjectActivity.class);
			startActivityForResult(intent, CREATE_PROJECT_REQUEST);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CREATE_PROJECT_REQUEST) {
			if (resultCode == RESULT_OK) {
				projectdao.open();
				projects.clear();
				projects.addAll(projectdao.selectAll());
				projectdao.close();

				projectAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		// Extrando qual item da lista foi pressionado
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		selectedItemPosition = info.position;

		// Criado o menu de contexto com o nome do filho no titulo
		Project p = projectAdapter.getItem(selectedItemPosition);
		menu.setHeaderTitle(p.getName());

		// Inflando o menu de contexto com as opcoes
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_main, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_show_project:
			// TODO show
			break;
		case R.id.action_edit_project:
			// TODO edit
			break;
		case R.id.action_destroy_project:
			new AlertDialog.Builder(this)
					.setTitle(R.string.action_destroy_project)
					.setMessage(R.string.action_are_you_sure)
					.setPositiveButton(R.string.action_yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO destroy
									dialog.dismiss();
								}
							})
					.setNegativeButton(R.string.action_no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Write your code if you want
									dialog.dismiss();
								}
							}).show();
			break;
		}
		return true;
	}
}
