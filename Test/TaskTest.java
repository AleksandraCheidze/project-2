import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
  @Test
  public void testToStringNoPriority() {
    Task task4 = new Task("No Priority Task");
    task4.setPriority(0);
    task4.setCompleted(false);

    String expectedOutput = "No Priority Task [\u001B[32mНе выполнено\u001B[0m] [Приоритет: Нет приоритета] ";
    String actualOutput = task4.toString();

    assertEquals(expectedOutput, actualOutput);
  }
}
