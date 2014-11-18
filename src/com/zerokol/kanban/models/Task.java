package com.zerokol.kanban.models;

import java.util.Date;

public class Task {
	private Integer id;
	private Integer projectId;
	private String description;
	private Integer situation;
	private Date createdAt;

	public Task() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
