package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/5.
 */

public class CustomImageView extends View {
    private final int WIDTHDEFAULTSIZE = DensityUtil.dp2Px(100);
    private final int HEIGHTDEFAULTSIZE = DensityUtil.dp2Px(100);
    private String mTitleText;
    private int mTitleTextColor;
    private float mTitleTextSize;
    private int mImageId;
    private Bitmap mBitmap;
    private int mImageScaleType;
    private Paint mPaint;
    private Rect mTextBound;
    private Rect mRect;
    private final int IMAGE_SCALE_FITXY = 0;
    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        mTitleText = a.getString(R.styleable.CustomImageView_titleText);
        mTitleTextColor = a.getColor(R.styleable.CustomImageView_titleTextColor, Color.BLACK);
        mTitleTextSize = a.getDimension(R.styleable.CustomImageView_titleTextSize, (int) DensityUtil.sp2px(context, 16));
        mImageId = a.getResourceId(R.styleable.CustomImageView_image, 0);
        LogUtil.i("mImageId="+mImageId);
        mBitmap = BitmapFactory.decodeResource(getResources(), mImageId);
        mImageScaleType = a.getInt(R.styleable.CustomImageView_imageScaleType, 0);
        LogUtil.i("mImageScaleType="+mImageScaleType);
        a.recycle();
        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTitleTextSize);
        //获取绘制字体所需要的范围
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //确定自定义view所需要的宽度，由于这个自定义的view包含了图片和文字，
        //所以，需要先计算出这两者之中，宽度更大的的一个值，在用这个宽度较大
        //的值suggestedWidth和我们自己设定的默认值WIDTHDEFAULTSIZE进行比较，
        //在将suggestedWidth和WIDTHDEFAULTSIZE这两个值的较小值设定为自定义view
        //的宽度。
        int bitmapWidth = mBitmap.getWidth();
        int textWidth = mTextBound.width();
        int suggestedWidth = Math.max(bitmapWidth,textWidth);
        int desiredWidth = Math.min((suggestedWidth + getPaddingLeft()+getPaddingRight()),widthSpecSize);

        //确定自定义view所需要的高度,思路和自定义view的宽度一致
        int bitmapHeight = mBitmap.getHeight();
        int textHeight = mTextBound.height();
        int desiredHeight = Math.min((bitmapHeight+getPaddingTop()+getPaddingBottom()+textHeight),heightSpecSize);
        if(MeasureSpec.AT_MOST == widthSpecMode &&
                MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(desiredWidth,desiredHeight);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(desiredWidth,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(widthSpecSize,desiredHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mRect.left = getPaddingLeft();
        mRect.right = getWidth()-getPaddingRight();
        mRect.top = getTop();
//        LogUtil.i("getHeight()="+getHeight()+"---getPaddingBottom()="+getPaddingBottom());
        mRect.bottom = getBottom() - getPaddingBottom();
        //绘制文字
        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        if(mTextBound.width()>getWidth()){
            //文字的宽度大于自定义view的宽度
            TextPaint textPaint = new TextPaint(mPaint);
            String text = TextUtils.ellipsize(mTitleText,textPaint,getWidth()-getPaddingLeft()-getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(text,getPaddingLeft(),getHeight()-getPaddingBottom(),mPaint);
        }else {
            //文字跨度小于自定义view的宽度，将字体居中显示
            canvas.drawText(mTitleText,getWidth()/2 - mTextBound.width()/2,getHeight()-getPaddingBottom(),mPaint);
        }
        //绘制图片
        if(mImageScaleType == IMAGE_SCALE_FITXY){
            mRect.bottom -= mTextBound.height();//减掉文字的高度后图片的bottom
            canvas.drawBitmap(mBitmap,null,mRect,mPaint);
        }else {
            LogUtil.i("getHeight()========"+getHeight());
            //让图片居中显示
            mRect.left = getWidth()/2 - mBitmap.getWidth()/2;
            mRect.right = getWidth()/2 + mBitmap.getWidth()/2;
            mRect.top = getHeight()/2 - mBitmap.getHeight()/2;
            mRect.bottom = getHeight()/2 + mBitmap.getHeight()/2;
            mRect.bottom -= mTextBound.height();//减掉文字的高度后图片的bottom
            canvas.drawBitmap(mBitmap,null,mRect,mPaint);
        }
        LogUtil.i("mRect="+mRect+"---mRect.width()="+mRect.width()+"---mRect.height()="+mRect.height());
//        LogUtil.i("mBitmap="+mBitmap+"---bitmapWidth="+mBitmap.getWidth()+"---bitmapHeight="+mBitmap.getHeight());


//        09-07 18:16:09.260 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::130::onDraw-->>mRect=Rect(20, 32 - 349, 148)---mRect.width()=329---mRect.height()=116
//        09-07 18:16:09.260 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::131::onDraw-->>mBitmap=android.graphics.Bitmap@41d8ff00---bitmapWidth=96---bitmapHeight=96
//        09-07 18:16:09.260 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::130::onDraw-->>mRect=Rect(28, 34 - 124, 102)---mRect.width()=96---mRect.height()=68
//        09-07 18:16:09.270 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::131::onDraw-->>mBitmap=android.graphics.Bitmap@41d8ba98---bitmapWidth=96---bitmapHeight=96
//        09-07 18:16:09.270 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::130::onDraw-->>mRect=Rect(20, 371 - 349, 487)---mRect.width()=329---mRect.height()=116
//        09-07 18:16:09.270 8611-8611/test.cn.example.com.androidskill I/MY_LOG: CustomImageView.java::131::onDraw-->>mBitmap=android.graphics.Bitmap@41da9618---bitmapWidth=96---bitmapHeight=96
    }
}
