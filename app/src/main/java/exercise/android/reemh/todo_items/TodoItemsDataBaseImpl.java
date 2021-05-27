package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

// TODO: implement!
public class TodoItemsDataBaseImpl implements TodoItemsDataBase, Serializable {

    ArrayList<TodoItem> items = new ArrayList<>();
    private final Context context;
//    private final MutableLiveData<List<TodoItem>> toDoItemLiveDataMutable = new MutableLiveData<>();
//    public LiveData<List<TodoItem>> toDoItemLiveDataPublic = toDoItemLiveDataMutable;
    private SharedPreferences sharedPreferences;

    public TodoItemsDataBaseImpl(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("local_db_toDoItems", Context.MODE_PRIVATE);
        initialize();

    }

    public void initialize() {
        Set<String> keys = sharedPreferences.getAll().keySet();
        for (String k : keys) {
            String itemString = sharedPreferences.getString(k, null);
            TodoItem todoItem = new TodoItem().stringToItem(itemString);
            if (todoItem != null) {
                items.add(todoItem);
            }
        }

//        toDoItemLiveDataMutable.setValue(new ArrayList(items));
    }

    @Override
    public List<TodoItem> getCurrentItems() {
        return new ArrayList<>(items);
    }

    @Override
    public void sortItems() {
        Collections.sort(getCurrentItems(), new comparatorToDOItems());

    }

    public void editToDoItem(int itemId, String newDescription) {
        TodoItem oldItem = getById(itemId);
        if (oldItem == null) return;
        TodoItem newItem = new TodoItem(newDescription);
        newItem.setId(oldItem.getId());
        items.remove(oldItem);
        items.add(newItem);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(newItem.getId()), newItem.itemToString());
        editor.apply();

//        toDoItemLiveDataMutable.setValue(new ArrayList(items));

    }

    private TodoItem getById(int itemId) {
        for (TodoItem toDoItem : items) {
            if (toDoItem.getId() == itemId) {
                return toDoItem;
            }
        }
        return null;
    }


    @Override
    public void addNewInProgressItem(String description) {
        TodoItem newItem = new TodoItem(description);
        this.items.add(newItem);
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(newItem.getId()), newItem.itemToString());
        editor.apply();


//        toDoItemLiveDataMutable.setValue(new ArrayList(items));


    }

    @Override
    public void markItemDone(TodoItem item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.DONE;
            }
        }
        sortItems();
//        toDoItemLiveDataMutable.setValue(new ArrayList(items));


    }

    @Override
    public void markItemInProgress(TodoItem item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.INPROGRASS;
            }
        }
        sortItems();
//        toDoItemLiveDataMutable.setValue(new ArrayList(items));


    }

    @Override
    public void deleteItem(TodoItem item) {
//    for (int i=0;i<items.size();i++){
//      if (item.description.equals(items.get(i).description)){
        this.items.remove(item);
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(item.getId()));
        editor.apply();

//        toDoItemLiveDataMutable.setValue(new ArrayList(items));


        //      }
//    }
    }
}
