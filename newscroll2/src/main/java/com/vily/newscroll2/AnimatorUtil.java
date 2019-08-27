package com.vily.newscroll2;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class AnimatorUtil {

    private static AccelerateDecelerateInterpolator LINEAR_INTERRPLATOR =new AccelerateDecelerateInterpolator();
    public static void showFab(View view,MyBehavior.AnimateListener ... listener){
        if (listener.length!=0){
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .translationYBy(500)
                    .setDuration(600)
                    .setInterpolator(LINEAR_INTERRPLATOR)
                    .setListener(listener[0])
                    .start();
        }else {
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .translationYBy(500)
                    .setDuration(600)
                    .setInterpolator(LINEAR_INTERRPLATOR)
                    .start();
        }

    }
    public static void hideFab(View view, MyBehavior.AnimateListener listener){
        view.animate()
                .scaleX(0.5f)
                .scaleY(0.5f)
                .alpha(1f)
                .translationYBy(-500)
                .setDuration(600)
                .setInterpolator(LINEAR_INTERRPLATOR)
                .setListener(listener)
                .start();
    }

}
