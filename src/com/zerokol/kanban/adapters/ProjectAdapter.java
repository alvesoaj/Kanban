package com.zerokol.kanban.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zerokol.kanban.R;
import com.zerokol.kanban.models.Project;
import com.zerokol.kanban.utils.KanbanHelper;

public class ProjectAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<Project> projects;
	private ViewHolder holder;

	public ProjectAdapter(Context context, List<Project> projects) {
		this.layoutInflater = LayoutInflater.from(context);
		this.projects = projects;
	}

	@Override
	public int getCount() {
		return projects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Project project = projects.get(position);

		holder = new ViewHolder();

		convertView = layoutInflater.inflate(R.layout.adapter_project, null);

		holder.createdAt = (TextView) convertView
				.findViewById(R.id.adapter_project_created_at_textView);
		holder.createdAt.setText(KanbanHelper.getFormatedData(project
				.getCreatedAt()));

		holder.name = (TextView) convertView
				.findViewById(R.id.adapter_project_name_textView);
		holder.name.setText(project.getName());

		holder.toDo = (TextView) convertView
				.findViewById(R.id.adapter_project_to_do_quantity_textView);

		holder.doing = (TextView) convertView
				.findViewById(R.id.adapter_project_doing_textView);

		holder.done = (TextView) convertView
				.findViewById(R.id.adapter_project_done_textView);

		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public TextView createdAt;
		public TextView toDo;
		public TextView doing;
		public TextView done;
	}
}
