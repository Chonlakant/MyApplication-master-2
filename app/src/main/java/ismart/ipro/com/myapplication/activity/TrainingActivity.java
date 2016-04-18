package ismart.ipro.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.adapter.TrainingAdapter;
import ismart.ipro.com.myapplication.event.ActivityResultBus;
import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.event.TraingReceivedEvent;
import ismart.ipro.com.myapplication.event.TraingRequestedEvent;
import ismart.ipro.com.myapplication.model.Post;

public class TrainingActivity extends AppCompatActivity {

    ArrayList<Post> training = new ArrayList<>();
    ListView listView;
    TrainingAdapter trainingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        listView = (ListView) findViewById(R.id.training);
        ApiBus.getInstance().postQueue(new TraingRequestedEvent());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = training.get(position).getPost().get(position).getTitle();
                String link = training.get(position).getPost().get(position).getLink();
                Intent detail = new Intent(getApplicationContext(),DetailTrainActivity.class);
                detail.putExtra("title",title);
                detail.putExtra("link",link);
                Log.e("data",title+"\n"+link);
                startActivity(detail);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }

    @Subscribe
    public void GetList(final TraingReceivedEvent event) {
        if (event != null) {

//            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                training.add(event.getPost());
                Log.e("event", event.getPost().getPost() + "");
//            }

            trainingAdapter  = new TrainingAdapter(getApplicationContext(),training);
            listView.setAdapter(trainingAdapter);
        }

    }

}
