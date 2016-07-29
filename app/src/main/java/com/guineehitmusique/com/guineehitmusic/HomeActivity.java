package com.guineehitmusique.com.guineehitmusic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


//TODO Add checkGooglePlayServices()

public class HomeActivity extends AppCompatActivity implements SettingDialogFragment.shareListener{

    WebView pageView;
//    public ProgressDialog ringProgressDialog ;
    String url;
    private static final String TAG = "HomeActivity";
    public boolean IS_HOME = true;

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SettingDialogFragment.set_subscription(this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity Created");
        setContentView(R.layout.activity_home);

        handleIntent(getIntent());
//        SettingDialogFragment.set_subscription(this);
//        callFCM();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SettingDialogFragment().show(getSupportFragmentManager(), "NotifDialog");
            }
        });

//        setupProgressdialog();

        CookieManager.getInstance().setAcceptCookie(true);
        setupPageView();


        loadPage();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Do nothing else
    }

    public void loadPage(){
        pageView.loadUrl(this.url);
    }

    public void loadPage(String url){
        pageView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (pageView.copyBackForwardList().getCurrentIndex() > 0) {
            pageView.goBack();
        }
        else if(!IS_HOME){
            loadPage();
        }
        else
            super.onBackPressed();
    }

    public void handleIntent(Intent intent){
        if(intent == null){
            this.url = getString(R.string.main_url);
            Log.d("HomeActivity", "handleIntent : Intent is blank");
            IS_HOME = true;
            return;
        }

        try{
            Log.d(TAG, "handleIntent" + intent.getExtras().keySet());
        }
        catch (Exception e){
            Log.d(TAG, "handleIntent : No extras, errorMessage : " +e.getMessage());
            IS_HOME = true;
        }
        this.url = intent.getStringExtra(MyFirebaseMessagingService.URL_KEY);
        if(this.url == null){
            this.url = getString(R.string.main_url);
            Log.d(TAG, "handleIntent :  null url inside intent, opening url : " + url);
            IS_HOME = true;
            return;
        }
        Log.d(TAG, "handleIntent :  found intent, opening url : " + url);
        IS_HOME = false;

    }

    public void setupProgressdialog(){
//        ringProgressDialog = new ProgressDialog(this);
//        ringProgressDialog.setTitle(getString(R.string.app_name));
//        ringProgressDialog.setMessage("Loading ...");
//        ringProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void setupPageView(){
        pageView = (WebView) findViewById(R.id.webView);
        WebSettings settings = (WebSettings)pageView.getSettings();
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setIndeterminate(true);

        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);

        pageView.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
//                progressDialog.setProgress(progress);
            }
        });
        pageView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                //The Page to be loaded in this app only
                //Can use conditions on the URL
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                pageView.loadUrl(getString(R.string.error_page_loc));

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                progressBar.setVisibility(View.VISIBLE);
//                ringProgressDialog.show();
//                ringProgressDialog = ProgressDialog.show(HomeActivity.this, getString(R.string.app_name), " Loading ...", true);
//                ringProgressDialog.setCancelable(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                ringProgressDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sharepageLoad(String url) {
        loadPage(url);
    }

//    public void callFCM(){
//        MyFirebaseInstanceIDService idService = new MyFirebaseInstanceIDService();
//        idService.onTokenRefresh();
//    }
}
