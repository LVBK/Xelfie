package lvbk.xsp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    private Runnable runnable;
    private static final String TAG="Welcome";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        runnable=new Runnable() {

            @Override
            public void run() {
                try
                {
                    Log.v(TAG, "Going to sleep...");
                    Thread.sleep(1000);
                    Log.v(TAG,"Going to wake up...");
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        try
        {
            Thread t=new Thread(null,runnable);
            t.start();

        }
        catch(Exception e)
        {

        }

    }
}
