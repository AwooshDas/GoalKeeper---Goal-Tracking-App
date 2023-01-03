package com.example.testgoal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testgoal.MainActivity;
import com.example.testgoal.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AddGoalDescriptionActivity extends AppCompatActivity {
    private ArrayList<String> descriptions;
    private String newGoalDescription;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readDescriptions();
        setContentView(R.layout.activity_addgoaldescription);
        Intent returnToMain = new Intent(this, MainActivity.class);
        returnToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(
                v -> {
                    startActivity(returnToMain);
                });
        Button saveButton = findViewById(R.id.save_button);
        Bundle addGoalDescription = getIntent().getExtras();
        EditText description = findViewById(R.id.description);
        //Map<String, String> map = new HashMap<>();
        saveButton.setOnClickListener(
                v -> {
                    onAddGoalDescription(v);
                    startActivity(returnToMain);

                });
                //v -> {
                    //onAddGoalDescription(View v);
                    //newGoalDescription = description.getText().toString();
                    //map.put(addGoalDescription.getString("Goal Name"), newGoalDescription);
                    //savedInstanceState.putString("Goal Description", newGoalDescription);
                    //descriptions.add(newGoalDescription);
                    //super.onSaveInstanceState(savedInstanceState);
                    //tartActivity(returnToMain);
               // });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Text", newGoalDescription);
        super.onSaveInstanceState(savedInstanceState);
    }
    private void readDescriptions() {
        File filesDir = getExternalFilesDir(newGoalDescription);
        File todoFile = new File(filesDir, "descriptions.txt");
        try {
            descriptions = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            descriptions = new ArrayList<>();
        }
    }
    private void writeDescriptions() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "descriptions.txt");
        try {
            FileUtils.writeLines(todoFile, descriptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onAddGoalDescription(View v) {
        EditText etDes = (EditText) findViewById(R.id.description);
        newGoalDescription = etDes.getText().toString();
        descriptions.add(newGoalDescription);
        etDes.setText("");
        writeDescriptions();
    }
}

