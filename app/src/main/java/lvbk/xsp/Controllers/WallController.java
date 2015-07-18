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

import lvbk.xsp.LoginActivity;
import lvbk.xsp.Models.Image;
import lvbk.xsp.WallActivity;

/**
 * Created by lvbk on 17/06/2015.
 */
public class WallController extends AsyncTask<Void, Void, ArrayList<Image>> {
    private Firebase imageRef = new Firebase(LoginActivity.URL).child("Images");
    private ArrayList<Image> images = new ArrayList<Image>();
    private WallActivity wallActivity;

    public WallController(WallActivity wallActivity) {
        this.wallActivity = wallActivity;
    }

    @Override
    protected ArrayList<Image> doInBackground(Void... arg0) {
        // Creating service handler class instance
        Query query = imageRef.limitToLast(10);
        /*query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.clear();
                Map value = (Map) dataSnapshot.getValue();
                List valueList = new ArrayList(value.values());
                for (int i = 0; i < valueList.size(); i++) {
                    Map<String, String> value2 = (Map<String, String>) valueList.get(i);
                    String imageFile = value2.get("imageFile");
                    String uname = value2.get("uname");
                    images.add(0,new Image(imageFile, uname));
                }
                wallActivity.setData(images);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                String imageFile = value.get("imageFile");
                String uname = value.get("uname");
                Image image = new Image(imageFile,uname,dataSnapshot.getKey());
                images.add(0,image);
                wallActivity.setData(images);
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
        return images;
    }

    @Override
    protected void onPostExecute(ArrayList<Image> result) {
        super.onPostExecute(result);
    }

}
