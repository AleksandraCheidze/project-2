import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {

  @Test
  public void testDeleteTask() {
    // Создание экземпляра TaskManager
    TaskManager taskManager = new TaskManager();

    // Создание задач и добавление в список
    Task task1 = new Task("Task 1");
    Task task2 = new Task("Task 2");
    taskManager.getTasks().add(task1);
    taskManager.getTasks().add(task2);

    // Подготовка ввода: создание Scanner с использованием строки "1"
    String input = "1\n";
    Scanner scanner = new Scanner(input);

    // Установка Scanner для ввода в TaskManager
    taskManager.setScanner(scanner);

    // Вызов метода deleteTask
    taskManager.deleteTask();

    // Проверка результатов
    assertEquals(1, taskManager.getTasks().size());
    assertEquals("Task 2", taskManager.getTasks().get(0).getDescription());
  }
}
