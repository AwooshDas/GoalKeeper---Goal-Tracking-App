package com.example.testgoal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> goals;
    public ArrayAdapter<String> goalsAdapter;
    public ListView lvGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvGoals = (ListView) findViewById(R.id.lvGoals);
        goals = new ArrayList<>();
        readGoals();
        goalsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, goals);
        lvGoals.setAdapter(goalsAdapter);
        Button addNewGoal = (Button) findViewById(R.id.btnAddGoal);
        addNewGoal.setOnClickListener(
                view -> onAddGoal(view)
        );
        setupListViewListener();
        singleClick();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void setupListViewListener() {
        lvGoals.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    // Remove the goal within array at position
                    goals.remove(pos);
                    // Refresh the adapter
                    goalsAdapter.notifyDataSetChanged();
                    writeGoals();

                    // Return true consumes the long click event (marks it handled)
                    return true;
                });
    }
    private void singleClick() {
        lvGoals.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                                   View goal, int pos, long id) {
                        Intent launchAddGoalDescription = new Intent(goal.getContext().getApplicationContext(), AddGoalDescriptionActivity.class);
                        int pos1 = pos;
                        String goal1 = goals.get(pos1);
                        launchAddGoalDescription.putExtra("Goal Name", goal1);
                        startActivity(launchAddGoalDescription);
                    }

                });
    }
    public void onAddGoal(View v) {
        EditText etNewGoal = (EditText) findViewById(R.id.etNewGoal);
        String goalText = etNewGoal.getText().toString();
        goalsAdapter.add(goalText);
        etNewGoal.setText("");
        writeGoals();
    }
    private void readGoals() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            goals = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            goals = new ArrayList<>();
        }
    }
    private void writeGoals() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, goals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}