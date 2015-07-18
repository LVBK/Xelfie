package lvbk.xsp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lvbk.xsp.Controllers.UserController;
import lvbk.xsp.Models.Image;

public class UserActivity extends Activity {

    private ArrayList<Image> images = new ArrayList<Image>();
    TextView unameTV;
    ImageView avatar;
    GridView gridView;
    private ProgressDialog pDialog;

    public void setAvatar(Bitmap bmp){
        avatar.setImageBitmap(bmp);
    }
    public void updateUI(){
        gridView.setAdapter(new ImageAdapter(UserActivity.this)); //pDialog.dismiss();
    }
    public void makeText(String content){
        Toast.makeText(UserActivity.this, content, Toast.LENGTH_LONG).show();
    }
    public void setData(ArrayList<Image> images){
        this.images = images;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        unameTV = (TextView)findViewById(R.id.unameTextView);
        avatar = (ImageView)findViewById(R.id.avatarView);
        gridView = (GridView) findViewById(R.id.gridview);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        final String uname = bundle.getString("uname");
        final String unameFollow = bundle.getString("unameFollow");
        unameTV.setText(unameFollow);
        /*pDialog = new ProgressDialog(UserActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();*/

        new UserController(UserActivity.this).execute(images);


        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(UserActivity.this, ImageActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("image", images.get(position));
                bundle2.putString("uname", uname);
                intent.putExtra("DATA", bundle2);
                startActivity(intent);

            }
        });
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            context = c;
        }
        //---returns the number of images---
        public int getCount() {
            try {
                return images.size();
            }catch (Exception e){
                return 0;
            }
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(158, 160));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0,0,0,0);
            } else {
                imageView = (ImageView) convertView;
            }
            String imageFile = images.get(position).getImageFile();
            byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            imageView.setImageBitmap(bmp);
            return imageView;
        }

    }
}

