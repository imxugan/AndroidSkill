package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/11.
 */

public class CustomTextView extends View {
    private static final int[] mAttr = {R.attr.custom_text,R.attr.custom_testAttr};
    private static final int CUSTOM_ATTR_TEXT = 0;
    private static final int ACUSTOM_TESTATTR = 1;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性值的方式一
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.test);
        String text = a.getString(R.styleable.test_text);
        int test_attr = a.getInt(R.styleable.test_testAttr, -1);
        LogUtil.i("text="+text+"---test_attr="+test_attr);
        //属性是android自带的属性时，,获取android自带的属性值的方式
        String android_text = a.getString(R.styleable.test_android_text);
        LogUtil.i("属性是android自带的属性时---"+android_text);
        LogUtil.e("》》》获取属性值的方式二");
        //获取属性值的方式二
        int attrCount = attrs.getAttributeCount();
        for (int i = 0; i < attrCount; i++) {
            String attrName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            LogUtil.i("attrName="+attrName+"---attributeValue="+attributeValue);
        }
        //通过AttributeSet获取的值，如果是引用都变成了@+数字的字符串,
        //而通过而通过获取的属性，不管是不是属性值引用类型，获取的值都是不带@的数据
        //这说明TypedArray是简化我们的工作的


        LogUtil.i("属性值是引用类型时，通过Attributes,获取属性值的方式");
        //属性值是引用类型时，通过Attributes,获取属性值的方式
        //注意attrs.getAttributeResourceValue(1, -1);这个方法的第一个参数，
        //这个参数代表要获取的属性在自定义view使用的属性的序号，
        //例如：我们想要获取的是layout_height的属性值，这里，就要传入1，
        //因为这个属性值的的索引好是1，layout_width的索引好是0
//        <test.cn.example.com.androidskill.view.defineView.CustomTextView
//        android:layout_width="wrap_content"
//        android:layout_height="@dimen/dp100"
//        app:text="hello"
//        app:testAttr="23">
//        </test.cn.example.com.androidskill.view.defineView.CustomTextView>

        int heightDimensionId = attrs.getAttributeResourceValue(1, -1);
        LogUtil.e(""+getResources().getDimension(heightDimensionId));


        //获取未被styleable标签包裹的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,mAttr);
        String custom_attr_text = typedArray.getString(CUSTOM_ATTR_TEXT);
//        int custom_attr_testAttr = typedArray.getInteger(ACUSTOM_TESTATTR, -1);
        LogUtil.i("custom_attr_text="+custom_attr_text);

        //总结：
//        (1) attrs.xml里面的declare-styleable以及item，android会根据
//        其在R.java中生成一些常量方便我们使用(aapt干的)，本质上，
//        我们可以不声明declare-styleable仅仅声明所需的属性即可。

//        (2) 我们在View的构造方法中，可以通过AttributeSet去获得自定义属性的值，
//            但是比较麻烦，而TypedArray可以很方便的便于我们去获取。
//        (3) 我们在自定义View的时候，可以使用系统已经定义的属性。
        a.recycle();
    }
}
