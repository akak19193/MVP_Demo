package app.com.uicollections.android.mvp_demo.func.main.fragment;

import android.support.v4.view.ViewPager;

import java.util.List;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseAdapter.BaseFragmentPagerAdapter;
import app.com.uicollections.android.mvp_demo.common.base.baseFragment.BaseFragment;
import app.com.uicollections.android.mvp_demo.common.widget.PagerSlidingTabStrip;
import app.com.uicollections.android.mvp_demo.func.main.activity.MainActivity;
import butterknife.Bind;

public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.indicator)
    PagerSlidingTabStrip mIndicator;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void baseInit() {
        setToolbar(true, false, "Movies");
        mViewPager.setAdapter(new BaseFragmentPagerAdapter.Holder(getChildFragmentManager()).add(
                new MoviesFragment()).add(new NewsFragment()).build(
                new String[]{"Movies", "News"}));


        mViewPager.addOnPageChangeListener(this);

        mIndicator.setShouldExpand(true);
        mIndicator.setTextSize(32);
        mIndicator.setIndicatorColor(getResources().getColor(R.color.indicator_color));
        mIndicator.setIndicatorHeight(2);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    protected void onMenuClick() {
        ((MainActivity) mContext).drawerAction(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            changeTitle("Movies");
        } else {
            changeTitle("News");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
