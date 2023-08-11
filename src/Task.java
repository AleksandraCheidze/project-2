public class Task {
  private String description;
  private boolean completed;
  private int priority;

  public Task(String description) {
    this.description = description;
    this.completed = false;
    this.priority = 1; // По умолчанию - не важно
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

  @Override
  public String toString() {
    String priorityLabel;
    if (priority == 1) {
      priorityLabel = "Не важно";
    } else if (priority == 2) {
      priorityLabel = "Важно";
    } else if (priority == 3) {
      priorityLabel = "Повышенная важность";
    } else {
      priorityLabel = "Неверный приоритет";
    }

    String completedLabel = completed ? "Выполнено" : "Не выполнено";

    return "[" + completedLabel + "] [Приоритет: " + priorityLabel + "] " + description;
  }
}
