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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import org.openecard.android.activation.ActivationImplementationInterface;
import org.openecard.demo.R;


/**
 * @author Mike Prechtl
 */
public class IdsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ids);

		String url = "https://argon.cloud.nds.rub.de:8080/";
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		CustomTabsIntent customTabsIntent = builder.build();
		customTabsIntent.launchUrl(this, Uri.parse(url));
		/*
		final WebView webView = findViewById(R.id.webView);
		final Context context = getApplicationContext();
		// set up web view settings
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setSupportMultipleWindows(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		setContentView(webView);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				WebView newWebView = new WebView(IdsActivity.this);

				WebSettings settings = newWebView.getSettings();
				settings.setJavaScriptEnabled(true);

				view.addView(newWebView);

				newWebView.setWebViewClient(new WebViewClient() {
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {

						Intent browserIntent = new Intent(Intent.ACTION_VIEW);
						browserIntent.setData(Uri.parse(url));
						startActivity(browserIntent);

						//view.loadUrl(url);
						return true;
					}

					@Override
					public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

						String url = request.getUrl().toString();

						Intent browserIntent = new Intent(Intent.ACTION_VIEW);
						browserIntent.setData(Uri.parse(url));
						startActivity(browserIntent);
						return true;

						//view.loadUrl(url);

					}
				});


				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(newWebView);
				resultMsg.sendToTarget();

				return true;
			}
		});

		if (getIntent().getData() != null) {
			webView.loadUrl(getIntent().getData().toString());
		} else {
			webView.loadUrl("https://argon.cloud.nds.rub.de:8080/");
		}
		*/
	}
}
