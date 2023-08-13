import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

public class TaskManagerTest {

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
