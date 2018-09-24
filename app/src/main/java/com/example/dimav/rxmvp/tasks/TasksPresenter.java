package com.example.dimav.rxmvp.tasks;

import android.support.annotation.NonNull;

import com.example.dimav.rxmvp.data.TasksRepository;
import com.example.dimav.rxmvp.util.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;


public class TasksPresenter implements TasksContract.Presenter {

    @NonNull
    private final TasksContract.View mTasksView;

    @NonNull
    private final TasksRepository mTasksRepository;

    @NonNull
    private final BaseSchedulerProvider mBaseSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksContract.View tasksView,
                          @NonNull TasksRepository tasksRepository,
                          @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        mTasksView = checkNotNull(tasksView,
                "tasksView cannot be null!");
        mTasksRepository = checkNotNull(tasksRepository,
                "tasksRepository cannot be null!");
        mBaseSchedulerProvider = checkNotNull(baseSchedulerProvider,
                "baseSchedulerProvider cannot be null!");

        mCompositeDisposable = new CompositeDisposable();
        mTasksView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTasks() {

    }

    private void loadTasks(final boolean showLoadingUI) {
        if(showLoadingUI) mTasksView.setLoadingIndicator(true);

        mCompositeDisposable.clear();
    }

    @Override
    public void addNewTask() {

    }

    @Override
    public void deleteAllTasks() {

    }
}
