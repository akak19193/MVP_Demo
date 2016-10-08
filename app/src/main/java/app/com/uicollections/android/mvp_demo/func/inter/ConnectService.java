package app.com.uicollections.android.mvp_demo.func.inter;

import java.util.Map;

import app.com.uicollections.android.mvp_demo.func.entitiy.MovieEntity;
import app.com.uicollections.android.mvp_demo.func.entitiy.NewsEntity;
import app.com.uicollections.android.mvp_demo.func.entitiy.ReviewEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ConnectService {

    @GET("https://api.themoviedb.org/3/discover/movie")
    Observable<MovieEntity> getMovieList(@QueryMap Map<String,String> params);

    @GET("{movie_id}/reviews")
    Observable<ReviewEntity> getReviewList(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    @GET("https://newsapi.org/v1/articles")
    Observable<NewsEntity> getNewsList(@QueryMap Map<String,String> params);
}
