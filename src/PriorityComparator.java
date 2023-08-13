import java.util.Comparator;
import java.util.Date;

public class PriorityComparator implements Comparator<Task> {
  @Override
  public int compare(Task task1, Task task2) {
    Date dueDate1 = task1.getDueDate();
    Date dueDate2 = task2.getDueDate();

    int dateComparison = dueDate1.compareTo(dueDate2);
    if (dateComparison != 0) {
      return dateComparison;
    }

    int priority1 = task1.getPriority();
    int priority2 = task2.getPriority();

    if (task2.isCompleted() && !task1.isCompleted()) {
      return 1;
    }
    if (!task2.isCompleted() && task1.isCompleted()) {
      return -1;
    }
    return Integer.compare(priority2, priority1);
  }
}
