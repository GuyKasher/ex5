package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditToDoItemActivity extends AppCompatActivity {

    public TodoItemsDataBaseImpl dataBase = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        Intent intent = getIntent();
        int curId = -1;
        if (intent.hasExtra("item id")) {
            curId = intent.getIntExtra("item id", -1);
        }
        EditText editTextEditTask = findViewById(R.id.editTextInsertTask);
        FloatingActionButton editButton = findViewById(R.id.edit_button);
        TextView desText = findViewById(R.id.item_description);
        TextView createdDate = findViewById(R.id.created_date);
        TextView lastModifiedDate = findViewById(R.id.last_modified);
        Button progressButton = findViewById(R.id.inProgress);


        dataBase = ToDoItemApplication.getInstance().getDataBase();
        TodoItem curTodoItem = dataBase.getById(curId);

        desText.setText(curTodoItem.getDescription());
        createdDate.setText("created time: "+curTodoItem.getDateString());
        dataBase.setLastModified(curId);
        lastModifiedDate.setText("last modified: "+curTodoItem.getLastModifiedString());


        editTextEditTask.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                String newText = editTextEditTask.getText().toString();
            }
            // text did change
        });


        int finalCurId = curId;
        editButton.setOnClickListener(v -> {
            String userInputString = editTextEditTask.getText().toString();
            if (!userInputString.equals("")) {
                dataBase.editToDoItem(finalCurId, userInputString);
                finish();
            }
        });
        progressButton.setOnClickListener(v -> {
            if (curTodoItem.status == Status.NOTDONE) {
                dataBase.markItemInProgress(curTodoItem);
                progressButton.setBackgroundColor(Color.YELLOW);
                progressButton.setText("In Pro");

            } else if (curTodoItem.status == Status.INPROGRASS) {

                dataBase.markItemDone(curTodoItem);
                progressButton.setBackgroundColor(Color.GREEN);
                progressButton.setText("Done");

            } else {
                dataBase.markItemNotDone(curTodoItem);
                progressButton.setBackgroundColor(Color.RED);
                progressButton.setText("Not Done");

            }


        });
    }
}
