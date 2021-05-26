package exercise.android.reemh.todo_items;


import java.util.Comparator;

public class comparatorToDOItems implements Comparator<TodoItem> {

    @Override
    public int compare(TodoItem o1, TodoItem o2) {
        if (o1.status==o2.status){
            return o2.id-o1.id;
        }
        else {
            if(o1.status==Status.INPROGRASS && o2.status==Status.NOTDONE){
                return -1;
            }
            else if (o1.status==Status.INPROGRASS && o2.status==Status.DONE){
                return -1;
            }
            else if (o1.status==Status.NOTDONE && o2.status==Status.DONE){
                return -1;
            }
            else {
                return 1;
            }

        }
    }
}