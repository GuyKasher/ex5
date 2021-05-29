package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

// TODO: implement!
public class TodoItemsDataBaseImpl implements TodoItemsDataBase, Serializable {

    ArrayList<TodoItem> items = new ArrayList<>();
    private static Context context = null;

    private static final MutableLiveData<List<TodoItem>> toDoItemLiveDataMutable = new MutableLiveData<>();
    public static final LiveData<List<TodoItem>> toDoItemLiveDataPublic = toDoItemLiveDataMutable;

    private static SharedPreferences sharedPreferences = null;

    public TodoItemsDataBaseImpl(Context context) {
        TodoItemsDataBaseImpl.context = context;
        sharedPreferences = context.getSharedPreferences("local_db_toDoItems", Context.MODE_PRIVATE);
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

        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));
    }

    public LiveData<List<TodoItem>> getToDoItemLiveDataPublic() {
        return toDoItemLiveDataPublic;
    }

    @Override
    public List<TodoItem> getCurrentItems() {
        return this.items;
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
        newItem.setStatus(oldItem.getStatus());
        newItem.setLastModifiedString(getModifiedTimeDifference(oldItem.getLastModified(),new Date()));
        newItem.setLastModified(new Date());
        items.remove(oldItem);
        items.add(newItem);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(newItem.getId()), newItem.itemToString());
        editor.apply();

        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));

    }

    public void setLastModified(int itemId){
        TodoItem item=getById(itemId);
        item.setLastModifiedString(getModifiedTimeDifference(item.getLastModified(),new Date()));
    }

    public TodoItem getById(int itemId) {
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


        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));


    }

    @Override
    public void markItemDone(TodoItem item) {
        int itemIndex = -1;
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.DONE;
                itemIndex = i;
                break;

            }
        }
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(item.getId()), this.items.get(itemIndex).itemToString());
        editor.apply();

        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));


    }

    public void markItemNotDone(TodoItem item) {
        int itemIndex = -1;
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.NOTDONE;
                itemIndex = i;
                break;

            }
        }
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(item.getId()), this.items.get(itemIndex).itemToString());
        editor.apply();

        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));


    }

    @Override
    public void markItemInProgress(TodoItem item) {
        int itemIndex = -1;
        for (int i = 0; i < this.items.size(); i++) {
            if (item.description.equals(this.items.get(i).description)) {
                this.items.get(i).status = Status.INPROGRASS;
                itemIndex = i;
                break;

            }
        }
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(item.getId()), this.items.get(itemIndex).itemToString());
        editor.apply();
        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));


    }

    @Override
    public void deleteItem(TodoItem item) {
//    for (int i=0;i<items.size();i++){
//      if (item.description.equals(items.get(i).description)){
        this.items.remove(item);
        sortItems();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(item.getId()));
//        editor.clear();
        editor.apply();

        toDoItemLiveDataMutable.setValue(new ArrayList<>(items));


    }

    public String getModifiedTimeDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if (elapsedDays == 0 && elapsedHours == 0) {
            return elapsedMinutes + " minutes ago";
        } else if (elapsedDays == 0 && elapsedHours > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            return "Today at " + calendar.get(Calendar.HOUR_OF_DAY);

        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            return calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." +
                    calendar.get(Calendar.DATE) + "at" + calendar.get(Calendar.HOUR_OF_DAY);
        }
    }
}
