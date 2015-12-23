package com.passerbywhu.materialstudy.behavior.footer;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hzwuwenchao on 2015/12/23.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {
    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.e("hehe", "translationY = " + dependency.getTranslationY());
        float top = dependency.getY();
        child.setY(parent.getBottom() - child.getHeight() - top);
        return true;
    }
}
