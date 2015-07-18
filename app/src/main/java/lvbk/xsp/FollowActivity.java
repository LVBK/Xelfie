package lvbk.xsp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;

import lvbk.xsp.Models.Follow;

/**
 * Created by lvbk on 12/06/2015.
 */
public class FollowActivity extends TabActivity {
    private ArrayList<Follow> follows = new ArrayList<Follow>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent  = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");


        TabHost tabHost = getTabHost();

        TabHost.TabSpec followingspec = tabHost.newTabSpec("Following");
        followingspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_account_star_variant));
        Intent followingIntent = new Intent(this, FollowingActivity.class);
        followingIntent.putExtra("DATA", bundle);
        followingspec.setContent(followingIntent);


        TabHost.TabSpec followablespec = tabHost.newTabSpec("Followable");
        followablespec.setIndicator(null, getResources().getDrawable(R.drawable.ic_account_search));
        Intent searchIntent = new Intent(this, FollowableActivity.class);
        searchIntent.putExtra("DATA", bundle);
        followablespec.setContent(searchIntent);

        tabHost.addTab(followingspec);
        tabHost.addTab(followablespec);
    }

}
