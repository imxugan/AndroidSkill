package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/8.
 */

public class ParallaxLayoutInflater extends LayoutInflater {
    private final ParallaxFragment mFragment;

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        this.mFragment = fragment;
        setFactory2(new ParallaxFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this,newContext,mFragment);
    }

    class ParallaxFactory implements LayoutInflater.Factory2{

        private LayoutInflater inflater;
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };
        public ParallaxFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        //自定义,视图创建的过程
        @Override
        public View onCreateView(String name, Context context,
                                 AttributeSet attrs) {
            return null;
        }

        private void setViewTag(View view, Context context, AttributeSet attrs) {
            //所有自定义的属性
            int[] attrIds = {
                    R.attr.a_in,
                    R.attr.a_out,
                    R.attr.x_in,
                    R.attr.x_out,
                    R.attr.y_in,
                    R.attr.y_out};

            //获取
            TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
            if (a != null && a.length() > 0) {
                //获取自定义属性的值
                ParallaxViewTag tag = new ParallaxViewTag();
                tag.alphaIn = a.getFloat(0, 0f);
                tag.alphaOut = a.getFloat(1, 0f);
                tag.xIn = a.getFloat(2, 0f);
                tag.xOut = a.getFloat(3, 0f);
                tag.yIn = a.getFloat(4, 0f);
                tag.yOut = a.getFloat(5, 0f);

                //index
                view.setTag(R.id.parallax_view_tag,tag);
            }

            a.recycle();

        }

        private View createViewOrFailQuietly(String name, String prefix, Context context,
                                             AttributeSet attrs) {
            try {
                //通过系统的inflater创建视图，读取系统的属性
                return inflater.createView(name, prefix, attrs);
            } catch (Exception e) {
                return null;
            }
        }

        private View createViewOrFailQuietly(String name, Context context,
                                             AttributeSet attrs) {
            //1.自定义控件标签名称带点，所以创建时不需要前缀
            if (name.contains(".")) {
                createViewOrFailQuietly(name, null, context, attrs);
            }
            //2.系统视图需要加上前缀
            for (String prefix : sClassPrefix) {
                View view = createViewOrFailQuietly(name, prefix, context, attrs);
                if (view != null) {
                    return view;
                }
            }

            return null;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            if (view == null) {
                view = createViewOrFailQuietly(name,context,attrs);
            }

            //实例化完成
            if (view != null) {
                //获取自定义属性，通过标签关联到视图上
                setViewTag(view,context,attrs);
                mFragment.getViews().add(view);
                LogUtil.d( "view:"+view);
            }

            return view;
        }
    }
}
