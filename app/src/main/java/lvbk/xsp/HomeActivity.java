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
    public static final int MENU_LOGOUT = Menu.FIRST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t);
        TabHost tabHost = getTabHost();

        //Khởi tạo tab wall
        TabHost.TabSpec wallspec = tabHost.newTabSpec("Wall");
        //Thiết lập tên tab hiển thị và icon
        wallspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_home));
        //Thiết lập nôi dung cho tab này là activity HinhAnhActivity.class
        Intent wallIntent = new Intent(this, WallActivity.class);
        wallspec.setContent(wallIntent);

        //Khởi tạo tab camera
        TabHost.TabSpec camspec = tabHost.newTabSpec("Camera");
        camspec.setIndicator(null, getResources().getDrawable(R.drawable.ic_camera));
        Intent camIntent = new Intent(this, CamActivity.class);
        camspec.setContent(camIntent);

        //Khởi tạo tab noti
        TabHost.TabSpec notispec = tabHost.newTabSpec("Notification");
        notispec.setIndicator(null, getResources().getDrawable(R.drawable.ic_heart_outline));
        Intent notiIntent = new Intent(this, NotiActivity.class);
        notispec.setContent(notiIntent);

        TabHost.TabSpec mespec = tabHost.newTabSpec("Me");
        mespec.setIndicator(null, getResources().getDrawable(R.drawable.ic_account));
        Intent meIntent = new Intent(this, MeActivity.class);
        mespec.setContent(meIntent);

        //Thêm các TabSpec trên vào TabHost
        tabHost.addTab(wallspec); //Thêm tab wall
        tabHost.addTab(camspec); //Thêm tab camera
        tabHost.addTab(notispec); //Thêm tab noti
        tabHost.addTab(mespec);   //Thêm tab me
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

