package com.example.administrator.toggletest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/10/5.
 */
public class ToggleView extends View {
    private Bitmap switchBackgroundBitmap;
    private Bitmap slideButtonBitmap;
    private Paint paint;
    private boolean state;
    private boolean isTouch;
    private float currentX;
    float newX;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public ToggleView(Context context) {
        super(context);
        init();
    }

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        String nameSpace="http://schemas.android.com/apk/res-auto";
        int switchBackground=attrs.getAttributeResourceValue(nameSpace,"switch_background",-1);
        setSwitchBackground(switchBackground);
        int slideButton=attrs.getAttributeResourceValue(nameSpace,"slide_button",-1);
        setSlideButton(slideButton);
        state=attrs.getAttributeBooleanValue(nameSpace,"slide_button_state",false);
    }

    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private  void init(){
        paint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isTouch){
            if(currentX<slideButtonBitmap.getWidth()/2)
                currentX=slideButtonBitmap.getWidth()/2;
            if(currentX>switchBackgroundBitmap.getWidth()
                    -slideButtonBitmap.getWidth())
                currentX=switchBackgroundBitmap.getWidth()
                        -slideButtonBitmap.getWidth();

            newX=currentX-slideButtonBitmap.getWidth()/2;
            canvas.drawBitmap(switchBackgroundBitmap,0,0,paint);
            canvas.drawBitmap(slideButtonBitmap,newX,0,paint);
        }else{
            canvas.drawBitmap(switchBackgroundBitmap,0,0,paint);
            if(state){
                canvas.drawBitmap(slideButtonBitmap,switchBackgroundBitmap.getWidth()
                        -slideButtonBitmap.getWidth(),0,paint);
            }else{
                canvas.drawBitmap(slideButtonBitmap,0,0,paint);
            }
        }
    }
    boolean newState;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("ToggleView","ACTION_DOWN+lil");
                isTouch=true;
                currentX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("ToggleView","ACTION_MOVE+lil");
                currentX=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ToggleView","ACTION_UP+lil");
                currentX=event.getX();
                newX=currentX-slideButtonBitmap.getWidth()/2;
                if(newX<switchBackgroundBitmap.getWidth()/2)
                    newState=false;
                if(newX>=switchBackgroundBitmap.getWidth()/2)
                    newState=true;
                isTouch=false;
                if(state!=newState&&onSwitchChangeListener!=null){
                    onSwitchChangeListener.onStateChange(newState);
                    state=newState;
                }

                break;
            default:
                break;
        }
        invalidate();

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroundBitmap.getWidth(),switchBackgroundBitmap.getHeight());
    }



    public void setSwitchBackground(int switchBackground){
        switchBackgroundBitmap= BitmapFactory.decodeResource(getResources(),switchBackground);
    }

    public void setSlideButton(int slideButton){
        slideButtonBitmap=BitmapFactory.decodeResource(getResources(),slideButton);
    }

    public interface OnSwitchChangeListener{
        void onStateChange(boolean state);
    }
    private OnSwitchChangeListener onSwitchChangeListener;
    public void setSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener){
        this.onSwitchChangeListener=onSwitchChangeListener;
    }
}
