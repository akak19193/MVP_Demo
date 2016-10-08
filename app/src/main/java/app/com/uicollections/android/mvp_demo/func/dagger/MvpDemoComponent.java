package app.com.uicollections.android.mvp_demo.func.dagger;

import javax.inject.Singleton;

import app.com.uicollections.android.mvp_demo.common.base.basePresenter.BasePresenter;
import dagger.Component;


@Singleton
@Component(modules = MvpDemoModules.class)
public interface MvpDemoComponent {

    void inject(BasePresenter presenter);

    final class MvpDemoInitialize {

        public static MvpDemoComponent init() {
            return DaggerMvpDemoComponent.builder().build();
        }
    }
}
