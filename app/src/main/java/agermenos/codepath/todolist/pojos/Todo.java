package agermenos.codepath.todolist.pojos;

import java.util.Date;

/**
 * Created by Alejandro on 10/11/2015.
 */
public class Todo {
    private long id;
    private long listId;
    private String text;
    private Date creationDate;
    private String status;
    public final static String OPEN_STATUS="open";
    public final static String CLOSED_STATUS="closed";
    public enum Priority {
        LOW ("low"), MEDIUM("medium"), HIGH("high");
        private String strPriority;
        Priority(String priority){
            this.strPriority=priority;
        }
        public String getPriority(){return this.strPriority;}
    }
    private Priority priority;

    public static Priority choosePriority(String priority){
        switch (priority){
            case "low":return Priority.LOW;
            case "medium": return Priority.MEDIUM;
            case "high": return Priority.HIGH;
        }
        return Priority.LOW;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getListId() {
        return listId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public Todo() {
    }

    public Todo(long listId, String text, Date creationDate, String status, Priority priority) {
        this.text = text;
        this.listId=listId;
        this.creationDate = creationDate;
        this.status = status;
        this.priority=priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (listId != todo.listId) return false;
        if (text != null ? !text.equals(todo.text) : todo.text != null) return false;
        if (!creationDate.equals(todo.creationDate)) return false;
        if (!priority.equals(todo.priority)) return false;
        return status.equals(todo.status);

    }

    @Override
    public int hashCode() {
        long result = id;
        result = 31 * result + listId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + priority.hashCode();
        return (int)result;
    }
}
