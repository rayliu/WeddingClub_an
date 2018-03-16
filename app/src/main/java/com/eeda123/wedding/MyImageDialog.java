package com.eeda123.wedding;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyImageDialog extends Dialog {
    private Window window = null;
    private String imgUrl;
    private ImageView iv;
    private Bitmap bms;
    private String[] imgs;

    private ViewPager mPager;

    public MyImageDialog(Context context, boolean cancelable,
                         DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyImageDialog(Context context, int cancelable,int x,int y,Bitmap bm,String[] imgs ) {
        super(context, cancelable);
        //windowDeploy(x, y);
        bms = bm;
        this.imgs = imgs;
    }
    public MyImageDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化布局
        View loadingview= LayoutInflater.from(getContext()).inflate(R.layout.imagedialogview,null);
//        iv=(ImageView) loadingview.findViewById(R.id.imageview_head_big);

//        iv = (ImageView) loadingview.findViewById(R.id.imageview_head_big);

        //iv.setImageBitmap(bms);
//        Picasso.with(loadingview.getContext())
//                .load(imgUrl)
//                .into(iv);

        //设置dialog的布局
        setContentView(loadingview);
        //如果需要放大或者缩小时的动画，可以直接在此出对loadingview或iv操作，在下面SHOW或者dismiss中操作

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (this.getContext().getResources().getDisplayMetrics().density * 0));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
                //return imgsId.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Context c = MyImageDialog.this.getContext();
                PhotoView view = new PhotoView(c);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);

                Picasso.with(c)
                        .load(imgs[position])
                        .into(view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    //设置窗口显示
    public void windowDeploy(int x, int y){
        window = getWindow(); //得到对话框
        //window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
       // window.setBackgroundDrawableResource(R.color.vifrification); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置

        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移

        //            wl.alpha = 0.6f; //设置透明度
        //            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    public void show() {
        //设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(true);
        super.show();
    }
    public void dismiss() {
        super.dismiss();
    }
}
