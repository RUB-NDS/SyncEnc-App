/****************************************************************************
 * Copyright (C) 2017 ecsec GmbH.
 * All rights reserved.
 * Contact: ecsec GmbH (info@ecsec.de)
 *
 * This file is part of the Open eCard App.
 *
 * GNU General Public License Usage
 * This file may be used under the terms of the GNU General Public
 * License version 3.0 as published by the Free Software Foundation
 * and appearing in the file LICENSE.GPL included in the packaging of
 * this file. Please review the following information to ensure the
 * GNU General Public License version 3.0 requirements will be met:
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * Other Usage
 * Alternatively, this file may be used in accordance with the terms
 * and conditions contained in a signed written agreement between
 * you and ecsec GmbH.
 *
 ***************************************************************************/

package org.openecard.demo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
//import android.support.customtabs.CustomTabsIntent;
import org.openecard.android.activation.ActivationImplementationInterface;
import org.openecard.demo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mike Prechtl
 */
public class IdsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ids);

		final WebView webView = findViewById(R.id.webView);
		webView.clearCache(true);

		// set up web view settings
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setSupportMultipleWindows(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		//setContentView(webView);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				System.out.println(" \t ---> Open new Window");
				//webView.removeAllViews();
				WebView newView = (WebView) findViewById(R.id.webView2);
				newView.getSettings().setJavaScriptEnabled(true);
				newView.getSettings().setDomStorageEnabled(true);
				newView.setWebViewClient(new WebViewClient() {
					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						System.out.println("\t --> TAP: " + url);
					}
				});

				//findViewById(R.id.webView).setVisibility(View.INVISIBLE);

				//webView.addView(newView);

				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(newView);
				resultMsg.sendToTarget();
				return true;
			}
		});


		if (getIntent().getData() != null) {
			webView.loadUrl(getIntent().getData().toString());
		} else {
			webView.loadUrl("https://argon.cloud.nds.rub.de:8080/");
			//this.openCustomChromeTab(Uri.parse("https://argon.cloud.nds.rub.de:8080/"));
		}
	}
/*
	private void openCustomChromeTab(Uri uri) {
		System.out.println("--------> openCustomChromeTab ---> " + uri.toString());
		CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

		// set toolbar colors
		//intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
		//intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		intentBuilder.setShowTitle(false);
		intentBuilder.enableUrlBarHiding();

		// build custom tabs intent
		CustomTabsIntent customTabsIntent = intentBuilder.build();

		customTabsIntent.intent.setPackage(this.getPackageNameToUse(getApplicationContext()));
		customTabsIntent.launchUrl(this, uri);

		// call helper to open custom tab
		/*CustomTabActivityHelper.openCustomTab(this, customTabsIntent, uri, new CustomTabActivityHelper.CustomTabFallback() {
			@Override
			public void openUri(Activity activity, Uri uri) {
				// fall back, call open open webview
				//openWebView(uri);
			}
		});*/
/*	}

	static final String STABLE_PACKAGE = "com.android.chrome";
	static final String BETA_PACKAGE = "com.chrome.beta";
	static final String DEV_PACKAGE = "com.chrome.dev";
	static final String LOCAL_PACKAGE = "com.google.android.apps.chrome";
	private static final String EXTRA_CUSTOM_TABS_KEEP_ALIVE =
			"android.support.customtabs.extra.KEEP_ALIVE";
	private static final String ACTION_CUSTOM_TABS_CONNECTION =
			"android.support.customtabs.action.CustomTabsService";
	private static String sPackageNameToUse;
	private static String getPackageNameToUse(Context context) {
		if (sPackageNameToUse != null) return sPackageNameToUse;

		PackageManager pm = context.getPackageManager();
		// Get default VIEW intent handler.
		Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
		ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);
		String defaultViewHandlerPackageName = null;
		if (defaultViewHandlerInfo != null) {
			defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
		}

		// Get all apps that can handle VIEW intents.
		List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
		List<String> packagesSupportingCustomTabs = new ArrayList<>();
		for (ResolveInfo info : resolvedActivityList) {
			Intent serviceIntent = new Intent();
			serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
			serviceIntent.setPackage(info.activityInfo.packageName);
			if (pm.resolveService(serviceIntent, 0) != null) {
				packagesSupportingCustomTabs.add(info.activityInfo.packageName);
			}
		}

		// Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
		// and service calls.
		if (packagesSupportingCustomTabs.isEmpty()) {
			sPackageNameToUse = null;
		} else if (packagesSupportingCustomTabs.size() == 1) {
			sPackageNameToUse = packagesSupportingCustomTabs.get(0);
		} else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
				&& !hasSpecializedHandlerIntents(context, activityIntent)
				&& packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
			sPackageNameToUse = defaultViewHandlerPackageName;
		} else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
			sPackageNameToUse = STABLE_PACKAGE;
		} else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
			sPackageNameToUse = BETA_PACKAGE;
		} else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
			sPackageNameToUse = DEV_PACKAGE;
		} else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
			sPackageNameToUse = LOCAL_PACKAGE;
		}
		return sPackageNameToUse;
	}

	private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
		try {
			PackageManager pm = context.getPackageManager();
			List<ResolveInfo> handlers = pm.queryIntentActivities(
					intent,
					PackageManager.GET_RESOLVED_FILTER);
			if (handlers == null || handlers.size() == 0) {
				return false;
			}
			for (ResolveInfo resolveInfo : handlers) {
				IntentFilter filter = resolveInfo.filter;
				if (filter == null) continue;
				if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue;
				if (resolveInfo.activityInfo == null) continue;
				return true;
			}
		} catch (RuntimeException e) {
			System.err.println("Runtime exception while getting specialized handlers");
		}
		return false;
	}
*/
}
