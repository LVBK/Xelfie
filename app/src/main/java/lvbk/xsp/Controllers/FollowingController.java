package lvbk.xsp.Controllers;

import android.os.AsyncTask;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lvbk.xsp.FollowingActivity;
import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Follow;

/**
 * Created by lvbk on 17/06/2015.
 */
public class FollowingController extends AsyncTask<String, Void, ArrayList<Follow>> {
    private FollowingActivity followingActivity;
    private final Firebase followRef = new Firebase(LoginActivity.URL).child("Follows");
    private ArrayList<Follow> follows = new ArrayList<Follow>();
    public FollowingController(FollowingActivity a){
        followingActivity = a;
    }
    @Override
    protected ArrayList<Follow> doInBackground(String... params) {
        final String uname = params[0];
        Query query = followRef.orderByChild("uname").equalTo(uname);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String unameFollow = value.get("unameFollow");
                String key = dataSnapshot.getKey();
                Follow follow = new Follow(uname, unameFollow, key);
                follows.add(follow);
                System.out.println("Follow"+follow.getUnameFollow().toString());
                followingActivity.setData(follows);
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
        return follows;
    }
    @Override
    protected void onPostExecute(ArrayList<Follow> result) {

        followingActivity.setData(follows);
    }
    public void remove(Follow follow){
        followRef.child(follow.getKey()).removeValue();
    }
}
