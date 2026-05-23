package com.syu.smarttimetable.ui.school;

import android.os.Bundle;
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
    private TextView errorText;
    private Button retryButton;

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
        errorText = view.findViewById(R.id.tv_school_error);
        retryButton = view.findViewById(R.id.btn_school_retry);

        setupWebView();
        retryButton.setOnClickListener(v -> loadSchoolHome());
        loadSchoolHome();
    }

    private void setupWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showWebView();
            }

            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    showError();
                }
            }
        });
    }

    private void loadSchoolHome() {
        showLoading();
        webView.loadUrl(SCHOOL_URL);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private void showWebView() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
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
        errorText = null;
        retryButton = null;
        super.onDestroyView();
    }
}
