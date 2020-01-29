package com.example.fbt;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;



public class ReplyActivity extends AppCompatActivity {

    String input_review;
    String input_Uid;
    int taste, cost;
    private ListView r_list;
    private MyAdapter mAdapter;

    String now;
    SimpleDateFormat format1, format2;
    Date date;
    EditText edit_review;
    Button save_btn, cancel;
    RatingBar taste_rate, cost_rate;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    DatabaseReference resRef;
    DatabaseReference userRef;
    DatabaseReference signRef;
//    DataSnapshot review_data;


    void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("리뷰 수정");
        builder.setMessage("리뷰를 수정하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Rating rating = new Rating(input_review, now, taste,cost);
                userRef.child(input_Uid).setValue(rating);
                edit_review.setText("");

                Toast.makeText(ReplyActivity.this, "리뷰가 작성되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        signRef = FirebaseDatabase.getInstance().getReference().child("회원정보");
        mRef = FirebaseDatabase.getInstance().getReference().child("음식점").child(getIntent().getStringExtra("Type"));
        resRef = mRef.child(getIntent().getStringExtra("Restaurant"));
        userRef = resRef.child("리뷰");


        edit_review = (EditText)findViewById(R.id.multiText);
        save_btn = (Button)findViewById(R.id.save_button);
        cancel = (Button)findViewById(R.id.Cancel_Button);
        r_list = (ListView)findViewById(R.id.review_list);
        taste_rate = (RatingBar)findViewById(R.id.Taste_Rate);
        cost_rate = (RatingBar)findViewById(R.id.Cost_Rate);
        mAuth = FirebaseAuth.getInstance();

        mAdapter = new MyAdapter();
        r_list.setAdapter(mAdapter);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter.clear();

                for(final DataSnapshot review_data : dataSnapshot.getChildren()){
                    Log.w("nickname", review_data.getKey());

                    signRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(review_data.getKey())){
                                String reply_nick = dataSnapshot.child(review_data.getKey()).child("Nickname").getValue().toString();
                                Log.w("re_nick", reply_nick);
                                mAdapter.addItem(reply_nick, review_data.child("시간").getValue().toString(), review_data.child("내용").getValue().toString());
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        save_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                input_review = edit_review.getText().toString();
                input_Uid = mAuth.getUid();

                date = new Date();
                format1 = new SimpleDateFormat("yyyy-MM-dd");
                format2 = new SimpleDateFormat("HH:mm");
                now = format1.format(date) + " " + format2.format(date);

                taste = (int)taste_rate.getRating();
                cost = (int)cost_rate.getRating();
                Log.w("Taste : ", taste + "");
                Log.w("Cost : ", cost + "");

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot check : dataSnapshot.getChildren()){
                            if(check.getKey().equals(input_Uid)){
                                dialog();
                                return;
                            }
                        }

                        Rating rating = new Rating(input_review, now, taste,cost);
                        userRef.child(input_Uid).setValue(rating);
                        edit_review.setText("");

                        Toast.makeText(ReplyActivity.this, "리뷰가 작성되었습니다!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

    }
}
