package com.zerokol.kanban.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zerokol.kanban.R;
import com.zerokol.kanban.daos.TaskDAO;
import com.zerokol.kanban.models.Task;
import com.zerokol.kanban.utils.KanbanHelper;

public class CreateTaskActivity extends ActionBarActivity {
	private EditText description;
	private Button save;
	private TaskDAO taskDataAccessObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);

		description = (EditText) findViewById(R.id.create_task_description_editText);
		save = (Button) findViewById(R.id.create_task_save_button);

		taskDataAccessObject = new TaskDAO(this);

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Task task = new Task();
				task.setDescription(description.getText().toString());
				task.setSituation(1);
				task.setCreatedAt(KanbanHelper.getTimeCurrent());

				taskDataAccessObject.open();
				taskDataAccessObject.create(task);
				taskDataAccessObject.close();
				
				description.setText("");
			}
		});
	}
}
