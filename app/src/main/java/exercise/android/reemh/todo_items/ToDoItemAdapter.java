package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemHolder> {
    @NonNull



    private TodoItemsDataBase todoItemsDataBase=ToDoItemApplication.getInstance().getDataBase();

    public ToDoItemAdapter(@NonNull TodoItemsDataBase todoItemsDataBaseImpl){
        this.todoItemsDataBase=  todoItemsDataBaseImpl;
    }

//    public void setToDo(ArrayList<TodoItem> _to_do_items){
//        this._to_do_items.clear();
//        this._to_do_items.addAll(_to_do_items);
//        notifyDataSetChanged();
//    }

    @Override
    public ToDoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.row_todo_item,parent,false);
        return new ToDoItemHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemHolder holder, int position) {
        TodoItem _to_do= this.todoItemsDataBase.getCurrentItems().get(position);
        if (_to_do.status==Status.INPROGRASS){
            holder.progressButton.setBackgroundColor(Color.YELLOW);
            holder.progressButton.setText("In Pro");

        }
        else if (_to_do.status==Status.DONE) {

            holder.progressButton.setBackgroundColor(Color.GREEN);
            holder.progressButton.setText("Done");
        }
        else  {

            holder.progressButton.setBackgroundColor(Color.RED);
            holder.progressButton.setText("Not Done");
        }

        holder.description.setText(_to_do.description);
        holder.deleteButton.setOnClickListener(v -> {
//      Intent intentToOpenService = new Intent(MainActivity.this, CalculateRootsService.class);

            todoItemsDataBase.deleteItem(_to_do);
//      System.out.println(to_do_items.get(0).description);
            this.todoItemsDataBase.sortItems();

            this.notifyDataSetChanged();

        });

        holder.progressButton.setOnClickListener(v->{
            if (_to_do.status==Status.NOTDONE){
                this.todoItemsDataBase.markItemInProgress(_to_do);
                holder.progressButton.setBackgroundColor(Color.YELLOW);
                holder.progressButton.setText("In Pro");

            }
            else if (_to_do.status==Status.INPROGRASS) {

                todoItemsDataBase.markItemDone(_to_do);
                holder.progressButton.setBackgroundColor(Color.GREEN);
                holder.progressButton.setText("Done");

            }
            else {
                this.todoItemsDataBase.getCurrentItems().get(position).status = Status.NOTDONE;
                holder.progressButton.setBackgroundColor(Color.RED);
                holder.progressButton.setText("Not Done");

            }
//            this.todoItemsDataBase.sortItems();

            this.notifyDataSetChanged();
        });

        holder.editButton.setOnClickListener(v -> {
//      Intent intentToOpenService = new Intent(MainActivity.this, CalculateRootsService.class);
            Intent intentToOpenSuccess = new Intent(this, EditToDoItemActivity.class);
            intentToOpenSuccess.putExtra("root1",num1);
            intentToOpenSuccess.putExtra("root2",num2);
            intentToOpenSuccess.putExtra("original_number",original_number);
            intentToOpenSuccess.putExtra("time",time);
            startActivity(intentToOpenSuccess);
            todoItemsDataBase.deleteItem(_to_do);
//      System.out.println(to_do_items.get(0).description);
            this.todoItemsDataBase.sortItems();

            this.notifyDataSetChanged();

        });


    }

    @Override
    public int getItemCount() {
        return this.todoItemsDataBase.getCurrentItems().size();
    }
}
