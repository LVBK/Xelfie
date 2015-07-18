package lvbk.xsp.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Image;
import lvbk.xsp.UserActivity;

/**
 * Created by lvbk on 15/06/2015.
 */
public class UserController extends AsyncTask<ArrayList<Image>, Void, ArrayList<Image>> {
    private final Firebase imageRef = new Firebase(LoginActivity.URL).child("Images");
    private final Firebase avatarRef = new Firebase(LoginActivity.URL).child("Avatars");

    private UserActivity userActivity;
    private ArrayList<String> keyList = new ArrayList<String>();
    public UserController(UserActivity a){
        this.userActivity = a;
    }
    @Override
    protected ArrayList<Image> doInBackground(ArrayList<Image>... params) {
        final ArrayList<Image> images = params[0];
        Intent intent = userActivity.getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        String uname = bundle.getString("uname");
        String unameFollow = bundle.getString("unameFollow");

        Query queryAvatar = avatarRef.orderByChild("uname").equalTo(unameFollow);
        queryAvatar.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (Map<String, String>) dataSnapshot.getValue();
                String imageFile = value.get("avatarFile");

                byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                userActivity.setAvatar(bmp);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Query queryRef = imageRef.orderByChild("uname").equalTo(unameFollow);
        /*queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    images.clear();
                    Map value = (Map) dataSnapshot.getValue();
                    List valueList = new ArrayList(value.values());
                    for(Object key: value.keySet()) {
                        keyList.add(key.toString());
                    }
                    for (int i = 0; i < valueList.size(); i++) {
                        Map<String, String> value2 = (Map<String, String>) valueList.get(i);
                        String imageFile = value2.get("imageFile");
                        String uname = value2.get("uname");
                        String key = keyList.get(i);
                        images.add(0,new Image(imageFile, uname, key));
                    }
                    userActivity.setData(images);
                    userActivity.updateUI();
                } else {
                    userActivity.updateUI();
                    userActivity.makeText("Invalid Images!");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
       queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String imageFile = value.get("imageFile");
                String uname = value.get("uname");
                Image image = new Image(imageFile,uname,dataSnapshot.getKey());
                images.add(0,image);
                userActivity.setData(images);
                userActivity.updateUI();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                userActivity.updateUI();
            }
        });

        return images;
    }
    @Override
    protected void onPostExecute(ArrayList<Image> result) {

        userActivity.setData(result);
    }
}
