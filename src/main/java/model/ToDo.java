package model;

public class ToDo extends Task{
    public ToDo(String label) {
        super(label);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a parsable string of the todo task. Meant to be used for storage purposes.
     *
     * @return a parsable string representation of the task and all its details
     */
    @Override
    public String parsableString() {
        return String.format("T,%s", super.parsableString());
    }
}
