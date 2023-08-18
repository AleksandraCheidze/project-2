import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityComparatorTest {

  @Test
  void testPriorityComparator() {
    List<Task> tasks = new ArrayList<>();

    Task task1 = createTask("Task 1", 1, new Date(1630000000000L), false);
    Task task2 = createTask("Task 2", 2, new Date(1631000000000L), false);
    Task task3 = createTask("Task 3", 3, new Date(1632000000000L), true);

    tasks.add(task1);
    tasks.add(task2);
    tasks.add(task3);

    tasks.sort(new PriorityComparator());

    assertEquals(task3, tasks.get(0));
    assertEquals(task2, tasks.get(1));
    assertEquals(task1, tasks.get(2));
  }

  private Task createTask(String description, int priority, Date dueDate, boolean completed) {
    Task task = new Task(description);
    task.setDueDate(dueDate);
    task.setPriority(priority);
    task.setCompleted(completed);
    return task;
  }
}






