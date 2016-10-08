package app.com.uicollections.android.mvp_demo.func.main.fragment;

import android.view.View;

import java.util.Map;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseFragment.BaseListFragment;
import app.com.uicollections.android.mvp_demo.common.base.baseHolder.BaseViewHolder;
import app.com.uicollections.android.mvp_demo.func.entitiy.NewsEntity;
import app.com.uicollections.android.mvp_demo.func.main.activity.MainActivity;
import app.com.uicollections.android.mvp_demo.func.main.presenter.NewsPresenter;

import static app.com.uicollections.android.mvp_demo.common.util.DateUtil.fromApiTime;


public class NewsFragment extends
        BaseListFragment<NewsPresenter, NewsEntity.NewsBean> {


    @Override
    protected Map<String, String> getRequestParams() {
        params.put("apiKey", "d4544b336783498e8a57a61480c75b51");
        params.put("source", "the-next-web");
        params.put("sortBy", "latest");
        return params;
    }

    @Override
    protected void fitDates(BaseViewHolder helper, NewsEntity.NewsBean item) {
        String dt = fromApiTime(item.getPublishedAt(), "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        helper.setText(R.id.tv_item_news_author, item.getAuthor()).
                setText(R.id.tv_item_news_content, item.getDescription())
                .setText(R.id.tv_item_news_time, dt)
                .setText(R.id.tv_item_news_title, item.getTitle());

    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_news;
    }

    @Override
    protected NewsPresenter getChildPresenter() {
        return new NewsPresenter(this);
    }

    @Override
    protected void onItemClick(View v, int position) {
        NewsEntity.NewsBean item = mAdapter.getItem(position);

        MainActivity activity = (MainActivity) mContext;
        if (null != activity) {
            activity.toDetail(item.getUrl());
        }
    }
}