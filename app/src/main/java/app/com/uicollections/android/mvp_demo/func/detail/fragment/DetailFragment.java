package app.com.uicollections.android.mvp_demo.func.detail.fragment;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseFragment.BaseFragment;
import app.com.uicollections.android.mvp_demo.func.main.activity.MainActivity;
import butterknife.Bind;

public class DetailFragment extends BaseFragment {

    @Bind(R.id.fl_detail_content)
    FrameLayout mDetailContent;

    private Map<String, String> mParams;
    private WebView mWebView;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void baseInit() {
        setToolbar(false, true, "Details");

        mParams = new HashMap<>();
    }

    @Override
    protected void onBackClick() {
        ((MainActivity) mContext).onBack();
    }

    private void setWebSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mWebView.getSettings().setMixedContentMode(0);
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (Build.VERSION.SDK_INT >= 7) {
            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        } else {
            WebSettings settings = mWebView.getSettings();
            Class<?> clazz = settings.getClass();
            try {
                clazz.getMethod("setPluginsEnabled", boolean.class).invoke(
                        settings, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mWebView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
    }

    public void showWeb(String url) {
        if (mWebView == null) {
            mWebView = new WebView(mContext);
            setWebSetting();
            mDetailContent.addView(mWebView);
        }
        mWebView.loadUrl(url);
    }

    @Override
    protected View getLoadingTargetView() {
        return mDetailContent;
    }

    public void clear() {
        if (mWebView != null) {
            mDetailContent.removeView(mWebView);
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public void hasNoMoreDate() {

    }

    @Override
    public void loadMoreFinish(List dates) {

    }

    @Override
    public void showRefreshFinish(List score) {

    }

    @Override
    public void showToastError() {

    }
}
