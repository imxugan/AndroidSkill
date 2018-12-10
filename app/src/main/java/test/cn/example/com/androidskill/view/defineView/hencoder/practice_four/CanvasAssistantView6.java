package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/4.
 */

public class CanvasAssistantView6 extends View {
    private Paint mPaint;
    private Bitmap bitmap;

    public CanvasAssistantView6(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView6(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] pts = new float[2];
        //关闭硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        float centerX = bitmap.getWidth()/2;
        float centerY = bitmap.getHeight()/2;
        canvas.save();
        Camera camera = new Camera();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LogUtil.i("camera.getLocationZ()="+camera.getLocationZ());
        }
        camera.save();
        camera.rotateX(30);//将camera沿着x，x的旋转方向是，将左手的大拇指指向正方向，四个手子的方法就是x的旋转方向
        canvas.translate(centerX,centerY);//将旋转后的投影移回原来的位置
        camera.applyToCanvas(canvas);//将旋转投影到canvas
        canvas.translate(-centerX,-centerY);//先将要绘制的内容的中心点移到原点
        camera.restore();
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.restore();
        canvas.drawBitmap(bitmap,bitmap.getWidth(),0,mPaint);
        canvas.save();
        camera.save();
        camera.translate(-130, 50, -1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LogUtil.i("camera.translate 后 camera.getLocationZ()="+camera.getLocationZ());
        }
        camera.applyToCanvas(canvas);

        LogUtil.i("pts[0]=  "+pts[0]+"     pts[1]= "+pts[1]);
        canvas.drawBitmap(bitmap,bitmap.getWidth()*3,0,mPaint);

        getMatrix().mapPoints(pts);
        LogUtil.i("pts[0]=  "+pts[0]+"     pts[1]= "+pts[1]);
        camera.restore();
        canvas.restore();
//        setBackgroundColor(Color.parseColor("#3f51b5"));
        LogUtil.i("onDraw为什么执行两次??????");
        canvas.save();
        testCamerTranslate(canvas,Color.RED,10, -150, -280);
        canvas.restore();
        canvas.save();
        testCamerTranslate(canvas,Color.YELLOW,200, -150, -180);
        canvas.restore();
        canvas.save();
        testCamerTranslate(canvas,Color.GREEN,400, -150, -80);
        canvas.restore();
        mPaint.setTextSize(20);
        canvas.drawText("onDraw方法为什么执行两次？",10,200,mPaint);
        canvas.drawText("onDraw方法为什么执行两次？",19,250,mPaint);
        canvas.drawLine(20,400,20,700,mPaint);
        canvas.drawLine(200,400,200,700,mPaint);
        canvas.drawLine(400,400,400,700,mPaint);
        canvas.save();
        canvas.drawBitmap(bitmap,10,800,mPaint);
        canvas.restore();
        canvas.save();
        Camera camera1 = new Camera();
        camera1.save();
        camera1.setLocation(0,0,180.0f);
//        camera1.rotate(0,30,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LogUtil.i("camera1.getLocationZ()="+camera1.getLocationZ());
        }
        camera1.applyToCanvas(canvas);
        camera1.restore();
        canvas.drawBitmap(bitmap,10+bitmap.getWidth()+20,800,mPaint);
        canvas.restore();

    }

    private void testCamerTranslate(Canvas canvas,int color,float x,float y,float z){
        Camera camera = new Camera();
        Matrix matrix = new Matrix();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        matrix.reset();
        camera.save();
        camera.translate(x, y, z);
        camera.getMatrix(matrix);
        float[] pts = new float[2];
        matrix.mapPoints(pts);
        LogUtil.e("pts[0]="+pts[0]+" pts[1]=  "+pts[1]);
        LogUtil.i(""+matrix.toString());
        camera.restore();
        canvas.concat(matrix);

        canvas.drawCircle(60, 60, 60, paint);
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}
