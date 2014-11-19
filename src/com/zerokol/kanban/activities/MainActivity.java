package com.zerokol.kanban.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.zerokol.kanban.R;
import com.zerokol.kanban.adapters.ProjectAdapter;
import com.zerokol.kanban.daos.ProjectDAO;
import com.zerokol.kanban.models.Project;

public class MainActivity extends ActionBarActivity {
	private ListView projectsListView;
	private ArrayList<Project> projects;
	private ProjectDAO projectdao;
	private ProjectAdapter projectAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		projectsListView = (ListView) findViewById(R.id.main_projects_listView);

		projectdao = new ProjectDAO(this);
		projectdao.open();
		projects = projectdao.selectAll();
		projectdao.close();

		projectAdapter = new ProjectAdapter(this, projects);

		projectsListView.setAdapter(projectAdapter);
	}

	@Override
	protected void onResume() {
		projectAdapter.notifyDataSetChanged();
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
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
