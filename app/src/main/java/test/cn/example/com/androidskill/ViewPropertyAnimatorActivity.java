package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import test.cn.example.com.util.LogUtil;

/**
 * 属性动画练习
 * Created by xgxg on 2017/8/24.
 */
public class ViewPropertyAnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv,iv_by;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator);
        initView();
    }

    private void initView() {
        Button alpha = (Button) findViewById(R.id.alpha);
        alpha.setOnClickListener(this);
        Button alphaBy = (Button) findViewById(R.id.alphaBy);
        alphaBy.setOnClickListener(this);
        Button scale = (Button) findViewById(R.id.scale);
        scale.setOnClickListener(this);
        Button scaleBy = (Button) findViewById(R.id.scaleBy);
        scaleBy.setOnClickListener(this);
        Button trans = (Button) findViewById(R.id.trans);
        trans.setOnClickListener(this);
        Button transBy = (Button) findViewById(R.id.transBy);
        transBy.setOnClickListener(this);
        Button rotation = (Button) findViewById(R.id.rotation);
        rotation.setOnClickListener(this);
        Button rotationBy = (Button) findViewById(R.id.rotationBy);
        rotationBy.setOnClickListener(this);
        Button combine = (Button) findViewById(R.id.combine);
        combine.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv_by = (ImageView) findViewById(R.id.iv_by);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha:
                alpha();
                break;
            case R.id.alphaBy:
                alphaBy();
                break;
            case R.id.scale:
                scale();
                break;
            case R.id.scaleBy:
                scaleBy();
                break;
            case R.id.trans:
                trans();
                break;
            case R.id.transBy:
                transBy();
                break;
            case R.id.rotation:
                rotation();
                break;
            case R.id.rotationBy:
                rotationBy();
                break;
            case R.id.combine:
                combine();
                break;
        }
    }

    private void combine() {
        iv.animate().alpha(0.5f).scaleY(2f)
                .scaleX(2f).translationX(50f).rotation(180).setDuration(5000);
    }

    private void rotationBy(){
        iv_by.animate().rotationBy(90f);
    }

    private void rotation() {
        iv.animate().rotation(90f);
    }

    private void transBy(){
        iv_by.animate().translationXBy(50);
    }

    private void trans() {
        iv.animate().translationX(50);
    }

    private void scaleBy(){
        //这里的传入值是指的偏移量，即当前值加上这个偏移量
        iv_by.animate().scaleYBy(2f);
    }

    private void scale() {
        //这里传入的值指的是变化到指定的传入值
        //scaleY(float value)表示从当前大小缩放到指定的大小，这里传入2f
        //表示缩放到原来的2倍大，
        iv.animate().scaleY(2f);
    }

    private void alphaBy(){
        LogUtil.e("alphaBy,没有效果???????");
        //这里表示变化了0.2，也就是说从不透明变化到80%的透明度
        iv_by.animate().alphaBy(0.8f);
    }

    private void alpha() {
        //这里传入0.2，表示从1变到0.2，也就是从不透明变化到20%半透明
        LogUtil.e("alpha");
        iv.animate().alpha(0.4f);
    }

}
