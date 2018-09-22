package com.example.dimav.rxmvp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable model class for a Task
 */

public class Task {

    @NonNull
    private final String mId;

    @NonNull
    private final String mTitle;

    @Nullable
    private final String mText;


    public Task(@NonNull String title, @Nullable String text) {
        this(UUID.randomUUID().toString(), title, text);
    }

    /**
     * For copy
     *
     * @param id
     * @param title
     * @param text
     */
    public Task(@NonNull String id, @NonNull String title, @Nullable String text) {
        this.mId = id;
        this.mTitle = title;
        this.mText = text;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getText() {
        return mText;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle)&&
                Strings.isNullOrEmpty(mText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mText, task.mText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mText);
    }

    @Override
    public String toString() {
        return "Task{" +
                "mTitle='" + mTitle + '\'' +
                '}';
    }
}
