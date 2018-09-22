package com.example.dimav.rxmvp.tasks;

import com.example.dimav.rxmvp.BasePresenter;
import com.example.dimav.rxmvp.BaseView;
import com.example.dimav.rxmvp.data.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showSuccessfullySavedMessage();

        void showLoadingTasksError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadTasks();

        void addNewTask();

        void deleteAllTasks();
    }
}
