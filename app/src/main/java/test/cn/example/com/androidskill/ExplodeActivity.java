package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.model.explode.factory.ExplodeParticleFacory;
import test.cn.example.com.androidskill.model.explode.factory.FallingParticleFactory;
import test.cn.example.com.androidskill.model.explode.factory.FlyawayFactory;
import test.cn.example.com.androidskill.model.explode.factory.InnerFallingParticleFactory;
import test.cn.example.com.androidskill.view.explode.ExplosionField;
import test.cn.example.com.util.LogUtil;

/**
 * 粒子爆炸特效
 * Created by xgxg on 2017/8/28.
 */
public class ExplodeActivity extends AppCompatActivity {
    private ImageView iv;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explode);
        initView();
        ExplosionField explosionField = new ExplosionField(this,new FallingParticleFactory());
        explosionField.addListener(iv);
        explosionField.addListener(ll);
        ExplosionField explosionFieldUp = new ExplosionField(this,new ExplodeParticleFacory());
        explosionFieldUp.addListener(findViewById(R.id.ll_2));
        explosionFieldUp.addListener(findViewById(R.id.text2));

        ExplosionField explosionFieldFlyaway = new ExplosionField(this,new FlyawayFactory());
        explosionFieldFlyaway.addListener(findViewById(R.id.text3));
        explosionField.addListener(findViewById(R.id.ll_3));

        ExplosionField explosionFieldInnerFalling = new ExplosionField(this,new InnerFallingParticleFactory());
        explosionFieldInnerFalling.addListener(findViewById(R.id.text4));
        explosionFieldInnerFalling.addListener(findViewById(R.id.ll_4));
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        ll = (LinearLayout)findViewById(R.id.ll);
    }

    @Override
    protected void onStart() {
        super.onStart();
        iv.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(iv.getWidth()+"");
            }
        });


    }
}
