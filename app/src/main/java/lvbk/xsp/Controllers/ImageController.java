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

import lvbk.xsp.ImageActivity;
import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Comment;

/**
 * Created by lvbk on 17/06/2015.
 */
public class ImageController extends AsyncTask<String, Void, ArrayList<Comment>> {
    private ImageActivity imageActivity;
    private final Firebase  commentRef = new Firebase(LoginActivity.URL).child("Comments");
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    public ImageController(ImageActivity a){
        imageActivity = a;
    }
    @Override
    protected ArrayList<Comment> doInBackground(String... params) {
        final String keyImage = params[0];
        Query query = commentRef.orderByChild("keyImage").equalTo(keyImage);
        /*query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    comments.clear();
                    Map value = (Map) dataSnapshot.getValue();
                    List valueList = new ArrayList(value.values());
                    for (int i = 0; i < valueList.size(); i++) {
                        Map<String, String> value2 = (Map<String, String>) valueList.get(i);
                        String uname = value2.get("uname");
                        String content = value2.get("content");
                        comments.add(new Comment(uname, content));
                    }
                    imageActivity.setData(comments);
                    imageActivity.updateUI();
                } else {
                    imageActivity.updateUI();
                    imageActivity.makeText("Invalid Comment!");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String uname = value.get("uname");
                String content = value.get("content");
                comments.add(new Comment(uname, content));
                imageActivity.setData(comments);
                imageActivity.updateUI();
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
        return comments;
    }
    @Override
    protected void onPostExecute(ArrayList<Comment> result) {

        imageActivity.setData(comments);
    }

}