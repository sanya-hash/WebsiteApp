package de.khris.sakrischguat;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {

    private boolean urlIsPDF(String url) {
        String[] parts = url.split("\\.");
        return parts[parts.length - 1].equalsIgnoreCase("pdf");
    }

    private void openPDF(WebView view, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "application/pdf");
        try{
            view.getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //user does not have a pdf viewer installed
            view.loadUrl(view.getResources().getString(R.string.no_pdf_url) + url);
        }
    }

    @TargetApi(21)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if ( urlIsPDF(url)){
            openPDF(view, url);
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if ( urlIsPDF(url)){
            openPDF(view, url);
        } else {
            view.loadUrl(url);
        }
        return true;
    }
}
