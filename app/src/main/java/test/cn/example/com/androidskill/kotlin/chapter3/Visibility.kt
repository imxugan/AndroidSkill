package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/11.
 */
class House

class Flower

open class Garden{
    private val house:House = House()
    protected val bigHouse = House()
    val flower = Flower()

}

class ImperialPalace: Garden() {
    val houses = arrayOf(House(),bigHouse)  //由于是继承自Gadren，所以可以直接使用bigHouse
    val flowers = arrayOf(flower,flower)   //由于继承自Garde,可以直接使用flower
}