package app.com.uicollections.android.mvp_demo.func.main.presenter;

import android.util.Log;

import java.util.List;
import java.util.Map;

import app.com.uicollections.android.mvp_demo.common.base.basePresenter.BasePresenter;
import app.com.uicollections.android.mvp_demo.func.detail.fragment.ReviewFragment;
import app.com.uicollections.android.mvp_demo.func.entitiy.ReviewEntity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReviewPresenter extends BasePresenter {
    private ReviewFragment reviewFragment;

    public ReviewPresenter(ReviewFragment reviewFragment) {
        this.reviewFragment = reviewFragment;
    }

    @Override
    protected void onAllSuccess(Object o) {
        Log.d("response", o.toString());
        ReviewEntity entity = (ReviewEntity) o;
        List<ReviewEntity.ReviewBean> data = entity.getResults();
        if (mode == RequestMode.FRIST) {
            reviewFragment.showContent(data);
            reviewFragment.initContent();
        } else {
            reviewFragment.refreshContent(data);
        }
    }

    public void requestReviewData(String api_key, String movie_id, RequestMode mode) {
        if (null == getReviewObservable(api_key, movie_id)) {
            throw new IllegalArgumentException("no Observable");
        }

        this.mode = mode;
        mSubscription = getReviewObservable(api_key, movie_id).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                onFinish();
            }

            @Override
            public void onError(Throwable e) {
                onFail(e.toString());
            }

            @Override
            public void onNext(Object o) {
                if (null != o) {
                    onAllSuccess(o);
                } else {
                    onFail("");
                }
            }
        });
    }

    @Override
    protected void onFail(String e) {
        Log.d("failed", e + "!!!");
    }


    protected Observable getReviewObservable(String api_key, String movie_id) {
        return getService().getReviewList(movie_id, api_key);
    }

    @Override
    protected Observable getObservable(Map<String, String> params) {
        return null;
    }
}
