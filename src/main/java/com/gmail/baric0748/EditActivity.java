package com.gmail.baric0748;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    private Button modAdd;

    private EditText modTitle;
    private EditText modComment;
    private EditText modUrl;
    private Spinner modSpinner;
    private String f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //calls to database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
        final FireBaseHelper helper = new FireBaseHelper(ref);

        //widgets
        modAdd = findViewById(R.id.modAdd);
        modTitle = findViewById(R.id.modTitle);
        modComment = findViewById(R.id.modComment);
        modUrl = findViewById(R.id.modUrl);
        modSpinner = findViewById(R.id.modSpinner);

        //for spinner selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.topics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modSpinner.setAdapter(adapter);

        //on item selected set string of topic
        modSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //when add button is clicked
        modAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();

                    //makes a new object to add to database
                    ListSubject newEntry = new ListSubject();
                    newEntry.setTitle(modTitle.getText().toString());
                    newEntry.setComment(modComment.getText().toString());
                    newEntry.setkeyRefrence(modUrl.getText().toString());
                    newEntry.setTopic(f);

                    //call the helper method to save entry
                    helper.save(newEntry);

                    finish();
          }
       });
    }
}
