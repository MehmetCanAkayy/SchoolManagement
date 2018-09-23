package com.socialActivity;

public class SocialActivityClass {

    String activityKey;
    String studentKeys;
    String name;
    float rating;


    public SocialActivityClass(){

    }

    public SocialActivityClass(String name,String activityKey,String studentKeys) {
        this.activityKey = activityKey;
        this.studentKeys=studentKeys;
        this.name = name;
    }
    public SocialActivityClass(String name,String activityKey,String studentKeys,float rating) {
        this.activityKey = activityKey;
        this.studentKeys=studentKeys;
        this.name = name;
        this.rating =rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(String activityKey) {
        this.activityKey = activityKey;
    }

    public String getStudentKeys() {
        return studentKeys;
    }

    public void setStudentKeys(String studentKeys) {
        this.studentKeys = studentKeys;
    }
}
