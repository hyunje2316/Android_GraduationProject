package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantActivity extends AppCompatActivity {
    String good_cost, good_taste;
    int user_count;
    double total_score = 0;
    double avg_score;

    TextView type;
    DatabaseReference res_info = FirebaseDatabase.getInstance().getReference().child("음식점");

    private ListView listView;
    private ResAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        DatabaseReference type_info = res_info.child(getIntent().getStringExtra("Type"));

        type = (TextView) findViewById(R.id.type_text);
        listView = (ListView) findViewById(R.id.res_listview);
        final RatingBar rating = findViewById(R.id.score_bar);


        rAdapter = new ResAdapter();
        listView.setAdapter(rAdapter);
        type.setText(getIntent().getStringExtra("Type"));

        type_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rAdapter.clear();

                for(DataSnapshot res_name : dataSnapshot.getChildren()){
                    String msg = res_name.getKey().toString();

                    if(res_name.hasChild("리뷰")){
                        if(res_name.child("리뷰").hasChildren()){
                            for(DataSnapshot getscore : res_name.child("리뷰").getChildren()){
                                user_count = (int)res_name.child("리뷰").getChildrenCount();
                                Log.w("user", String.valueOf(user_count));

                                good_cost = getscore.child("가성비").getValue().toString();
                                good_taste = getscore.child("맛").getValue().toString();

                                double user_cost = Double.parseDouble(good_cost);
                                double user_taste = Double.parseDouble(good_taste);

                                Log.w("cost", good_cost);
                                Log.w("taste", good_taste);

                                total_score = total_score + user_cost + user_taste;
                                Log.w("total", String.valueOf(total_score));

                            }

                        }
                    }
                    else{
                        total_score = 0; user_count = 1;
                    }

                    avg_score = total_score / user_count / 2.0;
                    String avg = String.format("%.1f", avg_score);
                    rAdapter.addItem(msg, avg);
                    total_score = 0;
                }
                rAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("hello", ""+position);
                Intent intent = new Intent(RestaurantActivity.this, DetailActivity.class);
                intent.putExtra("Type", getIntent().getStringExtra("Type"));
                intent.putExtra("Restaurant", rAdapter.getItem(position).getRestname());
                startActivity(intent);
            }
        });

    }
}
