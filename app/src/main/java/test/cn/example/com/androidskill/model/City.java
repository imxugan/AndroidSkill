package test.cn.example.com.androidskill.model;

import java.util.ArrayList;

/**
 * Created by xgxg on 2017/8/3.
 */

public class City {
    public House house = new House();
    public City(double price){
        house.price = price;
        houses[0] = house;
        housesList.add(house);
    }

    public House[] houses = new House[1];

    public ArrayList<House> housesList= new ArrayList<House>();
}
