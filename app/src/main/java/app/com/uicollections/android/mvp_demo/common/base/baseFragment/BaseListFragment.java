package app.com.uicollections.android.mvp_demo.common.base.baseFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseAdapter.RecycleViewAdapter;
import app.com.uicollections.android.mvp_demo.common.base.baseHolder.BaseViewHolder;
import app.com.uicollections.android.mvp_demo.common.base.basePresenter.BasePresenter;
import app.com.uicollections.android.mvp_demo.common.refresh.XRecyclerView;
import app.com.uicollections.android.mvp_demo.common.util.RandomNumberUtil;
import app.com.uicollections.android.mvp_demo.func.main.view.MainView;


public abstract class BaseListFragment<T extends BasePresenter, K> extends
        BaseFragment<T> implements
        MainView<K>, XRecyclerView.LoadingListener {

    XRecyclerView mRecyclerView;

    protected RecycleViewAdapter<K> mAdapter;

    protected Map<String, String> params;

    protected int PAGE = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_main_common;
    }

    @Override
    protected void baseInit() {
        params = new HashMap<>();
        showLoading();
        mPresenter.requestDate(getRequestParams(), BasePresenter.RequestMode.FRIST);
    }


    @Override
    protected void baseInitView() {
        mRecyclerView = (XRecyclerView) mRootView.findViewById(R.id.recycleView);
    }

    @Override
    protected abstract Map<String, String> getRequestParams();

    @Override
    protected View getLoadingTargetView() {
        return mRecyclerView;
    }


    @Override
    public void showFinishDates(List<K> dates) {
        refreshView();
        if (mAdapter == null) {
            mAdapter = new RecycleViewAdapter<K>(getItemLayout(), dates) {
                @Override
                protected void convert(BaseViewHolder helper, K item) {
                    fitDates(helper, item);
                }
            };

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setRefreshProgressStyle(new RandomNumberUtil().getRandomNumber(0, 27));
            mRecyclerView.setLoadingMoreProgressStyle(new RandomNumberUtil().getRandomNumber(0, 27));
            mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
            mRecyclerView.setLoadingListener(this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnRecyclerViewItemChildClickListener(
                (RecycleViewAdapter adapter, View v, int position) -> onItemClick(v, position));
    }

    protected void onItemClick(View v, int position) {

    }

    @Override
    public void hasNoMoreDate() {
        mRecyclerView.setNoMore(true);
        PAGE = PAGE - 1;
    }

    protected abstract void fitDates(BaseViewHolder helper, K item);

    protected abstract int getItemLayout();

    @Override
    public void loadMoreFinish(List dates) {
        mRecyclerView.loadMoreComplete();
        mAdapter.addDataAndNotify(dates);
    }

    @Override
    public void showRefreshFinish(List score) {
        mRecyclerView.refreshComplete();
        mAdapter.setNewData(score);
    }

    @Override
    public void showToastError() {
        mRecyclerView.reset();
        PAGE = PAGE - 1;
        Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        PAGE = 1;
        mPresenter.requestDate(getRequestParams(), BasePresenter.RequestMode.REFRESH);
    }

    @Override
    public void onLoadMore() {
        PAGE = PAGE + 1;
        mPresenter.requestDate(getRequestParams(), BasePresenter.RequestMode.LOAD_MORE);
    }
}
