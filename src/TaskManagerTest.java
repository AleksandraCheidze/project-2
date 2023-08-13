import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {

  @Test
  public void testDeleteTask() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    Task task2 = new Task("Task 2");
    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);
    String input = "1\n";
    Scanner scanner = new Scanner(input);

    taskManager.setScanner(scanner);
    taskManager.deleteTask();

    assertEquals(1, taskManager.getTasks().size());
    assertEquals("Task 2", taskManager.getTasks().get(0).getDescription());
  }


  @Test
  public void testGetUserChoiceValidInput() {
    String input = "42\n"; // Ввод пользователя
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in); // Перенаправляем System.in для ввода данных

    TaskManager taskManager = new TaskManager();
    int choice = taskManager.getUserChoice();

    assertEquals(42, choice); // Проверяем, что результат совпадает с ожидаемым значением
  }
}

