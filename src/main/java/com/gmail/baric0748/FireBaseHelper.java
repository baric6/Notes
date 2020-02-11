package com.gmail.baric0748;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseHelper {

    //reference to db
    private DatabaseReference reference;

    //object array
    private ArrayList<ListSubject> note = new ArrayList<>();

    //if saved
    private Boolean saved = null;
    private Boolean updated = null;
    private Boolean deleted = null;

    //constructor
    public FireBaseHelper(DatabaseReference reference){
        this.reference = reference;
    }

    //returns true if data was entered into database else false
    public Boolean save(ListSubject notes)
    {
        if(notes == null)
        {
            saved = false;
        }
        else
        {
            try {
                //get a new key
                String key  = reference.push().getKey();

                //push data and set the value from object
                reference.child(key).setValue(notes);

                saved = true;
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    //takes key in database and new object and saves new data to the selected db firebase object
    public Boolean update(String key, ListSubject notes)
    {
        if(notes == null)
        {
            updated = false;
        }
        else
        {
            try
            {
                //uses the key find where the entry is in the database and updates the node with
                //the new object the user inputs
                reference.child(key).setValue(notes);
                updated = true;
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
                updated = false;
            }
        }
        return updated;
    }

    //deletes db entry
    public Boolean delete(String key)
    {
        //with the key searches the firebase db and sets the object to null that deletes the
        //firebase db entry
        reference.child(key).setValue(null);

        return deleted;
    }

    //this updates the database in real time
    public ArrayList<ListSubject> retrieve()
    {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                fetchData();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //returns array of objects
        return note;
    }

    //read data
    private ArrayList<ListSubject> fetchData()
    {
        //nned this wrapper helps to talk to the firebase db
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears array of objects
                note.clear();

                //loops through the firebase db
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    //selects the class i want to use as a model
                    ListSubject notes = ds.getValue(ListSubject.class);
                    //sets keys to there correlating objects
                    notes.setId(ds.getKey());
                    //add object to array of objects
                    note.add(notes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //returns list of objects
        return note;
    }
}
