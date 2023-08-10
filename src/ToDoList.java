import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

  private static final String FILE_PATH = "tasks.txt";

  public static void main(String[] args) {
    ArrayList<String> tasks = loadTasks();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      printMenu();
      int choice = getUserChoice(scanner);

      switch (choice) {
        case 1:
          addTask(tasks, scanner);
          break;
        case 2:
          showTasks(tasks);
          break;
        case 3:
          markTaskAsCompleted(tasks, scanner);
          break;
        case 4:
          saveTasks(tasks);
          System.out.println("Выход.");
          scanner.close();
          return;
        default:
          System.out.println("Неверный выбор.");
      }
    }
  }

  private static void printMenu() {
    System.out.println("\u001B[33m╔════════════════════════════════════════════╗\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m           \u001B[36mМенеджер задач ToDoList\u001B[0m          \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m╠════════════════════════════════════════════╣\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m1. Добавить задачу\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m2. Показать задачи\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m3. Отметить задачу как выполненную\u001B[0m       \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m║\u001B[0m   \u001B[35m4. Выход\u001B[0m                                 \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m╚════════════════════════════════════════════╝\u001B[0m");
  }




  private static int getUserChoice(Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Неверный ввод. Введите число.");
      return -1;
    }
  }

  private static void addTask(ArrayList<String> tasks, Scanner scanner) {
    System.out.println("Введите задачу:");
    String task = scanner.nextLine();
    if (!task.isEmpty()) {
      tasks.add(task);
      System.out.println("Задача добавлена.");
    } else {
      System.out.println("Пустая задача не может быть добавлена.");
    }
  }

  private static void markTaskAsCompleted(ArrayList<String> tasks, Scanner scanner) {
    showTasks(tasks);
    System.out.println("Введите номер задачи для отметки как выполненной:");
    int completedTaskIndex = getUserChoice(scanner);
    if (completedTaskIndex >= 1 && completedTaskIndex <= tasks.size()) {
      tasks.set(completedTaskIndex - 1, "[Выполнено] " + tasks.get(completedTaskIndex - 1));
      System.out.println("Задача отмечена как выполненная.");
    } else {
      System.out.println("Неверный номер задачи.");
    }
  }


  private static ArrayList<String> loadTasks() {
    ArrayList<String> tasks = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
      String line;
      while ((line = reader.readLine()) != null) {
        tasks.add(line);
      }
      reader.close();
    } catch (IOException e) {
      // Если файл не найден, просто вернем пустой список
    }
    return tasks;
  }

  private static void saveTasks(ArrayList<String> tasks) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
      for (String task : tasks) {
        writer.write(task);
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void showTasks(ArrayList<String> tasks) {
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
