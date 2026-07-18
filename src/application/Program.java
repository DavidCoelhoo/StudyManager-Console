package application;

import entities.Task;
import enums.TaskStatus;
import services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskService taskService = new TaskService();
        Task task;
        int select;
        do{
            System.out.println("Menu\n1 - add task\n2 - show list\n3 - find task by id\n4 - remove task\n5 - update task\n6 - exit");
            select = sc.nextInt();
            sc.nextLine();
            switch (select){
                case 1:
                    System.out.println("Enter the task name:");
                    String name = sc.nextLine();
                    System.out.println("Enter the subject:");
                    String subject = sc.nextLine();
                    System.out.println("Does the task have a deadline? (1 - yes, 2 - no)");
                    int option = sc.nextInt();
                    sc.nextLine();
                    if(option == 1){
                        System.out.println("enter deadline: (dd/MM/yyyy)");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String input = sc.nextLine();
                        LocalDate deadline = LocalDate.parse(input, formatter);
                        task = new Task(name, subject, deadline);
                    } else if (option == 2) {
                        task = new Task(name, subject);
                    }else{
                        System.out.println("Invalid value.");
                        continue;
                    }
                    taskService.addTask(task);
                    break;

                case 2:
                    if(taskService.getAllTasks().isEmpty()){
                        System.out.println("nothing yet");
                    }
                    else {
                        for (Task currentTask : taskService.getAllTasks()) {
                            System.out.println(currentTask);
                        }
                    }
                    break;

                case 3:
                    System.out.println("search ID task:");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    if(searchId <= 0){
                        System.out.println("Invalid value.");
                    }
                    else{
                        Task foundTask = taskService.findTaskById(searchId);
                        if (foundTask ==  null){
                            System.out.println("task not found");
                        }else{
                            System.out.println(foundTask);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Enter the ID of the task to be removed:");
                    int idToRemove = sc.nextInt();
                    sc.nextLine();
                    if(idToRemove <= 0){
                        System.out.println("Invalid value.");
                    }else {
                        boolean removed = taskService.removeTaskById(idToRemove);
                        if (removed) {
                            System.out.println("task removed successfully.");
                        } else {
                            System.out.println("not found.");
                        }
                    }
                    break;

                case 5:
                    updateTaskMenu(sc, taskService);
                    break;


                case 6:
                    System.out.println("leaving...");
                    break;

                default:
                    System.out.println("Invalid option");

            }
        }while (select != 6);
        sc.close();
    }
    private static void updateTaskMenu(Scanner sc,TaskService taskService){
        System.out.println("enter the ID of the task to be updated.");
        int idToUpdate = sc.nextInt();
        sc.nextLine();
        if(idToUpdate <= 0){
            System.out.println("Invalid value.");
        }else{
            Task taskFound = taskService.findTaskById(idToUpdate);
            if(taskFound == null){
                System.out.println("task not found");
            }else{
                System.out.println("update menu\n1 - update name\n2 - update subject\n3 - update deadline\n4 - update status\n5 - cancel");
                int selectUpdate = sc.nextInt();
                sc.nextLine();
                switch (selectUpdate){
                    case 1:
                        System.out.println("update task name: ");
                        String name = sc.nextLine();
                        taskService.updateTaskName(taskFound, name);
                        System.out.println("Task updated successfully.");
                        break;

                    case 2:
                        System.out.println("update task subject: ");
                        String subject = sc.nextLine();
                        taskService.updateTaskSubject(taskFound, subject);
                        System.out.println("Task updated successfully.");
                        break;

                    case 3:
                        System.out.println("update task deadline: ");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String input = sc.nextLine();
                        LocalDate deadline = LocalDate.parse(input, formatter);
                        taskService.updateTaskDeadline(taskFound, deadline);
                        System.out.println("Task updated successfully.");
                        break;

                    case 4:
                        System.out.println("update task status:");
                        TaskStatus newStatus = readTaskStatus(sc);
                        if(newStatus == null){
                            System.out.println("Invalid status.");
                            break;
                        }
                        taskService.updateTaskStatus(taskFound, newStatus);
                        System.out.println("Task updated successfully");
                        break;

                    case 5:
                        System.out.println("Update canceled.");
                        break;

                    default:
                        System.out.println("Invalid option");
                }
            }
        }
    }
    private static TaskStatus readTaskStatus(Scanner sc){
        System.out.println("Select the new status:\n1 - pending\n2 - in progress\n3 - completed");
        int option = sc.nextInt();
        sc.nextLine();
        return switch (option){
            case 1 -> TaskStatus.PENDING;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.COMPLETED;
            default -> null;
        };
    }
}