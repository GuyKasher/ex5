package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: implement!
public class TodoItemsDataBaseImpl implements TodoItemsDataBase, Serializable {

    ArrayList<TodoItem> items = new ArrayList<>();


    @Override
    public List<TodoItem> getCurrentItems() {
        return this.items;
    }
    @Override
    public void sortItems(){
        Collections.sort(getCurrentItems(), new comparatorToDOItems());

    }

    @Override
    public void addNewInProgressItem(String description) {
        this.items.add(new TodoItem(description));
        sortItems();


    }

    @Override
    public void markItemDone(TodoItem item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.DONE;
            }
        }
        sortItems();



    }

    @Override
    public void markItemInProgress(TodoItem item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.INPROGRASS;
            }
        }
        sortItems();


    }

    @Override
    public void deleteItem(TodoItem item) {
//    for (int i=0;i<items.size();i++){
//      if (item.description.equals(items.get(i).description)){
        this.items.remove(item);
        sortItems();


        //      }
//    }
    }
}
