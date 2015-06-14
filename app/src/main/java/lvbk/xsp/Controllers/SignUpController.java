package lvbk.xsp.Controllers;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import lvbk.xsp.LoginActivity;
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
        else {
            Query queryRef = userRef.orderByChild("uname").equalTo(uname);
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null)
                        signUpActivity.makeText("Username is already");
                    else
                        signUpActivity.doSignUp();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

}
