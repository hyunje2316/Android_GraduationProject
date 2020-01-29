package com.example.fbt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    String[] items = {"IT", "경상", "인문", "법정", "공대", "생활과학", "체육", "미술", "음악"};
    private String selected;

    private Button submit;
    private Button cancel;
    private EditText email_sub;
    private EditText pass_sub;
    private EditText nick_sub;
    private Spinner college;
    FirebaseAuth mAuth;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("회원정보");
    DatabaseReference UidRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_sub = (EditText) findViewById(R.id.sub_email);
        pass_sub = (EditText) findViewById(R.id.sub_password);
        nick_sub = (EditText) findViewById(R.id.sub_nick);
        submit = (Button) findViewById(R.id.sub_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        college = (Spinner) findViewById(R.id.select_col);

        mAuth = FirebaseAuth.getInstance();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        college.setAdapter(adapter);

        college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = "알레르기 유발";
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_sub.getText().toString() == null || email_sub.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(pass_sub.getText().toString() == null || pass_sub.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (nick_sub.getText().toString() == null || nick_sub.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    final String email = email_sub.getText().toString().trim();
                    final String pass = pass_sub.getText().toString().trim();
                    final String nick = nick_sub.getText().toString().trim();

                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            String e_code;
                            String New_uid = mAuth.getUid();

                            if(task.isSuccessful()){
                                UidRef = userRef.child(New_uid);
                                UidRef.child("Email").setValue(email);
                                UidRef.child("Nickname").setValue(nick);
                                UidRef.child("College").setValue(selected);

                                Toast.makeText(SignupActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                Log.e("firebseLogin", "createUserWithEmail:failure", task.getException());
                                Log.e("ExceptionMessage", ((FirebaseAuthException)task.getException()).getErrorCode());
                                e_code = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch(e_code){
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(SignupActivity.this, "중복된 이메일이 있습니다!", Toast.LENGTH_SHORT).show();
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(SignupActivity.this, "비밀번호는 6글자 이상어야 합니다!", Toast.LENGTH_SHORT).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(SignupActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                                return;
                            }
                        }
                    });
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
