package test.cn.example.com.androidskill.optimize.flatbuffer;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/20.
 */

public class FlatBufferActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flatbuffer);
        findViewById(R.id.btn_serialize).setOnClickListener(this);
        //关于flatbuffer的使用，首先要生成相应的javabean文件，然后，给这些javabean对象
        //设置相应值，保存到文件，由于保存的是二进制的数据，用文本直接打开，看到的是二进制数据，
        //不方便阅读，所以，想要看到数据，可以通过反序列化的操作，将存入到文件的数据读出来。
        //flatbuffer的使用，首先要准备4个文件
        // 1.FlatBufferBuilder.java文件
        // 2.flatc文件
        // 3.flatc.exe文件
        // 4.Table.java文件
        //步骤二：
        // 编写xx.fbs文件，这个文件是用来描述需要序列化的数据
        //类似于看到json格式的数据，编写相应的javabean的过程
        //步骤三：
        // 完成xx.fbs文件的编写后，使用flat.ext工具将xx.fbs文件生成生产
        // 一些javabean文件。具体操作是：
        //打开cmd，在命令行窗口，进入到flat.exe这个工具所在的目录，然后，将
        //xx.fbs文件，也放到这个目录下，然后执行   flat -j -b xx.fbs
        // 命令,生成一些javabean文件。
        //步骤四：
        //将生成的javabean文件复制到android工程中，并将FlatBufferBuilder.java文件
        //以及Table.java文件复制到android工程中，
        //步骤五：添加依赖   compile 'com.github.davidmoten:flatbuffers-java:1.3.0.1'
        //步骤六：给生成的javabean文件设置相应的值，
        //为了验证设置的值是否正确，可以将给javabean对象设置的值保存到文件中，然后
        //通过反序列化，重文件中读取这些数据，看是否和设置的数据一致。
        //flatbuffer的优点：
        //a.强数据类型，易于使用，跨平台，几乎语言无关
        //b.高效的内存使用和速度：FlatBuffer，使用过程，不需要额外的内存，几乎接近原始
        //  数据在内存中的大小。
        //c.灵活：数据能够前后兼容，能灵活控制数据结构。
        //d.很少的代码侵入性：使用少量的自动生成的代码即可实现。
        //f.直接读取序列化数据，FlatBuffer将数据层级结构保存在一个扁平的二进制缓存(一维数组)中，
        // 同时能够保持直接获取里面的数据结构，而不需要解析，并且还能保证数据结构变化的前后向兼容。

        //缺点：
        //a。编写xx.fbs文件，很麻烦
        //b.存在代码的侵入性
        //c. 对于数据量小的情况下，解析性能和json差不多。
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_serialize:
                //序列化
                FlatBufferBuilder builder = new FlatBufferBuilder();
                int id1 = builder.createString("奥迪");
                int car_id1 = Car.createCar(builder, 8888,id1);

                int id2 = builder.createString("奔驰");
                int car_id2 = Car.createCar(builder, 9999, id2);

                int id3 = builder.createString("BMW");
                int car_id3 = Car.createCar(builder,6666,id3);

                int[] cars = {car_id1,car_id2,car_id3};
                int carList= Basic.createCarListVector(builder, cars);
                int basic_name_id1 = builder.createString("网易新闻");
                int basic_id1 = Basic.createBasic(builder, 12, basic_name_id1, true, carList);

                int basic_name_id2 = builder.createString("腾信新闻");
                int basic_id2 = Basic.createBasic(builder, 13, basic_name_id2, false, carList);

                int basic_name_id3 = builder.createString("百度新闻");
                int basic_id3 = Basic.createBasic(builder, 11, basic_name_id3, true, carList);

                int[] basic = {basic_id1,basic_id2,basic_id3};
                int basicList = Items.createBasicVector(builder, basic);
                int items = Items.createItems(builder, 1, System.currentTimeMillis(), basicList);

                Items.finishItemsBuffer(builder,items);
                //保存数据到文件
                File sdcard = Environment.getExternalStorageDirectory();

                File file = new File(sdcard,"Items.txt");
                if(file.exists()){
                    file.delete();
                }

                ByteBuffer byteBuffer = builder.dataBuffer();
                FileChannel fileChannel = null;
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fileChannel = fos.getChannel();
                    while(byteBuffer.hasRemaining()){
                        fileChannel.write(byteBuffer);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(null !=fileChannel){
                        try {
                            fileChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(null != fos){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //反序列化
                try {
                    FileInputStream fis = new FileInputStream(file);
                    FileChannel readChannel = fis.getChannel();
                    ByteBuffer data = ByteBuffer.allocate(1024);
                    int readBytes = 0;
                    while((readBytes=readChannel.read(data))!=-1){
                        LogUtil.i("读取的数据个数：     "+readBytes);
                    }
                    //将指针互道最初的状态，准备冲byteBuffer中读取数据
                    data.flip();
                    Items rootAsItems = Items.getRootAsItems(data);
                    long itemId = rootAsItems.ItemId();
                    long timestamp = rootAsItems.timestamp();
                    LogUtil.i(itemId+"      "+timestamp);
                    int basicLength = rootAsItems.basicLength();
                    Basic basicObject;
                    for(int i=0;i<basicLength;i++){
                        basicObject = rootAsItems.basic(i);
                        int id = basicObject.id();
                        String name = basicObject.name();
                        boolean visible = basicObject.isVisible();
                        LogUtil.i(id+"      "+name+"      "+visible);
                        int carListLength = basicObject.carListLength();
//                        LogUtil.i(carListLength+"");
                        for(int j=0;j<carListLength;j++){
                            Car car = basicObject.carList(j);
                            int car_id = car.id();
                            String number = car.number();
                            LogUtil.i(car_id+"          "+number);
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
