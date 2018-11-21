package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/21.
 */

public class XfermodeView extends View {
    private Paint mPaint;
    private int width = 400;
    private int height = 400;
    private Bitmap srcRectBitmap;
    private Bitmap dstCircleBitmap;

    static Bitmap makeDst(int width,int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint();
        p.setColor(0xFFFFCC44);
        p.setAntiAlias(true);
//        canvas.drawCircle(width*3/4,height*3/4,150,p);
//        canvas.drawOval(new RectF(0,0,width*3/4,height*3/4),p);
        canvas.drawCircle(width/2,height/2,width/2,p);
        return bitmap;
    }

    static Bitmap makeSrc(int width,int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF66AAFF);
        canvas.drawRect(width/3,width/3,width*19/20,height*19/20,paint);
        return bitmap;
    }

    public XfermodeView(Context context) {
        super(context);
        initPaint();
        srcRectBitmap = makeSrc(width, height);
        dstCircleBitmap = makeDst(width, height);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        //拿到的srcBitmap.getWidth()和srcBitmap.getHeight()的高是，
        //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //创建这个bitmap时的宽和高
        LogUtil.e(""+srcRectBitmap.getWidth()+"     "+srcRectBitmap.getHeight());
        int layId = canvas.saveLayer(null,null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstCircleBitmap,0,0,mPaint);
//        Sa：全称为Source alpha，表示源图的Alpha通道；
//        Sc：全称为Source color，表示源图的颜色；
//        Da：全称为Destination alpha，表示目标图的Alpha通道；
//        Dc：全称为Destination color，表示目标图的颜色.
//        SRC_IN
//        [Sa * Da, Sc * Da]，在两者相交的地方绘制源图像，并且绘制的效果会受到目标图像对应地方透明度的影响
        //保持覆盖目标像素的源像素，丢弃剩余的源像素和目标像素
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcRectBitmap,width/4,height/4,mPaint);
//        canvas.drawBitmap(srcRectBitmap,0,0,mPaint);
        mPaint.setXfermode(null);//用完及时清除xfermode
        canvas.restoreToCount(layId);
        //自己理解的使用离屏缓冲的作用是：
        // 1.相当于将srcRectBitmap大小和srcRectBitmap在屏幕中的位置保存，
        //2.在根据PorterDuff.Mode.SRC_IN的规则，将原图和目标图的重合部分图形大小以及这个重复部分
        //图形的在屏幕中的位置，保存后，在将这个重复图形，按照保存的大小和位置，
        //接着在将步骤1中保存的外层区域绘制出来，只是这个外层区域是白无色的，在接着绘制步骤2中重合图形,
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
