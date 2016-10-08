package app.com.uicollections.android.mvp_demo.func.dagger;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import app.com.uicollections.android.mvp_demo.func.inter.ConnectService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MvpDemoModules {

    @Singleton
    @Provides
    protected ConnectService getService(RxJavaCallAdapterFactory callAdapterFactory, GsonConverterFactory gsonConverterFactory, OkHttpClient client) {
        return new Retrofit.Builder().addCallAdapterFactory(callAdapterFactory).baseUrl("https://api.themoviedb.org/3/movie/").addConverterFactory(
                gsonConverterFactory).client(client).build().create(ConnectService.class);
    }

    @Singleton
    @Provides
    protected RxJavaCallAdapterFactory getCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    protected GsonConverterFactory getGsonConvertFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    protected OkHttpClient getClient() {
        return new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS).readTimeout(
                5000, TimeUnit.MILLISECONDS).addInterceptor(new HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY)).build();
    }
}