package com.example.android.splitfeatures.Utils;

public class UserSettings {

    private User user;
    private UserWorkoutSplit workoutSplit;

    public UserSettings(User user, UserWorkoutSplit workoutSplit) {
        this.user = user;
        this.workoutSplit = workoutSplit;
    }

    public UserSettings() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserWorkoutSplit getWorkoutSplit() {
        return workoutSplit;
    }

    public void setSettings(UserWorkoutSplit workoutSplit) {
        this.workoutSplit = workoutSplit;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", workoutSplit=" + workoutSplit +
                '}';
    }
}