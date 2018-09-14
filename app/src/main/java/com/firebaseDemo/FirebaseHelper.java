package com.firebaseDemo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Teacher teacher) {

        if (teacher == null) {
            saved = false;
        } else {
            try {
                db.child("teachers").push().setValue(teacher);
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    public ArrayList<String> retrieve()
    {
        final ArrayList<String> teachers=new ArrayList<>();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,teachers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,teachers);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return teachers;
    }
    private void fetchData(DataSnapshot snapshot,ArrayList<String> teachers)
    {
        teachers.clear();
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String name=ds.getValue(Teacher.class).getName();
            teachers.add(name);
        }

    }


}
