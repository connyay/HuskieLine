package com.connyay.huskieline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Schedule extends Activity {
	String passedRoute;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus);
		// get reference to the textview in the header
		final TextView tv = (TextView) findViewById(R.id.webHead);
		// Get string from intent with getExtras
		String route = getIntent().getExtras().getString("route");
		passedRoute = route;

		// Special case for Route 4
		if (route.equals("Campus+Circle"))
			tv.setText("Route 4");
		else
			tv.setText(route);

		// Get reference to the webview
		final WebView myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		myWebView.setBackgroundColor(Color.BLACK);
		webSettings.setJavaScriptEnabled(true);

		//
		// Begin massive if else.
		//
		// Determines which schedule to load by comparing the string passed by
		// intent. For 3 & 4 a dialog will pop up because there is two choices.

		if (route.equals("Route 1"))
			myWebView.loadUrl("file:///android_res/raw/route1.html");
		else if (route.equals("Route 2"))
			myWebView.loadUrl("file:///android_res/raw/route2.html");

		else if (route.equals("Route 3")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Schedule.this);
			builder.setMessage("Route 3 or 3A?");
			builder.setCancelable(false);

			builder.setPositiveButton("3",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							myWebView
									.loadUrl("file:///android_res/raw/route3.html");
							tv.setText("Route 3");
						}
					});

			builder.setNegativeButton("3A",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							myWebView
									.loadUrl("file:///android_res/raw/route3a.html");
							tv.setText("Route 3A");
						}
					});

			AlertDialog alert = builder.create();
			alert.show();

		}

		else if (route.equals("Campus+Circle")) {
			AlertDialog.Builder builder2 = new AlertDialog.Builder(
					Schedule.this);
			builder2.setMessage("Circle Left or Right?");
			builder2.setCancelable(false);

			builder2.setPositiveButton("Left",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							myWebView
									.loadUrl("file:///android_res/raw/route4l.html");
							tv.setText("Circle 4L");
						}
					});

			builder2.setNegativeButton("Right",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							myWebView
									.loadUrl("file:///android_res/raw/route4r.html");
							tv.setText("Circle 4R");
						}
					});

			AlertDialog alert = builder2.create();
			alert.show();

		}

		else if (route.equals("Route 5"))
			myWebView.loadUrl("file:///android_res/raw/route5.html");
		else if (route.equals("Route 7"))
			myWebView.loadUrl("file:///android_res/raw/route7.html");
		else if (route.equals("Route 8"))
			myWebView.loadUrl("file:///android_res/raw/route8.html");
		else if (route.equals("Weekend"))
			myWebView.loadUrl("file:///android_res/raw/weekend.html");

	}

	// Create menu button menu to switch from schedule -> tracking
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_schedule, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(Schedule.this, Track.class);
		intent.putExtra("route", passedRoute);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

}