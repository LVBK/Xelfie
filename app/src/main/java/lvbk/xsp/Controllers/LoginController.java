package lvbk.xsp.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

/**
 * Created by lvbk on 12/06/2015.
 */
public class LoginController {
    public static final String URL = "https://selfieworld.firebaseio.com/";
    final Firebase rootRef = new Firebase(URL);
    final Firebase userRef = rootRef.child("Users");
    public LoginController(){

    }
    public void login(final String uname, final String password,  final LoginActivity loginActivity){
        if (uname.equals(""))
            loginActivity.makeText("Invalid Username");
        else if(password.equals(""))
            loginActivity.makeText("Invalid Password");
        else if(!haveNetworkConnection(loginActivity))
            loginActivity.makeText("No Internet Connection");
        else {
            loginActivity.showDialog();
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
                            loginActivity.disMissDialog();;
                            loginActivity.finish();
                            Intent intent = new Intent(loginActivity, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            for(Object key: value.keySet()) {
                                bundle.putString("key",key.toString());
                            }
                            bundle.putString("uname", uname);
                            intent.putExtra("DATA", bundle);
                            loginActivity.startActivity(intent);
                        } else {
                            loginActivity.disMissDialog();
                            loginActivity.makeText("Wrong Password");
                        }
                    } else {
                        loginActivity.disMissDialog();
                        loginActivity.makeText("Wrong Username");
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
    private boolean haveNetworkConnection(Activity activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
