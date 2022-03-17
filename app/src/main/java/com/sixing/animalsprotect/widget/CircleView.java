package com.sixing.animalsprotect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;


public class CircleView extends androidx.appcompat.widget.AppCompatImageView {

    private int mWidth,mHeight;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path=new Path();
        path.addCircle(mWidth/2,mHeight/2,mHeight/2, Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }
}
