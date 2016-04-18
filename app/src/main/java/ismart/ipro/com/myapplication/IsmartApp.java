package ismart.ipro.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.service.ApiHandler;
import ismart.ipro.com.myapplication.service.ApiHandlerCourse;
import ismart.ipro.com.myapplication.service.ApiService;
import ismart.ipro.com.myapplication.service.ApiServiceCourse;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


/**
 * Created by madhur on 3/1/15.
 */
public class IsmartApp extends Application implements Application.ActivityLifecycleCallbacks {
    private static PrefManager prefManager;
    public static final String TAG = IsmartApp.class
            .getSimpleName();
    public static final String API_ENDPOINT = "http://mn-community.com";
    public static final String COURSE_API_ENDPOINT = "http://todayissoftware.com";

    public static Activity currentActivity;


    private static IsmartApp Instance;
    public static volatile Handler applicationHandler = null;
    private ApiHandler someApiHandler;
    private ApiHandlerCourse courseApiHandler;

    private static OkHttpClient sHttpClient;
    private static Context sContext = null;


    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        Instance = this;
        applicationHandler = new Handler(getInstance().getMainLooper());
        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));
        saveInstallation(0);


        someApiHandler = new ApiHandler(this, buildApi(), ApiBus.getInstance());
        someApiHandler.registerForEvents();
        courseApiHandler = new ApiHandlerCourse(this, buildCourseApi(), ApiBus.getInstance());
        courseApiHandler.registerForEvents();
    }


    public static Context getAppContext() {
        return sContext;
    }


    public static OkHttpClient getHttpClient() {
        if (sHttpClient == null) {
            sHttpClient = new OkHttpClient();
            int cacheSize = 10 * 1024 * 1024;
            File cacheLocation = new File(StorageUtils.getIdealCacheDirectory(IsmartApp.getAppContext()).toString());
            cacheLocation.mkdirs();
            com.squareup.okhttp.Cache cache = new com.squareup.okhttp.Cache(cacheLocation, cacheSize);
            sHttpClient.setCache(cache);
        }
        return sHttpClient;
    }


    ApiService buildApi() {

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_ENDPOINT)

                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //request.addQueryParam("p1", "var1");
                        //request.addQueryParam("p2", "");
                    }
                })

                .build()
                .create(ApiService.class);
    }

    ApiServiceCourse buildCourseApi() {

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(COURSE_API_ENDPOINT)

                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //request.addQueryParam("p1", "var1");
                        //request.addQueryParam("p2", "");
                    }
                })

                .build()
                .create(ApiServiceCourse.class);
    }

    public static void saveInstallation(int userId) {
    }
    public static void removeInstallation(int userId) {

    }

    public static IsmartApp get(Context context) {
        return (IsmartApp) context.getApplicationContext();
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public void logout(Context context) {
//        pref.clear();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);

    }

    //Function to display simple Alert Dialog
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Set Dialog Title
        alertDialog.setTitle(title);

        // Set Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Set alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Set OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Show Alert Message
        alertDialog.show();
    }

    // Notifies UI to display a message.
    void displayMessageOnScreen(Context context, String message) {

        Intent intent = new Intent(CommonUtilities.DISPLAY_MESSAGE_ACTION);
        intent.putExtra(CommonUtilities.EXTRA_MESSAGE, message);

        // Send Broadcast to Broadcast receiver with message
        context.sendBroadcast(intent);

    }

    void unregister(final Context context, final String regId) {

        Log.i("TAG", "unregistering device (regId = " + regId + ")");

        String serverUrl = CommonUtilities.SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);

        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            displayMessageOnScreen(context, message);
        } catch (IOException e) {

            // At this point the device is unregistered from GCM, but still
            // registered in the our server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.

            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            displayMessageOnScreen(context, message);
        }
    }

    public static IsmartApp getInstance() {
        return Instance;
    }
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {

            url = new URL(endpoint);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }

        String body = bodyBuilder.toString();

        Log.v("TAG", "Posting '" + body + "' to " + url);

        byte[] bytes = body.getBytes();

        HttpURLConnection conn = null;
        try {

            Log.e("URL", "> " + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // handle the response
            int status = conn.getResponseCode();

            // If response is not success
            if (status != 200) {

                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    public static boolean applicationOnPause = false;
    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
        currentActivity = arg0;
        Log.e("VMVMVM", "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        applicationOnPause = false;
        Log.e("VMVMVM", "onActivityResumed " + activity.getClass());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        applicationOnPause = true;
        Log.e("VMVMVM", "onActivityPaused " + activity.getClass());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("VMVMVM", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("VMVMVM", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }
    public static PrefManager getPrefManagerPaty() {
        return prefManager;
    }
    private PowerManager.WakeLock wakeLock;

    public  void acquireWakeLock(Context context) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");

        wakeLock.acquire();
    }

    public  void releaseWakeLock() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
}
