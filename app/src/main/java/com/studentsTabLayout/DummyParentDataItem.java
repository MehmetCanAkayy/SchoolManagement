package com.studentsTabLayout;

import com.firebaseDemo.LessonInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <b></b>
 * <p>This class is used to </p>
 * Created by Rohit.
 */
class DummyParentDataItem implements Serializable {
    private String parentName;
    private ArrayList<LessonInfo> childDataItems;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<LessonInfo> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<LessonInfo> childDataItems) {
        this.childDataItems = childDataItems;
    }
}
