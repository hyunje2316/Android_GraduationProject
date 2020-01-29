package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {
    private long time = 0;

    private Button sign_up;
    private Button log_in;
    private EditText email_login;
    private EditText pass_login;
    FirebaseAuth mAuth;


    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - time >= 2000){
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "뒤로가기 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();

        } else if(System.currentTimeMillis() - time < 2000){
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_in = (Button) findViewById(R.id.log_button);
        sign_up = (Button) findViewById(R.id.sign_button);
        email_login = (EditText) findViewById(R.id.log_email);
        pass_login = (EditText) findViewById(R.id.log_password);
        mAuth = FirebaseAuth.getInstance();

        log_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(email_login.getText().toString() == null || email_login.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(pass_login.getText().toString() == null || pass_login.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    String email = email_login.getText().toString().trim();
                    String pass = pass_login.getText().toString().trim();

                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String e_code;

                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "로그인 완료!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, TypeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                Log.e("FBLogin", "SignIn: failure", task.getException());
                                Log.e("ExceptionMessage", ((FirebaseAuthException) task.getException()).getErrorCode());
                                e_code = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch(e_code){
                                    case "ERROR_WRONG_PASSWORD":
                                        Toast.makeText(MainActivity.this, "등록되지 않은 계정이거나 비밀번호가 올바르지 않습니다!", Toast.LENGTH_SHORT).show();
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        Toast.makeText(MainActivity.this, "등록되지 않은 계정이거나 비밀번호가 올바르지 않습니다!", Toast.LENGTH_SHORT).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(MainActivity.this, "올바른 이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                    });

                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
