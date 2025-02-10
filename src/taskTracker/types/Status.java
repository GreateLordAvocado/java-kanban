package taskTracker.types;

public enum Status {
    NEW("Новое"),
    IN_PROGRESS("Выполняется"),
    DONE("Выполнено");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getName() {
        return statusName;
    }
}

