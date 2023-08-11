import java.util.Comparator;

public class PriorityComparator implements Comparator<Task> {
  @Override
  public int compare(Task task1, Task task2) {
    int priority1 = task1.getPriority();
    int priority2 = task2.getPriority();


    if (task1.isCompleted() && !task2.isCompleted()) {
      return 1;
    }
    if (!task1.isCompleted() && task2.isCompleted()) {
      return -1;
    }
    return Integer.compare(priority1, priority2);
  }
}

