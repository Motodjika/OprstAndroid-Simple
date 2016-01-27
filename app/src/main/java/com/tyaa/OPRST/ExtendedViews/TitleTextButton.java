package com.tyaa.OPRST.ExtendedViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/03/2015.
 */
public class TitleTextButton extends Button {
    public TitleTextButton(Context context) {
        super(context);
        setTextSize(getTextSize() / textRate);
    }
    public TitleTextButton(Context context, AttributeSet attrs){
        super(context, attrs);
        setTextSize(getTextSize() / textRate);
        //setText("OPRST");
        //setTitleColor(R.color.blue);
    }
    public TitleTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(getTextSize() / textRate);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleTextButton(Context context, AttributeSet attrs, int defStyleAttr,int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean isInEditMode (){
        return true;
    }
    /**
     * @return text height
     */
    private float getTextHeight(String text, Paint paint) {

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }
    public Bitmap textAsBitmap(String text, Paint paint) {
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    private int mTitleColor;
    private final static float textRate=3;
    public void setTitleColor(int res){
        mTitleColor=getResources().getColor(res);
    }
    public void updateTitleColor(){
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = new TextPaint(getPaint());
        paint.setTextSize(getTextSize()*textRate);
        paint.setColor(mTitleColor);
        Rect rect=new Rect();
        getDrawingRect(rect);
        if(getText().length()!=0) {
            String letter = String.valueOf(getText().charAt(0));
            final Bitmap bmpTop = textAsBitmap(letter, paint);
            canvas.drawBitmap(bmpTop, rect.centerX() - getTextSize(),
                    rect.centerY() + paint.ascent()+getTextSize(), paint);
            canvas.save();
        }

        //canvas.drawText(getText().toString(), rect.left + getTextSize(), rect.centerY() + getPaint().descent(), getPaint());
        super.onDraw(canvas);
        canvas.restore();
    }
}