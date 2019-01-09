package com.demo.common.view;

/**
 * Created by wanghongliang on 16/3/18.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class WheelMenu extends ImageView {

    private Bitmap imageOriginal, imageScaled;     // 原始和重新大小的图像的变量
    private Matrix matrix;                         // 用于执行旋转的矩阵
    private int wheelHeight, wheelWidth;           //height and width of the view
    private int top;
    private double totalRotation;               // 变量计数总旋转在一个由用户给出的轮（从action_down到action_up）
    private int divCount;
    private int divAngle;                          // 每个值的角度
    private int selectedPosition;                  //.目前由用户选择的部分。
    private boolean snapToCenterFlag = true;       //决定是否要捕捉的变量
    private Context context;
    private WheelChangeListener wheelChangeListener;

    public WheelMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setScaleType(ScaleType.MATRIX);
        selectedPosition = 0;

        //  初始化矩阵
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        //touch events listener
        this.setOnTouchListener(new WheelTouchListener());
    }


    public int getSelectedPosition() {
        return selectedPosition;
    }


    public void setDivCount(int divCount) {
        this.divCount = divCount;

        divAngle = 360 / divCount;
        totalRotation = -1 * (divAngle / 2);
    }


    public void setWheelImage(int drawableId) {
        imageOriginal = BitmapFactory.decodeResource(context.getResources(), drawableId);
    }

    /*
     * 我们需要这个来获得视图的尺寸。一旦我们得到了那些，我们可以缩放的图像，以确保它的正确，初始化矩阵并将其与视图中心对齐。
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("LOG","******onSizeChanged");
        // method called multiple times but initialized just once
        if (wheelHeight == 0 || wheelWidth == 0) {
            wheelHeight = h;
            wheelWidth = w;
            Log.i("LOG","w="+w+"h="+h);
            // resize the image
            Matrix resize = new Matrix();
            resize.postScale((float) Math.min(wheelWidth, wheelHeight) / (float) imageOriginal
                    .getWidth(), (float) Math.min(wheelWidth,
                    wheelHeight) / (float) imageOriginal.getHeight());
            imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(),
                    imageOriginal.getHeight(), resize, false);
            // translate the matrix to the image view's center
            float translateX = wheelWidth / 2 - imageScaled.getWidth() / 2;
            float translateY = wheelHeight / 2 - imageScaled.getHeight() / 2;
            matrix.postTranslate(translateX, translateY);
            WheelMenu.this.setImageBitmap(imageScaled);
            WheelMenu.this.setImageMatrix(matrix);
        }
    }


    private double getAngle(double x, double y) {
        Log.i("LOG", "wheelWidth=" + wheelWidth + "wheelHeight=" + wheelHeight);

        x = x - (wheelWidth / 2d);
        y = wheelHeight - y - (wheelHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    private static int getQuadrant(double x, double y) {
        if (x >= 0) {

            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    private void rotateWheel(float degrees) {
        matrix.postRotate(degrees, wheelWidth / 2, wheelHeight / 2);
        WheelMenu.this.setImageMatrix(matrix);
        //add the rotation to the total rotation
        //增加旋转到总旋转
        totalRotation = totalRotation + degrees;
    }



    public void setWheelChangeListener(WheelChangeListener wheelChangeListener) {
        this.wheelChangeListener = wheelChangeListener;
    }


    public interface WheelChangeListener {

        public void onSelectionChange(int selectedPosition);
    }

    private class WheelTouchListener implements OnTouchListener {
        private double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    //get the start angle for the current move event 获取当前移动事件的开始角度

                    Log.i("LOG","event.getX()");
                    startAngle = getAngle(event.getX(), event.getY());
                    Log.i("LOG","-----startAngle**"+startAngle);

                    break;


                case MotionEvent.ACTION_MOVE:
                    //get the current angle for the current move event
                    //    获取当前移动事件的当前角度
                    double currentAngle = getAngle(event.getX(), event.getY());
                    Log.i("LOG","***currentAngle**"+currentAngle);
                    //rotate the wheel by the difference
                    rotateWheel((float) (startAngle - currentAngle));

                    // 设置当前角度成为下一个动作的开始角度
                    startAngle = currentAngle;
                    break;


                case MotionEvent.ACTION_UP:
                   /* //get the total angle rotated in 360 degrees
                    totalRotation = totalRotation % 360;

                    //represent total rotation in positive value
                    if (totalRotation < 0) {
                        totalRotation = 360 + totalRotation;
                    }

                    //calculate the no of divs the rotation has crossed
                    int no_of_divs_crossed = (int) ((totalRotation) / divAngle);

                    //calculate current top
                    top = (divCount + top - no_of_divs_crossed) % divCount;

                    //for next rotation, the initial total rotation will be the no of degrees
                    // inside the current top
                    totalRotation = totalRotation % divAngle;

                    //snapping to the top's center
                    if (snapToCenterFlag) {

                        //calculate the angle to be rotated to reach the top's center.
                        double leftover = divAngle / 2 - totalRotation;

                        rotateWheel((float) (leftover));

                        //re-initialize total rotation
                        totalRotation = divAngle / 2;
                    }

                    //set the currently selected option
                    if (top == 0) {
                        selectedPosition = divCount - 1;//loop around the array
                    } else {
                        selectedPosition = top - 1;
                    }

                    if (wheelChangeListener != null) {
                        wheelChangeListener.onSelectionChange(selectedPosition);
                    }*/

                    break;
            }

            return true;
        }
    }




}
