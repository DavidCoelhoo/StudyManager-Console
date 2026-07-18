package services;

import entities.Task;
import enums.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task){
        if(task == null){
            throw new IllegalArgumentException("Task must not be null");
        }
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }
    public Task findTaskById(int id){
        for(Task task: tasks){
            if(id == task.getId()){
                return task;
            }
        }
        return null;
    }
    public boolean removeTaskById(int id){
        Iterator<Task> iterator = tasks.iterator();

        while(iterator.hasNext()){
            Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    public void updateTaskName(Task taskFound, String name){
        taskFound.setName(name);
    }
    public void updateTaskSubject(Task taskFound, String subject){
        taskFound.setSubject(subject);
    }
    public void updateTaskDeadline(Task taskFound, LocalDate deadline){
        taskFound.setDeadline(deadline);
    }
    public void updateTaskStatus(Task taskFound, TaskStatus taskStatus){
        taskFound.setTaskStatus(taskStatus);
    }


}
