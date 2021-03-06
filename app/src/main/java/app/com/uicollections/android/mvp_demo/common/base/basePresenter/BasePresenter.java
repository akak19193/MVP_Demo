package app.com.uicollections.android.mvp_demo.common.base.basePresenter;

import java.util.Map;

import javax.inject.Inject;

import app.com.uicollections.android.mvp_demo.MvpDemoApplication;
import app.com.uicollections.android.mvp_demo.func.inter.ConnectService;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public abstract class BasePresenter {

    @Inject
    ConnectService mService;

    public Subscription mSubscription;

    protected RequestMode mode = RequestMode.FRIST;

    public BasePresenter() {
        MvpDemoApplication.getComponent().inject(this);
    }

    public enum RequestMode {
        FRIST, LOAD_MORE, REFRESH
    }

    @SuppressWarnings("unchecked")
    public void requestDate(Map<String, String> params, RequestMode mode) {
        if (null == getObservable(params)) {
            throw new IllegalArgumentException("no Observable");
        }

        this.mode = mode;
        mSubscription = getObservable(params).subscribeOn(Schedulers.io()).observeOn(
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

    protected void onAllSuccess(Object o) {
    }

    protected ConnectService getService() {
        return mService;
    }

    protected void onFinish() {

    }

    protected abstract void onFail(String info);


    protected abstract Observable getObservable(Map<String, String> params);
}
