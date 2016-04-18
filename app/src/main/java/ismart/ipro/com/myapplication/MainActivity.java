package ismart.ipro.com.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


import com.squareup.picasso.Picasso;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import ismart.ipro.com.myapplication.activity.About;
import ismart.ipro.com.myapplication.activity.ArticleActivity;
import ismart.ipro.com.myapplication.activity.CourseActivity;
import ismart.ipro.com.myapplication.activity.History;
import ismart.ipro.com.myapplication.activity.LoginActivity;
import ismart.ipro.com.myapplication.activity.TrainingActivity;
import ismart.ipro.com.myapplication.adapter.MyRecyclerAdapter;
import ismart.ipro.com.myapplication.event.ActivityResultBus;
import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.event.CourseRequestedEvent;
import ismart.ipro.com.myapplication.event.FeedReceivedEvent;
import ismart.ipro.com.myapplication.event.FeedRequestedEvent;
import ismart.ipro.com.myapplication.event.PhotoReceivedEvent;
import ismart.ipro.com.myapplication.event.PhotoRequestedEvent;
import ismart.ipro.com.myapplication.model.Post;

public class MainActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RelativeLayout content_frame;
    LinearLayout layoutImg;
    ProgressBar progressBar2;
    PrefManager prefManager;
    MyRecyclerAdapter myRecyclerAdapter;
    IsmartApp aController;
    String u_email,password;

    ArrayList<Post> list = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();
    ObservableRecyclerView recList;
    ImageView image_view;

    String photo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomActivityOnCrash.install(this);
        aController = (IsmartApp) getApplicationContext();
        prefManager = IsmartApp.getPrefManagerPaty();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image_view = (ImageView) findViewById(R.id.image_view);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        layoutImg = (LinearLayout) findViewById(R.id.layout_img);
      //  mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        progressBar2.setVisibility(View.VISIBLE);
        ApiBus.getInstance().postQueue(new FeedRequestedEvent());
        ApiBus.getInstance().postQueue(new PhotoRequestedEvent());
        ApiBus.getInstance().postQueue(new CourseRequestedEvent());



        //String selfUserId = IsmartApp.getInstance().getPrefManager().getUser().getId();
        // String selfUserId =  IsmartApp.getInstance().getPrefManagerPaty().id().getOr("");
        String selfUserId = IsmartApp.getInstance().getPrefManagerPaty().vendeName().getOr("");
        Log.e("ssss", selfUserId);


        setupViews();
        recList = (ObservableRecyclerView) findViewById(R.id.cardList);
        recList.setScrollViewCallbacks(this);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);



        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Maintenance Community");
            toolbar.setTitleTextColor(Color.BLACK);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);
            drawerLayout.setDrawerListener(drawerToggle);
            drawerToggle.setDrawerIndicatorEnabled(true);

            drawerToggle.syncState();
        }

        GCMRegistrar.checkDevice(this);


        // Make sure the manifest permissions was properly set
        GCMRegistrar.checkManifest(this);

        //lblMessage = (TextView) findViewById(R.id.lblMessage);


        // Register custom Broadcast receiver to show messages on activity
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                CommonUtilities.DISPLAY_MESSAGE_ACTION));

    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String newMessage = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);

            // Waking up mobile if it is sleeping
            aController.acquireWakeLock(getApplicationContext());

            // Display message on the screen
            //lblMessage.append(newMessage + "\n");

            Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();



            // Releasing wake lock
            aController.releaseWakeLock();
        }
    };
    private void setupViews() {
        // navigationView.addHeaderView(new DrawerHeaderView(this));
        ImageView user_image = (ImageView) navigationView.findViewById(R.id.user_image);
        LinearLayout News = (LinearLayout) navigationView.findViewById(R.id.News);
        LinearLayout vdo = (LinearLayout) navigationView.findViewById(R.id.vdo);
        LinearLayout About = (LinearLayout) navigationView.findViewById(R.id.About);
        LinearLayout Log = (LinearLayout) navigationView.findViewById(R.id.Log);
        LinearLayout fb = (LinearLayout) navigationView.findViewById(R.id.fb);
        LinearLayout his = (LinearLayout) navigationView.findViewById(R.id.history);
        LinearLayout articles = (LinearLayout) navigationView.findViewById(R.id.articles);
        LinearLayout train = (LinearLayout) navigationView.findViewById(R.id.training_menu);


        Picasso.with(getApplicationContext())
                .load(R.drawable.ipro)
                .fit()
                .into(user_image);
        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                drawerLayout.closeDrawers();
                Intent i = new Intent(getApplicationContext(), About.class);
                startActivity(i);
            }
        });
        vdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), CourseActivity.class);
                startActivity(i);
            }
        });
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOut();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/iprotoday/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihis = new Intent(getApplicationContext(), History.class);
                startActivity(ihis);
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itrain = new Intent(getApplicationContext(), TrainingActivity.class);
                startActivity(itrain);
            }
        });
        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iar = new Intent(getApplicationContext(), ArticleActivity.class);
                startActivity(iar);
            }
        });
    }

    public void logOut(){
        drawerLayout.closeDrawers();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isCheckLogin", false);
        editor.commit();
        Intent intenLogout = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intenLogout);
		finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }


    @Subscribe
    public void GetFeed(final FeedReceivedEvent event) {
        if (event != null) {
            progressBar2.setVisibility(View.GONE);
            list.add(event.getPost());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                Log.e("sssss", event.getPost().getPost().get(i).getFile_img());
            }

            myRecyclerAdapter = new MyRecyclerAdapter(getApplicationContext(), list);
            recList.setAdapter(myRecyclerAdapter);

            myRecyclerAdapter.SetOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

            myRecyclerAdapter.SetOnItemClickListenerShare(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String image = list.get(position).getPost().get(position).getFile_img();
                    String title = list.get(position).getPost().get(position).getTitle();
                    String link = list.get(position).getPost().get(position).getLink();
                    Uri myUri = Uri.parse(image);

//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, title);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, link);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, myUri);
//                    shareIntent.setType("image/*");
//                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(Intent.createChooser(shareIntent, "Share images..."));

                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                    // Add data to the intent, the receiving app will decide
                    // what to do with it.
                    share.putExtra(Intent.EXTRA_SUBJECT, "ข่าวสารจาก Maintenance Community");
                    share.putExtra(Intent.EXTRA_TEXT, link);

                    startActivity(Intent.createChooser(share, "Share link!"));
                }
            });

            myRecyclerAdapter.SetOnItemClickListenerRead(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
        }

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            if(android.os.Build.VERSION.SDK_INT >= 16)
            {
                finishAffinity();
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        //Snackbar.make(findViewById(R.id.photo_album_parent_view), "Please click BACK again to exit", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Subscribe
    public void GetPhoto(final PhotoReceivedEvent event) {
        if (event != null) {

            photo1 = event.getPost().getPost().get(0).getFile_img();
//            photo2 = event.getPost().getPost().get(1).getFile_img();
//            photo3 = event.getPost().getPost().get(2).getFile_img();
            Picasso.with(getApplicationContext())
                    .load(R.drawable.header)
                    .fit()
                    .into(image_view);
        }

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (layoutImg.isShown()) {
//                ab.hide();
                layoutImg.setVisibility(View.GONE);
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!layoutImg.isShown()) {
//                ab.show();
                layoutImg.setVisibility(View.VISIBLE);
            }
        }

    }
}

