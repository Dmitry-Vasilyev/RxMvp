package com.example.dimav.rxmvp.tasks;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dimav.rxmvp.R;
import com.example.dimav.rxmvp.addtask.AddTaskActivity;
import com.example.dimav.rxmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksFragment extends Fragment implements TasksContract.View {

    TasksAdapter mTasksAdapter;

    TasksContract.Presenter mPresenter;

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
        listView.setAdapter(mTasksAdapter);

        FloatingActionButton fabAdd = getActivity().findViewById(R.id.fab_add_task);
        fabAdd.setImageResource(R.drawable.ic_add_white_24dp);
        fabAdd.setOnClickListener(__ -> mPresenter.addNewTask());

        FloatingActionButton fabDelAll = getActivity().findViewById(R.id.fab_del_all);
        fabDelAll.setImageResource(R.drawable.ic_delete_white_24dp);
        fabDelAll.setOnClickListener(__ -> mPresenter.deleteAllTasks());


        //Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        //Set the scrolling view in the custom SwipeRefreshLayout
        swipeRefreshLayout.setScrollUpChild(listView);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadTasks());

        return root;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if(getView() == null) return;

        final SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);

        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mTasksAdapter.replaceData(tasks);
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddTaskActivity.class);
        startActivityForResult(intent, AddTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_task_error));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private static class TasksAdapter extends BaseAdapter {

        private List<Task> mTasks;

        private TasksAdapter(List<Task> tasks) {
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
