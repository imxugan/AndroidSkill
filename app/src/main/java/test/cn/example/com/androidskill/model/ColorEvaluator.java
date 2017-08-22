package test.cn.example.com.androidskill.model;

import android.animation.TypeEvaluator;

import test.cn.example.com.util.LogUtil;

/**
 * 自定义颜色估值器
 * Created by xgxg on 2017/8/22.
 */

public class ColorEvaluator implements TypeEvaluator {
    private int currentRed = -1;
    private int currentGreen = -1;
    private int currentBlue = -1;
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startRed = Integer.parseInt(((String)startValue).substring(1,3),16);
        int startGreen = Integer.parseInt(((String)startValue).substring(3,5),16);
        int startBlue = Integer.parseInt(((String)startValue).substring(5,7),16);
        int endRed = Integer.parseInt(((String)endValue).substring(1,3),16);
        int endGreen = Integer.parseInt(((String)endValue).substring(1,3),16);
        int endBlue = Integer.parseInt(((String)endValue).substring(1,3),16);
        //初始化各个颜色值
        if(currentRed == -1){
            currentRed = startRed;
        }

        if(currentGreen == -1){
            currentGreen = startGreen;
        }

        if (currentBlue == -1){
            currentBlue = startBlue;
        }

        int redDiff =  Math.abs(endRed-startRed);
        int greenDiff =  Math.abs(endGreen-startGreen);
        int blueDiff =  Math.abs(endBlue-startBlue);
        int colorDiff = redDiff + blueDiff + greenDiff;
//        LogUtil.i("---"+redDiff+"----"+greenDiff+"----"+blueDiff+"---"+colorDiff);
        if(currentRed != endRed){
            currentRed = getCurrentColor(startRed,endRed,fraction,colorDiff,0);
        }else if(currentGreen != endGreen){
            currentGreen = getCurrentColor(startGreen,endGreen,fraction,colorDiff,redDiff);
        }else if(currentBlue != endBlue){
            currentBlue = getCurrentColor(startBlue,endBlue,fraction,colorDiff,redDiff+greenDiff);
        }

//        LogUtil.e("currentRed="+currentRed+"---currentGreen="+currentGreen+"---currentBlue="+currentBlue);
        String currentColor = "#"+getHexString(currentRed)+getHexString(currentGreen)+getHexString(currentBlue);
        LogUtil.e(currentColor);
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getHexString(int color){
        String hexString = Integer.toHexString(color);
        if(hexString.length()==1){
            hexString = "0"+hexString;
        }
        return hexString;
    }

    private int getCurrentColor(int startColor, int endColor, float fraction, int colorDiff, int offset) {
        int currentColor = -1;
        if(startColor<endColor){
            currentColor = (int)(startColor + (fraction * colorDiff - offset));
            if(currentColor>endColor){
                currentColor = endColor;
            }
        }else {
            currentColor = (int)(startColor - (fraction * colorDiff - offset));
            if(currentColor<endColor){
                currentColor = endColor;
            }
        }
        return currentColor;
    }
}
