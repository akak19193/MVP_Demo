package app.com.uicollections.android.mvp_demo.func.main.view;

import java.util.List;

import app.com.uicollections.android.mvp_demo.common.base.baseView.BaseView;

public interface MainView<T> extends BaseView {

    void showFinishDates(List<T> dates);
}
