package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

enum Status {
    NOTDONE, DONE, INPROGRASS;
}

public class TodoItem implements Serializable {
    // TODO: edit this class as you want
//
    String description;
    Status status;
    static int counter = 0;
    int id;
    Date createTime;
    Date lastModified;
    String lastModifiedString;

    //    boolean done;
    public TodoItem() {
        this.description = "";
        this.status = Status.NOTDONE;
        this.id = counter;
        createTime=new Date();
        lastModified=new Date();
        lastModifiedString="";
    }


    public TodoItem(String description) {
        this.description = description;
        this.status = Status.NOTDONE;
        this.id = counter;
        createTime=new Date();
        lastModified=new Date();
        lastModifiedString="";

        counter += 1;
    }
//TODO!!!!!!!!!!!!!!!!!!!!!!!!
    public String itemToString() {
        return String.valueOf(id) + "#" + description + "#" + String.valueOf(status)+"#"+createTime;
    }

    public TodoItem stringToItem(String itemString) {
        try {
            String[] split = itemString.split("#");
            int id = Integer.parseInt(split[0]);
            String des = split[1];
            Status status;
            switch (split[2]) {
                case "NOTDONE":
                    status = Status.NOTDONE;
                    break;
                case "DONE":
                    status = Status.DONE;
                    break;
                default:
                    status = Status.INPROGRASS;
                    break;
            }
            TodoItem newToSOItem = new TodoItem(des);
            newToSOItem.setId(id);
            newToSOItem.setStatus(status);
            return newToSOItem;
        } catch (Exception e) {
            return null;
        }
    }

    public void setLastModifiedString(String lastModifiedString) {
        this.lastModifiedString = lastModifiedString;
    }

    public String getLastModifiedString() {
        return lastModifiedString;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public String getDateString(){
        return new SimpleDateFormat("HH:mm dd MMM yyyy").format(getCreateTime());
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
