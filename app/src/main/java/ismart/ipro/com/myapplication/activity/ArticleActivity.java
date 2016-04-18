package ismart.ipro.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import ismart.ipro.com.myapplication.R;
import ismart.ipro.com.myapplication.adapter.ArticleListViewAdapter;
import ismart.ipro.com.myapplication.event.ActivityResultBus;
import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.event.ArticlesReceivedEvent;
import ismart.ipro.com.myapplication.event.ArticlesRequestedEvent;
import ismart.ipro.com.myapplication.model.Post;

public class ArticleActivity extends AppCompatActivity {

    ProgressBar progressBar4;
    ListView listView;
    ArrayList<Post> article = new ArrayList<>();
    ArticleListViewAdapter articleListViewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        listView = (ListView) findViewById(R.id.articles_list);
        ApiBus.getInstance().postQueue(new ArticlesRequestedEvent());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = article.get(position).getPost().get(position).getTitle();
                String link = article.get(position).getPost().get(position).getLink();
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

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    @Subscribe
    public void GetList(final ArticlesReceivedEvent event) {
        if (event != null) {
            Log.e("event", event.getPost().getPost().get(0).getTitle());
            String map = "บทความ CMMS";
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                Log.e("event", event.getPost().getPost().get(i).getTitle());
                if(!event.getPost().getPost().get(i).getTitle().matches(map)){
                    article.add(event.getPost());
                }
            }
            articleListViewAdapter  = new ArticleListViewAdapter(getApplicationContext(),article);
            listView.setAdapter(articleListViewAdapter);
        }

    }
}
