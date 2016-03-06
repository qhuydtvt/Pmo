package vn.edu.techkids.pomodoro.entity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;

/**
 * Created by qhuydtvt on 12/18/2015.
 */
public class TaskManager {

    private List<Task> mTaskList;

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.mTaskList = taskList;
    }

    public TaskManager(List<Task> taskList) {
        this.mTaskList = taskList;
    }

    public void add(Task task) {
        this.mTaskList.add(task);
    }

    public void remove(Task task) {
        this.mTaskList.remove(task);
    }

    public Task get(int position) {
        return this.mTaskList.get(position);
    }

    public List<Task> getTaskList(TaskStatus taskStatus) {
        ArrayList<Task> retList = new ArrayList<>();
        for(Task task : mTaskList) {
            if(task.getStatus() == taskStatus) {
                retList.add(task);
            }
        }
        return retList;
    }
}
