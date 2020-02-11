package com.gmail.baric0748;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private FireBaseHelper helper;
    private DatabaseReference reference;

    //for init
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference().child("notes");
        reference.keepSynced(true);
        helper = new FireBaseHelper(reference);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(this, helper.retrieve());
        recyclerView.setAdapter(adapter);

    }//end onCreate

    //executes when app starts
    @Override
    protected void onStart() {
        super.onStart();

        //this needs to be here to read the firebase db for recyclerView
        //if deleted no entries will show on the home screen
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                //get values of the firebase db
                dataSnapshot.getValue().toString();

                Toast.makeText(getApplicationContext(), "Load successful", Toast.LENGTH_LONG).show();

                //making and setting new adapter
                adapter = new RecyclerAdapter(getApplicationContext(), helper.retrieve());
                recyclerView.setAdapter(adapter);

                //needs to be in this method so you can click on a card and it dose something
                adapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {

                        //when item is clicked takes you to the update Activity
                        Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                        //TODO pass key to other activity
                        intent.putExtra("id", helper.retrieve().get(pos).getId());
                        intent.putExtra( "name" ,helper.retrieve().get(pos).getTitle());
                        intent.putExtra( "dis" ,helper.retrieve().get(pos).getComment());
                        intent.putExtra( "url" ,helper.retrieve().get(pos).getkeyRefrence());
                        intent.putExtra("topic", helper.retrieve().get(pos).getTopic());

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Load Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    //for search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem menuItem = menu.findItem(R.id.menuSearch1);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
        return true;
    }

    //for drop down menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuAdd:
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
