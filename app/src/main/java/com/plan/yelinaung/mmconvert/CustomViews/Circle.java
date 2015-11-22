package com.plan.yelinaung.mmconvert.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.plan.yelinaung.mmconvert.R;

/**
 * Created by user on 9/2/15.
 */
public class Circle extends View {
    private float dimen;
    private int mColor,mShape,cColor;
    private Paint mPaint,cPaint;


    public Circle(Context context) {
        super(context);

    }



    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = null;
        try {
mPaint=new Paint();
            cPaint=new Paint();
            attributes = context.obtainStyledAttributes(attrs, R.styleable.Circle);
            dimen = attributes.getDimension(R.styleable.Circle_dimen, 20f);
            mShape = attributes.getInteger(R.styleable.Circle_shape, 0);
            mColor = attributes.getInteger(R.styleable.Circle_circle_color, 0);
            cColor=attributes.getInteger(R.styleable.Circle_border_color,0);
        }finally {
            attributes.recycle();
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        cPaint.setAntiAlias(true);
        cPaint.setColor(cColor);
        mPaint.setColor(mColor);
        switch (mShape){
            case 0:

                canvas.drawCircle(dimen+5,dimen+7,dimen+5,cPaint);
canvas.drawCircle(dimen+5,dimen+7,dimen,mPaint);
break;

            case 1:
                canvas.drawRect(0,0,dimen,dimen,mPaint);
                break;
        }

    }
    public void setCircleColor(int Color_in){
        this.mColor=Color_in;
        invalidate();
        requestLayout();
    }
    public int getColor(){
        invalidate();
        requestLayout();
        return mColor;
    }
}
