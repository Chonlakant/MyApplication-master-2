package ismart.ipro.com.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.adapter.TrainingAdapter;
import ismart.ipro.com.myapplication.adapter.TrainingDetailAdapter;
import ismart.ipro.com.myapplication.event.ActivityResultBus;
import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailTrainActivity extends AppCompatActivity {
    ProgressBar progressBar3;
    private Toolbar toolbar;

    ArrayList<Post.PostEntity> list = new ArrayList<>();
    TrainingDetailAdapter adapter;
    ListView listView;
    AQuery aq;
    String url;


    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_train);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.detailsTrain);
        aq = new AQuery(this);

        url = getIntent().getStringExtra("link");
        Log.e("ddd", url);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Articles");
            toolbar.setTitleTextColor(Color.BLACK);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

        aq.ajax(url, JSONObject.class, this, "jsonCallback");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = list.get(position).getLink();
                Intent i = new Intent(getApplicationContext(), webView.class);
                i.putExtra("link",link);
                startActivity(i);
            }
        });


    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) {
        //When JSON is not null
        if (json != null) {
            JSONArray ja = null;
            try {
                ja = json.getJSONArray("post");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject job = ja.getJSONObject(i);
                    Log.e("job", job + "");

                    Post.PostEntity item = new Post.PostEntity();
                    item.setLink(job.optString("link"));
                    item.setTitle(job.optString("title"));
                    list.add(item);
                } adapter = new TrainingDetailAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}