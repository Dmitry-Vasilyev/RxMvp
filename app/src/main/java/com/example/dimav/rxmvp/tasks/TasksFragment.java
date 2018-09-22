package com.example.dimav.rxmvp.tasks;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dimav.rxmvp.R;
import com.example.dimav.rxmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksFragment extends Fragment{

    TasksAdapter mTasksAdapter;


    public TasksFragment() {

    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Task> tasks = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            tasks.add(new Task("Title" + (i+1), getResources().getString(R.string.lorem10)));
        }
        Observable.range(1, 10)
                .flatMap(num -> Observable.just(
                        new Task("Lambda" + (num),
                                getResources().getString(R.string.lorem10))))
                .subscribe(tasks::add);


        mTasksAdapter = new TasksAdapter(tasks);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tasks_fragment,
                container, false);

        //Set up mTasks view

        ListView listView = root.findViewById(R.id.tasks_list);
        //TODO setAdapter
        listView.setAdapter(mTasksAdapter);

        FloatingActionButton fabAdd = getActivity().findViewById(R.id.fab_add_task);

        fabAdd.setImageResource(R.drawable.ic_add_white_24dp);
        //TODO set fabAdd onClickListener

        FloatingActionButton fabDelAll = getActivity().findViewById(R.id.fab_del_all);

        fabDelAll.setImageResource(R.drawable.ic_delete_white_24dp);
        //TODO set fabDelAll onClickListener

        return root;
    }

    private static class TasksAdapter extends BaseAdapter {

        private List<Task> mTasks;

        public TasksAdapter(List<Task> tasks) {
            setList(tasks);
        }

        private void setList(List<Task> tasks) {
            mTasks = checkNotNull(tasks);
        }

        public void replaceData(List<Task> tasks) {
            setList(tasks);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

        @Override
        public Task getItem(int position) {
            return mTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if(rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                rowView = inflater.inflate(R.layout.task_item, parent, false);
            }

            final Task task = getItem(position);

            TextView titleTV = rowView.findViewById(R.id.title);
            titleTV.setText(task.getTitle());
            TextView textTV = rowView.findViewById(R.id.text);
            textTV.setText(task.getText());

            return rowView;
        }
    }
}
