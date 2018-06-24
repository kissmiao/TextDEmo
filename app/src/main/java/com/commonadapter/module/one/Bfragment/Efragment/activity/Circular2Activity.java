package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.commonadapter.R;

/**
 * Created by whl on 16/9/6.
 */
public class Circular2Activity extends Activity {

    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_circular2);
        iv_image = (ImageView) findViewById(R.id.iv_circular_image);
        findViewById(R.id.bt_circular_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim(iv_image);
            }
        });

    }

    private void startAnim(final ImageView imageView) {
        final PointF point = new PointF();
        TypeEvaluator<PointF> typeEvaluator = new TypeEvaluator<PointF>() {
            //
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                //fraction 就相当于进度0->1
                //  point.x=(endValue.x-startValue.x)*fraction;
                //  point.y=(endValue.y-startValue.y)*fraction;
                point.x = (endValue.x - startValue.x) * fraction;
                point.y = 600 + 200 * (float) Math.sin(point.x / 65);
                return point;
            }
        };

        ValueAnimator valueAnimator = ValueAnimator.ofObject(typeEvaluator, new PointF(0, 600), new PointF((float) (260 * Math.PI), 600));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //getAnimatedValue方法返回的值，由evaluate计算得到的
                PointF point = (PointF) animation.getAnimatedValue();
                //  ViewHelper.setX(iv, point.x);
                //  ViewHelper.setY(iv, point.y);
                imageView.setX(point.x);
                imageView.setY(point.y);
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();


    }

}
