package ismart.ipro.com.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static ismart.ipro.com.myapplication.CommonUtilities.SENDER_ID;
import static ismart.ipro.com.myapplication.CommonUtilities.SERVER_URL;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import ismart.ipro.com.myapplication.AlertDialogManager;
import ismart.ipro.com.myapplication.CommonUtilities;
import ismart.ipro.com.myapplication.ConnectionDetector;
import ismart.ipro.com.myapplication.IsmartApp;
import ismart.ipro.com.myapplication.MainActivity;
import ismart.ipro.com.myapplication.PrefManager;
import ismart.ipro.com.myapplication.R;



public class LoginActivity extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    String  REGID;
    // Internet detector
    ConnectionDetector cd;
    SharedPreferences sharedPreferences ;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String TAG = LoginActivity.class.getSimpleName();
    private EditText inputPass, inputEmail;
    private TextInputLayout inputLayoutName, inputLayoutEmail;
    private Button btnEnter;
    private TextView txt_register, textView13;
    private String regId;
    PrefManager pref;
    Dialog loadingDialog;
    private AQuery aq;
    String regId1;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean check = sharedPreferences.getBoolean("isCheckLogin",false);
        final IsmartApp aController = (IsmartApp) getApplicationContext();
        Log.e("chk",check+"");

        if (check != false) {
            Log.e("chk", check + "");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        CustomActivityOnCrash.install(this);

        setContentView(R.layout.activity_login);
        cd = new ConnectionDetector(getApplicationContext());
        aq = new AQuery(this);
        pref = IsmartApp.getPrefManagerPaty();
        regId = pref.token().getOr("bbb");

        cd = new ConnectionDetector(getApplicationContext());
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        //GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
        String regIdNew = "";
        if (GCMRegistrar.isRegistered(this)) {
            regIdNew = GCMRegistrar.getRegistrationId(this);
            Log.e("regIdNew1", regIdNew);
        } else {
            GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
            Log.e("regIdNew2", regIdNew);
        }
        if (regIdNew.equals("")) {
            GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
            Log.e("regIdNew3", regIdNew);
        } else {

        }


         regId1 = regIdNew;
        Log.e("REGID", regId1);
        Toast.makeText(getApplicationContext(),regId1,Toast.LENGTH_SHORT).show();
        // Toast.makeText(getBaseContext(), "Got Message: check "+regIdNew , Toast.LENGTH_LONG).show();




        // Check if GCM configuration is set
        if (CommonUtilities.SERVER_URL == null || CommonUtilities.SENDER_ID == null || CommonUtilities.SERVER_URL.length() == 0
                || CommonUtilities.SENDER_ID.length() == 0) {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(LoginActivity.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);

            // stop executing code by return
            return;
        }

        loadingDialog = new Dialog(LoginActivity.this, R.style.FullHeightDialog);
        textView13 = (TextView) findViewById(R.id.textView13);
        loadingDialog.setContentView(R.layout.dialog_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_register = (TextView) findViewById(R.id.txt_register);
        setSupportActionBar(toolbar);
//        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
//        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputPass = (EditText) findViewById(R.id.input_pass);
        inputEmail = (EditText) findViewById(R.id.input_email);
        btnEnter = (Button) findViewById(R.id.btn_enter);




//        inputName.addTextChangedListener(new MyTextWatcher(inputName));
//        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login();
                onLoginButtonClickTest();
                onLoginButtonClick();

            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              //  Is_Valid_Email(inputEmail);
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    email = null;
                } else {
                    email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        });

        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ForgetActivity.class);
//                startActivity(i);
//                finish();
            }
        });

    }

    /**
     * logging in user. Will make http post request with name, email
     * as parameters
     */


    private void onLoginButtonClick() {
        email = inputEmail.getText().toString();
        password = inputPass.getText().toString();

        Log.e("ttt", regId);
        Log.e("email", email);
        Log.e("password", password);
        loadingDialog.show();
        if (TextUtils.isEmpty(email)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่พาสเวิร์ด", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://mn-community.com/web/login_all.php";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("regId", regId1);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        Log.e("qqqq", json.optInt("success") + "");
        String id = json.getString("chat_id");
        String nameHeader = json.getString("name");
        Log.e("ddd", id);
        Log.e("aaaaa", nameHeader);
        if (success == 0) {
            Toast.makeText(getApplicationContext(), "กรอก Email หรือ Password ผิด", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
        if (success == 1) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isCheckLogin", true);
            editor.commit();
            String email = json.getString("email");
            String name = json.getString("name");


            pref.isLogin().put(true);
            pref.userName().put(json.getString("username"));
            pref.vendeName().put(nameHeader);
            pref.id().put(id);
            pref.commit();


        }
    }

    private void onLoginButtonClickTest() {

        String url = "http://192.168.1.104:8080/gcm_server_php/register.php";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "sdsds");
        params.put("email", "sdsdsd");
        params.put("regId", regId1);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallbackTest");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallbackTest(String url, JSONObject json, AjaxStatus status) throws JSONException {

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

        Toast.makeText(LoginActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        //Snackbar.make(findViewById(R.id.photo_album_parent_view), "Please click BACK again to exit", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
