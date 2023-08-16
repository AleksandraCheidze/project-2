import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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



  @Test
  public void testAddTaskValidInputWithDifferentDateFormats() {
    String input = "Sample Task\n10.08.2023\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    TaskManager taskManager = new TaskManager();
    taskManager.setScanner(new Scanner(System.in));

    taskManager.addTask();

    assertEquals(1, taskManager.getTasks().size());
    assertEquals("Sample Task", taskManager.getTasks().get(0).getDescription());

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dueDateString = dateFormat.format(taskManager.getTasks().get(0).getDueDate());
    assertEquals("10.08.2023", dueDateString);
  }

  @Test
  public void testShowTasksByDueDate() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    Task task2 = new Task("Task 2");

    // Set different due dates for tasks
    task1.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
    task2.setDueDate(new Date(System.currentTimeMillis() + 172800000)); // Day after tomorrow

    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    taskManager.showTasksByDueDate();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    String expectedOutput = "Tasks sorted by due date:\n" +
        "1. Task 1 - " + dateFormat.format(task1.getDueDate()) + "\n" +
        "2. Task 2 - " + dateFormat.format(task2.getDueDate()) + "\n" +
        "1. Task 1 [[32mНе выполнено[0m] [Приоритет: Нет приоритета]  (до " + dateFormat.format(task1.getDueDate()) + ")\n" +
        "2. Task 2 [[32mНе выполнено[0m] [Приоритет: Нет приоритета]  (до " + dateFormat.format(task2.getDueDate()) + ")\n";

    assertEquals(expectedOutput, outContent.toString());
  }
}