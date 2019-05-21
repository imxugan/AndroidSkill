// automatically generated, do not modify

package test.cn.example.com.androidskill.optimize.flatbuffer;

import java.nio.*;
import java.lang.*;

public class Car extends Table {
  public static Car getRootAsCar(ByteBuffer _bb) { return getRootAsCar(_bb, new Car()); }
  public static Car getRootAsCar(ByteBuffer _bb, Car obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Car __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public int id() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String number() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer numberAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }

  public static int createCar(FlatBufferBuilder builder,
      int id,
      int number) {
    builder.startObject(2);
    Car.addNumber(builder, number);
    Car.addId(builder, id);
    return Car.endCar(builder);
  }

  public static void startCar(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(0, id, 0); }
  public static void addNumber(FlatBufferBuilder builder, int numberOffset) { builder.addOffset(1, numberOffset, 0); }
  public static int endCar(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

