package com.vily.newscrollactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.animation.AnimationUtils;
import android.support.design.animation.AnimatorSetCompat;
import android.support.design.animation.ImageMatrixProperty;
import android.support.design.animation.MatrixEvaluator;
import android.support.design.animation.MotionSpec;
import android.support.design.ripple.RippleUtils;
import android.support.design.widget.CircularBorderDrawable;

import android.support.design.widget.ShadowDrawableWrapper;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.StateListAnimator;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class MyFloating   {
    static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR;
    static final long ELEVATION_ANIM_DURATION = 100L;
    static final long ELEVATION_ANIM_DELAY = 100L;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_SHOWING = 2;
    private static final float HIDE_OPACITY = 0.0F;
    private static final float HIDE_SCALE = 0.0F;
    private static final float HIDE_ICON_SCALE = 0.0F;
    private static final float SHOW_OPACITY = 1.0F;
    private static final float SHOW_SCALE = 1.0F;
    private static final float SHOW_ICON_SCALE = 1.0F;
    int animState = 0;
    @Nullable
    Animator currentAnimator;
    @Nullable
    MotionSpec showMotionSpec;
    @Nullable
    MotionSpec hideMotionSpec;
    @Nullable
    private MotionSpec defaultShowMotionSpec;
    @Nullable
    private MotionSpec defaultHideMotionSpec;
    private final StateListAnimator stateListAnimator;
    ShadowDrawableWrapper shadowDrawable;
    private float rotation;
    Drawable shapeDrawable;
    Drawable rippleDrawable;
    CircularBorderDrawable borderDrawable;
    Drawable contentBackground;
    float elevation;
    float hoveredFocusedTranslationZ;
    float pressedTranslationZ;
    int maxImageSize;
    float imageMatrixScale = 1.0F;
    private ArrayList<Animator.AnimatorListener> showListeners;
    private ArrayList<Animator.AnimatorListener> hideListeners;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] HOVERED_ENABLED_STATE_SET;
    static final int[] ENABLED_STATE_SET;
    static final int[] EMPTY_STATE_SET;
    final VisibilityAwareImageButton view;
    final ShadowViewDelegate shadowViewDelegate;
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();
    private final Matrix tmpMatrix = new Matrix();
    private ViewTreeObserver.OnPreDrawListener preDrawListener;

    @SuppressLint("RestrictedApi")
    MyFloating(VisibilityAwareImageButton view, ShadowViewDelegate shadowViewDelegate) {
        this.view = view;
        this.shadowViewDelegate = shadowViewDelegate;
        this.stateListAnimator = new StateListAnimator();
        this.stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.createElevationAnimator(new MyFloating.ElevateToPressedTranslationZAnimation()));
        this.stateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, this.createElevationAnimator(new MyFloating.ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, this.createElevationAnimator(new MyFloating.ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(HOVERED_ENABLED_STATE_SET, this.createElevationAnimator(new MyFloating.ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(ENABLED_STATE_SET, this.createElevationAnimator(new MyFloating.ResetElevationAnimation()));
        this.stateListAnimator.addState(EMPTY_STATE_SET, this.createElevationAnimator(new MyFloating.DisabledElevationAnimation()));
        this.rotation = this.view.getRotation();
    }

    @SuppressLint("RestrictedApi")
    void setBackgroundDrawable(ColorStateList backgroundTint, PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        this.shapeDrawable = DrawableCompat.wrap(this.createShapeDrawable());
        DrawableCompat.setTintList(this.shapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(this.shapeDrawable, backgroundTintMode);
        }

        GradientDrawable touchFeedbackShape = this.createShapeDrawable();
        this.rippleDrawable = DrawableCompat.wrap(touchFeedbackShape);
        DrawableCompat.setTintList(this.rippleDrawable, RippleUtils.convertToRippleDrawableColor(rippleColor));
        Drawable[] layers;
        if (borderWidth > 0) {
            this.borderDrawable = this.createBorderDrawable(borderWidth, backgroundTint);
            layers = new Drawable[]{this.borderDrawable, this.shapeDrawable, this.rippleDrawable};
        } else {
            this.borderDrawable = null;
            layers = new Drawable[]{this.shapeDrawable, this.rippleDrawable};
        }

        this.contentBackground = new LayerDrawable(layers);
        this.shadowDrawable = new ShadowDrawableWrapper(this.view.getContext(), this.contentBackground, this.shadowViewDelegate.getRadius(), this.elevation, this.elevation + this.pressedTranslationZ);
        this.shadowDrawable.setAddPaddingForCorners(false);
        this.shadowViewDelegate.setBackgroundDrawable(this.shadowDrawable);
    }

    @SuppressLint("RestrictedApi")
    void setBackgroundTintList(ColorStateList tint) {
        if (this.shapeDrawable != null) {
            DrawableCompat.setTintList(this.shapeDrawable, tint);
        }

        if (this.borderDrawable != null) {
            this.borderDrawable.setBorderTint(tint);
        }

    }

    void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (this.shapeDrawable != null) {
            DrawableCompat.setTintMode(this.shapeDrawable, tintMode);
        }

    }

    @SuppressLint("RestrictedApi")
    void setRippleColor(ColorStateList rippleColor) {
        if (this.rippleDrawable != null) {
            DrawableCompat.setTintList(this.rippleDrawable, RippleUtils.convertToRippleDrawableColor(rippleColor));
        }

    }

    final void setElevation(float elevation) {
        if (this.elevation != elevation) {
            this.elevation = elevation;
            this.onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }

    }

    float getElevation() {
        return this.elevation;
    }

    float getHoveredFocusedTranslationZ() {
        return this.hoveredFocusedTranslationZ;
    }

    float getPressedTranslationZ() {
        return this.pressedTranslationZ;
    }

    final void setHoveredFocusedTranslationZ(float translationZ) {
        if (this.hoveredFocusedTranslationZ != translationZ) {
            this.hoveredFocusedTranslationZ = translationZ;
            this.onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }

    }

    final void setPressedTranslationZ(float translationZ) {
        if (this.pressedTranslationZ != translationZ) {
            this.pressedTranslationZ = translationZ;
            this.onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }

    }

    final void setMaxImageSize(int maxImageSize) {
        if (this.maxImageSize != maxImageSize) {
            this.maxImageSize = maxImageSize;
            this.updateImageMatrixScale();
        }

    }

    final void updateImageMatrixScale() {
        this.setImageMatrixScale(this.imageMatrixScale);
    }

    final void setImageMatrixScale(float scale) {
        this.imageMatrixScale = scale;
        Matrix matrix = this.tmpMatrix;
        this.calculateImageMatrixFromScale(scale, matrix);
        this.view.setImageMatrix(matrix);
    }

    private void calculateImageMatrixFromScale(float scale, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.view.getDrawable();
        if (drawable != null && this.maxImageSize != 0) {
            RectF drawableBounds = this.tmpRectF1;
            RectF imageBounds = this.tmpRectF2;
            drawableBounds.set(0.0F, 0.0F, (float)drawable.getIntrinsicWidth(), (float)drawable.getIntrinsicHeight());
            imageBounds.set(0.0F, 0.0F, (float)this.maxImageSize, (float)this.maxImageSize);
            matrix.setRectToRect(drawableBounds, imageBounds, Matrix.ScaleToFit.CENTER);
            matrix.postScale(scale, scale, (float)this.maxImageSize / 2.0F, (float)this.maxImageSize / 2.0F);
        }

    }

    @Nullable
    final MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    final void setShowMotionSpec(@Nullable MotionSpec spec) {
        this.showMotionSpec = spec;
    }

    @Nullable
    final MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    final void setHideMotionSpec(@Nullable MotionSpec spec) {
        this.hideMotionSpec = spec;
    }

    void onElevationsChanged(float elevation, float hoveredFocusedTranslationZ, float pressedTranslationZ) {
        if (this.shadowDrawable != null) {
            this.shadowDrawable.setShadowSize(elevation, elevation + this.pressedTranslationZ);
            this.updatePadding();
        }

    }

    @SuppressLint("RestrictedApi")
    void onDrawableStateChanged(int[] state) {
        this.stateListAnimator.setState(state);
    }

    @SuppressLint("RestrictedApi")
    void jumpDrawableToCurrentState() {
        this.stateListAnimator.jumpToCurrentState();
    }

    void addOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList();
        }

        this.showListeners.add(listener);
    }

    void removeOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.showListeners != null) {
            this.showListeners.remove(listener);
        }
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.hideListeners == null) {
            this.hideListeners = new ArrayList();
        }

        this.hideListeners.add(listener);
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.hideListeners != null) {
            this.hideListeners.remove(listener);
        }
    }

    @SuppressLint("RestrictedApi")
    void hide(@Nullable final MyFloating.InternalVisibilityChangedListener listener, final boolean fromUser) {
        if (!this.isOrWillBeHidden()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }

            if (this.shouldAnimateVisibilityChange()) {
                AnimatorSet set = this.createAnimator(this.hideMotionSpec != null ? this.hideMotionSpec : this.getDefaultHideMotionSpec(), 1F, 0.5F, 0.5F);
                set.addListener(new AnimatorListenerAdapter() {
                    private boolean cancelled;

                    @SuppressLint("RestrictedApi")
                    public void onAnimationStart(Animator animation) {
//                        MyFloating.this.view.internalSetVisibility(0, fromUser);
                        MyFloating.this.animState = 1;
                        MyFloating.this.currentAnimator = animation;
                        this.cancelled = false;
                    }

                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animation) {
                        MyFloating.this.animState = 0;
                        MyFloating.this.currentAnimator = null;
                        if (!this.cancelled) {
//                            MyFloating.this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
                            if (listener != null) {
                                listener.onHidden();
                            }
                        }

                    }
                });
                if (this.hideListeners != null) {
                    Iterator var4 = this.hideListeners.iterator();

                    while(var4.hasNext()) {
                        Animator.AnimatorListener l = (Animator.AnimatorListener)var4.next();
                        set.addListener(l);
                    }
                }

                set.start();
            } else {
//                this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
                if (listener != null) {
                    listener.onHidden();
                }
            }

        }
    }

    @SuppressLint({"RestrictedApi", "WrongConstant"})
    void show(@Nullable final MyFloating.InternalVisibilityChangedListener listener, final boolean fromUser) {
        if (!this.isOrWillBeShown()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }

            if (this.shouldAnimateVisibilityChange()) {
                if (this.view.getVisibility() != 0) {
                    this.view.setAlpha(0.5F);
                    this.view.setScaleY(0.5F);
                    this.view.setScaleX(0.0F);
                    this.setImageMatrixScale(0.5F);
                }

                AnimatorSet set = this.createAnimator(this.showMotionSpec != null ? this.showMotionSpec : this.getDefaultShowMotionSpec(), 1.0F, 1.0F, 1.0F);
                set.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
//                        MyFloating.this.view.internalSetVisibility(0, fromUser);
                        MyFloating.this.animState = 2;
                        MyFloating.this.currentAnimator = animation;
                    }

                    public void onAnimationEnd(Animator animation) {
                        MyFloating.this.animState = 0;
                        MyFloating.this.currentAnimator = null;
                        if (listener != null) {
                            listener.onShown();
                        }

                    }
                });
                if (this.showListeners != null) {
                    Iterator var4 = this.showListeners.iterator();

                    while(var4.hasNext()) {
                        Animator.AnimatorListener l = (Animator.AnimatorListener)var4.next();
                        set.addListener(l);
                    }
                }

                set.start();
            } else {
                this.view.internalSetVisibility(0, fromUser);
                this.view.setAlpha(1.0F);
                this.view.setScaleY(1.0F);
                this.view.setScaleX(1.0F);
                this.setImageMatrixScale(1.0F);
                if (listener != null) {
                    listener.onShown();
                }
            }

        }
    }

    private MotionSpec getDefaultShowMotionSpec() {
        if (this.defaultShowMotionSpec == null) {
            this.defaultShowMotionSpec = MotionSpec.createFromResource(this.view.getContext(),  R.animator.design_fab_show_motion_spec);
        }

        return this.defaultShowMotionSpec;
    }

    private MotionSpec getDefaultHideMotionSpec() {
        if (this.defaultHideMotionSpec == null) {
            this.defaultHideMotionSpec = MotionSpec.createFromResource(this.view.getContext(),  R.animator.design_fab_hide_motion_spec);
        }

        return this.defaultHideMotionSpec;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    private AnimatorSet createAnimator(@NonNull MotionSpec spec, float opacity, float scale, float iconScale) {
        List<Animator> animators = new ArrayList();
        Animator animator = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[]{opacity});
        spec.getTiming("opacity").apply(animator);
        animators.add(animator);
        animator = ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[]{scale});
        spec.getTiming("scale").apply(animator);
        animators.add(animator);
        animator = ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[]{scale});
        spec.getTiming("scale").apply(animator);
        animators.add(animator);
        this.calculateImageMatrixFromScale(iconScale, this.tmpMatrix);
        animator = ObjectAnimator.ofObject(this.view, new ImageMatrixProperty(), new MatrixEvaluator(), new Matrix[]{new Matrix(this.tmpMatrix)});
        spec.getTiming("iconScale").apply(animator);
        animators.add(animator);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }

    final Drawable getContentBackground() {
        return this.contentBackground;
    }

    void onCompatShadowChanged() {
    }

    final void updatePadding() {
        Rect rect = this.tmpRect;
        this.getPadding(rect);
        this.onPaddingUpdated(rect);
        this.shadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    void getPadding(Rect rect) {
        this.shadowDrawable.getPadding(rect);
    }

    void onPaddingUpdated(Rect padding) {
    }

    void onAttachedToWindow() {
        if (this.requirePreDrawListener()) {
            this.ensurePreDrawListener();
            this.view.getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
        }

    }

    void onDetachedFromWindow() {
        if (this.preDrawListener != null) {
            this.view.getViewTreeObserver().removeOnPreDrawListener(this.preDrawListener);
            this.preDrawListener = null;
        }

    }

    boolean requirePreDrawListener() {
        return true;
    }

    @SuppressLint("RestrictedApi")
    CircularBorderDrawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Context context = this.view.getContext();
        CircularBorderDrawable borderDrawable = this.newCircularDrawable();
        borderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float)borderWidth);
        borderDrawable.setBorderTint(backgroundTint);
        return borderDrawable;
    }

    @SuppressLint("RestrictedApi")
    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    void onPreDraw() {
        float rotation = this.view.getRotation();
        if (this.rotation != rotation) {
            this.rotation = rotation;
            this.updateFromViewRotation();
        }

    }

    private void ensurePreDrawListener() {
        if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    MyFloating.this.onPreDraw();
                    return true;
                }
            };
        }

    }

    @SuppressLint("WrongConstant")
    GradientDrawable createShapeDrawable() {
        GradientDrawable d = this.newGradientDrawableForShape();
        d.setShape(1);
        d.setColor(-1);
        return d;
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    @SuppressLint("WrongConstant")
    boolean isOrWillBeShown() {
        if (this.view.getVisibility() != 0) {
            return this.animState == 2;
        } else {
            return this.animState != 1;
        }
    }

    @SuppressLint("WrongConstant")
    boolean isOrWillBeHidden() {
        if (this.view.getVisibility() == 0) {
            return this.animState == 1;
        } else {
            return this.animState != 2;
        }
    }

    private ValueAnimator createElevationAnimator(@NonNull MyFloating.ShadowAnimatorImpl impl) {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        animator.setDuration(100L);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(new float[]{0.0F, 1.0F});
        return animator;
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.view) && !this.view.isInEditMode();
    }

    @SuppressLint("RestrictedApi")
    private void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.rotation % 90.0F != 0.0F) {
                if (this.view.getLayerType() != 1) {
                    this.view.setLayerType(1, (Paint)null);
                }
            } else if (this.view.getLayerType() != 0) {
                this.view.setLayerType(0, (Paint)null);
            }
        }

        if (this.shadowDrawable != null) {
            this.shadowDrawable.setRotation(-this.rotation);
        }

        if (this.borderDrawable != null) {
            this.borderDrawable.setRotation(-this.rotation);
        }

    }

    static {
        ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        HOVERED_FOCUSED_ENABLED_STATE_SET = new int[]{16843623, 16842908, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        HOVERED_ENABLED_STATE_SET = new int[]{16843623, 16842910};
        ENABLED_STATE_SET = new int[]{16842910};
        EMPTY_STATE_SET = new int[0];
    }

    private class DisabledElevationAnimation extends MyFloating.ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super();
        }

        protected float getTargetShadowSize() {
            return 0.0F;
        }
    }

    private class ElevateToPressedTranslationZAnimation extends MyFloating.ShadowAnimatorImpl {
        ElevateToPressedTranslationZAnimation() {
            super();
        }

        protected float getTargetShadowSize() {
            return MyFloating.this.elevation + MyFloating.this.pressedTranslationZ;
        }
    }

    private class ElevateToHoveredFocusedTranslationZAnimation extends MyFloating.ShadowAnimatorImpl {
        ElevateToHoveredFocusedTranslationZAnimation() {
            super();
        }

        protected float getTargetShadowSize() {
            return MyFloating.this.elevation + MyFloating.this.hoveredFocusedTranslationZ;
        }
    }

    private class ResetElevationAnimation extends MyFloating.ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        protected float getTargetShadowSize() {
            return MyFloating.this.elevation;
        }
    }

    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private boolean validValues;
        private float shadowSizeStart;
        private float shadowSizeEnd;

        private ShadowAnimatorImpl() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            if (!this.validValues) {
                this.shadowSizeStart = MyFloating.this.shadowDrawable.getShadowSize();
                this.shadowSizeEnd = this.getTargetShadowSize();
                this.validValues = true;
            }

            MyFloating.this.shadowDrawable.setShadowSize(this.shadowSizeStart + (this.shadowSizeEnd - this.shadowSizeStart) * animator.getAnimatedFraction());
        }

        public void onAnimationEnd(Animator animator) {
            MyFloating.this.shadowDrawable.setShadowSize(this.shadowSizeEnd);
            this.validValues = false;
        }

        protected abstract float getTargetShadowSize();
    }

    interface InternalVisibilityChangedListener {
        void onShown();

        void onHidden();
    }

}
