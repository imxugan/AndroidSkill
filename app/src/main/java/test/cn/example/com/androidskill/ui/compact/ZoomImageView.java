package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class ZoomImageView extends View {

    private final Bitmap bitmap;
    private final int fraction = 3;
    private final int radius = 100;//放大镜的半径
    private final ShapeDrawable shapeDrawable;
    private final Matrix matrix;

    public ZoomImageView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZoomImageView);
        int resourceId = typedArray.getResourceId(R.styleable.ZoomImageView_src, R.drawable.splash12);
        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        Bitmap scaleBitmap = bitmap;
        scaleBitmap = Bitmap.createScaledBitmap(scaleBitmap,bitmap.getWidth()*fraction,bitmap.getHeight()*fraction,true);
        BitmapShader bitmapShader = new BitmapShader(scaleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);

        //切出矩形区域，用于绘制内切圆
        shapeDrawable.setBounds(0,0,radius*2,radius*2);
        matrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
        shapeDrawable.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int eventX = (int) event.getX();
                int eventY = (int) event.getY();
                LogUtil.i((radius-fraction*eventX)+"     "+(radius-fraction*eventY));
                matrix.setTranslate(radius-fraction*eventX,radius-fraction*eventY);
                shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
                shapeDrawable.setBounds(eventX-radius,eventY-radius,eventX+radius,eventY+radius);
                postInvalidate();
                break;
        }

        return super.onTouchEvent(event);
    }
}
