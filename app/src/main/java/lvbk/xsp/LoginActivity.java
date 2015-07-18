package lvbk.xsp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import lvbk.xsp.Controllers.LoginController;

/**
 * Created by lvbk on 12/06/2015.
 */

public class LoginActivity extends ActionBarActivity {
    public static final String URL = "https://selfieworld.firebaseio.com/";
    private ProgressDialog pDialog;
    EditText nameTextView;
    EditText passTextView;
    CheckBox chksaveaccount;
    String prefname="my_data";
    Button loginBtn;
    Button signUpBtn;
    String uname;
    String password;

    public void showDialog(){
        this.pDialog.show();
    }
    public void disMissDialog(){
        this.pDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        final Firebase rootRef = new Firebase(URL);
        final Firebase userRef = rootRef.child("Users");
        nameTextView = (EditText)findViewById(R.id.username);
        passTextView = (EditText)findViewById(R.id.password);
        chksaveaccount =(CheckBox)findViewById(R.id.checkBox);
        loginBtn = (Button)findViewById(R.id.logiinBtn);
        nameTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameTextView.setText("");
                return false;
            }
        });

        passTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passTextView.setText("");
                return false;
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginController loginController = new LoginController();
                uname = nameTextView.getText().toString();
                password = passTextView.getText().toString();
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                loginController.login(uname, password, LoginActivity.this);
            }
        });

        signUpBtn = (Button)findViewById(R.id.signup);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //gọi hàm lưu trạng thái ở đây
        savingPreferences();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }

    public void savingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        System.out.println("\n Dang luu SPs");
        SharedPreferences pre=getSharedPreferences
                (prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        boolean bchk=chksaveaccount.isChecked();
        if(!bchk)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("user",nameTextView.getText().toString() );
            editor.putString("pwd", passTextView.getText().toString());
            editor.putBoolean("checked", bchk);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }
    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences(prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            nameTextView.setText(user);
            passTextView.setText(pwd);
        }
        chksaveaccount.setChecked(bchk);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_CANCELED)
                restoringPreferences();
            else if(resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("Data");
                nameTextView.setText(bundle.getString("username"));
                passTextView.setText(bundle.getString("pass"));
                savingPreferences();
            }
        }
    }
    public void makeText(String content){
            Toast.makeText(LoginActivity.this, content, Toast.LENGTH_LONG).show();
    }

}
