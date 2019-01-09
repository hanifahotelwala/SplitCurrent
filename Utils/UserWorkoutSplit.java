package com.example.android.splitfeatures.Utils;

public class UserWorkoutSplit {

    private String user_id;
    private String workout;
    private String reps;
    private String sets;


    public UserWorkoutSplit(String user_id, String workout, String sets, String reps) {
        this.user_id = user_id;
        this.workout = workout;
        this.sets = sets;
        this.reps = reps;
    }

    public UserWorkoutSplit(String workout, String sets, String reps) {

        this.workout = workout;
        this.sets = sets;
        this.reps = reps;

    }

    public UserWorkoutSplit() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", workout='" + workout + '\'' +
                ", sets='" + sets + '\'' +
                ", reps='" + reps + '\'' +
                '}';
    }
}