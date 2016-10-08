package app.com.uicollections.android.mvp_demo.func.main.presenter;

import java.util.List;
import java.util.Map;

import app.com.uicollections.android.mvp_demo.common.base.basePresenter.BasePresenter;
import app.com.uicollections.android.mvp_demo.func.entitiy.NewsEntity;
import app.com.uicollections.android.mvp_demo.func.main.view.MainView;
import rx.Observable;

public class NewsPresenter extends BasePresenter {

    private MainView mMainView;

    public NewsPresenter(MainView mainView) {
        mMainView = mainView;
    }

    @Override
    protected void onAllSuccess(Object o) {
        NewsEntity entity = (NewsEntity) o;
        if (null != entity) {
            if (null != entity.getArticles()) {
                List<NewsEntity.NewsBean> data = entity.getArticles();
                if (null != data && data.size() > 0) {
                    if (mode == RequestMode.FRIST) {
                        mMainView.showFinishDates(data);
                    } else if (mode == RequestMode.LOAD_MORE) {
                        mMainView.loadMoreFinish(data);
                    } else if (mode == RequestMode.REFRESH) {
                        mMainView.showRefreshFinish(data);
                    }
                } else {
                    if (mode == RequestMode.LOAD_MORE) {
                        mMainView.hasNoMoreDate();
                    } else {
                        mMainView.showEmptyView(null);
                    }
                }
            } else {
                mMainView.showEmptyView(null);
            }
        } else {
            mMainView.showEmptyView(null);
        }
    }

    @Override
    protected void onFail(String e) {
        if (mode == RequestMode.FRIST) {
            mMainView.showNetError();
        } else {
            mMainView.showToastError();
        }
    }

    @Override
    protected Observable getObservable(Map<String, String> params) {
        return getService().getNewsList(params);
    }
}