package test.cn.example.com.androidskill.inter;

/**
 * 抽象工厂的目的是为了生产多种产品
 * Created by xgxg on 2017/7/17.
 */

public interface FactoryAbstract {
    //生产小轿车
    public abstract ProductCarInter createProductCar();
    //生产货车
    public abstract ProductTruckInter createProductTruck();
    //生产公交车
    public abstract ProductBusInter createProductBus();
}
