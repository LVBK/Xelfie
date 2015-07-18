package lvbk.xsp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

/**
 * Created by lvbk on 12/06/2015.
 */
public class HomeActivity extends TabActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        TabHost tabHost = getTabHost();

        TabHost.TabSpec wallspec = tabHost.newTabSpec("Wall");
        wallspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_home));
        Intent wallIntent = new Intent(this, WallActivity.class);
        wallIntent.putExtra("DATA", bundle);
        wallspec.setContent(wallIntent);


        TabHost.TabSpec camspec = tabHost.newTabSpec("Camera");
        camspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_camera));
        Intent camIntent = new Intent(this, CamActivity.class);
        camIntent.putExtra("DATA", bundle);
        camspec.setContent(camIntent);


        TabHost.TabSpec followspec = tabHost.newTabSpec("Notification");
        followspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_heart_outline));
        Intent followIntent = new Intent(this, FollowActivity.class);
        Bundle bundle3 = new Bundle();
        followIntent.putExtra("DATA", bundle);
        followspec.setContent(followIntent);

        TabHost.TabSpec userspec = tabHost.newTabSpec("User");
        userspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_account));
        Intent userIntent = new Intent(this, UserActivity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("uname", bundle.getString("uname"));
        bundle2.putString("unameFollow", bundle.getString("uname"));
        userIntent.putExtra("DATA", bundle2);
        userspec.setContent(userIntent);


        tabHost.addTab(wallspec);
        tabHost.addTab(camspec);
        tabHost.addTab(followspec);
        tabHost.addTab(userspec);

    }
    public void createTabHost(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                finish();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

