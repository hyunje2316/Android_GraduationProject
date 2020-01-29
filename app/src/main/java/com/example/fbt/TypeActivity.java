package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TypeActivity extends AppCompatActivity {

    private Button kor;
    private Button chn;
    private Button jpn;
    private Button west;
    private Button pizza;
    private Button chicken;
    private Button hamburg;
    private Button snack;
    private Button jokbal;

    private Button my_page;
    private Button log_out;

    FirebaseAuth mAuth;

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TypeActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(TypeActivity.this, "로그아웃 완료!", Toast.LENGTH_SHORT).show();
        mAuth.getInstance().signOut();
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        kor = (Button) findViewById(R.id.type_kor);
        chn = (Button) findViewById(R.id.type_chn);
        jpn = (Button) findViewById(R.id.type_jpn);
        west = (Button) findViewById(R.id.type_west);
        pizza = (Button) findViewById(R.id.type_pizza);
        chicken = (Button) findViewById(R.id.type_chick);
        hamburg = (Button) findViewById(R.id.type_hamb);
        snack = (Button) findViewById(R.id.type_snack);
        jokbal = (Button) findViewById(R.id.type_jok);


        my_page = (Button) findViewById(R.id.mypage);
        log_out = (Button) findViewById(R.id.logout);

        my_page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(TypeActivity.this, "로그아웃 완료!", Toast.LENGTH_SHORT).show();
                mAuth.getInstance().signOut();
                finish();
            }
        });


        kor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", kor.getText().toString());
                Log.w("cat_type",kor.getText().toString());
                startActivity(intent);
            }
        });

        chn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", chn.getText().toString());
                startActivity(intent);
            }
        });

        jpn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", jpn.getText().toString());
                startActivity(intent);
            }
        });

        west.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", west.getText().toString());
                startActivity(intent);
            }
        });

        pizza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", pizza.getText().toString());
                startActivity(intent);
            }
        });

        chicken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", chicken.getText().toString());
                startActivity(intent);
            }
        });

        hamburg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", hamburg.getText().toString());
                startActivity(intent);
            }
        });

        snack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", snack.getText().toString());
                startActivity(intent);
            }
        });

        jokbal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TypeActivity.this, RestaurantActivity.class);
                intent.putExtra("Type", jokbal.getText().toString());
                startActivity(intent);
            }
        });
    }
}
