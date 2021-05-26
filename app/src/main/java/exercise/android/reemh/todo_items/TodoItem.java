package exercise.android.reemh.todo_items;

import java.io.Serializable;
enum Status
{
    NOTDONE, DONE, INPROGRASS;
}
public class TodoItem implements Serializable {
  // TODO: edit this class as you want
//
    String description;
    Status status;
    static int counter=0;
    int id;
    //    boolean done;
    public TodoItem(){
        this.description="";
        this.status=Status.NOTDONE;
        this.id=counter;
    }


    public TodoItem(String description){
        this.description=description;
        this.status=Status.NOTDONE;
        this.id=counter;
        counter+=1;
    }



}
