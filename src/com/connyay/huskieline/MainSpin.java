package com.connyay.huskieline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSpin extends Activity implements
		AdapterView.OnItemSelectedListener, android.view.View.OnClickListener {
	// String from the spinner
	private String route;
	WebView myWebView;
	// Array used to build the spinner
	private static final String[] routes = { "Route 1", "Route 2", "Route 3",
			"Route 4", "Route 5", "Route 7", "Route 8", "Weekend" };
	private Button busSched, busTrack;
	final Activity activity = this;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.spin_choice);

		// DUMMY WEBVIEW
		// Needed to retrieve the cookie for the huskietracks site 
		myWebView = (WebView) findViewById(R.id.dummy);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView
				.loadUrl("http://huskietracks.niu.edu/dynamicdefault.asp?mapType=JPG");
		
		// Give the user feedback for the fake webview
		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage("Preparing routes...");
		// Can't dismiss this!
		progressDialog.setCancelable(false);
	
		myWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				progressDialog.show();
				progressDialog.setProgress(0);
				activity.setProgress(progress * 1000);

				progressDialog.incrementProgressBy(progress);

				if (progress == 100 && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}

			}
		});

		// Set the header font to a custom one located in assets/fonts
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Kick.otf");
		TextView tv = (TextView) findViewById(R.id.header);
		tv.setTypeface(tf);

		// Get reference to buttons
		busSched = (Button) findViewById(R.id.busSched);
		busTrack = (Button) findViewById(R.id.busTrack);

		// Connect buttons to onclicklistener
		busSched.setOnClickListener(this);
		busTrack.setOnClickListener(this);

		// Create spinner
		Spinner spin = (Spinner) findViewById(R.id.spinner);
		// Connect spinner to listener
		spin.setOnItemSelectedListener(this);

		// Spinner adapter. Builds spinner from the routes array.
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
				R.layout.spinnerformat, routes);

		// Used my own spinnerformat because the stock one had radio buttons
		myAdapter.setDropDownViewResource(R.layout.spinnerformat_dropdown);
		// Connect spinner and adapter
		spin.setAdapter(myAdapter);
	}

	// Get the value of the route selected from the spinner
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		route = routes[position];
	}
	
	// Do nothing
	public void onNothingSelected(AdapterView<?> parent) {
	}

	// Determine what to do with the spinner choice
	public void onClick(View v) {

		if (v == busSched) {
			// Special case for route 4
			if (route.equals("Route 4"))
				route = "Campus+Circle";
			// Call the schedule activity
			Intent intent = new Intent(MainSpin.this, Schedule.class);
			intent.putExtra("route", route);
			startActivity(intent);

		}
		else if (v == busTrack) {
			
			// Special case for route 4
			if (route.equals("Route 4"))
				route = "Campus+Circle";
			// Call the track activity
			Intent intent = new Intent(MainSpin.this, Track.class);
			intent.putExtra("route", route);
			startActivity(intent);
		}
	}

	
}