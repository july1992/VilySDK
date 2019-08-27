package com.vily.newscroll2;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class MyBehavior extends FloatingActionButton.Behavior{

    private boolean isAnimateIng = false;   // 是否正在动画
    private boolean isShow = true;  // 是否已经显示

    public MyBehavior(Context context, AttributeSet attrs) {
        super();
    }
    //只有当返回值为true才会执行下面的方法,例如onNestedScroll
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
                ||axes== ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //界面向下滑动,fab动画结束,且正在显示
        //隐藏Fab
        if ((dyConsumed>0||dyUnconsumed>0)&&!isAnimateIng&&isShow){
            AnimatorUtil.hideFab(child,new AnimateListener());
        }
        //界面向上滑动,fab动画结束,且隐藏
        //显示Fab
        else if ((dyConsumed<0||dyUnconsumed<0)&&!isAnimateIng&&!isShow){
            AnimatorUtil.showFab(child,new AnimateListener());

        }

    }
    public class AnimateListener implements  Animator.AnimatorListener {


        @Override
        public void onAnimationStart(Animator animation) {
            isAnimateIng=true;
            isShow=!isShow;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimateIng=false;

        }

        @Override
        public void onAnimationCancel(Animator animation) {


        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }


}
