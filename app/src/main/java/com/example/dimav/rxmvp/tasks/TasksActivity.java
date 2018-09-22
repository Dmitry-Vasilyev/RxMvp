package com.example.dimav.rxmvp.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dimav.rxmvp.R;
import com.example.dimav.rxmvp.util.ActivityUtils;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.contentFrame);

        if(tasksFragment == null) {
            //Create the fragment
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }
    }
}
