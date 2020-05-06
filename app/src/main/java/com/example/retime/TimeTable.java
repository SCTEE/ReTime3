package com.example.retime;

public class TimeTable {

    private int id;
    private String time, task1, task2;
    //Constructor
    public TimeTable(int id, String time, String task1, String task2) {
        this.id = id;
        this.time = time;
        this.task1 = task1;
        this.task2 = task2;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    //Setter & Getter Methods
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    } //to set company id
    public String getTask1() {
        return task1;
    } //to get company image
    public void setTask1(String task1) { //to set company image

        this.task1 = task1;
    }
    public String getTask2() {
        return task2;
    } //to get company name
    public void setTask2(String task2) { //to set company name
        this.task2 = task2;
    }

}
