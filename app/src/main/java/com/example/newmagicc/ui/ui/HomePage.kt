package com.example.newmagicc.ui.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.newmagicc.R
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.view.WindowManager
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.RelativeLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.webkit.WebResourceError

import android.webkit.WebResourceRequest
import android.widget.Toast


class HomePage : AppCompatActivity() {


    lateinit var web :WebView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var  NointernetBtn : Button
    lateinit var relativeLayout : RelativeLayout

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        NointernetBtn = findViewById(R.id.btnRetry)
        swipeRefreshLayout = findViewById(R.id.swipe);
        relativeLayout = findViewById(R.id.nointernet);
        web = findViewById(R.id.myweb)





            web.loadUrl("https://newmagicc.com/")

            var mywebsettings : WebSettings = web.getSettings()
            mywebsettings.setJavaScriptEnabled(true);

        web.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                internetcheck()
                super.onReceivedError(view, request, error)
            }
        }


        //improve webview performance
        web.getSettings().loadsImagesAutomatically = true;
            web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            web.getSettings().setAppCacheEnabled(true);
            web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mywebsettings.setDomStorageEnabled(true);
            mywebsettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            mywebsettings.setUseWideViewPort(true);
           // mywebsettings.setSavePassword(true);
           // mywebsettings.setSaveFormData(true);
           // mywebsettings.setEnableSmoothTransition(true);
        web.loadUrl("https://newmagicc.com/")

            //pull to refresh

            swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

                    swipeRefreshLayout.setRefreshing(true);
                    Handler().postDelayed(Runnable {
                        swipeRefreshLayout.setRefreshing(false);
                        web.reload();
                    }, 1000) ;

            })


            swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark)

            );

            //internet connection check

            internetcheck();

            NointernetBtn.setOnClickListener(View.OnClickListener {
                internetcheck();
            });



        }

    var doubleBackPressed : Boolean = false
    override fun onBackPressed() {

        if(web.canGoBack()){

            web.goBack()
        }
        else {
            if(doubleBackPressed)
            {
                finishAffinity();
            }
            else
            {
                doubleBackPressed=true;
                Toast.makeText(this, R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();
                //delay

               Handler().postDelayed(Runnable {
                   doubleBackPressed=false;
               }, 2000)

            }
            super.onBackPressed();
        }
    }

      fun  internetcheck(){

         val connectivityManager : ConnectivityManager  =
             this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
           val mobiledata : NetworkInfo =
               connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!;
          val wifi : NetworkInfo =
              connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!;

          if(mobiledata.isConnected()){
              web.setVisibility(View.VISIBLE);
              swipeRefreshLayout.setVisibility(View.VISIBLE);
              relativeLayout.setVisibility(View.GONE);
              web.reload();


          }

          else if(wifi.isConnected()){

              web.setVisibility(View.VISIBLE);
              swipeRefreshLayout.setVisibility(View.VISIBLE);
              relativeLayout.setVisibility(View.GONE);
              web.reload();
          }

          else{

              web.setVisibility(View.GONE);
              swipeRefreshLayout.setVisibility(View.GONE);
              relativeLayout.setVisibility(View.VISIBLE);

          }
      }


    }
