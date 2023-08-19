import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TaskManagerTest {

//  @Test
//  void testDeleteTask() {
//    TaskManager taskManager = new TaskManager();
//    taskManager.getTasks().add(new Task("Задача 1"));
//    taskManager.getTasks().add(new Task("Задача 2"));
//    taskManager.getTasks().add(new Task("Задача 3"));
//
//    String input = "2\n";
//    Scanner scanner = new Scanner(input);
//    taskManager.deleteTask(scanner);
//
//    assertEquals(2, taskManager.getTasks().size());
//    assertEquals("Задача 1", taskManager.getTasks().get(0).getDescription());
//    assertEquals("Задача 3", taskManager.getTasks().get(1).getDescription());
//  }

  @Test
  public void testGetUserChoiceValidInput() {
    TaskManager taskManager = new TaskManager();
    String input = "42";
    Scanner scanner = new Scanner(input);
    int choice = taskManager.getUserChoice(scanner);
    assertEquals(42, choice);
  }

  //@Test
//  public void testAddTaskValidInput() {
//    String input = "Sample Task\n10.08.2023\n";
//    Scanner scanner = new Scanner(input);
//    TaskManager taskManager = new TaskManager();
//    taskManager.addTask(scanner);
//    assertEquals(2, taskManager.getTasks().size());
//    assertEquals("Sample Task", taskManager.getTasks().get(0).getDescription());
//
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//    String dueDateString = dateFormat.format(taskManager.getTasks().get(0).getDueDate());
//    assertEquals("10.08.2023", dueDateString);
//  }
//
//
  @Test
  public void testAddTaskEmptyDescription() {
    String input = "\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());

    InputStream originalSystemIn = System.in;

    System.setIn(inputStream);

    TaskManager taskManager = new TaskManager();

    try {
      Scanner scanner = new Scanner(System.in);
      taskManager.addTask(scanner);

      assertTrue(taskManager.getTasks().isEmpty());

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.setIn(originalSystemIn);
    }
  }

//  @Test
//  public void testAddTaskInvalidDateFormat() {
//    String input = "Sample Task\ninvalid_date\n";
//    Scanner scanner = new Scanner(input);
//    TaskManager taskManager = new TaskManager();
//    taskManager.addTask(scanner);
//    assertEquals(0, taskManager.getTasks().size());
//  }

//
//  @Test
//  public void testMarkTaskAsCompletedValidInput() {
//    TaskManager taskManager = new TaskManager();
//    Task task1 = new Task("Task 1");
//    Task task2 = new Task("Task 2");
//    taskManager.getTasks().add(task1);
//    taskManager.getTasks().add(task2);
//    String input = "1\n";
//    Scanner scanner = new Scanner(input);
//    taskManager.markTaskAsCompleted(scanner);
//    assertTrue(task1.isCompleted());
//    assertFalse(task2.isCompleted());
//  }
//

  @Test
  public void testMarkTaskAsCompletedInvalidInput() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    taskManager.getTasks().add(task1);
    String input = "0\n";
    Scanner scanner = new Scanner(input);
    taskManager.markTaskAsCompleted(scanner);
    assertFalse(task1.isCompleted());
  }

  //    @Test
//  public void testMarkTasksByPriority() {
//    TaskManager taskManager = new TaskManager();
//    Task task1 = new Task("Task 1");
//    Scanner scanner = null;
//    taskManager.getTasks().add(task1);
//    String input = "1\n2\n";
//    scanner = new Scanner(input);
//    taskManager.markTasksByPriority(scanner);
//    assertEquals(2, task1.getPriority());
//  }
//
//  @Test
//  public void testLoadTasks() throws ParseException {
//    TaskManager taskManager = new TaskManager();
//    String input = "Sample Task|false|2|10.08.2023\n";
//    Scanner scanner = new Scanner(input);
//
//    // Copy the list of tasks
//    ArrayList<Task> tasks = new ArrayList<>(taskManager.getTasks());
//
//    assertEquals(1, tasks.size());
//
//    Task loadedTask = tasks.get(0);
//    assertEquals("Sample Task", loadedTask.getDescription());
//    assertFalse(loadedTask.isCompleted());
//    assertEquals(2, loadedTask.getPriority());
//
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//    Date dueDate = dateFormat.parse("10.08.2023");
//    assertEquals(dueDate, loadedTask.getDueDate());
//  }
//
//
//      @Test
//  public void testSaveTasks() throws IOException {
//    TaskManager taskManager = new TaskManager();
//    Task task1 = new Task("Task 1");
//    task1.setCompleted(true);
//    task1.setPriority(2);
//    Task task2 = new Task("Task 2");
//    task2.setPriority(1);
//    taskManager.getTasks().add(task1);
//    taskManager.getTasks().add(task2);
//
//
//    File tempFile = File.createTempFile("testTasks", ".txt");
//    String filePath = tempFile.getAbsolutePath();
//
//    try (FileWriter writer = new FileWriter(tempFile)) {
//      taskManager.saveTasks(writer);
//    }
//
//
//    String savedContent = Files.readString(Path.of(filePath));
//
//    String expectedContent = "Task 1|true|2|Нет даты\n" +
//        "Task 2|false|1|Нет даты\n";
//
//    assertEquals(expectedContent, savedContent);
//  }
  @Test
  public void testFormatDateWithDate() throws ParseException {
    TaskManager taskManager = new TaskManager();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Date dueDate = dateFormat.parse("10.08.2023");
    String formattedDate = taskManager.formatDate(dueDate);
    assertEquals("10.08.2023", formattedDate);
  }

  @Test
  public void testFormatDateWithNull() {
    TaskManager taskManager = new TaskManager();
    String formattedDate = taskManager.formatDate(null);
    assertEquals("Нет даты", formattedDate);
  }
}