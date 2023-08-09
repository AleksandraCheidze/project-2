import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class ToDoList {
  private static final String FILE_PATH = "tasks.txt";
  public static void main(String[] args) {
    ArrayList<String> tasks = loadTasks();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Выберите действие:");
      System.out.println("1. Добавить задачу");
      System.out.println("2. Показать задачи");
      System.out.println("3. Отметить задачу как выполненную");
      System.out.println("4. Выход");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Считываем символ новой строки после числа
      switch (choice) {
        case 1:
          System.out.println("Введите задачу:");
          String task = scanner.nextLine();
          tasks.add(task);
          System.out.println("Задача добавлена.");
          break;
        case 2:
          showTasks(tasks);
          break;
        case 3:
          showTasks(tasks);
          System.out.println("Введите номер задачи для отметки как выполненной:");
          int completedTaskIndex = scanner.nextInt();
          if (completedTaskIndex >= 1 && completedTaskIndex <= tasks.size()) {
            tasks.set(completedTaskIndex - 1, "[Выполнено] " + tasks.get(completedTaskIndex - 1));
            System.out.println("Задача отмечена как выполненная.");
          } else {
            System.out.println("Неверный номер задачи.");
          }
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