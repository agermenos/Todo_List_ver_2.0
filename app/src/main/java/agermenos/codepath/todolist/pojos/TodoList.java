package agermenos.codepath.todolist.pojos;

import java.util.Date;

/**
 * Created by Alejandro on 10/13/15.
 */
public class TodoList {
    private Integer id;
    private String name;
    private Date creationDate;

    public TodoList(Integer id, String name){
        this.id=id;
        this.creationDate = new Date();
        this.name = name;
    }

    public TodoList(String name) {
        this.creationDate = new Date();
        this.name = name;
    }

    public TodoList() {
        this.creationDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoList todoList = (TodoList) o;

        if (id != todoList.id) return false;
        if (!name.equals(todoList.name)) return false;
        return creationDate.equals(todoList.creationDate);

    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32);
        result = 31 * result + name.hashCode();
        result = 31 * result + creationDate.hashCode();
        return result;
    }
}
