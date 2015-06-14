package lvbk.xsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import lvbk.xsp.Controllers.SignUpController;
import lvbk.xsp.Models.User;

/**
 * Created by lvbk on 13/06/2015.
 */
public class SignUpActivity extends Activity{
    final Firebase rootRèf = new Firebase(LoginActivity.URL);
    final Firebase userRef = rootRèf.child("Users");
    EditText username, password, confirm;
    String uname, pass, pass2;
    Button signUpBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirm = (EditText)findViewById(R.id.confirm);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                username.setText("");
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password.setText("");
                return false;
            }
        });

        confirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirm.setText("");
                return false;
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname  = username.getText().toString();
                pass = password.getText().toString();
                pass2 = confirm.getText().toString();
                SignUpController signUpController = new SignUpController();
                signUpController.signUp(uname,pass,pass2,SignUpActivity.this);
            }
        });
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtra("Data", bundle);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    public void doSignUp(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        User user = new User(uname, pass);
        userRef.push().setValue(user);
        bundle.putString("username", uname);
        bundle.putString("pass", pass);
        intent.putExtra("Data", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void makeText(String content){
            Toast.makeText(SignUpActivity.this, content+"!", Toast.LENGTH_LONG).show();
    }
}
