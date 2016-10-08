package app.com.uicollections.android.mvp_demo.common.anim;

import android.animation.Animator;
import android.view.View;

public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
