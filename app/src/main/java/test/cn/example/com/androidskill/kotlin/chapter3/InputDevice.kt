package test.cn.example.com.androidskill.kotlin.chapter3

import test.cn.example.com.util.LogUtil

/**
 * Created by xugan on 2019/7/11.
 */
interface InputDevice{
    fun input(event:Any)
}

//接口的继承
interface USBInputDevice:InputDevice

interface BLEInputDevice:InputDevice

abstract class MouseInputDevice(val name:String):USBInputDevice{
    override fun input(event: Any) {
        LogUtil.i(""+event)
    }

    override fun toString(): String {
        return name
    }
}

class MacMouse: MouseInputDevice("苹果鼠标") {

}

class Computer {
    fun addUSBInputDevice(usbInputDevice:USBInputDevice){
        LogUtil.i("添加usb设备  ${usbInputDevice.toString()}")
    }

    fun addBLEInputDevice(bleInputDevice:BLEInputDevice){
        LogUtil.i("添加蓝牙设备 ${bleInputDevice.toString()}")
    }

    fun addInputDevice(inputDevice:InputDevice){
        when(inputDevice){
            is USBInputDevice -> addUSBInputDevice(inputDevice)
            is BLEInputDevice -> addBLEInputDevice(inputDevice)
            else -> throw IllegalArgumentException("不支持的输入设备类型")
        }
    }
}