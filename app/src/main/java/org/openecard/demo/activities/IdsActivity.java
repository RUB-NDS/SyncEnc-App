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

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
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

		final WebView webView = findViewById(R.id.webView);

		// set up web view settings
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setSupportMultipleWindows(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		setContentView(webView);
/*
		webView.setWebChromeClient(new WebChromeClient() {
			WebView newView = new WebView(getApplicationContext());
			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				final WebView newView = (WebView) findViewById(R.id.webView);
				newView.setWebViewClient(new WebActivityClient(context) {
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						Intent intent = new Intent(context, PopupActivity.class);
						intent.putExtra("URL", url);
						startActivity(intent);

						newView.destroy();
					}
				});

				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(newView);
				resultMsg.sendToTarget();
				return true;
			}
		});*/


		if (getIntent().getData() != null) {
			webView.loadUrl(getIntent().getData().toString());
		} else {
			webView.loadUrl("https://argon.cloud.nds.rub.de:8080/");
		}
	}
}
