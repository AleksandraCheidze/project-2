import java.util.Comparator;
import java.util.Date;

public class PriorityComparator implements Comparator<Task> {
  @Override
  public int compare(Task task1, Task task2) {
    Date dueDate1 = task1.getDueDate();
    Date dueDate2 = task2.getDueDate();

    int dateComparison = dueDate2.compareTo(dueDate1); // Обратное сравнение дат
    if (dateComparison != 0) {
      return dateComparison;
    }

    int priority1 = task1.getPriority();
    int priority2 = task2.getPriority();

    if (task1.isCompleted() && !task2.isCompleted()) { // Поменяли местами task1 и task2
      return 1;
    }
    if (!task1.isCompleted() && task2.isCompleted()) { // Поменяли местами task1 и task2
      return -1;
    }
    return Integer.compare(priority1, priority2); // Обратное сравнение приоритетов
  }
}