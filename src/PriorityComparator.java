import java.util.Comparator;

public class PriorityComparator implements Comparator<Task> {
  @Override
  public int compare(Task task1, Task task2) {
    int priority1 = task1.getPriority();
    int priority2 = task2.getPriority();

    // Выполненные задачи всегда идут в конце списка
    if (task1.isCompleted() && !task2.isCompleted()) {
      return 1;
    }
    if (!task1.isCompleted() && task2.isCompleted()) {
      return -1;
    }

    // Сортировка по приоритету
    if (priority1 < priority2) {
      return -1;
    } else if (priority1 > priority2) {
      return 1;
    } else {
      return 0;
    }
  }
}

