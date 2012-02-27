package com.connyay.huskieline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Track extends Activity {
	// String from the getExtra call from the intent
	String passedRoute;
	final Activity activity = this;
	WebView myWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus);
		// get reference to the textview in the header
		TextView tv = (TextView) findViewById(R.id.webHead);
		// Get string from intent with getExtras
		String route = getIntent().getExtras().getString("route");
		passedRoute = route;

		// Special case for route 4
		if (route.equals("Campus+Circle"))
			tv.setText("Route 4");
		else
			tv.setText(route);

		// Get the users screen dimension to get the proper size of map
		// These methods have been replaced in api 13... nothing better for
		// api < 13
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth(); // deprecated
		int height = display.getHeight(); // deprecated

		// Get reference to the webview
		myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// Looks better
		myWebView.setBackgroundColor(Color.BLACK);

		// Load the webview with the correct map
		myWebView
				.loadUrl("http://huskietracks.niu.edu/BusMapJPG.asp?userSelectedRoute="
						+ route
						+ "&dynamicMapWidth="
						+ width
						+ "&dynamicMapHeight=" + height);

		// Give the user feedback by a progress bar with the webview progress
		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage("Loading route...");
		progressDialog.setCancelable(true);

		myWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				progressDialog.show();
				progressDialog.setProgress(0);
				activity.setProgress(progress * 1000);

				progressDialog.incrementProgressBy(progress);

				if (progress == 100 && progressDialog.isShowing())
					progressDialog.dismiss();
			}
		});

	}

	// Create menu button menu to switch from tracking -> schedule
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_track, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Intent intent = new Intent(Track.this, Schedule.class);
			intent.putExtra("route", passedRoute);
			startActivity(intent);
			return true;
		case R.id.reload:
			myWebView.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}