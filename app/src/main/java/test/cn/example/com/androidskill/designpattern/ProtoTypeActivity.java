package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Resume;
import test.cn.example.com.androidskill.model.Resume2;

/**
 * 原型模式
 * 优点
 1、如果创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能够提高效率。

 2、可以使用深克隆保持对象的状态。

 3、原型模式提供了简化的创建结构。

 缺点
 1、在实现深克隆的时候可能需要比较复杂的代码。

 2、需要为每一个类配备一个克隆方法，而且这个克隆方法需要对类的功能进行通盘考虑，这对全新的类来说不是很难，
 但对已有的类进行改造时，
 不一定是件容易的事，必须修改其源代码，违背了“开闭原则”。
 * Created by xugan on 2018/11/12.
 */

public class ProtoTypeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        模式使用场景
//        1、如果创建新对象成本较大，我们可以利用已有的对象进行复制来获得。
//
//        2、如果系统要保存对象的状态，而对象的状态变化很小，或者对象本身占内存不大的时候，
//            也可以使用原型模式配合备忘录模式来应用。相反，如果对象的状态变化很大，或者对象占用的内存很大，
//             那么采用状态模式会比原型模式更好。
//        3、需要避免使用分层次的工厂类来创建分层次的对象，并且类的实例对象只有一个或很少的几个组合状态，
//           通过复制原型对象得到新实例可能比使用构造函数创建一个新实例更加方便。

//        模式总结
//        1、原型模式向客户隐藏了创建对象的复杂性。客户只需要知道要创建对象的类型，
//           然后通过请求就可以获得和该对象一模一样的新对象，无须知道具体的创建过程。
//
//        2、克隆分为浅克隆和深克隆两种。
//
//        3、我们虽然可以利用原型模式来获得一个新对象，但有时对象的复制可能会相当的复杂，比如深克隆。
        setContentView(R.layout.activity_proto_type);
        initView();
    }

    private void initView(){
        findViewById(R.id.btn_clone).setOnClickListener(this);
        findViewById(R.id.btn_deep_clone).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_clone:
                Resume resume = new Resume();
                resume.setPersonInfo("张三",20,"文康");
                resume.display();
                try {
                    Resume resume1 = (Resume) resume.Clone();

                    resume1.display();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                //下面的WorkExperience的地址值是一样的，表示是同一个对象，这个就是浅复制
                // /MY_LOG: Resume.java::21::display-->>name=张三    age=20
                // /MY_LOG: Resume.java::22::display-->>test.cn.example.com.androidskill.model.WorkExperience@46b03c9    文康
                // /MY_LOG: Resume.java::21::display-->>name=张三    age=20
                // /MY_LOG: Resume.java::22::display-->>test.cn.example.com.androidskill.model.WorkExperience@46b03c9    文康
                break;
            case R.id.btn_deep_clone:
                Resume2 resume2 = new Resume2();
                resume2.setPersonInfo2("李四",12,"阿里");
                resume2.display();
                try {
                    Resume2 resume21 = (Resume2) resume2.Clone();
                    resume21.display();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                //下面的两个this.workExperience的地址值不同，表示是深度复制了，this.workExperience是两个不同的对象
                // /MY_LOG: Resume2.java::21::display-->>name2=李四      age2=12
                // /MY_LOG: Resume2.java::22::display-->>this.workExperience2=test.cn.example.com.androidskill.model.WorkExperience2@32e0ace    阿里
                // /MY_LOG: Resume2.java::21::display-->>name2=李四      age2=12
                // /MY_LOG: Resume2.java::22::display-->>this.workExperience2=test.cn.example.com.androidskill.model.WorkExperience2@b398def    阿里


                break;
        }
    }
}
