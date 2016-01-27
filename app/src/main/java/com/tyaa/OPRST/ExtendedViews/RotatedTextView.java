package com.tyaa.OPRST.ExtendedViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/03/2015.
 */
public class RotatedTextView extends TextView {
    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    private Rect text_bounds = new Rect();
    private Rect canvas_bounds = new Rect();
    private int a1,a2;//a1-ширина холста, a2-ширина текста
    private int b1,b2;//b1-высота холста, в2-высота текста
    private int text_width;
    private int text_height;
    protected int angle;
    protected double rad;
    private int mAscent;
    private int mDescent;

    protected void setAttrs(TypedArray a){
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.RotatedTextView_rotation:
                    angle = a.getInt(attr,0);
                    if(Math.abs(angle)>180){
                        angle%=180;
                    }
                    rad=Math.toRadians(angle);
                    break;
            }
        }
        a.recycle();
    }
    public RotatedTextView(Context context) {
        super(context);
    }
    public RotatedTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RotatedTextView);
        setAttrs(a);
    }
    public RotatedTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RotatedTextView, defStyleAttr, 0);
        setAttrs(a);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RotatedTextView(Context context, AttributeSet attrs, int defStyleAttr,int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RotatedTextView, defStyleAttr, defStyleRes);
        setAttrs(a);
    }
    @Override
    public boolean isInEditMode (){
        return true;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), text_bounds);
        mAscent = (int) getPaint().ascent();
        mDescent=(int) getPaint().descent();
        text_width=text_bounds.width();
        text_height=text_bounds.height();
        a1= (int) (text_width*Math.cos(rad));
        a2= (int) (text_height*Math.sin(rad));
        b1= (int) (text_width*Math.sin(rad));
        b2= (int) (text_height*Math.cos(rad));
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = Math.abs(a1) +Math.abs(a2) +Math.abs(mAscent) +
                    getPaddingLeft() + getPaddingRight();

            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = Math.abs(b1) + Math.abs(b2)+Math.abs(mAscent) +
                    getPaddingTop() + getPaddingBottom();

            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        if(angle>0 && angle <=90){
            canvas.translate(b1, a2 + mAscent);
        }else if(angle>90 && angle <=180){
            canvas.translate(-a1+b1,-b2- mAscent);
        }else if(angle<0 && angle >=-90){
            canvas.translate(a2, -b1);
        }else if(angle<-90 && angle >=-180){
            canvas.translate(-a1+a2, -a1-b1+b2+mDescent);
        }

        canvas.rotate(angle);

        super.onDraw(canvas);
        canvas.restore();
    }
}
