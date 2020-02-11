package com.gmail.baric0748;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    private Button updateUpdate;
    private Button updateDel;

    private EditText updateTitle;
    private EditText updateComment;
    private EditText updateLink;
    private Spinner updateSpinner;
    private String updateTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
        final FireBaseHelper helper = new FireBaseHelper(ref);

        updateUpdate = findViewById(R.id.updateUpdate);
        updateDel = findViewById(R.id.updateDel);
        updateTitle = findViewById(R.id.updateTitle);
        updateComment = findViewById(R.id.updateComment);
        updateLink = findViewById(R.id.updateLink);
        updateSpinner = findViewById(R.id.updateSpinner);

        //getting intent from mainActivity when card is clicked
        Intent fromCard = getIntent();
        final String id = fromCard.getStringExtra("id");
        String name = fromCard.getStringExtra("name");
        String dis = fromCard.getStringExtra("dis");
        String url =  fromCard.getStringExtra("url");
        String topic = fromCard.getStringExtra("topic");

        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.topics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateSpinner.setAdapter(adapter);

        //set info in widgets
        updateTitle.setText(name);
        updateComment.setText(dis);
        updateLink.setText(url);

        //spinner
        int spinnerPos = adapter.getPosition(topic);
        updateSpinner.setSelection(spinnerPos);

        //when item is selected from spinner
        updateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTopic = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //when the update button is clicked
        updateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //makes a new object for use in firebase db and will delete previous entry
                ListSubject list = new ListSubject();
                list.setTitle(updateTitle.getText().toString());
                list.setComment(updateComment.getText().toString());
                list.setkeyRefrence(updateLink.getText().toString());
                list.setTopic(updateTopic);

                //call update method in firebaseHelper class
                helper.update(id, list);

                finish();
            }
        });

        //when delete button is clicked
        updateDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call delete method in firebaseHelper class
                helper.delete(id);

               finish();
            }
        });
    }
}
