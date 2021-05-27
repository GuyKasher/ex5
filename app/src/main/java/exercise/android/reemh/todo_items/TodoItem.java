package exercise.android.reemh.todo_items;

import java.io.Serializable;

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

    //    boolean done;
    public TodoItem() {
        this.description = "";
        this.status = Status.NOTDONE;
        this.id = counter;
    }


    public TodoItem(String description) {
        this.description = description;
        this.status = Status.NOTDONE;
        this.id = counter;
        counter += 1;
    }

    public String itemToString() {
        return String.valueOf(id) + "#" + description + "#" + String.valueOf(status);
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
