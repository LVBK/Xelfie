package lvbk.xsp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lvbk.xsp.Controllers.WallController;
import lvbk.xsp.Models.Image;

public class WallActivity extends Activity {

    private ProgressDialog pDialog;
    ArrayList<Image> images = new ArrayList<Image>();
    ListView lv;
    ImageArrayAdapter adapter;
    public void setData(ArrayList<Image> images){
        this.images = images;
        adapter = new ImageArrayAdapter(this, R.layout.wall_item, images);
        lv.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        lv = (ListView)findViewById(R.id.listView);
        new WallController(WallActivity.this).execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("DATA");
                Intent imageIntent = new Intent(WallActivity.this, ImageActivity.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putSerializable("image", images.get(position));
                imageBundle.putString("uname", bundle.getString("uname"));
                imageIntent.putExtra("DATA",imageBundle);
                startActivity(imageIntent);
            }
        });
    }
    private class ImageArrayAdapter extends ArrayAdapter<Image>
    {
        Activity context=null;
        ArrayList<Image> myArray=null;
        int layoutId;

        public ImageArrayAdapter(Activity context, int layoutId, ArrayList<Image> arr){
            super(context, layoutId, arr);
            this.context=context;
            this.layoutId=layoutId;
            this.myArray=arr;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= context.getLayoutInflater();
            convertView=inflater.inflate(layoutId, null);
            //chỉ là test thôi, bạn có thể bỏ If đi
            if(myArray.size()>0 && position>=0)
            {
                final TextView uname=(TextView)
                        convertView.findViewById(R.id.uname);
                final ImageView imageView=(ImageView)
                        convertView.findViewById(R.id.imageView);
                final Image image =myArray.get(position);
                uname.setText(image.getUname());
                String imageFile = myArray.get(position).getImageFile();
                byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                imageView.setImageBitmap(bmp);
            }
            return convertView;
        }

    }
}