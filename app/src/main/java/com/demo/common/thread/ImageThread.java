package com.demo.common.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wanghongliang on 16/3/29.
 */
public class ImageThread extends Thread {
    private ImageView imageView;
    private String url;
    private Handler handler;

    public ImageThread(String url, ImageView imageView, Handler handler) {
        this.url = url;
        this.imageView = imageView;
        this.handler = handler;
    }


    @Override
    public void run() {
        try {
            URL URL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) URL.openConnection();
            //网络超时
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //得到一个图片2进制流
            InputStream in = conn.getInputStream();
            // File本地输入流
            FileOutputStream out = null;

            //得到时间作为文件名
            String fileName = String.valueOf(System.currentTimeMillis());
            File downloadFile = null;

            //判读是否挂载SD
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //得到根目录
                File parent = Environment.getExternalStorageDirectory();
                //父目录和名字
                downloadFile = new File(parent, fileName);
                out = new FileOutputStream(downloadFile);

            }

            final byte[] b = new byte[2 * 1024];
            int len;
            if (out != null) {
                while ((len = in.read(b)) != -1) {
                    //从零开始有多长些多长
                    out.write(b, 0, len);
                }
            }
            final Bitmap bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath());


            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
