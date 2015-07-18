package lvbk.xsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lvbk.xsp.Controllers.FollowableController;
import lvbk.xsp.Models.Follow;

/**
 * Created by lvbk on 17/06/2015.
 */
public class FollowableActivity extends Activity {
    ListView lv ;
    UserArrayAdapter adapter;
    ArrayList<Follow> followables = new ArrayList<Follow>();
    FollowableController fc =  new FollowableController(FollowableActivity.this);

    public void setData(ArrayList<Follow> followables){
        this.followables = followables;
        adapter = new UserArrayAdapter(this, R.layout.followable_item,followables);
        lv.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        lv = (ListView)findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        fc.execute(bundle.getString("uname"));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent userIntent = new Intent(FollowableActivity.this, UserActivity.class);
                Bundle bundle2 = new Bundle();
                String uname = followables.get(position).getUname();
                String unameFollow = followables.get(position).getUnameFollow();
                bundle2.putString("uname", uname);
                bundle2.putString("unameFollow", unameFollow);
                userIntent.putExtra("DATA", bundle2);
                startActivity(userIntent);

            }
        });
    }
    private class UserArrayAdapter extends ArrayAdapter<Follow>
    {
        Activity context=null;
        ArrayList<Follow > myArray=null;
        int layoutId;

        public UserArrayAdapter(Activity context, int layoutId, ArrayList<Follow> arr){
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
                final TextView txtdisplay=(TextView)
                        convertView.findViewById(R.id.uname);
                //lấy ra nhân viên thứ position
                final Follow follow =myArray.get(position);
                //đưa thông tin lên TextView
                //emp.toString() sẽ trả về Id và Name
                txtdisplay.setText(follow.getUnameFollow());
                Button btnFollow =(Button)convertView.findViewById(R.id.btnFollow);
                btnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fc.remove(myArray.get(position));
                        myArray.remove(position);
                        setData(myArray);
                    }
                });
            }
            return convertView;
        }

    }

}
