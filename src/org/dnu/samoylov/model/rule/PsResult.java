package org.dnu.samoylov.model.rule;

public class PsResult {

    private static int NEXT_RESULT_ID = 0;
    private static final int DEFAULT_PRIORITY = 0;
    private final String message;
    private final int priority;
    private final int subPriority;
    private final int id;

    public PsResult(String message, int subPriority) {
        this.message = message;
        this.priority = DEFAULT_PRIORITY;
        this.subPriority = subPriority;
        id = NEXT_RESULT_ID++;
    }

    public PsResult(String message, int priority, int subPriority) {
        id = NEXT_RESULT_ID++;
        this.message = message;
        this.priority = priority;
        this.subPriority = subPriority;
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }

    public int getSubPriority() {
        return subPriority;
    }

    public int getId() {
        return id;
    }



    public String getFullPriority() {
        return String.valueOf(priority)
                + "."  + String.valueOf(subPriority)
                + "."  + String.valueOf(id);
    }

    @Override
    public String toString() {
        return getMessage() + " " + getFullPriority();
    }

    public static PsResult create(String message, int subPriority) {
        return new PsResult(message, subPriority);
    }
    public static PsResult create(String message, int priority, int subPriority) {
        return new PsResult(message, priority, subPriority);
    }
}
