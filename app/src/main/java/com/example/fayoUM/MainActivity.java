package com.example.fayoUM;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fayoUM.util.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore mFirestore;
    CollectionReference collectionReference;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    String TAG="tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        mFirestore= FirebaseFirestore.getInstance();
        collectionReference=mFirestore.collection("users");

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query= collectionReference.orderBy("fullName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<User> options=new FirestoreRecyclerOptions.Builder<User>()
                           .setQuery(query, User.class)
                           .build();
        adapter=new RecyclerViewAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //    public void getAllUsers(){
//        mFirestore.collection("cities")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("success", document.getId() + " => " + document.getData());
//                                usersList.add(document.toObject(User.class));
//                                Toast.makeText(getApplicationContext(), "Successfully fetched", Toast.LENGTH_LONG).show();
//
//                            }
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                            Toast.makeText(getApplicationContext(), "failed to fetch", Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                });
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), UserLogin.class));
            finish();
        }
        return true;
    }
}
