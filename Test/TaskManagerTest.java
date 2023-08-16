import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    String input = "42\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    TaskManager taskManager = new TaskManager();
    int choice = taskManager.getUserChoice();

    assertEquals(42, choice);
  }

  //addTask
  @Test
  public void testAddTaskValidInput() {
    String input = "Sample Task\n10.08.2023\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    TaskManager taskManager = new TaskManager();
    taskManager.setScanner(new Scanner(System.in));

    taskManager.addTask();

    assertEquals(1, taskManager.getTasks().size()); // Assuming 1 task is added
    assertEquals("Sample Task", taskManager.getTasks().get(0).getDescription());

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dueDateString = dateFormat.format(taskManager.getTasks().get(0).getDueDate());
    assertEquals("10.08.2023", dueDateString);
  }

  @Test
  public void testAddTaskEmptyDescription() {
    String input = "\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    TaskManager taskManager = new TaskManager();
    taskManager.setScanner(new Scanner(System.in));

    taskManager.addTask();

    assertEquals(0, taskManager.getTasks().size());
  }

  @Test
  public void testAddTaskInvalidDateFormat() {
    String input = "Sample Task\ninvalid_date\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    TaskManager taskManager = new TaskManager();
    taskManager.setScanner(new Scanner(System.in));

    taskManager.addTask();

    assertEquals(0, taskManager.getTasks().size());
  }

  //markTaskAsCompleted()
  @Test
  public void testMarkTaskAsCompletedValidInput() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    Task task2 = new Task("Task 2");
    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);

    String input = "1\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    taskManager.setScanner(new Scanner(System.in));
    taskManager.markTaskAsCompleted();

    assertTrue(task1.isCompleted());
    assertFalse(task2.isCompleted());
  }

  @Test
  public void testMarkTaskAsCompletedInvalidInput() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    taskManager.getTasks().add(task1);

    String input = "0\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    taskManager.setScanner(new Scanner(System.in));
    taskManager.markTaskAsCompleted();

    assertFalse(task1.isCompleted());
  }

  @Test
  public void testMarkTasksByPriority() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    taskManager.getTasks().add(task1);

    String input = "1\n2\n";
    InputStream originalSystemIn = System.in;

    try {
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      taskManager.setScanner(new Scanner(System.in));

      taskManager.markTasksByPriority();

      assertEquals(2, task1.getPriority());
    } finally {
      System.setIn(originalSystemIn);
    }
  }



}







