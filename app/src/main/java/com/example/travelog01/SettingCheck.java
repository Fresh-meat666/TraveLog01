package com.example.travelog01;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.travelog01.R;

public class SettingCheck extends View {
    private static final int DEFAULT_CHECK_BG_ID = 2130838062;
    private static final int DEFAULT_CHECK_DRAWABLE_ID = 2130838061;
    private static final int DEFAULT_UNCHECK_BG_ID = 2130838060;
    private static final int DEFAULT_UNCHECK_DRAWABLE_ID = 2130838059;
    private Drawable mCheckBgDrawable;
    private Drawable mCheckDrawable;
    private Context mContext;
    private int mHeight;
    private boolean mIsCheck = true;
    private Drawable mUnCheckBgDrawable;
    private Drawable mUnCheckDrawable;
    private int mWidth;

    public SettingCheck(Context context) {
        super(context);
        init(context);
    }

    public SettingCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SettingCheck(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public SettingCheck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mCheckBgDrawable = this.mContext.getResources().getDrawable(R.drawable.switch_on_line);
        this.mUnCheckBgDrawable = this.mContext.getResources().getDrawable(R.drawable.switch_off_line);
        this.mCheckDrawable = this.mContext.getResources().getDrawable(R.drawable.switch_on_circle);
        this.mUnCheckDrawable = this.mContext.getResources().getDrawable(R.drawable.switch_off_circle);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = Math.max(this.mCheckBgDrawable.getIntrinsicWidth(), this.mCheckDrawable.getIntrinsicWidth());
        int maxHeight = Math.max(this.mCheckBgDrawable.getIntrinsicHeight(), this.mCheckDrawable.getIntrinsicHeight());
        setMeasuredDimension(resolveSizeAndState(getPaddingLeft() + getPaddingRight() + maxWidth, widthMeasureSpec), resolveSizeAndState(getPaddingBottom() + getPaddingTop() + maxHeight, heightMeasureSpec));
        this.mWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        this.mHeight = (getMeasuredHeight() - getPaddingBottom()) - getPaddingTop();
        initBgDrawable(this.mCheckBgDrawable);
        initBgDrawable(this.mUnCheckBgDrawable);
        initButtonDrawable(this.mCheckDrawable, true);
        initButtonDrawable(this.mUnCheckDrawable, false);
    }

    private void initButtonDrawable(Drawable checkBgDrawable, boolean isRight) {
        if (isRight) {
            int drawableHeight = checkBgDrawable.getIntrinsicHeight();
            int drawableWidth = checkBgDrawable.getIntrinsicWidth();
            int right = this.mWidth - getPaddingRight();
            int top = getPaddingTop();
            checkBgDrawable.setBounds(right - drawableWidth, top, right, top + drawableHeight);
            return;
        }
        int drawableHeight2 = checkBgDrawable.getIntrinsicHeight();
        int top2 = getPaddingTop();
        checkBgDrawable.setBounds(0, top2, checkBgDrawable.getIntrinsicWidth() + 0, top2 + drawableHeight2);
    }

    private void initBgDrawable(Drawable checkBgDrawable) {
        int drawableHeight = checkBgDrawable.getIntrinsicHeight();
        int drawableWidth = checkBgDrawable.getIntrinsicWidth();
        int left = (this.mWidth - drawableWidth) / 2;
        int top = (this.mHeight - drawableHeight) / 2;
        checkBgDrawable.setBounds(left, top, left + drawableWidth, top + drawableHeight);
    }

    public static int resolveSizeAndState(int desireSize, int measureSpec) {
        int result = desireSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                if (specSize < desireSize) {
                    return specSize;
                }
                return desireSize;
            case 0:
                return desireSize;
            case 1073741824:
                return specSize;
            default:
                return result;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        if (this.mIsCheck) {
            this.mCheckBgDrawable.draw(canvas);
            this.mCheckDrawable.draw(canvas);
        } else {
            this.mUnCheckBgDrawable.draw(canvas);
            this.mUnCheckDrawable.draw(canvas);
        }
        canvas.restore();
    }

    public void setCheck(boolean check) {
        if (this.mIsCheck != check) {
            this.mIsCheck = check;
            invalidate();
        }
    }

    public boolean isCheck() {
        return this.mIsCheck;
    }
}
