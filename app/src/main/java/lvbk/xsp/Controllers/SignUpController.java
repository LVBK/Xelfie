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

import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.User;
import lvbk.xsp.SignUpActivity;

/**
 * Created by lvbk on 15/06/2015.
 */
public class SignUpController {
    Firebase userRef = new Firebase(LoginActivity.URL).child("Users");

    public SignUpController(){

    }

    public void signUp(final String uname, final String pass, final String confirm, final SignUpActivity signUpActivity){
        if(uname.equals(""))
            signUpActivity.makeText("Invalid Username");
        else if(pass.equals(""))
            signUpActivity.makeText("Invalid Password");
        else if(confirm.equals(""))
            signUpActivity.makeText("Invalid Confirm Password");
        else if(!pass.equals(confirm))
            signUpActivity.makeText("Not Match Password");
        else if(!haveNetworkConnection(signUpActivity))
            signUpActivity.makeText("No Internet Connection");
        else {
            signUpActivity.pDialog.show();
            Query queryRef = userRef.orderByChild("uname").equalTo(uname);
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null) {
                        signUpActivity.pDialog.dismiss();
                        signUpActivity.makeText("Username is already");
                    }
                    else {
                        signUpActivity.pDialog.dismiss();
                        doSignUp(uname,pass,signUpActivity);
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

    public void doSignUp(String uname, String pass, Activity activity){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        User user = new User(uname, pass);
        userRef.push().setValue(user);
        bundle.putString("username", uname);
        bundle.putString("pass", pass);
        intent.putExtra("Data", bundle);
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
    }

}
