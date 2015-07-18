package lvbk.xsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;

import lvbk.xsp.Models.Comment;
import lvbk.xsp.Models.Image;

/**
 * Created by lvbk on 18/06/2015.
 */
public class CommentActivity extends Activity {
    private Firebase commentRef = new Firebase(LoginActivity.URL).child("Comments");
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        final String uname = bundle.getString("uname");
        final Image image = (Image)bundle.getSerializable("image");
        final EditText commentET =(EditText)findViewById(R.id.commentET);
        ImageButton btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = commentET.getText().toString();
                if(!content.equals("")) {
                    Comment comment = new Comment(uname, content, image.getKey());
                    commentRef.push().setValue(comment);
                    finish();
                }
                else
                    Toast.makeText(CommentActivity.this, "Please enter a comment", Toast.LENGTH_LONG).show();
            }
        });
    }
}
