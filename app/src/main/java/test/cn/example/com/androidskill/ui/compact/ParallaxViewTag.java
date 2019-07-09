package test.cn.example.com.androidskill.ui.compact;

/**
 * Created by xugan on 2019/7/9.
 */

public class ParallaxViewTag {
    protected int index;
    protected float xIn;
    protected float xOut;
    protected float yIn;
    protected float yOut;
    protected float alphaIn;
    protected float alphaOut;

    @Override
    public String toString() {
        return "ParallaxViewTag[index"+index+",  xIn"+xIn+",  xOut"+xOut+",  yOut"+yOut+",  alphaIn"+alphaIn+",  alphaOut"+alphaOut+"]";
    }
}
