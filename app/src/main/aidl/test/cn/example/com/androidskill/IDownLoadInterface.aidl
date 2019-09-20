// IDownLoadInterface.aidl
package test.cn.example.com.androidskill;

// Declare any non-default types here with import statements

interface IDownLoadInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void downLoad(String url);
}
