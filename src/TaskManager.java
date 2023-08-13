import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        case 1 -> addTask();
        case 2 -> showTasks();
        case 3 -> markTasksByPriority();
        case 4 -> showTasksByPriority();
        case 5 -> markTaskAsCompleted();
        case 6 -> showUncompletedTasks();
        case 7 -> deleteTask();
        case 8 -> showTasksByDueDate();
        case 9 -> {
          saveTasks();
          System.out.println("Выход.");
          scanner.close();
          return;
        }
        default -> System.err.println("Неверный выбор.");
      }
    }
  }

  private void deleteTask() {
    showTasks();
    System.out.println("Введите номер задачи для удаления:");
    int taskIndex = getUserChoice() - 1;

    if (taskIndex >= 0 && taskIndex < tasks.size()) {
      Task removedTask = tasks.remove(taskIndex);
      System.out.println("Задача удалена: " + removedTask.getDescription());
    } else {
      System.err.println("Неверный номер задачи.");
    }
  }

  private void printMenu() {
    System.out.println("\u001B[33m╔════════════════════════════════════════════╗\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m           \u001B[36mМенеджер задач ToDoList\u001B[0m          \u001B[33m║\u001B[0m");
    System.out.println("\u001B[33m╠════════════════════════════════════════════╣\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m1. Добавить задачу\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m2. Показать задачи\u001B[0m                       \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m3. Отметить задачи по приоритету\u001B[0m       \u001B[33m  ║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m4. Показать задачи по приоритету\u001B[0m       \u001B[33m  ║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m5. Отметить задачу как выполненную\u001B[0m       \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m6. Показать только невыполненные задачи\u001B[0m  \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m7. Удалить задачу\u001B[0m                      \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m8. Сортировать задачи по дате 'до которой надо выполнить'\u001B[0m   \u001B[33m║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m9. Сохранить и выйти\u001B[0m                  \u001B[33m   ║\u001B[0m");
    System.out.println("\u001B[33m╚════════════════════════════════════════════╝\u001B[0m");
  }

  private int getUserChoice() {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.err.println("Неверный ввод. Введите число.");
      return -1;
    }
  }

  private void addTask() {
    System.out.println("Введите задачу:");
    String taskDescription = scanner.nextLine().trim(); // Удаляем лишние пробелы в начале и конце строки

    if (!taskDescription.isEmpty()) {
      Task task = new Task(taskDescription);

      System.out.println("Введите дату 'до которой надо выполнить' (формат: дд.мм.гггг):");
      String dateString = scanner.nextLine();
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      try {
        Date dueDate = dateFormat.parse(dateString);
        task.setDueDate(dueDate);
      } catch (ParseException e) {
        System.out.println("Неверный формат даты.");
        return;
      }

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
      System.err.println("Неверный номер задачи.");
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
        System.out.println("Введите приоритет для задачи:\n" +
            "1 - \u001B[33mне важно\u001B[0m\n" +
            "2 - \u001B[32mважно\u001B[0m\n" +
            "3 - \u001B[31mповышенная важность\u001B[0m");
        int priority = getUserChoice();
        if (priority >= 1 && priority <= 3) {
          task.setPriority(priority);
          System.out.println("Приоритет добавлен.");
        } else {
          System.err.println("Неверный приоритет. Введите число от 1 до 3.");
        }
      } else {
        System.err.println("Невозможно задать приоритет для выполненной задачи.");
      }
    } else {
      System.err.println("Неверный номер задачи.");
    }
  }

  private void showTasksByPriority() {
    Collections.sort(tasks, new PriorityComparator());
    System.out.println("Задачи отсортированы по приоритету:");
    showTasks();
  }

  private void showTasksByDueDate() {
    Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
    System.out.println("Задачи отсортированы по дате 'до которой надо выполнить':");
    showTasks();
  }

  private void saveTasks() {
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
      for (Task task : tasks) {
        writer.write(
            task.getDescription() + "|" + task.isCompleted() + "|" + task.getPriority() + "\n");
      }
      System.out.println("Задачи сохранены.");
    } catch (IOException e) {
      System.err.println("Произошла ошибка при сохранении задач: " + e.getMessage());
    }
  }

  private void showTasks() {
    if (tasks.isEmpty()) {
      System.err.println("Список задач пуст.");
    } else {
      System.out.println("Список задач:");
      for (int i = 0; i < tasks.size(); i++) {
        System.out.println((i + 1) + ". " + tasks.get(i));
      }
    }
  }
}