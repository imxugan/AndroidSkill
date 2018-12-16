package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.TypeEvaluator;
import android.graphics.Color;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/16.
 */

public class HsvTypeEvaluator implements TypeEvaluator<Integer> {
    float[] startHsvValue = new float[3];
    float[] endHsvValue = new float[3];
    float[] outHsvValue = new float[3];
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        LogUtil.i("fraction=  "+fraction+"   outHsvValue[0]= "+outHsvValue[0]);
        //将ARGB颜色值转换成HSV颜色值
        Color.colorToHSV(startValue,startHsvValue);
        Color.colorToHSV(endValue,endHsvValue);
        //HSV是一种比较直观的颜色模型，所以在许多图像编辑工具中应用比较广泛，
        // 这个模型中颜色的参数分别是：色调（H, Hue），饱和度（S,Saturation），明度（V, Value）
//        outHsvValue[0] 代表了HSV颜色中的H 色调（H, Hue）色调H
//        用角度度量，取值范围为0°～360°，从红色开始按逆时针方向计算，红色为0°，绿色为120°,蓝色为240°。
//        它们的补色是：黄色为60°，青色为180°,品红为300°；

//        outHsvValue[1] 代表了HSV颜色中的S 饱和度（S,Saturation）
//        饱和度S表示颜色接近光谱色的程度。一种颜色，可以看成是某种光谱色与白色混合的结果。
//        其中光谱色所占的比例愈大，颜色接近光谱色的程度就愈高，颜色的饱和度也就愈高。饱和度高，颜色则深而艳。
//        光谱色的白光成分为0，饱和度达到最高。通常取值范围为0%～100%，值越大，颜色越饱和。

//        outHsvValue[2] 代表了HSV颜色中的V 明度（V, Value）
//        明度表示颜色明亮的程度，对于光源色，明度值与发光体的光亮度有关；对于物体色，
//        此值和物体的透射比或反射比有关。通常取值范围为0%（黑）到100%（白）。
        if((endHsvValue[0]-startHsvValue[0])>180){
            endHsvValue[0] -=360;
        }else if((endHsvValue[0]-startHsvValue[0])<-180){
            endHsvValue[0] +=360;
        }
        outHsvValue[0] = startHsvValue[0] + fraction* (endHsvValue[0]-startHsvValue[0]);
        if(outHsvValue[0]>360){
            LogUtil.i("outHsvValue[0]>360 时 "+outHsvValue[0]);
            outHsvValue[0] -=360;
        }else if(outHsvValue[0]<0){
            LogUtil.i("outHsvValue[0]<0 时 "+outHsvValue[0]);
            outHsvValue[0] +=360;
        }
        outHsvValue[1] = startHsvValue[1]+ fraction *(endHsvValue[1]-startHsvValue[1]);
        outHsvValue[2] = startHsvValue[2] + fraction*(endHsvValue[2]-startHsvValue[2]);
        //十六进制的一位就是2进制的四位，16进制>>24位，的解释：
        //startValue的十六进制的值是 0xffff0000,如果右移24位后的结果是 0xff,这个部分正好表示的是透明度
        int alpha = startValue>>24+(int)(fraction*(endValue>>24-startValue>>24));
        return Color.HSVToColor(alpha,outHsvValue);
    }
}
