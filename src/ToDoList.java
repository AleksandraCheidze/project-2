import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

  public static void main(String[] args) {
    ArrayList<String> tasks = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Выберите действие:");
      System.out.println("1. Добавить задачу");
      System.out.println("2. Показать задачи");
      System.out.println("3. Выход");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Считываем символ новой строки после числа

      switch (choice) {
        case 1 -> {
          System.out.println("Введите задачу:");
          String task = scanner.nextLine();
          tasks.add(task);
          System.out.println("Задача добавлена.");
        }
        case 2 -> {
          if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
          } else {
            System.out.println("Список задач:");
            for (int i = 0; i < tasks.size(); i++) {
              System.out.println((i + 1) + ". " + tasks.get(i));
            }
          }
        }
        case 3 -> {
          System.out.println("Выход.");
          scanner.close();
          return;
        }
        default -> System.out.println("Неверный выбор.");
      }
    }
  }
}
