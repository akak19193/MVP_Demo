package app.com.uicollections.android.mvp_demo.func.main.fragment;

import android.view.View;
import android.widget.Toast;

import java.util.Map;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseFragment.BaseListFragment;
import app.com.uicollections.android.mvp_demo.common.base.baseHolder.BaseViewHolder;
import app.com.uicollections.android.mvp_demo.func.entitiy.MovieEntity;
import app.com.uicollections.android.mvp_demo.func.main.activity.MainActivity;
import app.com.uicollections.android.mvp_demo.func.main.presenter.MoviesPresenter;


public class MoviesFragment extends
        BaseListFragment<MoviesPresenter, MovieEntity.MovieBean> {


    @Override
    protected Map<String, String> getRequestParams() {
        params.put("api_key", "8ae97b01a4d0315b2283ff2f62f50dea");
        params.put("page", PAGE + "");
        return params;
    }

    @Override
    protected void fitDates(BaseViewHolder helper, MovieEntity.MovieBean item) {
        helper.setText(R.id.tv_item_movie_title, item.getTitle()).
                setText(R.id.tv_item_movie_overview, item.getOverview())
                .setImageUrl(R.id.iv_item_movie_poster, mContext, "http://image.tmdb.org/t/p/w185//" + item.getPosterPath());
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_movies;
    }

    @Override
    protected MoviesPresenter getChildPresenter() {
        return new MoviesPresenter(this);
    }

    @Override
    protected void onItemClick(View v, int position) {
        MovieEntity.MovieBean item = mAdapter.getItem(position);

        MainActivity activity = (MainActivity) mContext;
        if (null != activity) {
            activity.toReview(item);
        }
    }
}