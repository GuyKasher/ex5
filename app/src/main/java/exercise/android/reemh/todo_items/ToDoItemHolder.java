package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoItemHolder extends RecyclerView.ViewHolder {
    TextView description;
    Button deleteButton;
    Button progressButton;
    Button editButton;


    public ToDoItemHolder(@NonNull View itemView) {
        super(itemView);
        this.description = itemView.findViewById(R.id.description_text);
        this.deleteButton=itemView.findViewById(R.id.deleteButton);
        this.progressButton=itemView.findViewById(R.id.inProgress);
        this.editButton=itemView.findViewById(R.id.description_button);

    }
}
