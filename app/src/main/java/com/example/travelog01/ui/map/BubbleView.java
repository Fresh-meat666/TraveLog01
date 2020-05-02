package com.example.travelog01.ui.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * 实现在图片右上角显示照片数
 * 2020/4/25
 */

public class BubbleView extends androidx.appcompat.widget.AppCompatImageView {

    /**
     * 默认模式
     */
    private int pointMode = NO_POINT;

    // 1.不显示红点
    public static final int NO_POINT = 1;
    // 2.只显示一个红点,表示有图片
    public static final int ONLY_POINT = 2;
    // 3.显示一个红点,红点中间显示相应照片的数量
    public static final int NUMBER_POINT = 3;

    //照片的数量
    private  int  number = 0;

    //记录当前是否有新照片
    public boolean isHaveUpdated = false;

    /**
     * 画圆
     */
    private Paint paint;

    /**
     * 画照片数
     */
    private TextPaint paintText;

    public BubbleView(Context context) {
        super(context);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setColor(0xffff0000);//红色
        paint.setAntiAlias(true);//抗锯齿

        paintText= new TextPaint();
        paintText.setColor(0xffffffff);//白色
        paintText.setTextSize(25);//设置显示条数的文本大小
        paintText.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);//实心
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isHaveUpdated){
            return;
        }
        switch (pointMode){
            case NO_POINT://不显示红点
                break;
            case ONLY_POINT://只显示红点
                canvas.drawCircle(getWidth()-25,25,25,paint);
                break;
            case NUMBER_POINT://显示红点且带照片数
                canvas.drawCircle(getWidth()-25,25,25,paint);

                //文字显示基准线
                String showText="";
                if(number>0 && number<100){
                    showText = number+"";
                }else if(number >= 100){
                    showText = "+99";
                }
                float textWidth = paintText.measureText(showText);//测量出文本的宽度
                //图片右顶点减去文本的一半,使文本中心与图片右顶点重合
                float x = getWidth() - 25 - textWidth / 2;
                //y抽坐标,文字的基准线为图片右顶点下面点
                float y = (float) (25 + paintText.getFontMetrics().bottom*1.5);
                canvas.drawText(showText,x ,y,paintText);
                break;
        }
    }

    /**
     * 设置照片数
     */
    public void setNum(int number){
        this.number=number;
    }

    /**
     * 是否有图片
     * @param isHaveUpdated true代表有
     */
    public void setHaveUpdated(boolean isHaveUpdated){
        this.isHaveUpdated =isHaveUpdated;
        invalidate();
    }

    /**
     * 设置显示模式
     * @param mode
     */
    public void setPointMode(int mode){
        if(mode>0 && mode<=3){
            pointMode = mode;
        }else {
            throw new RuntimeException("设置的模式有误");
        }
    }

}