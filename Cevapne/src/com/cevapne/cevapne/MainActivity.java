package com.cevapne.cevapne;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	MenuItem mi_close;
	private ProgressBar progress;
	private WebView webview;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		if(webview.canGoBack()){
			webview.goBack();
		}else{
			super.onBackPressed();
		}
		
		
	}
	
    @SuppressLint({ "SetJavaScriptEnabled", "ShowToast" }) @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String mails="asd";
        
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        String possibleEmail = account.name;
		        mails+=possibleEmail+",";
		    }
		}
		
        
            
        
        
      
        
        
        webview=(WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://www.cevapne.com");
        webview.setWebChromeClient(new myWebChromeClient());
        myWebClient wc=new myWebClient();
        webview.setWebViewClient(wc);
        webview.getSettings().setSupportZoom(true);  
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setInitialScale(30);
        webview.getSettings().setUseWideViewPort(true);

		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setMax(100);
		
		
   
    }

   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if(id==mi_close.getItemId()){
        	finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class myWebChromeClient extends WebChromeClient {	
		@Override
		public void onProgressChanged(WebView view, int newProgress) {			
			MainActivity.this.setValue(newProgress);
			super.onProgressChanged(view, newProgress);
		}
	}
    
    private class myWebClient extends WebViewClient {

    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		// TODO Auto-generated method stub
    		super.onPageStarted(view, url, favicon);
    		progress.setVisibility(View.VISIBLE);
    	}

    	@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
    	public void onPageFinished(WebView view, String url) {
    		// TODO Auto-generated method stub
    		super.onPageFinished(view, url);
    		progress.setVisibility(View.INVISIBLE);
    	}

    }
    public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
