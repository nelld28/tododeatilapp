package com.example.tododetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int IS_SUCCESS = 0;
    private String[] mTodos;
    private int mTodoIndex = 0;
    private static final String TODO_INDEX = "todoIndex";

    public static final String TAG = "TodoActivity";

    public static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        final TextView TodoTextView;
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);

        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todos);

        TodoTextView.setText(mTodos[mTodoIndex]);

        Button buttonNext;
        buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTodoIndex += 1;
                TodoTextView.setText(mTodos[mTodoIndex]);
            }
        });

        Button buttonPrevious;
        buttonPrevious = (Button) findViewById(R.id.buttonPrev);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoIndex = mTodoIndex - 1;
                TodoTextView.setText(mTodos[mTodoIndex]);
            }
        });

        Button buttonDetail;
        buttonDetail = (Button) findViewById(R.id.buttonDetail);
        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = detailActivity.newIntent(MainActivity.this, mTodoIndex);
                startActivityForResult(intent, IS_SUCCESS);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == IS_SUCCESS){
            if (intent !=null){
                boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);
                updateTodoComplete(isTodoComplete);
            }else
            {
                Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(this, R.string.request_code_mismatch, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTodoComplete(boolean is_todo_complete) {
        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        if (is_todo_complete) {
            textViewTodo.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(
                    ContextCompat.getColor(this, R.color.colorSuccess));

            setTextViewComplete("\u2713");
        }
    }

    private void setTextViewComplete(String message) {
        final TextView textViewComplete;
        textViewComplete = (TextView) findViewById(R.id.textViewComplete);
        textViewComplete.setText(message);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
    }
}