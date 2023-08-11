import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TaskManager {
  private static final String FILE_PATH = "tasks.txt";
  private ArrayList<Task> tasks;
  private Scanner scanner;

  public TaskManager() {
    tasks = new ArrayList<>();
    scanner = new Scanner(System.in);
  }

  public void start() {
    while (true) {
      printMenu();
      int choice = getUserChoice();

      switch (choice) {
        case 1:
          addTask();
          break;
        case 2:
          showTasks();
          break;
        case 3:
          markTasksByPriority();
          break;
        case 4:
          showTasksByPriority();
          break;
        case 5:
          markTaskAsCompleted();
          break;
        case 6:
          showUncompletedTasks();
          break;
        case 7:
          saveTasks();
          System.out.println("Выход.");
          scanner.close();
          return;
        default:
          System.out.println("Неверный выбор.");
      }
    }
  }

  private void printMenu() {
    System.out.println("\u001B[33m╔════════════════════════════════════════════╗\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m           \u001B[36mМенеджер задач ToDoList\u001B[0m          \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m╠════════════════════════════════════════════╣\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m1. Добавить задачу\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m2. Показать задачи\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m3. Отметить задачи по приоритету\u001B[0m       \u001B[33m  ║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m4. Показать задачи по приоритету\u001B[0m       \u001B[33m  ║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m5. Отметить задачу как выполненную\u001B[0m       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m6. Показать только невыполненные задачи\u001B[0m  \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m7. Сохранить и выйти\u001B[0m                  \u001B[33m   ║\u001B[0m");
    System.out.println("\u001B[33m╚════════════════════════════════════════════╝\u001B[0m");
  }


  private int getUserChoice() {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Неверный ввод. Введите число.");
      return -1;
    }
  }

  private void addTask() {
    System.out.println("Введите задачу:");
    String taskDescription = scanner.nextLine();
    if (!taskDescription.isEmpty()) {
      Task task = new Task(taskDescription);
      tasks.add(task);
      System.out.println("Задача добавлена.");
    } else {
      System.out.println("Пустая задача не может быть добавлена.");
    }
  }

  private void markTaskAsCompleted() {
    showTasks();
    System.out.println("Введите номер задачи для отметки как выполненной:");
    int completedTaskIndex = getUserChoice();
    if (completedTaskIndex >= 1 && completedTaskIndex <= tasks.size()) {
      Task task = tasks.get(completedTaskIndex - 1);
      task.setCompleted(true);
      System.out.printf("Задача номер %d отмечена как выполненная.%n", completedTaskIndex);
    } else {
      System.out.println("Неверный номер задачи.");
    }
  }

  private void showUncompletedTasks() {
    System.out.println("Список невыполненных задач:");
    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      if (!task.isCompleted()) {
        System.out.println((i + 1) + ". " + task);
      }
    }
  }

  private void markTasksByPriority() {
    showTasks();
    System.out.println("Введите номер задачи для добавления приоритета:");
    int selectedTaskIndex = getUserChoice() - 1;
    if (selectedTaskIndex >= 0 && selectedTaskIndex < tasks.size()) {
      Task task = tasks.get(selectedTaskIndex);
      if (!task.isCompleted()) {
        System.out.println("Введите приоритет для задачи: 1 - не важно, 2 - важно, 3 - повышенная важность");
        int priority = getUserChoice();
        if (priority >= 1 && priority <= 3) {
          task.setPriority(priority);
          System.out.println("Приоритет добавлен.");
        } else {
          System.out.println("Неверный приоритет. Введите число от 1 до 3.");
        }
      } else {
        System.out.println("Невозможно задать приоритет для выполненной задачи.");
      }
    } else {
      System.out.println("Неверный номер задачи.");
    }
  }


  private void showTasksByPriority() {
    Collections.sort(tasks, new PriorityComparator());
    System.out.println("Задачи отсортированы по приоритету:");
    showTasks();
  }

  private void saveTasks() {
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
      for (Task task : tasks) {
        writer.write(task.getDescription() + "|" + task.isCompleted() + "|" + task.getPriority() + "\n");
      }
      System.out.println("Задачи сохранены.");
    } catch (IOException e) {
      System.out.println("Произошла ошибка при сохранении задач: " + e.getMessage());
    }
  }


  private void showTasks() {
    if (tasks.isEmpty()) {
      System.out.println("Список задач пуст.");
    } else {
      System.out.println("Список задач:");
      for (int i = 0; i < tasks.size(); i++) {
        System.out.println((i + 1) + ". " + tasks.get(i));
      }
    }
  }
}
