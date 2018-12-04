package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/2.
 */

public class CanvasAssistantView4 extends View {
    private Paint mPaint;
    private Bitmap bitmap;

    public CanvasAssistantView4(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        float x = 0f;
        float y = 0f;
        float[] matrixValues = new float[9];
        canvas.getMatrix().getValues(matrixValues);
        float mscale_x = matrixValues[Matrix.MSCALE_X];
        float mtrans_x = matrixValues[Matrix.MTRANS_X];
        float mscale_y = matrixValues[Matrix.MSCALE_Y];
        float mtrans_y = matrixValues[Matrix.MTRANS_Y];

        LogUtil.i("mscale_x="+mscale_x+"  mtrans_x="+mtrans_x+"  mscale_y= "+mscale_y+"  mtrans_y="+mtrans_y);
        x = x * mscale_x + 1 * mtrans_x;
        y = y * mscale_y + 1 * mtrans_y;
        LogUtil.i("还未画任何图形时的  x= "+x+"   y= "+y);
        // 变化后的点
        LogUtil.i("bitmap.getWidth()= "+bitmap.getWidth()+"    bitmap.getHeight()="+bitmap.getHeight());
        //打印当前画布的位置
        printCanvasLocation(canvas);

        int[] location2 = new int[2];
        this.getLocationOnScreen(location2);
        //由于当前view在viewpager中，并且是viewpager中的第四个页面，所以，
        //获取的location2[0]的坐标不是期望的0，但是每次进入
        //HenCoderPracticeDrawFourActivity页面时，获取的location2[0]的坐标都是不相同的，很奇葩
        LogUtil.i("location2 = " + Arrays.toString(location2));


        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap,0,0,mPaint);
        Matrix matrix = new Matrix();

        matrix.getValues(matrixValues);
        // 变化的倍数
        mscale_x = matrixValues[Matrix.MSCALE_X];
        mtrans_x = matrixValues[Matrix.MTRANS_X];
        mscale_y = matrixValues[Matrix.MSCALE_Y];
        mtrans_y = matrixValues[Matrix.MTRANS_Y];
        LogUtil.i("mscale_x="+mscale_x+"  mtrans_x="+mtrans_x+"  mscale_y= "+mscale_y+"  mtrans_y="+mtrans_y);
        x = x * mscale_x + 1 * mtrans_x;
        y = y * mscale_y + 1 * mtrans_y;
        LogUtil.i("绘制完一个bitmap后的    x= "+x+"   y= "+y);
        matrix.setTranslate(bitmapWidth,0);
        matrix.getValues(matrixValues);
        mscale_x = matrixValues[Matrix.MSCALE_X];
        mtrans_x = matrixValues[Matrix.MTRANS_X];
        mscale_y = matrixValues[Matrix.MSCALE_Y];
        mtrans_y = matrixValues[Matrix.MTRANS_Y];
        LogUtil.i("mscale_x="+mscale_x+"  mtrans_x="+mtrans_x+"  mscale_y= "+mscale_y+"  mtrans_y="+mtrans_y);
        // 变化后的点
        x = x * mscale_x + 1 * mtrans_x;
        y = y * mscale_y + 1 * mtrans_y;
        LogUtil.i("matrix.setTranslate后的    x= "+x+"   y= "+y);
//        LogUtil.i("matrix.toString()= "+matrix.toString());
        canvas.save();
        canvas.setMatrix(matrix);

//        关于Canvas.getMatrix()的一些问题收集
//        https://blog.csdn.net/skyunlin/article/details/52080436
//        如果想使用matrix来做常见变化，在利用canvas.setMatrix(matrix)设置打canvas上去，则最好关闭硬件加速，否则，会看到的效果会很奇葩的。坑了4个半小时
        canvas.drawBitmap(bitmap,0,0,mPaint);
        LogUtil.e("canvas.getWidth()="+canvas.getWidth()+"    canvas.getHeight()="+canvas.getHeight());

        canvas.restore();
        canvas.save();
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(2f,1f);
        Matrix translateMatrix = new Matrix();
        translateMatrix.setTranslate(20,bitmapHeight);
        matrix.postConcat(translateMatrix);
        //postConcat  translateMatrix后的  Matrix{[1.0, 0.0, 283.0][0.0, 1.0, 195.0][0.0, 0.0, 1.0]}
        LogUtil.e("postConcat  translateMatrix后的  "+matrix.toString());
        matrix.postConcat(scaleMatrix);
        //postConcat  scaleMatrix后的  Matrix{[2.0, 0.0, 566.0][0.0, 1.0, 195.0][0.0, 0.0, 1.0]}
        LogUtil.e("postConcat  scaleMatrix后的  "+matrix.toString());
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.restore();
        canvas.save();
        Matrix matrix1 = new Matrix();
        matrix1.reset();
        float left = bitmapWidth;
        float top = bitmapHeight;
        float right = bitmapWidth*2;
        float bottom = bitmapHeight*2;
        float pointsSrc[] = {left, top, right, top, left, bottom, right, bottom};
        float pointsDst[] = {left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};

        matrix1.setPolyToPoly(pointsSrc,0,pointsDst,0,4);
        //matrix1.setPolyToPoly后的  Matrix{[0.22049858, 0.5850427, 80.9256][-0.7393609, 1.5373745, 139.66388][-0.0019718239, 0.0015236624, 1.2214755]}
        LogUtil.e("matrix1.setPolyToPoly后的  "+matrix1.toString());
        canvas.concat(matrix1);
        canvas.drawBitmap(bitmap,0,bitmapHeight,mPaint);
        canvas.drawBitmap(bitmap,bitmapWidth,bitmapHeight,mPaint);
        canvas.drawBitmap(bitmap,bitmapWidth,bitmapHeight,mPaint);
        canvas.drawBitmap(bitmap,bitmapWidth,bitmapHeight*2,mPaint);
        canvas.drawBitmap(bitmap,bitmapWidth,bitmapHeight*3,mPaint);


    }

    private void printCanvasLocation(Canvas canvas){
        Matrix matrix = canvas.getMatrix();
        LogUtil.i("matrix.toString()= "+ matrix.toString());
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}
