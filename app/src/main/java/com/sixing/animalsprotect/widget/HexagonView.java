package com.sixing.animalsprotect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sixing.animalsprotect.R;

public class HexagonView extends androidx.appcompat.widget.AppCompatImageView {
    private Path path;
    private int width,height;
    private Paint paint;
    public HexagonView(@NonNull Context context) {
        this(context,null);
    }

    public HexagonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HexagonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path=new Path();
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HexagonView);
        int proColor=typedArray.getColor(R.styleable.HexagonView_borderColor, Color.BLACK);
        paint.setColor(proColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.moveTo(width/2,0);
        double temp= (double) Math.pow(3,0.5);
        int w= (int) (width/(2*temp));
        Log.d("TAG", "onDraw: "+temp);
        path.lineTo(width,w);
        path.lineTo(width, height-w);
        path.lineTo(width/2,height);
        path.lineTo(0, height-w);
        path.lineTo(0, w);
        path.lineTo(width/2,0);
        canvas.clipPath(path);
        super.onDraw(canvas);
        paint.setStrokeWidth(width/11);
        canvas.drawPath(path,paint);
    }
}
