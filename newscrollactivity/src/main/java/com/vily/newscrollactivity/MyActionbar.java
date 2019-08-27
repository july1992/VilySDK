package com.vily.newscrollactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AnimatorRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.animation.MotionSpec;
import android.support.design.expandable.ExpandableTransformationWidget;
import android.support.design.expandable.ExpandableWidgetHelper;
import android.support.design.internal.ThemeEnforcement;
import android.support.design.internal.ViewUtils;
import android.support.design.resources.MaterialResources;
import android.support.design.stateful.ExtendableSavedState;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.DescendantOffsetUtils;

import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TintableImageSourceView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/

@SuppressLint("RestrictedApi")
@CoordinatorLayout.DefaultBehavior(MyActionbar.Behavior.class)
public class MyActionbar extends VisibilityAwareImageButton implements TintableBackgroundView, TintableImageSourceView, ExpandableTransformationWidget {


    private static final String LOG_TAG = "MyActionbar";
    private static final String EXPANDABLE_WIDGET_HELPER_KEY = "expandableWidgetHelper";
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_AUTO = -1;
    public static final int NO_CUSTOM_SIZE = 0;
    private static final int AUTO_MINI_LARGEST_SCREEN_WIDTH = 470;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    @Nullable
    private ColorStateList imageTint;
    @Nullable
    private PorterDuff.Mode imageMode;
    private int borderWidth;
    private ColorStateList rippleColor;
    private int size;
    private int customSize;
    private int imagePadding;
    private int maxImageSize;
    boolean compatPadding;
    final Rect shadowPadding;
    private final Rect touchArea;
    private final AppCompatImageHelper imageHelper;
    private final ExpandableWidgetHelper expandableWidgetHelper;
    private MyFloating impl;

    public MyActionbar(Context context) {
        this(context, (AttributeSet)null);
    }

    public MyActionbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.floatingActionButtonStyle);
    }

    @SuppressLint("RestrictedApi")
    public MyActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.shadowPadding = new Rect();
        this.touchArea = new Rect();
        @SuppressLint("RestrictedApi")
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.FloatingActionButton, defStyleAttr, R.style.Widget_Design_FloatingActionButton, new int[0]);
        this.backgroundTint = MaterialResources.getColorStateList(context, a, R.styleable.FloatingActionButton_backgroundTint);
        this.backgroundTintMode = ViewUtils.parseTintMode(a.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), (PorterDuff.Mode)null);
        this.rippleColor = MaterialResources.getColorStateList(context, a, R.styleable.FloatingActionButton_rippleColor);
        this.size = a.getInt(R.styleable.FloatingActionButton_fabSize, -1);
        this.customSize = a.getDimensionPixelSize(R.styleable.FloatingActionButton_fabCustomSize, 0);
        this.borderWidth = a.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float elevation = a.getDimension(R.styleable.FloatingActionButton_elevation, 0.0F);
        float hoveredFocusedTranslationZ = a.getDimension(R.styleable.FloatingActionButton_hoveredFocusedTranslationZ, 0.0F);
        float pressedTranslationZ = a.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0F);
        this.compatPadding = a.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        this.maxImageSize = a.getDimensionPixelSize(R.styleable.FloatingActionButton_maxImageSize, 0);
        MotionSpec showMotionSpec = MotionSpec.createFromAttribute(context, a, R.styleable.FloatingActionButton_showMotionSpec);
        MotionSpec hideMotionSpec = MotionSpec.createFromAttribute(context, a, R.styleable.FloatingActionButton_hideMotionSpec);
        a.recycle();
        this.imageHelper = new AppCompatImageHelper(this);
        this.imageHelper.loadFromAttributes(attrs, defStyleAttr);
        this.expandableWidgetHelper = new ExpandableWidgetHelper(this);
        this.getImpl().setBackgroundDrawable(this.backgroundTint, this.backgroundTintMode, this.rippleColor, this.borderWidth);
        this.getImpl().setElevation(elevation);
        this.getImpl().setHoveredFocusedTranslationZ(hoveredFocusedTranslationZ);
        this.getImpl().setPressedTranslationZ(pressedTranslationZ);
        this.getImpl().setMaxImageSize(this.maxImageSize);
        this.getImpl().setShowMotionSpec(showMotionSpec);
        this.getImpl().setHideMotionSpec(hideMotionSpec);
        this.setScaleType(ScaleType.MATRIX);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredSize = this.getSizeDimension();
        this.imagePadding = (preferredSize - this.maxImageSize) / 2;
        this.getImpl().updatePadding();
        int w = resolveAdjustedSize(preferredSize, widthMeasureSpec);
        int h = resolveAdjustedSize(preferredSize, heightMeasureSpec);
        int d = Math.min(w, h);
        this.setMeasuredDimension(d + this.shadowPadding.left + this.shadowPadding.right, d + this.shadowPadding.top + this.shadowPadding.bottom);
    }

    /** @deprecated */
    @Deprecated
    @ColorInt
    public int getRippleColor() {
        return this.rippleColor != null ? this.rippleColor.getDefaultColor() : 0;
    }

    @Nullable
    public ColorStateList getRippleColorStateList() {
        return this.rippleColor;
    }

    public void setRippleColor(@ColorInt int color) {
        this.setRippleColor(ColorStateList.valueOf(color));
    }

    public void setRippleColor(@Nullable ColorStateList color) {
        if (this.rippleColor != color) {
            this.rippleColor = color;
            this.getImpl().setRippleColor(this.rippleColor);
        }

    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.backgroundTint;
    }

    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (this.backgroundTint != tint) {
            this.backgroundTint = tint;
            this.getImpl().setBackgroundTintList(tint);
        }

    }

    @Nullable
    public PorterDuff.Mode getBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (this.backgroundTintMode != tintMode) {
            this.backgroundTintMode = tintMode;
            this.getImpl().setBackgroundTintMode(tintMode);
        }

    }

    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        this.setBackgroundTintList(tint);
    }

    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        return this.getBackgroundTintList();
    }

    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.setBackgroundTintMode(tintMode);
    }

    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.getBackgroundTintMode();
    }

    public void setSupportImageTintList(@Nullable ColorStateList tint) {
        if (this.imageTint != tint) {
            this.imageTint = tint;
            this.onApplySupportImageTint();
        }

    }

    @Nullable
    public ColorStateList getSupportImageTintList() {
        return this.imageTint;
    }

    public void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (this.imageMode != tintMode) {
            this.imageMode = tintMode;
            this.onApplySupportImageTint();
        }

    }

    @Nullable
    public PorterDuff.Mode getSupportImageTintMode() {
        return this.imageMode;
    }

    @SuppressLint("RestrictedApi")
    private void onApplySupportImageTint() {
        Drawable drawable = this.getDrawable();
        if (drawable != null) {
            if (this.imageTint == null) {
                DrawableCompat.clearColorFilter(drawable);
            } else {
                int color = this.imageTint.getColorForState(this.getDrawableState(), 0);
                PorterDuff.Mode mode = this.imageMode;
                if (mode == null) {
                    mode = PorterDuff.Mode.SRC_IN;
                }

                drawable.mutate().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(color, mode));
            }
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        Log.i("MyActionbar", "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int resid) {
        Log.i("MyActionbar", "Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int color) {
        Log.i("MyActionbar", "Setting a custom background is not supported.");
    }

    @SuppressLint("RestrictedApi")
    public void setImageResource(@DrawableRes int resId) {
        this.imageHelper.setImageResource(resId);
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        this.getImpl().updateImageMatrixScale();
    }

    public void show() {
        this.show((MyActionbar.OnVisibilityChangedListener)null);
    }

    public void show(@Nullable MyActionbar.OnVisibilityChangedListener listener) {
        this.show(listener, true);
    }

    void show(MyActionbar.OnVisibilityChangedListener listener, boolean fromUser) {
        this.getImpl().show(this.wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void addOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.getImpl().addOnShowAnimationListener(listener);
    }

    public void removeOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.getImpl().removeOnShowAnimationListener(listener);
    }

    public void hide() {
        this.hide((MyActionbar.OnVisibilityChangedListener)null);
    }

    public void hide(@Nullable MyActionbar.OnVisibilityChangedListener listener) {
        this.hide(listener, true);
    }

    void hide(@Nullable MyActionbar.OnVisibilityChangedListener listener, boolean fromUser) {
        this.getImpl().hide(this.wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.getImpl().addOnHideAnimationListener(listener);
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.getImpl().removeOnHideAnimationListener(listener);
    }

    public boolean setExpanded(boolean expanded) {
        return this.expandableWidgetHelper.setExpanded(expanded);
    }

    public boolean isExpanded() {
        return this.expandableWidgetHelper.isExpanded();
    }

    public void setExpandedComponentIdHint(@IdRes int expandedComponentIdHint) {
        this.expandableWidgetHelper.setExpandedComponentIdHint(expandedComponentIdHint);
    }

    public int getExpandedComponentIdHint() {
        return this.expandableWidgetHelper.getExpandedComponentIdHint();
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.compatPadding != useCompatPadding) {
            this.compatPadding = useCompatPadding;
            this.getImpl().onCompatShadowChanged();
        }

    }

    public boolean getUseCompatPadding() {
        return this.compatPadding;
    }

    public void setSize(int size) {
        this.customSize = 0;
        if (size != this.size) {
            this.size = size;
            this.requestLayout();
        }

    }

    public int getSize() {
        return this.size;
    }

    @Nullable
    private MyFloating.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(@Nullable final MyActionbar.OnVisibilityChangedListener listener) {
        return listener == null ? null : new MyFloating.InternalVisibilityChangedListener() {
            public void onShown() {
                listener.onShown(MyActionbar.this);
            }

            public void onHidden() {
                listener.onHidden(MyActionbar.this);
            }
        };
    }

    public boolean isOrWillBeHidden() {
        return this.getImpl().isOrWillBeHidden();
    }

    public boolean isOrWillBeShown() {
        return this.getImpl().isOrWillBeShown();
    }

    public void setCustomSize(@Px int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Custom size must be non-negative");
        } else {
            this.customSize = size;
        }
    }

    @Px
    public int getCustomSize() {
        return this.customSize;
    }

    public void clearCustomSize() {
        this.setCustomSize(0);
    }

    int getSizeDimension() {
        return this.getSizeDimension(this.size);
    }

    private int getSizeDimension(int size) {
        if (this.customSize != 0) {
            return this.customSize;
        } else {
            Resources res = this.getResources();
            switch(size) {
                case -1:
                    int width = res.getConfiguration().screenWidthDp;
                    int height = res.getConfiguration().screenHeightDp;
                    return Math.max(width, height) < 470 ? this.getSizeDimension(1) : this.getSizeDimension(0);
                case 0:
                default:
                    return res.getDimensionPixelSize(R.dimen.design_fab_size_normal);
                case 1:
                    return res.getDimensionPixelSize(R.dimen.design_fab_size_mini);
            }
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getImpl().onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getImpl().onDetachedFromWindow();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.getImpl().onDrawableStateChanged(this.getDrawableState());
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.getImpl().jumpDrawableToCurrentState();
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        ExtendableSavedState state = new ExtendableSavedState(superState);
        state.extendableStates.put("expandableWidgetHelper", this.expandableWidgetHelper.onSaveInstanceState());
        return state;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(state);
        } else {
            ExtendableSavedState ess = (ExtendableSavedState)state;
            super.onRestoreInstanceState(ess.getSuperState());
            this.expandableWidgetHelper.onRestoreInstanceState((Bundle)ess.extendableStates.get("expandableWidgetHelper"));
        }
    }

    /** @deprecated */
    @Deprecated
    public boolean getContentRect(@NonNull Rect rect) {
        if (ViewCompat.isLaidOut(this)) {
            rect.set(0, 0, this.getWidth(), this.getHeight());
            this.offsetRectWithShadow(rect);
            return true;
        } else {
            return false;
        }
    }

    public void getMeasuredContentRect(@NonNull Rect rect) {
        rect.set(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        this.offsetRectWithShadow(rect);
    }

    private void offsetRectWithShadow(@NonNull Rect rect) {
        rect.left += this.shadowPadding.left;
        rect.top += this.shadowPadding.top;
        rect.right -= this.shadowPadding.right;
        rect.bottom -= this.shadowPadding.bottom;
    }

    @NonNull
    public Drawable getContentBackground() {
        return this.getImpl().getContentBackground();
    }

    private static int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        switch(specMode) {
            case -2147483648:
                result = Math.min(desiredSize, specSize);
                break;
            case 0:
                result = desiredSize;
                break;
            case 1073741824:
                result = specSize;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return result;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return ev.getAction() == 0 && this.getContentRect(this.touchArea) && !this.touchArea.contains((int)ev.getX(), (int)ev.getY()) ? false : super.onTouchEvent(ev);
    }

    public float getCompatElevation() {
        return this.getImpl().getElevation();
    }

    public void setCompatElevation(float elevation) {
        this.getImpl().setElevation(elevation);
    }

    public void setCompatElevationResource(@DimenRes int id) {
        this.setCompatElevation(this.getResources().getDimension(id));
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return this.getImpl().getHoveredFocusedTranslationZ();
    }

    public void setCompatHoveredFocusedTranslationZ(float translationZ) {
        this.getImpl().setHoveredFocusedTranslationZ(translationZ);
    }

    public void setCompatHoveredFocusedTranslationZResource(@DimenRes int id) {
        this.setCompatHoveredFocusedTranslationZ(this.getResources().getDimension(id));
    }

    public float getCompatPressedTranslationZ() {
        return this.getImpl().getPressedTranslationZ();
    }

    public void setCompatPressedTranslationZ(float translationZ) {
        this.getImpl().setPressedTranslationZ(translationZ);
    }

    public void setCompatPressedTranslationZResource(@DimenRes int id) {
        this.setCompatPressedTranslationZ(this.getResources().getDimension(id));
    }

    public MotionSpec getShowMotionSpec() {
        return this.getImpl().getShowMotionSpec();
    }

    public void setShowMotionSpec(MotionSpec spec) {
        this.getImpl().setShowMotionSpec(spec);
    }

    public void setShowMotionSpecResource(@AnimatorRes int id) {
        this.setShowMotionSpec(MotionSpec.createFromResource(this.getContext(), id));
    }

    public MotionSpec getHideMotionSpec() {
        return this.getImpl().getHideMotionSpec();
    }

    public void setHideMotionSpec(MotionSpec spec) {
        this.getImpl().setHideMotionSpec(spec);
    }

    public void setHideMotionSpecResource(@AnimatorRes int id) {
        this.setHideMotionSpec(MotionSpec.createFromResource(this.getContext(), id));
    }

    private MyFloating getImpl() {
        if (this.impl == null) {
            this.impl = this.createImpl();
        }

        return this.impl;
    }

    private MyFloating createImpl() {
        return (MyFloating)(Build.VERSION.SDK_INT >= 21 ? new MyFloating(this, new MyActionbar.ShadowDelegateImpl()) : new MyFloating(this, new MyActionbar.ShadowDelegateImpl()));
    }

    private class ShadowDelegateImpl implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        public float getRadius() {
            return (float)MyActionbar.this.getSizeDimension() / 2.0F;
        }

        public void setShadowPadding(int left, int top, int right, int bottom) {
            MyActionbar.this.shadowPadding.set(left, top, right, bottom);
            MyActionbar.this.setPadding(left + MyActionbar.this.imagePadding, top + MyActionbar.this.imagePadding, right + MyActionbar.this.imagePadding, bottom + MyActionbar.this.imagePadding);
        }

        public void setBackgroundDrawable(Drawable background) {
            MyActionbar.super.setBackgroundDrawable(background);
        }

        public boolean isCompatPaddingEnabled() {
            return MyActionbar.this.compatPadding;
        }
    }

    protected static class BaseBehavior<T extends MyActionbar> extends android.support.design.widget.CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private Rect tmpRect;
        private MyActionbar.OnVisibilityChangedListener internalAutoHideListener;
        private boolean autoHideEnabled;

        public BaseBehavior() {
            this.autoHideEnabled = true;
        }

        public BaseBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = a.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean autoHide) {
            this.autoHideEnabled = autoHide;
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }

        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, MyActionbar child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                this.updateFabVisibilityForAppBarLayout(parent, (AppBarLayout)dependency, child);
            } else if (isBottomSheet(dependency)) {
                this.updateFabVisibilityForBottomSheet(dependency, child);
            }

            return false;
        }

        private static boolean isBottomSheet(@NonNull View view) {
            android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
            return lp instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams)lp).getBehavior() instanceof BottomSheetBehavior : false;
        }

        @VisibleForTesting
        public void setInternalAutoHideListener(MyActionbar.OnVisibilityChangedListener listener) {
            this.internalAutoHideListener = listener;
        }

        @SuppressLint("RestrictedApi")
        private boolean shouldUpdateVisibility(View dependency, MyActionbar child) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
            if (!this.autoHideEnabled) {
                return false;
            } else if (lp.getAnchorId() != dependency.getId()) {
                return false;
            } else {
                return child.getUserSetVisibility() == 0;
            }
        }

        @SuppressLint("RestrictedApi")
        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout parent, AppBarLayout appBarLayout, MyActionbar child) {
            if (!this.shouldUpdateVisibility(appBarLayout, child)) {
                return false;
            } else {
                if (this.tmpRect == null) {
                    this.tmpRect = new Rect();
                }

                Rect rect = this.tmpRect;
                DescendantOffsetUtils.getDescendantRect(parent, appBarLayout, rect);
                if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                    child.hide(this.internalAutoHideListener, false);
                } else {
                    child.show(this.internalAutoHideListener, false);
                }

                return true;
            }
        }

        private boolean updateFabVisibilityForBottomSheet(View bottomSheet, MyActionbar child) {
            if (!this.shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            } else {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
                if (bottomSheet.getTop() < child.getHeight() / 2 + lp.topMargin) {
                    child.hide(this.internalAutoHideListener, false);
                } else {
                    child.show(this.internalAutoHideListener, false);
                }

                return true;
            }
        }

        public boolean onLayoutChild(CoordinatorLayout parent, MyActionbar child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int i = 0;

            for(int count = dependencies.size(); i < count; ++i) {
                View dependency = (View)dependencies.get(i);
                if (dependency instanceof AppBarLayout) {
                    if (this.updateFabVisibilityForAppBarLayout(parent, (AppBarLayout)dependency, child)) {
                        break;
                    }
                } else if (isBottomSheet(dependency) && this.updateFabVisibilityForBottomSheet(dependency, child)) {
                    break;
                }
            }

            parent.onLayoutChild(child, layoutDirection);
            this.offsetIfNeeded(parent, child);
            return true;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent, @NonNull MyActionbar child, @NonNull Rect rect) {
            Rect shadowPadding = child.shadowPadding;
            rect.set(child.getLeft() + shadowPadding.left, child.getTop() + shadowPadding.top, child.getRight() - shadowPadding.right, child.getBottom() - shadowPadding.bottom);
            return true;
        }

        private void offsetIfNeeded(CoordinatorLayout parent, MyActionbar fab) {
            Rect padding = fab.shadowPadding;
            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }

                if (fab.getBottom() >= parent.getHeight() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }

                if (offsetTB != 0) {
                    ViewCompat.offsetTopAndBottom(fab, offsetTB);
                }

                if (offsetLR != 0) {
                    ViewCompat.offsetLeftAndRight(fab, offsetLR);
                }
            }

        }
    }

    public static class Behavior extends MyActionbar.BaseBehavior<MyActionbar> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Size {
    }

    public abstract static class OnVisibilityChangedListener {
        public OnVisibilityChangedListener() {
        }

        public void onShown(MyActionbar fab) {
        }

        public void onHidden(MyActionbar fab) {
        }
    }

}
