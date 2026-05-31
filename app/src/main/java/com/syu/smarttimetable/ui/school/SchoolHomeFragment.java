package com.syu.smarttimetable.ui.school;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.syu.smarttimetable.R;

public class SchoolHomeFragment extends Fragment {

    private static final String SCHOOL_URL = "https://www.syu.ac.kr";

    private WebView webView;
    private ProgressBar progressBar;
    private View errorContainer;
    private TextView errorText;
    private Button retryButton;
    private boolean mainFrameError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.webview_school);
        progressBar = view.findViewById(R.id.progress_school);
        errorContainer = view.findViewById(R.id.layout_school_error);
        errorText = view.findViewById(R.id.tv_school_error);
        retryButton = view.findViewById(R.id.btn_school_retry);

        setupWebView();
        setupActions();
        loadSchoolHome();
    }

    private void setupActions() {
        retryButton.setOnClickListener(v -> reloadCurrentPage());
    }

    private void setupWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mainFrameError = false;
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                stabilizeSchoolPageUi(view);
                if (!mainFrameError) {
                    showWebView();
                }
            }

            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    mainFrameError = true;
                    showError();
                }
            }
        });
    }

    private void loadSchoolHome() {
        loadUrl(SCHOOL_URL);
    }

    private void reloadCurrentPage() {
        if (webView == null) {
            return;
        }

        String currentUrl = webView.getUrl();
        if (TextUtils.isEmpty(currentUrl)) {
            loadSchoolHome();
        } else {
            loadUrl(currentUrl);
        }
    }

    private void loadUrl(String url) {
        showLoading();
        webView.loadUrl(url);
    }

    private void stabilizeSchoolPageUi(WebView view) {
        String javascript = "(function(){"
                + "try{"
                + "var style=document.getElementById('smartTimetableWebViewFix');"
                + "if(!style){style=document.createElement('style');style.id='smartTimetableWebViewFix';document.head.appendChild(style);}"
                + "style.innerHTML='"
                + ".quick-menu,.quick_menu,.quickMenu,#quickMenu,.floating-menu,.floating_menu,.subot,.chatbot,.chat-bot{display:none!important;}"
                + ".search-layer,.search_layer,.search-modal,.search_modal{max-height:55vh!important;overflow:auto!important;}"
                + "body{overflow-x:hidden!important;}"
                + "';"
                + "}catch(e){}"
                + "})();";
        view.evaluateJavascript(javascript, null);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private void showWebView() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.stopLoading();
            webView.setWebViewClient(null);
        }
        webView = null;
        progressBar = null;
        errorContainer = null;
        errorText = null;
        retryButton = null;
        super.onDestroyView();
    }
}
