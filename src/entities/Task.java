package entities;

import enums.TaskStatus;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

public class Task {
    private static int nexId = 0;
    private Integer id;
    private String name;
    private String subject;
    private TaskStatus taskStatus;
    private LocalDate deadline;

    public Task(String name, String subject, LocalDate deadline){
        this.id = ++Task.nexId;
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;

        this.taskStatus = TaskStatus.PENDING;
    }
    public Task(String name, String subject){
        this.id = ++Task.nexId;
        this.name = name;
        this.subject = subject;

        this.taskStatus = TaskStatus.PENDING;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TaskStatus getTaskStatus() {return taskStatus; }
    public void setTaskStatus(TaskStatus taskStatus) {this.taskStatus = taskStatus; }

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {this.deadline = deadline; }

    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String deadlineText;

        if (deadline == null) {
            deadlineText = "No deadline";
        } else {
            deadlineText = deadline.format(formatter);
        }
        return "ID: "
                + id
                + " ,name: "
                + name
                + " ,subject: "
                + subject
                + " ,deadline: "
                +deadlineText
                + " ,status: "
                + taskStatus;
    }
}
