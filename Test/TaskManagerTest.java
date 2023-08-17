import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    assertEquals(1, taskManager.getTasks().size());
    assertEquals("Task 2", taskManager.getTasks().get(0).getDescription());
  }


  @Test
  public void testGetUserChoiceValidInput() {
    TaskManager taskManager = new TaskManager();
    String input = "42\n";
    Scanner scanner = new Scanner(input);
    taskManager.deleteTask(scanner);
    int choice = taskManager.getUserChoice();
    assertEquals(42, choice);
  }

  @Test
  public void testAddTaskValidInput() {
    TaskManager taskManager = new TaskManager();
    String input = "Sample Task\n10.08.2023\n";
    Scanner scanner = new Scanner(input);
    taskManager.addTask(scanner);
    assertEquals(1, taskManager.getTasks().size());
    assertEquals("Sample Task", taskManager.getTasks().get(0).getDescription());

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dueDateString = dateFormat.format(taskManager.getTasks().get(0).getDueDate());
    assertEquals("10.08.2023", dueDateString);
  }

  @Test
  public void testAddTaskEmptyDescription() {
    TaskManager taskManager = new TaskManager();
    String input = "\n";
    Scanner scanner = new Scanner(input);
    taskManager.addTask(scanner);
    assertEquals(0, taskManager.getTasks().size());
  }

  @Test
  public void testAddTaskInvalidDateFormat() {
    TaskManager taskManager = new TaskManager();
    String input = "Sample Task\ninvalid_date\n";
    Scanner scanner = new Scanner(input);
    taskManager.addTask(scanner);
    assertEquals(0, taskManager.getTasks().size());
  }

  @Test
  public void testMarkTaskAsCompletedValidInput() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    Task task2 = new Task("Task 2");
    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);
    String input = "1\n";
    Scanner scanner = new Scanner(input);
    taskManager.markTaskAsCompleted(scanner);
    assertTrue(task1.isCompleted());
    assertFalse(task2.isCompleted());
  }

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

  @Test
  public void testMarkTasksByPriority() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    Scanner scanner = null;
    taskManager.getTasks().add(task1);
    String input = "1\n2\n";
    scanner = new Scanner(input);
    taskManager.markTasksByPriority(scanner);
    assertEquals(2, task1.getPriority());
  }
  @Test
  public void testLoadTasks() throws ParseException {
    TaskManager taskManager = new TaskManager();
    String input = "Sample Task|false|2|10.08.2023\n";
    Scanner scanner = new Scanner(input);
    ArrayList<Task> tasks = taskManager.getTasks();
    assertEquals(1, tasks.size());

    Task loadedTask = tasks.get(0);
    assertEquals("Sample Task", loadedTask.getDescription());
    assertFalse(loadedTask.isCompleted());
    assertEquals(2, loadedTask.getPriority());

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Date dueDate = dateFormat.parse("10.08.2023");
    assertEquals(dueDate, loadedTask.getDueDate());
  }

  @Test
  public void testSaveTasks() throws IOException {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Task 1");
    task1.setCompleted(true);
    task1.setPriority(2);
    Task task2 = new Task("Task 2");
    task2.setPriority(1);
    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);


    File tempFile = File.createTempFile("testTasks", ".txt");
    String filePath = tempFile.getAbsolutePath();

    try (FileWriter writer = new FileWriter(tempFile)) {
      taskManager.saveTasks(writer);
    }


    String savedContent = Files.readString(Path.of(filePath));

    String expectedContent = "Task 1|true|2|Нет даты\n" +
        "Task 2|false|1|Нет даты\n";

    assertEquals(expectedContent, savedContent);
  }
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