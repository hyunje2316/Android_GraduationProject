package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {
    private String user_uid;

    private TextView user_e;
    private TextView user_c;
    private TextView user_n;
    FirebaseAuth mAuth;
    DatabaseReference userinfo = FirebaseDatabase.getInstance().getReference().child("회원정보");
    DatabaseReference a_user;

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MenuActivity.this, TypeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user_e = (TextView) findViewById(R.id.user_email);
        user_c = (TextView) findViewById(R.id.user_col);
        user_n = (TextView) findViewById(R.id.user_nick);
        mAuth = FirebaseAuth.getInstance();

        user_uid = mAuth.getCurrentUser().getUid();
        a_user = userinfo.child(user_uid);

        a_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.v("LOG", snapshot.child("Email").getValue().toString());
                user_e.setText(snapshot.child("Email").getValue().toString());
                user_c.setText(snapshot.child("College").getValue().toString());
                user_n.setText(snapshot.child("Nickname").getValue().toString());
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Log.v("firebase", user_uid+"");


    }
}
