package entities;

import java.time.LocalDate;

public class Task {
    private Integer id;
    private String name;
    private String subject;
    private LocalDate deadline;

    public Task(int id, String name, String subject, LocalDate deadline){
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;
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

    public LocalDate getDeadline() {
        return deadline;
    }
}
