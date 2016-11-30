package com.infinite.piecharta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inf on 2016/11/29.
 */

public class One extends View{

    private int mWidth,mHeight;
    private float mRadius;
    private RectF mPieRect;
    private Paint mPiePaint,mBlankPaint,mLinePaint,mTextPaint;
    private List<IPieElement> mElements;
    private List<Float> mAngles=new ArrayList<>();
    private List<String> mColors=new ArrayList<>();
    public One(Context context) {
        this(context,null);
    }

    public One(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public One(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPieRect =new RectF();
        mPiePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setColor(Color.RED);

        mBlankPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlankPaint.setColor(Color.WHITE);

        mLinePaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(4);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width=0,height=0;
        if (widthMode==MeasureSpec.AT_MOST){
            width= (int) getSize();
        }else {
            width=widthSize;
        }
        if (heightMode==MeasureSpec.AT_MOST){
            height= (int) getSize();
        }else {
            height=heightSize;
        }
        int size=Math.min(width,height);
        setMeasuredDimension(size,size);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w-getPaddingLeft()-getPaddingRight();
        mHeight=h-getPaddingTop()-getPaddingBottom();
        mRadius= (float) (Math.min(mWidth, mHeight)/2*0.6);
        mPieRect.left=-mRadius;
        mPieRect.top= -mRadius;
        mPieRect.right=mRadius;
        mPieRect.bottom=mRadius;

    }

    private Path mPath=new Path();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawArc(mPieRect,0,90,true,mPiePaint);
        canvas.drawArc(mPieRect,90,1,true,mBlankPaint);
        canvas.drawArc(mPieRect,91,45,true,mPiePaint);
        canvas.drawArc(mPieRect,136,1,true,mBlankPaint);
        canvas.drawArc(mPieRect,137,50,true,mPiePaint);
        //从12点方向开始画
        float sweepedAngle=-90;
        for(int i=0;mElements!=null&&mElements.size()>i;i++){
            //设置扇形的颜色
            mPiePaint.setColor(Color.parseColor(mColors.get(i)));
            mLinePaint.setColor(Color.parseColor(mColors.get(i)));
            //画扇形
            canvas.drawArc(mPieRect,sweepedAngle,mAngles.get(i),true,mPiePaint);
            //扫过的角度++
            sweepedAngle+=mAngles.get(i);
            //画分割线
            canvas.drawArc(mPieRect,sweepedAngle,1,true,mBlankPaint);
            sweepedAngle+=1;
            float x=getXCoordinate(mAngles.get(i),sweepedAngle);
            float y=getYCoordinate(mAngles.get(i),sweepedAngle);
            Log.e("coor",x+"  "+y);
            mPath.reset();
            mPath.moveTo(x,y);
            mPath.lineTo((float)(x*1.2),(float)(y*1.2));
            canvas.drawPath(mPath,mLinePaint);
            mPath.reset();
            mPath.moveTo((float)(x*1.2),(float)(y*1.2));
            float horizontalLineLength= getTextWidth(mTextPaint,String.valueOf(mAngles.get(i)));
            if (x<0){
                horizontalLineLength=-horizontalLineLength;
            }
            mPath.lineTo((float)(x*1.2)+horizontalLineLength,(float)(y*1.2));
            canvas.drawPath(mPath,mLinePaint);
            float offsetV=20;
            if (x>0){
                offsetV=-getTextHeight(mTextPaint,String.valueOf(mAngles.get(i)));
            }else {
                offsetV=40;
            }
            canvas.drawTextOnPath(String.valueOf(mAngles.get(i)),mPath,0,offsetV,mTextPaint);
//            canvas.drawCircle(x,y,10,mLinePaint);
        }
    }

    private float getTextHeight(Paint paint,String text){
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        return rect.height();
    }
    private float getTextWidth(Paint paint,String text){
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length()-1,rect);
        return rect.width();
    }

    private float getXCoordinate(float angle,float sweepedAngle){
        float x= (float) (mRadius*Math.cos(Math.toRadians(sweepedAngle-angle/2)));
        return x;

    }
    private float getYCoordinate(float angle,float sweepedAngle){
        float y= (float) (mRadius*Math.sin(Math.toRadians(sweepedAngle-angle/2)));
        return y;

    }

    private float getSize(){
        Display display= ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float widht=display.getWidth();
        float height=display.getHeight();
        return Math.min(widht,height);
    }

    public void setData(List<IPieElement> elements){
        mElements=elements;
        setValuesAndColors();
        invalidate();
    }

    private void setValuesAndColors(){
        float sum=0;
        if (mElements!=null&&mElements.size()>0){
            for(IPieElement ele:mElements){
                sum+=ele.getValue();
                mColors.add(ele.getColor());
            }
            BigDecimal totleAngel=BigDecimal.valueOf(360-mElements.size());

            for(int i=0;i<mElements.size();i++){
                IPieElement ele=mElements.get(i);
                BigDecimal bigDecimal=new BigDecimal(String.valueOf(ele.getValue()));
                BigDecimal sumBigDecimal=BigDecimal.valueOf(sum);
                BigDecimal res=bigDecimal.divide(sumBigDecimal,5,BigDecimal.ROUND_HALF_UP);
                BigDecimal angle=res.multiply(totleAngel);
                mAngles.add(angle.floatValue());

            }
        }

    }
}
