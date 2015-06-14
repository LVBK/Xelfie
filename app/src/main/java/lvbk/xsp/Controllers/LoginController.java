package lvbk.xsp.Controllers;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lvbk.xsp.HomeActivity;
import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.User;

/**
 * Created by lvbk on 12/06/2015.
 */
public class LoginController {
    public String output = "";
    public static final String URL = "https://selfieworld.firebaseio.com/";
    final Firebase rootRef = new Firebase(URL);
    final Firebase userRef = rootRef.child("Users");
    public LoginController(){

    }
    public void login(final String uname, final String password,  final LoginActivity loginActivity){
        if (uname.equals("") || password.equals("")) {
            output = "Invalid username or password";
        } else {
            Query queryRef = userRef.orderByChild("uname").equalTo(uname);
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("\ndata" + dataSnapshot.toString());
                    if (dataSnapshot.getValue() != null) {
                        Map value = (Map) dataSnapshot.getValue();
                        List valueList = new ArrayList(value.values());
                        Map<String, Objects> value2 = (Map<String, Objects>) valueList.get(0);
                        if (password.equals(value2.get("password"))) {
                            output = "Success login";
                            loginActivity.finish();
                            User user = new User(uname, password);
                            Intent intent = new Intent(loginActivity, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtra("DATA", bundle);
                            loginActivity.startActivity(intent);
                        } else {
                            System.out.println("wrong pass");
                            System.out.println("" + password);
                            System.out.println("" + value.get("password"));
                            output = "Wrong password";
                        }
                    } else {
                        System.out.println("wrong username");
                        output = "Wrong username!";
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        loginActivity.makeText(output);
    }
}
