package application;

import entities.Task;
import enums.TaskStatus;
import services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TaskService taskService = new TaskService();
        int select = 0;

        do {
            System.out.println("""
                    Menu
                    1 - add task
                    2 - show list
                    3 - find task by id
                    4 - remove task
                    5 - update task
                    6 - exit
                    """);

            try {
                select = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                sc.nextLine();
                continue;
            }

            switch (select) {
                case 1:
                    addTaskMenu(sc, taskService);
                    break;
                case 2:
                    showTaskList(taskService);
                    break;
                case 3:
                    findTaskMenu(sc, taskService);
                    break;
                case 4:
                    removeTaskMenu(sc, taskService);
                    break;
                case 5:
                    updateTaskMenu(sc, taskService);
                    break;
                case 6:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (select != 6);
        sc.close();
    }


    private static void addTaskMenu(Scanner sc, TaskService taskService) {
        Task task;
        System.out.println("Enter the task name:");
        String name = sc.nextLine();
        System.out.println("Enter the subject:");
        String subject = sc.nextLine();
        System.out.println("Does the task have a deadline? (1 - yes, 2 - no)");
        int option;

        try {
            option = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        if (option == 1) {
            System.out.println("Enter deadline: (dd/MM/yyyy)");
            String input = sc.nextLine();
            LocalDate deadline;

            try {
                deadline = LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date.");
                return;
            }

            task = new Task(name, subject, deadline);
        } else if (option == 2) {
            task = new Task(name, subject);
        } else {
            System.out.println("Invalid value.");
            return;
        }
        taskService.addTask(task);
    }


    private static void showTaskList(TaskService taskService) {
        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Nothing yet");
        } else {
            for (Task currentTask : taskService.getAllTasks()) {
                System.out.println(currentTask);
            }
        }
    }


    private static void findTaskMenu(Scanner sc, TaskService taskService) {
        System.out.println("Search the ID task:");
        int searchId;

        try {
            searchId = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        if (searchId <= 0) {
            System.out.println("Invalid value.");
            return;
        }
        Task foundTask = taskService.findTaskById(searchId);
        if (foundTask == null) {
            System.out.println("Task not found");
        } else {
            System.out.println(foundTask);
        }
    }


    private static void removeTaskMenu(Scanner sc, TaskService taskService) {
        System.out.println("Enter the ID of the task to be removed:");
        int idToRemove;

        try {
            idToRemove = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        if (idToRemove <= 0) {
            System.out.println("Invalid value.");
            return;
        }
        boolean removed = taskService.removeTaskById(idToRemove);
        if (removed) {
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Not found.");
        }
    }


    private static void updateTaskMenu(Scanner sc, TaskService taskService) {
        System.out.println("Enter the ID of the task to be updated.");
        int idToUpdate;

        try {
            idToUpdate = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
            return;
        }

        if (idToUpdate <= 0) {
            System.out.println("Invalid value.");
            return;
        }
        Task taskFound = taskService.findTaskById(idToUpdate);

        if (taskFound == null) {
            System.out.println("task not found");
            return;
        }
        System.out.println("update menu\n1 - update name\n2 - update subject\n3 - update deadline\n4 - update status\n5 - cancel");
        int selectUpdate;

        try {
            selectUpdate = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        switch (selectUpdate) {
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
                String input = sc.nextLine();
                LocalDate deadline;

                try {
                    deadline = LocalDate.parse(input, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date.");
                    break;
                }

                taskService.updateTaskDeadline(taskFound, deadline);
                System.out.println("Task updated successfully.");
                break;

            case 4:
                System.out.println("update task status:");
                TaskStatus newStatus = readTaskStatus(sc);
                if (newStatus == null) {
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


    private static TaskStatus readTaskStatus(Scanner sc) {
        System.out.println("""
                Select the new status:
                1 - pending
                2 - in progress
                3 - completed
                """);

        int option;
        try {
            option = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            return null;
        }
        return switch (option) {
            case 1 -> TaskStatus.PENDING;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.COMPLETED;
            default -> null;
        };
    }
}