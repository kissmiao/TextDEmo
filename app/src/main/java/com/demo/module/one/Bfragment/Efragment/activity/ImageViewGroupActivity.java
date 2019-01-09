package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.R;
import com.demo.common.view.MViewGroup3;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ImageViewGroupActivity extends Activity {
    private int [] images={R.mipmap.viewimage,R.mipmap.viewimage,R.mipmap.viewimage,R.mipmap.viewimage,R.mipmap.viewimage,R.mipmap.viewimage};
    private MViewGroup3 viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_image_viewgroup);
        init();
    }
    private void init(){
        viewGroup= (MViewGroup3) findViewById(R.id.image_viewGroup);
        ImageView imageView;
        for (int i=0;i<images.length;i++){
            imageView=new ImageView(this);
            imageView.setImageResource(images[i]);
            imageView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            viewGroup.addView(imageView);
        }
    }

}
