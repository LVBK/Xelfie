package lvbk.xsp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import lvbk.xsp.Controllers.CamController;

/**
 * Created by lvbk on 12/06/2015.
 */
public class CamActivity extends Activity {

    ImageButton btnTake, btnUpload, btnSetAvatar;
    private ProgressDialog pDialog;
    private ImageView imgView;
    private static final int CAM_REQUEST =1313;

    public void setImageView(Bitmap bitmap){
        this.imgView.setImageBitmap(bitmap);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        btnTake = (ImageButton)findViewById(R.id.btnTake);
        btnUpload = (ImageButton)findViewById(R.id.btnUpload);
        btnSetAvatar = (ImageButton)findViewById(R.id.btnSetAvatar);
        imgView = (ImageView)findViewById(R.id.imageView1);
        final CamController camController = new CamController();

        btnTake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(CamActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgView.getDrawable() instanceof BitmapDrawable) {
                    try {
                        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                        Bitmap thumbnail = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                        thumbnail.recycle();
                        byte[] byteArray = bYtE.toByteArray();
                        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        camController.upload(imageFile, CamActivity.this);
                    }catch (Exception e){
                        makeText("Invalid Image! Take a picture please!");
                    }
                }
                else
                    Toast.makeText(CamActivity.this, "Invalid Image! Take a picture please!", Toast.LENGTH_LONG).show();
            }
        });

        btnSetAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgView.getDrawable() instanceof BitmapDrawable) {
                    try {
                        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                        Bitmap thumbnail = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                        thumbnail.recycle();
                        byte[] byteArray = bYtE.toByteArray();
                        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        camController.upAvatar(imageFile, CamActivity.this);
                    }catch (Exception e){
                        makeText("Invalid Image! Take a picture please!");
                    }
                }
                else
                    makeText("Invalid Image! Take a picture please!");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        try {
            pDialog.dismiss();
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CAM_REQUEST) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgView.setImageBitmap(thumbnail);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
    public void makeText(String content){
        Toast.makeText(CamActivity.this, content, Toast.LENGTH_LONG).show();
    }

}