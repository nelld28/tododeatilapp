package com.example.tododetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class detailActivity extends AppCompatActivity {
    private int mTodoIndex;
    private String[] todoDetails;

    public static Intent newIntent(Context packageContext, int mTodoIndex) {
        Intent intent = new Intent(packageContext, detailActivity.class);
        intent.putExtra(TODO_INDEX, mTodoIndex);
        return intent;
    }

    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";
    private static final String TODO_INDEX = "com.example.todoIndex";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState != null) {
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        Resources res = getResources();
        todoDetails = res.getStringArray(R.array.todos);

        int mTodoIndex = getIntent().getIntExtra(TODO_INDEX, 0);
        updateTextViewTodoDetail(mTodoIndex);

        CheckBox checkboxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);

        checkboxIsComplete.setOnClickListener(mTodoListener);

    }

    private void updateTextViewTodoDetail(int mTodoIndex) {
        final TextView textViewTodoDetail;
        textViewTodoDetail = (TextView) findViewById(R.id.textViewTodoDetail);

        textViewTodoDetail.setText(todoDetails[mTodoIndex]);

    }

    private View.OnClickListener mTodoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId() /*to get clicked view object id**/) {
                case R.id.checkBoxIsComplete:
                    CheckBox checkboxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);
                    setIsComplete(checkboxIsComplete.isChecked());
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void setIsComplete(boolean isChecked){
        if(isChecked){
            Toast.makeText(detailActivity.this,
                    "Finally done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(detailActivity.this,
                    "There is always tomorrow!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.putExtra(IS_TODO_COMPLETE, isChecked);
        setResult(RESULT_OK, intent);
    }
}