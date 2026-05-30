package com.syu.smarttimetable.ui.school;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.syu.smarttimetable.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SchoolHomeFragment extends Fragment {

    private static final String SCHOOL_URL = "https://www.syu.ac.kr";
    private static final String SCHEDULE_QUERY = "학사일정";

    private WebView webView;
    private ProgressBar progressBar;
    private View errorContainer;
    private TextView errorText;
    private Button retryButton;
    private TextInputEditText searchEditText;
    private MaterialButton searchButton;
    private MaterialButton homeButton;
    private MaterialButton scheduleButton;
    private MaterialButton refreshButton;
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
        searchEditText = view.findViewById(R.id.et_school_search);
        searchButton = view.findViewById(R.id.btn_school_search);
        homeButton = view.findViewById(R.id.btn_school_home);
        scheduleButton = view.findViewById(R.id.btn_school_schedule);
        refreshButton = view.findViewById(R.id.btn_school_refresh);

        setupWebView();
        setupActions();
        loadSchoolHome();
    }

    private void setupActions() {
        retryButton.setOnClickListener(v -> reloadCurrentPage());
        homeButton.setOnClickListener(v -> loadSchoolHome());
        scheduleButton.setOnClickListener(v -> loadUrl(buildSearchUrl(SCHEDULE_QUERY)));
        refreshButton.setOnClickListener(v -> reloadCurrentPage());
        searchButton.setOnClickListener(v -> searchSchoolSite());

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean isSearchAction = actionId == EditorInfo.IME_ACTION_SEARCH;
            boolean isEnterKey = event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_UP;
            if (isSearchAction || isEnterKey) {
                searchSchoolSite();
                return true;
            }
            return false;
        });
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

    private void searchSchoolSite() {
        String query = searchEditText.getText() == null
                ? ""
                : searchEditText.getText().toString().trim();
        hideKeyboard();

        if (TextUtils.isEmpty(query)) {
            searchEditText.setError(getString(R.string.school_home_search_empty));
            return;
        }

        searchEditText.setError(null);
        loadUrl(buildSearchUrl(query));
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

    private String buildSearchUrl(String query) {
        try {
            return SCHOOL_URL + "/?s=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return SCHOOL_URL + "/?s=" + query;
        }
    }

    private void hideKeyboard() {
        if (getContext() == null || searchEditText == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        }
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
        searchEditText = null;
        searchButton = null;
        homeButton = null;
        scheduleButton = null;
        refreshButton = null;
        super.onDestroyView();
    }
}
