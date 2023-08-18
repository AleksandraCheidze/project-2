import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DateComparatorTest {

  @Test
  public void testShowClosestDueDates() throws ParseException {
    List<Task> tasks = new ArrayList<>();

    Task task1 = new Task("Task 1");
    task1.setDueDate(new SimpleDateFormat("dd.MM.yyyy").parse("20.08.2023"));
    task1.setPriority(1);
    task1.setCompleted(false);

    Task task2 = new Task("Task 2");
    task2.setDueDate(new SimpleDateFormat("dd.MM.yyyy").parse("25.08.2023"));
    task2.setPriority(2);
    task2.setCompleted(false);

    Task task3 = new Task("Task 3");
    task3.setDueDate(new SimpleDateFormat("dd.MM.yyyy").parse("15.08.2023"));
    task3.setPriority(3);
    task3.setCompleted(true);

    tasks.add(task1);
    tasks.add(task2);
    tasks.add(task3);

  }
}
