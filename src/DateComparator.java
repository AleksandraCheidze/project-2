import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateComparator {
  public static void showClosestDueDates(List<Task> tasks) {
    tasks.sort(Comparator.comparing(Task::getDueDate));

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    System.out.println("Ближайшие даты выполнения задач:");

    Date currentDate = new Date(); // Текущая дата и время

    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      if (task.getDueDate().after(currentDate)) { // Если дата выполнения после текущей даты
        String dueDateString = dateFormat.format(task.getDueDate());
        System.out.println((i + 1) + ". " + task + " (до " + dueDateString + ")");
      }
    }
  }
}
