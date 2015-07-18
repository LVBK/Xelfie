package lvbk.xsp;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lvbk.xsp.Controllers.ImageController;
import lvbk.xsp.Models.Comment;
import lvbk.xsp.Models.Image;
import lvbk.xsp.Models.TouchImageView;

public class ImageActivity extends Activity {
    Button btnUsername;
    ImageButton btnLike, btnComment;
    TouchImageView imageView;
    ListView listComment;
    CommentArrayAdapter adapter;
    ArrayList<Comment> comments = new ArrayList<Comment>();
    Bundle bundle;

    public void updateUI(){
        adapter = new CommentArrayAdapter(this, R.layout.comment_item,comments);
        listComment.setAdapter(adapter);
    }
    public void makeText(String content){
        Toast.makeText(ImageActivity.this, content, Toast.LENGTH_LONG).show();
    }
    public void setData(ArrayList<Comment> comments){
        this.comments = comments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("DATA");
        Image image = (Image)bundle.getSerializable("image");
        new ImageController(ImageActivity.this).execute(image.getKey());
        listComment = (ListView)findViewById(R.id.listComment);
        btnUsername = (Button)findViewById(R.id.btnUsername);
        btnLike = (ImageButton)findViewById(R.id.btnLike);
        btnComment = (ImageButton)findViewById(R.id.btnComment);
        imageView = (TouchImageView)findViewById(R.id.imageView3);
        btnUsername.setText(image.getUname());
        String imageFile = image.getImageFile();
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        imageView.setImageBitmap(bmp);
        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, CommentActivity.class);
                intent.putExtra("DATA", bundle);
                startActivity(intent);
            }
        });
        listComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent userIntent = new Intent(ImageActivity.this, UserActivity.class);
                Bundle bundle2 = new Bundle();
                String uname = bundle.getString("uname");
                System.out.println(uname);
                String unameFollow = comments.get(position).getUname();
                bundle2.putString("uname", uname);
                bundle2.putString("unameFollow", unameFollow);
                userIntent.putExtra("DATA", bundle2);
                startActivity(userIntent);

            }
        });

    }
    private class CommentArrayAdapter extends ArrayAdapter<Comment>
    {
        Activity context=null;
        ArrayList<Comment> myArray=null;
        int layoutId;

        public CommentArrayAdapter(Activity context, int layoutId, ArrayList<Comment> arr){
            super(context, layoutId, arr);
            this.context=context;
            this.layoutId=layoutId;
            this.myArray=arr;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= context.getLayoutInflater();
            convertView=inflater.inflate(layoutId, null);
            if(myArray.size()>0 && position>=0)
            {
                final TextView uname=(TextView)
                        convertView.findViewById(R.id.nameTV);
                final TextView content=(TextView)
                        convertView.findViewById(R.id.contentTV);
                final Comment comment =myArray.get(position);
                uname.setText(comment.getUname());
                content.setText(comment.getContent());
            }
            return convertView;
        }

    }

}
