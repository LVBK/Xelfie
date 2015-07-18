package lvbk.xsp.Controllers;

import android.os.AsyncTask;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lvbk.xsp.FollowableActivity;
import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Follow;

/**
 * Created by lvbk on 17/06/2015.
 */
public class FollowableController extends AsyncTask<String, Void, ArrayList<Follow>> {
    private FollowableActivity followableActivity;
    private final Firebase followRef = new Firebase(LoginActivity.URL).child("Follows");
    private final Firebase userRef = new Firebase(LoginActivity.URL).child("Users");
    private ArrayList<Follow> followings = new ArrayList<Follow>();
    private ArrayList<Follow> followables = new ArrayList<Follow>();
    public FollowableController(FollowableActivity a){
        followableActivity = a;
    }
    @Override
    protected ArrayList<Follow> doInBackground(String... params) {
        final String uname = params[0];
        System.out.println(uname);
        Query query = followRef.orderByChild("uname").equalTo(uname);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String unameFollow = value.get("unameFollow");
                followings.add(new Follow(uname, unameFollow));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String uname = value.get("uname");
                String unameFollow = value.get("unameFollow");
                followables.add(new Follow(uname,unameFollow));
                followableActivity.setData(followables);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Query userQuery = userRef.orderByChild("uname");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    followables.clear();
                    System.out.println("change");
                    Map value = (Map) dataSnapshot.getValue();
                    List valueList = new ArrayList(value.values());
                    for (int i = 0; i < valueList.size(); i++) {
                        Map<String, String> value2 = (Map<String, String>) valueList.get(i);
                        String unameFollow = value2.get("uname");
                        if (!unameFollow.equals(uname))
                            followables.add(new Follow(uname, value2.get("uname")));
                    }
                    System.out.println(followables.size());
                    query();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        return followables;
    }
    @Override
    protected void onPostExecute(ArrayList<Follow> result) {

        //followableActivity.setData(followables);
    }
    public void remove(Follow follow){
        followRef.push().setValue(follow);
    }

    public void query(){
        for(int i=0; i< followings.size();i++) {
            for(int j = 0; j<followables.size();j++){
                if(followings.get(i).getUnameFollow().equals(followables.get(j).getUnameFollow()))
                    followables.remove(j);
            }
        }
        followableActivity.setData(followables);
    }
}
