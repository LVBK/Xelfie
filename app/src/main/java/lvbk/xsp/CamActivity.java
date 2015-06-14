package lvbk.xsp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by lvbk on 12/06/2015.
 */
public class CamActivity extends Activity {

    ImageButton btnTake;
    ImageView imgView;
    private static final int CAM_REQUEST =1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        btnTake = (ImageButton)findViewById(R.id.button1);
        imgView = (ImageView)findViewById(R.id.imageView1);

        btnTake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CAM_REQUEST) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgView.setImageBitmap(thumbnail);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}