package com.cevapne.cevapne;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class sendview extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sendview);
        
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        
        if (Intent.ACTION_SEND.equals(action) && type != null) {
             if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        }
	}

	void handleSendImage(Intent intent) {
	    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	    	((TextView)findViewById(R.id.textView1)).setText(imageUri.toString());
	    	((ImageView)findViewById(R.id.img)).setImageURI(imageUri);
	        // Update UI to reflect image being shared
	    }
	}

}
