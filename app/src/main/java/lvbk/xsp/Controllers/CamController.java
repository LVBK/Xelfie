package lvbk.xsp.Controllers;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import lvbk.xsp.CamActivity;
import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Avatar;
import lvbk.xsp.Models.Image;

/**
 * Created by lvbk on 15/06/2015.
 */
public class CamController {
    private final Firebase imageRef = new Firebase(LoginActivity.URL).child("Images");
    private final Firebase avatarRef = new Firebase(LoginActivity.URL).child("Avatars");
    public CamController(){

    }

    public void upload(String imageFile, CamActivity activity){
        Intent intent = activity.getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        Image image = new Image(imageFile, bundle.getString("uname"));
        imageRef.push().setValue(image);
        activity.setImageView(null);
        activity.makeText("Uploaded!");
        System.out.println("Upload....");
    }

    public void upAvatar(final String avatarFile, final CamActivity activity){
        Intent intent = activity.getIntent();
        final Bundle bundle = intent.getBundleExtra("DATA");
        Query query = avatarRef.orderByChild("uname").equalTo(bundle.getString("uname"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    Map value = (Map) dataSnapshot.getValue();
                    for(Object key :value.keySet()){
                        avatarRef.child(key.toString()).removeValue();
                    }
                }
                Avatar avatar = new Avatar(avatarFile, bundle.getString("uname"));
                avatarRef.push().setValue(avatar);
                activity.setImageView(null);
                activity.makeText("Upload avatar Successful!");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
