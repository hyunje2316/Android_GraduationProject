package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class DetailActivity extends AppCompatActivity {
    int count;
    TextView r_name;
    TextView r_address;
    TextView r_phone;
    Button re_button;

    private ListView listView;
    private ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter adapter;
    private HashMap<String, String> inputdata;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DatabaseReference r_ref = FirebaseDatabase.getInstance().getReference().child("음식점").child(getIntent().getStringExtra("Type"));
        DatabaseReference res_detail = r_ref.child(getIntent().getStringExtra("Restaurant"));

        r_name = (TextView) findViewById(R.id.res_name);
        r_address = (TextView) findViewById(R.id.res_address);
        r_phone = (TextView) findViewById(R.id.res_phone);
        re_button = (Button) findViewById(R.id.review);
        listView = (ListView) findViewById(R.id.food_list);

        adapter = new SimpleAdapter(this, array, android.R.layout.simple_list_item_2, new String[]{"menu", "cost"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);

        re_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DetailActivity.this, ReplyActivity.class);
                intent.putExtra("Type", getIntent().getStringExtra("Type"));
                intent.putExtra("Restaurant", getIntent().getStringExtra("Restaurant"));

                startActivity(intent);
            }
        });


        res_detail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                r_name.setText(getIntent().getStringExtra("Restaurant"));
                r_address.setText(dataSnapshot.child("주소").getValue().toString());
                r_phone.setText(dataSnapshot.child("전화번호").getValue().toString());

                count = (int)dataSnapshot.child("메뉴").getChildrenCount();
                Log.e("CountCheck", String.valueOf(count));

                array.clear();

                for(int i = 1; i <= count; i++){
                    for(DataSnapshot food : dataSnapshot.child("메뉴").child(String.valueOf(i)).getChildren()) {
                        inputdata = new HashMap<>();
                        inputdata.put("menu", food.getKey().toString());
                        inputdata.put("cost", food.getValue().toString());
                        array.add(inputdata);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
