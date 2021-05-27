package exercise.android.reemh.todo_items;

import android.app.Application;

public class ToDoItemApplication extends Application {
    private TodoItemsDataBaseImpl dataBase;
    public TodoItemsDataBaseImpl getDataBase(){
        return dataBase;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        this.dataBase=new TodoItemsDataBaseImpl(this);
    }

    private static ToDoItemApplication instance=null;
    public static ToDoItemApplication getInstance(){
        return instance;
    }
}
