package com.wfms.model.common.task;

public class TaskDto {
	private String taskState;
	private String taskType;
	private String taskTopic;
	private String sendTime;
	private String mainPage;
	private String moduleName;
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public TaskDto(){}
	
	public TaskDto(String taskType)
	{
		this.taskType = taskType;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskTopic() {
		return taskTopic;
	}
	public void setTaskTopic(String taskTopic) {
		this.taskTopic = taskTopic;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	
}
