import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskManager {

  private static final String FILE_PATH = "tasks.txt";
  private static final int ADD_TASK_OPTION = 1;
  private static final int SHOW_TASKS_OPTION = 2;
  private static final int MARK_TASKS_BY_PRIORITY_OPTION = 3;
  private static final int SHOW_TASKS_BY_PRIORITY_OPTION = 4;
  private static final int MARK_TASK_AS_COMPLETED_OPTION = 5;
  private static final int SHOW_UNCOMPLETED_TASKS_OPTION = 6;
  private static final int DELETE_TASK_OPTION = 7;
  private static final int SORT_TASKS_BY_DATE_OPTION = 8;
  private static final int SAVE_AND_EXIT_OPTION = 9;
  private final ArrayList<Task> tasks;
  private final Scanner scanner;
  private FileWriter writer;

  public TaskManager() {
    this.tasks = new ArrayList<>();
    this.scanner = new Scanner(System.in);
    loadTasks();
  }

  private void initializeWriter() {
    try {
      this.writer = new FileWriter(FILE_PATH);
    } catch (Exception e) {
      System.out.println("Произошла ошибка при выполнении операции: " + e.getMessage());
    }
  }

  private void closeWriter() {
    try {
      if (writer != null) {
        writer.close();
      }
    } catch (IOException e) {
      System.err.println("Ошибка при закрытии FileWriter: " + e.getMessage());
    }
  }

  public void start() {
    while (true) {
      printMenu();
      int choice = getUserChoice(scanner);

      switch (choice) {
        case ADD_TASK_OPTION -> addTask(scanner);
        case SHOW_TASKS_OPTION -> showTasks(tasks);
        case MARK_TASKS_BY_PRIORITY_OPTION -> markTasksByPriority(scanner);
        case SHOW_TASKS_BY_PRIORITY_OPTION -> showTasksByPriority();
        case MARK_TASK_AS_COMPLETED_OPTION -> markTaskAsCompleted(scanner);
        case SHOW_UNCOMPLETED_TASKS_OPTION -> showUncompletedTasks();
        case DELETE_TASK_OPTION -> deleteTask(scanner);
        case SORT_TASKS_BY_DATE_OPTION -> DateComparator.showClosestDueDates(tasks);
        case SAVE_AND_EXIT_OPTION -> {
          initializeWriter();
          saveTasks(writer);
          closeWriter();
          System.out.println("Выход.");
          scanner.close();
          return;
        }
      }
    }
  }

  void deleteTask(Scanner scanner) {
    showTasks(tasks);
    System.out.println("Введите номер задачи для удаления:");
    int taskIndex = getUserChoice(this.scanner) - 1;

    if (taskIndex >= 0 && taskIndex < tasks.size()) {
      Task removedTask = tasks.remove(taskIndex);
      System.out.println("Задача удалена: " + removedTask.getDescription());
    } else {
      System.err.println("Неверный номер задачи.");
    }
  }

  void printMenu() {
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
        "\u001B[33m║\u001B[0m   \u001B[35m7. Удалить задачу\u001B[0m                      \u001B[33m  ║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m8. Сортировать задачи по дате       \u001B[0m   \u001B[33m  ║\u001B[0m");
    System.out.println(
        "\u001B[33m║\u001B[0m   \u001B[35m9. Сохранить и выйти\u001B[0m                  \u001B[33m   ║\u001B[0m");
    System.out.println("\u001B[33m╚════════════════════════════════════════════╝\u001B[0m");
  }

  int getUserChoice(Scanner scanner) {
    try {
      int choice = scanner.nextInt();
      scanner.nextLine();
      return choice;
    } catch (InputMismatchException e) {
      System.err.println("Неверный ввод. Введите число." + e.getMessage());
      scanner.nextLine();
      return -1;
    }
  }

  void addTask(Scanner scanner) {
    while (true) {
      System.out.println("Введите задачу:");
      String taskDescription = scanner.nextLine();

      if (!taskDescription.isEmpty()) {
        Task task = new Task(taskDescription);

        while (true) {
          System.out.println("Введите дату 'до которой надо выполнить' (формат: дд.мм.гггг):");
          String dateString = scanner.nextLine();
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

          try {
            Date dueDate = dateFormat.parse(dateString);
            task.setDueDate(dueDate);
            tasks.add(task);
            System.out.println("Задача добавлена.");
            return;
          } catch (ParseException e) {
            System.out.println("Неверный формат даты. Попробуйте снова.");
          }
        }
      } else {
        System.out.println("Пустая задача не может быть добавлена. Попробуйте снова.");
      }
    }
  }

  void markTaskAsCompleted(Scanner scanner) {
    showTasks(tasks);
    System.out.println("Введите номер задачи для отметки как выполненной:");
    int completedTaskIndex = getUserChoice(scanner);
    if (completedTaskIndex >= 1 && completedTaskIndex <= tasks.size()) {
      Task task = tasks.get(completedTaskIndex - 1);
      task.setCompleted(true);
      System.out.printf("Задача номер %d отмечена как выполненная.%n", completedTaskIndex);
    } else {
      System.err.println("Неверный номер задачи.");
    }
  }

  void showUncompletedTasks() {
    System.out.println("Список невыполненных задач:");
    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      if (!task.isCompleted()) {
        System.out.println((i + 1) + ". " + task);
      }
    }
  }

  void markTasksByPriority(Scanner scanner) {
    showTasks(tasks);
    System.out.println("Введите номер задачи для добавления приоритета:");
    int selectedTaskIndex = getUserChoice(scanner) - 1;
    if (selectedTaskIndex >= 0 && selectedTaskIndex < tasks.size()) {
      Task task = tasks.get(selectedTaskIndex);
      if (!task.isCompleted()) {
        System.out.println("Введите приоритет для задачи:\n" +
            "1 - \u001B[33mне важно\u001B[0m\n" +
            "2 - \u001B[32mважно\u001B[0m\n" +
            "3 - \u001B[31mповышенная важность\u001B[0m");
        int priority = getUserChoice(scanner);
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

  void loadTasks() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("\\|");
        if (parts.length == 4) {
          String description = parts[0];
          boolean completed = Boolean.parseBoolean(parts[1]);
          int priority = Integer.parseInt(parts[2]);
          Date dueDate = parseDate(parts[3]);

          Task task = new Task(description);
          task.setCompleted(completed);
          task.setPriority(priority);
          task.setDueDate(dueDate);

          tasks.add(task);
        }
      }
      System.out.println("Задачи загружены.");
    } catch (IOException e) {
      System.err.println("Произошла ошибка при загрузке задач: " + e.getMessage());
    }
  }

  Date parseDate(String dateString) {
    try {
      return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }

  void showTasksByPriority() {
    Collections.sort(tasks, new PriorityComparator());
    System.out.println("Задачи отсортированы по приоритету:");
    showTasks(tasks);
  }

  void saveTasks(FileWriter writer) {
    try  {
      for (Task task : tasks) {
        writer.write(
            task.getDescription() + "|" + task.isCompleted() + "|" + task.getPriority() + "|"
                + formatDate(task.getDueDate()) + "\n");
      }
      System.out.println("Задачи сохранены.");
    } catch (IOException e) {
      System.err.println("Произошла ошибка при сохранении задач: " + e.getMessage());
    }
  }

  void showTasks(ArrayList<Task> tasks) {
    if (tasks.isEmpty()) {
      System.err.println("Список задач пуст.");
    } else {
      System.out.println("Список задач:");
      for (int i = 0; i < tasks.size(); i++) {
        System.out.println(
            (i + 1) + ". " + tasks.get(i) + " [Дедлайн: " + formatDate(tasks.get(i).getDueDate())
                + "]");
      }
    }
  }

  String formatDate(Date date) {
    if (date != null) {
      return new SimpleDateFormat("dd.MM.yyyy").format(date);
    } else {
      return "Нет даты";
    }
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }
}
