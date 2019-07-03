package test.cn.example.com.androidskill.java_about;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.annotation.AnnotationTest;
import test.cn.example.com.androidskill.annotation.MyBindViewAnnotation;
import test.cn.example.com.androidskill.annotation.TestMethodAnnotation;
import test.cn.example.com.androidskill.annotation.Todo;
import test.cn.example.com.androidskill.model.Boss;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * 注解使用演示
 */
public class AnnotationActivity extends AppCompatActivity implements View.OnClickListener {

    @MyBindViewAnnotation(viewId = R.id.btn_1)
    private Button btn_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        findViewById(R.id.btn_start).setOnClickListener(this);
        Class businessLogicClass = AnnotationTest.class;
        for(Method method : businessLogicClass.getMethods()) {
            Todo todoAnnotation = (Todo)method.getAnnotation(Todo.class);
            if(todoAnnotation != null) {
                LogUtil.i(" Method Name : " + method.getName());
                LogUtil.i(" Author : " + todoAnnotation.author());
                LogUtil.i(" Priority : " + todoAnnotation.priority());
                LogUtil.i(" Status : " + todoAnnotation.stauts());
            }
        }
        try {
//            getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
//            getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段
            Field field_btn_1 = AnnotationActivity.class.getDeclaredField("btn_1");
            field_btn_1.setAccessible(true);
            MyBindViewAnnotation myBindViewAnnotation = field_btn_1.getAnnotation(MyBindViewAnnotation.class);
            if(btn_1 == null){
                LogUtil.i("id   "+myBindViewAnnotation.viewId());
                btn_1 = (Button) findViewById(myBindViewAnnotation.viewId());
                btn_1.setOnClickListener(this);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Week monday = Week.MONDAY;
        LogUtil.i("name= "+monday.name()+"    ordinal=  "+monday.ordinal());
        Color red = Color.RED;
        LogUtil.i(""+red+"   "+red.getColorName());
        LogUtil.i("colorName=  "+red.colorName+"     ordinal=  "+ red.ordinal());
        Week[] weeks = new Week[]{Week.MONDAY,Week.TUESDAY,Week.WEDNESDAY,Week.THURSDAY,
                Week.FRIDAY,Week.SATURDAY, Week.SUNDAY};
        LogUtil.e("枚举的ordinal()方法和name()方法演示");
        for (int i = 0; i < weeks.length; i++) {
            LogUtil.i("weeks["+i+"].ordinal()="+weeks[i].ordinal()+"     weeks["+i+"].name()="+weeks[i].name());
        }
        LogUtil.e("枚举的toString()方法演示");
        LogUtil.i("week[0].toString()="+weeks[0].toString());

        LogUtil.e("枚举的equals方法演示");
        LogUtil.i("weeks[0].equals(weeks[1])="+weeks[0].equals(weeks[1]));
        LogUtil.i("weeks[0].equals(Week.MONDAY)="+weeks[0].equals(Week.MONDAY));
        LogUtil.e("枚举的compareTo()方法演示");
        LogUtil.i("Week.MONDAY.compareTo(Week.FRIDAY)="+Week.MONDAY.compareTo(Week.FRIDAY));
        LogUtil.i("Week.FRIDAY.compareTo(Week.MONDAY)="+Week.FRIDAY.compareTo(Week.MONDAY));
        LogUtil.i("Week.MONDAY.compareTo(Week.TUESDAY)="+Week.MONDAY.compareTo(Week.TUESDAY));
        LogUtil.e("枚举的getDeclaringClass()方法演示");
        LogUtil.i("weeks[0].getDeclaringClass()= "+weeks[0].getDeclaringClass());
        LogUtil.e("valueOf(String name)演示");
        LogUtil.i("Week.valueOf('MONDAY')="+Week.valueOf("MONDAY"));
        LogUtil.i("Enum.valueOf(weeks[0].getDeclaringClass(),weeks[0].name())="+Enum.valueOf(weeks[0].getDeclaringClass(),weeks[0].name()));
        LogUtil.i("valueOf(Class clazz,String name)方法演示");
        LogUtil.i("Week.valueOf(weeks[0].getDeclaringClass(),weeks[0].name())="+Week.valueOf(weeks[0].getDeclaringClass(),weeks[0].name()));
        LogUtil.e("values()方法演示");
        Week[] values = Week.values();
        LogUtil.i("Arrays.toString(values)="+ Arrays.toString(values));
        Week[] values1 = Week.MONDAY.values();
        LogUtil.i("Arrays.toString(values1)="+Arrays.toString(values1));
        Enum euum = Week.MONDAY;
//        euum.values(); //没有values()方法
        LogUtil.e("getDeclaringClass()方法演示");
        Class<Week> declaringClass = Week.MONDAY.getDeclaringClass();
        LogUtil.e("isEnum()方法演示,此方法是Class 类型的对象的方法");
        LogUtil.i("declaringClass.isEnum()="+declaringClass.isEnum());
        LogUtil.e("getEnumConstants()方法演示，此方法是Class 类型的对象的方法");
        Week[] enumConstants = declaringClass.getEnumConstants();
        LogUtil.i(""+enumConstants);
        if(null != enumConstants){
            LogUtil.i("Arrays.toString(enumConstants)="+Arrays.toString(enumConstants));
        }
        LogUtil.e("------------------枚举的进阶用法--------------------'");
        LogUtil.e("向枚举中添加方法和自定义的构造函数");
        Season.main(null);
        String cn_desc = Season.SPRING.getCn_desc();
        LogUtil.i(""+cn_desc);

        LogUtil.e("覆写Enum类中的toString()方法的枚举");
        for (Sex sex:Sex.values()){
            LogUtil.i("sex.name()="+sex.name()+"     sex.gender="+sex.gender+"    sex.toString()="+sex.toString());
        }

        LogUtil.e("在enum类中定义抽象方法");
        MobileSystem.ANDRIOD.getSystemInfo();

        LogUtil.e("让enum类实现接口");
        Fruit.APPLE.price();
        Fruit.JUICE.price();
        Fruit.ORANGE.price();

        Food appetizerFood = Food.Appetizer.SPRING_ROLLS;
        Food mainCourse = Food.MainCourse.BURRITO;
        Food dessert = Food.Dessert.GELATO;
        Food coffee = Food.Coffee.LATTE;
        LogUtil.i(appetizerFood.toString()+"       "+appetizerFood.getClass().getEnumConstants()[2]);
        LogUtil.i(mainCourse.toString());
        LogUtil.i(dessert.toString());
        LogUtil.i(coffee.toString());
        Food[] enumConstants1 = appetizerFood.getClass().getEnumConstants();
        LogUtil.i(""+Arrays.toString(enumConstants1));

        Meal appetizer = Meal.APPETIZER;
        LogUtil.i(""+appetizer.foodValues);
        LogUtil.i(""+Arrays.toString(appetizer.foodValues));
        LogUtil.i(""+Arrays.toString(Meal.MAINCOURSE.foodValues));
        LogUtil.i(""+Arrays.toString(Meal.DESSERT.foodValues));
        LogUtil.i(""+Arrays.toString(Meal.COFFEE.foodValues));

        printName(Color.RED);

        LogUtil.e("枚举的单例演示");
        LogUtil.i(""+SingleInstance.INSTANCE.getBoss());
        LogUtil.i(""+SingleInstance.INSTANCE.getBoss());

        LogUtil.i("EnumMap的使用演示");
        List<Clothes> list = new ArrayList<Clothes>();
        list.add(new Clothes("c001",Color.RED));
        list.add(new Clothes("c002",Color.GREEN));
        list.add(new Clothes("c003",Color.GREEN));
        list.add(new Clothes("c004",Color.RED));
        list.add(new Clothes("c005",Color.YELLOW));
        list.add(new Clothes("c006",Color.RED));
        list.add(new Clothes("c007",Color.YELLOW));
        Map<Color,Integer> enumMap = new EnumMap(Color.class);
        for (int i = 0; i < list.size(); i++) {
            Integer integer = enumMap.get(list.get(i).color);
            if(null != integer){
                enumMap.put(list.get(i).color,integer+1);
            }else {
                enumMap.put(list.get(i).color,1);
            }
        }

        LogUtil.i(enumMap.toString());
        LogUtil.e("enumSet使用演示");
        //EnumSet 使用还未完成
        EnumSet enumSet = EnumSet.noneOf(Color.class);
        enumSet.add(Color.RED);
        enumSet.add(Color.GREEN);
        enumSet.add(Color.YELLOW);
        enumSet.add(Color.GREEN);
        LogUtil.i(""+enumSet.toString());
        LogUtil.e("使用allOf方法初始化EnumSet");
        EnumSet enumSet_allOf = EnumSet.allOf(Color.class);
        LogUtil.i(""+enumSet_allOf.toString());

        LogUtil.e("EnumSet.range()方法使用演示");
        //range方法中的参数是start 和 end,就是说指定一个范围，从枚举的哪个实例开始，到哪个实例结束，
        //如果将Color.YELLOW写在前面，Color.RED写在后面，则会报错 end before start的错误
        EnumSet<Color> enumSet_range = EnumSet.range(Color.RED,Color.GREEN);
        LogUtil.i(enumSet_range.toString());
        LogUtil.e("complementOf方法，取补集");
        EnumSet enumSet_complementOf = EnumSet.complementOf(enumSet_range);
        LogUtil.i(""+enumSet_complementOf.toString());//[YELLOW]

        LogUtil.e("enumSetOf方法使用演示");
        EnumSet<Color> enumSetOf = EnumSet.of(Color.GREEN);
        LogUtil.i(""+enumSetOf.toString());

        LogUtil.e("enumSetCopy使用演示");
        EnumSet enumSetCopy = EnumSet.copyOf(enumSet_allOf);
        LogUtil.i(""+enumSetCopy.toString());

        List<Color> list1 = new ArrayList<>();
        list1.add(Color.GREEN);
        list1.add(Color.RED);
        list1.add(Color.GREEN);
        list1.add(Color.YELLOW);
        LogUtil.i(""+list1.toString());
        enumSetCopy = EnumSet.copyOf(list1);
        LogUtil.i(""+enumSetCopy.toString());
    }

    private class Clothes{
        public final String name;
        public Color color;

        public Clothes(String name, Color color){
            this.name = name;
            this.color = color;
        }
    }

    private enum SingleInstance{
        INSTANCE;
        private Boss boss;
        SingleInstance(){
            this.boss = new Boss("张三");
        }

        public Boss getBoss() {
            return boss;
        }
    }

    private void printName(Color color){
        switch (color){
            case RED://无需使用Color进行引用
                LogUtil.i("红色");
                break;
            case GREEN:
                LogUtil.i("绿色");
                break;
        }
    }

    public enum Meal{
        APPETIZER(Food.Appetizer.class),MAINCOURSE(Food.MainCourse.class),
        DESSERT(Food.Dessert.class),COFFEE(Food.Coffee.class);

        private final Food[] foodValues;

        Meal(Class<? extends Food> kind){
            foodValues = kind.getEnumConstants();
        }
    }

    interface Food{
        enum Appetizer implements Food{
            SALAD("色拉"),SOUP("汤"),SPRING_ROLLS("春卷");
            private String appetizerFoodName;
            Appetizer(String appetizerFoodName){
                this.appetizerFoodName = appetizerFoodName;
            }

            public String getAppetizerFoodName() {
                return appetizerFoodName;
            }

            @Override
            public String toString() {
                return appetizerFoodName;
            }
        }

        enum MainCourse implements Food{
            LASAGNE("烤宽面条"),BURRITO("墨西哥玉米煎饼"),PAD_THAI("泰式炒面"),
            LENTILS("小扁豆"),HUMMOUS("鹰嘴豆芝麻酱"),VINDALOO("咖喱肉");
            private String mainCourseFoodName;
            MainCourse(String mainCourseFoodName){
                this.mainCourseFoodName = mainCourseFoodName;
            }

            public String getMainCourseFoodName() {
                return mainCourseFoodName;
            }
        }

        enum Dessert implements Food{
            TIRAMISU("提拉米苏"),GELATO("冰淇淋"),BLACK_FOREST_CAKE("魔法森林蛋糕"),
            FRUIT("水果"),CREMA_CARAMEL("卡仕达布丁");
            private String dessertFoodName;
            Dessert(String dessertFoodName){
                this.dessertFoodName = dessertFoodName;
            }

            public String getDessertFoodName() {
                return dessertFoodName;
            }
        }

        enum Coffee implements Food{
            BLACK_COFFEE("黑咖啡"),DECAF_COFFEE("无咖啡因咖啡"),ESPRESSO("浓咖啡"),
            LATTE("拿铁"),CAPPUCCINO("卡布奇诺"),TEA("茶"),HERB_TEA("凉茶");
            private String coffeeName;
            Coffee(String coffeeName){
                this.coffeeName = coffeeName;
            }

            public String getCoffeeName() {
                return coffeeName;
            }
        }

    }

    private enum Fruit implements Sale{
        APPLE,JUICE,ORANGE;

        @Override
        public void price() {
            if("APPLE".equals(name())){
                LogUtil.i(name()+"  8元/斤");
            }else if("JUICE".equals(name())){
                LogUtil.i(name()+"  5元/斤");
            }else {
                LogUtil.i(name()+"  1元/斤，促销");
            }

        }

    }

    interface Sale{
        void price();
    }

    private enum MobileSystem{
        ANDRIOD {
            @Override
            void getSystemInfo() {
                LogUtil.i("安卓系统");
            }
        },MAC{
            @Override
            void getSystemInfo(){
                LogUtil.i("苹果系统");
            }
        },WINPHONE{
            @Override
            void getSystemInfo(){
                LogUtil.i("微软的移动操作系统");
            }
        };

        abstract void getSystemInfo();
    }

    private enum Sex{
        MAN("男"),WOMEN("女");
        private String gender;
        Sex(String gender){
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }
    }

    private enum Season{
        SPRING("春"),SUMMER("夏"),AUTUMN("秋"),WINTER("冬");
        String cn_desc;
        Season(String cn_desc){
            this.cn_desc = cn_desc;
        }

        public String getCn_desc() {
            return cn_desc;
        }

        public static void main(String[] args){
            for (Season season:Season.values()){
                LogUtil.i("ordinal()="+season.ordinal()+"    name()="+season.name());
            }
        }
    }

    private enum  Week{
        MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY
    }

    private enum Color{
        RED("红色"),GREEN("绿色"),YELLOW("黄色");
        private String colorName;
        Color(String colorName){
            this.colorName = colorName;
        }

        private String getColorName() {
            return colorName;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @TestMethodAnnotation(id = 1)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                try {
                    Method onClick = AnnotationActivity.class.getMethod("onClick", View.class);
                    TestMethodAnnotation annotation = onClick.getAnnotation(TestMethodAnnotation.class);
                    ToastUtils.shortToast(AnnotationActivity.this,"获取到注解到onClick方法上的注解TestMethodAnnotation中的属性id的值是  "+annotation.id());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_1:
                ToastUtils.shortToast(AnnotationActivity.this,"通过注解获取到btn_1这个button控件的id");
                break;
        }
    }
}
