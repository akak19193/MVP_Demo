package app.com.uicollections.android.mvp_demo.func.detail.fragment;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseFragment.BaseFragment;
import app.com.uicollections.android.mvp_demo.common.base.basePresenter.BasePresenter;
import app.com.uicollections.android.mvp_demo.func.entitiy.MovieEntity;
import app.com.uicollections.android.mvp_demo.func.entitiy.ReviewEntity;
import app.com.uicollections.android.mvp_demo.func.main.presenter.ReviewPresenter;

public class ReviewFragment extends BaseFragment<ReviewPresenter> {
    private MovieEntity.MovieBean movie;

    @Override
    protected void baseInit() {
    }

    @Override
    protected void onBackClick() {
        getActivity().onBackPressed();
    }

    public void setMovie(MovieEntity.MovieBean mmovie) {
        if (mPresenter == null) {
            getChildPresenter();
        }
        if (movie == null) {
            this.movie = mmovie;
            mPresenter.requestReviewData("8ae97b01a4d0315b2283ff2f62f50dea", movie.getId() + "", BasePresenter.RequestMode.FRIST);
        } else {
            this.movie = mmovie;
            mPresenter.requestReviewData("8ae97b01a4d0315b2283ff2f62f50dea", movie.getId() + "", BasePresenter.RequestMode.REFRESH);
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_review;
    }

    @Override
    protected Map<String, String> getRequestParams() {
        return null;
    }

    public void initContent() {
//        CollapsingToolbarLayout mCollapsingToolbarLayout =
//                (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar);
//        mCollapsingToolbarLayout.setTitleEnabled(false);
        setToolbar(false, true, movie.getTitle());
    }

    private void setCollapsingToolbarLayoutTitle(String title) {
        CollapsingToolbarLayout mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(title);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    public void showContent(List<ReviewEntity.ReviewBean> items) {
        changeTitle(movie.getTitle());
        setCollapsingToolbarLayoutTitle(movie.getTitle());
        String imgPath = "http://image.tmdb.org/t/p/w500//" + movie.getPosterPath();
        ImageView img = (ImageView) mRootView.findViewById(R.id.backdrop);
        Glide.with(getActivity()).load(imgPath).centerCrop().into(img);

        LinearLayout mLinear = (LinearLayout) mRootView.findViewById(R.id.review_container);

        // add movie detail card
        TextView release_date = (TextView) mRootView.findViewById(R.id.movie_release_date);
        release_date.setText("Release Date: " + movie.getReleaseDate());
        TextView popularity = (TextView) mRootView.findViewById(R.id.movie_popularity);
        popularity.setText("Popularity: " + movie.getPopularity());
        TextView vote_count = (TextView) mRootView.findViewById(R.id.movie_vote_count);
        vote_count.setText("Vote Count: " + movie.getVoteCount());
        TextView vote_average = (TextView) mRootView.findViewById(R.id.movie_vote_average);
        vote_average.setText("Vote Average: " + movie.getVoteAverage());
        TextView overview = (TextView) mRootView.findViewById(R.id.movie_overview);
        overview.setText("Overview: " + movie.getOverview());

        LayoutInflater mInflater = (LayoutInflater) mLinear.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // add reviews card
        for (int i = 0; i < items.size(); i++) {
            View review = mInflater.inflate(R.layout.item_review, mLinear, false);
            TextView review_content = (TextView) review.findViewById(R.id.review_content);
            review_content.setText(items.get(i).getContent());
            TextView review_author = (TextView) review.findViewById(R.id.review_author);
            review_author.setText("Review by: " + items.get(i).getAuthor());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(48, 20, 48, 20);
            mLinear.addView(review, layoutParams);
        }
    }

    public void refreshContent(List<ReviewEntity.ReviewBean> items) {
        LinearLayout linearLayout = (LinearLayout) mRootView.findViewById(R.id.review_container);
        linearLayout.removeAllViews();
        showContent(items);
        scrollToTop();
    }

    private void scrollToTop() {
        NestedScrollView nestedScrollView = (NestedScrollView) mRootView.findViewById(R.id.nested_scroll);
        nestedScrollView.scrollTo(0, 0);
    }

    @Override
    protected ReviewPresenter getChildPresenter() {
        return new ReviewPresenter(this);
    }

    @Override
    public void showToastError() {

    }

    @Override
    public void loadMoreFinish(List dates) {

    }

    @Override
    public void hasNoMoreDate() {

    }

    @Override
    public void showRefreshFinish(List score) {

    }


}
