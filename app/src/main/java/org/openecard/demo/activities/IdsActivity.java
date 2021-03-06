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
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import syncenc.demo.R;


/**
 * @author René Lottes
 * @author Sebastian Schuberth
 */
public class IdsActivity extends Activity {

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

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				System.out.println(" \t ---> Open new Window");
				webView.setVisibility(View.GONE);
				final WebView newView = (WebView) findViewById(R.id.webView2);
				newView.setVisibility(View.VISIBLE);
				newView.getSettings().setJavaScriptEnabled(true);
				newView.getSettings().setDomStorageEnabled(true);
				newView.setWebViewClient(new WebViewClient() {
					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						System.out.println("\t --> TAP: " + url);

						if (url.endsWith("/KMS/ACS")) {
							System.out.println("\t --> Close webview ");
							webView.setVisibility(View.VISIBLE);
							newView.setVisibility(View.GONE);
						}
					}
				});

				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(newView);
				resultMsg.sendToTarget();
				return true;
			}
		});

		if(getIntent().getData() != null) {
			webView.loadUrl(getIntent().getData().toString());
		} else {
			webView.loadUrl("https://argon.cloud.nds.rub.de:8080/");
		}
	}
}
