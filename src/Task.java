import java.util.Date;
public class Task {
  private String description;
  private boolean completed;
  private int priority;
  private Date dueDate;


  public Task(String description) {
    this.description = description;
    this.completed = false;
    this.priority = 0;
  }

  public Task(String description, int priority) {
    this.description = description;
    this.completed = false;
    this.priority = priority;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }
  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public String toString() {
    String priorityLabel;
    if (priority == 1) {
      priorityLabel = "\u001B[33mНе важно\u001B[0m ";
    } else if (priority == 2) {
      priorityLabel = "\u001B[32mВажно\u001B[0m ";
    } else if (priority == 3) {
      priorityLabel = "\u001B[31mПовышенная важность\u001B[0m ";
    } else {
      priorityLabel = "Нет приоритета";
    }

    String completedLabel = completed ? "\u001B[31mВыполнено\u001B[0m" : "\u001B[34mНе выполнено\u001B[0m";

    return description + " " + "[" + completedLabel + "] [Приоритет: " + priorityLabel + "] " ;
  }
}
