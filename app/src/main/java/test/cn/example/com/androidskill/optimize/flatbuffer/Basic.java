// automatically generated, do not modify

package test.cn.example.com.androidskill.optimize.flatbuffer;

import java.nio.*;
import java.lang.*;

public class Basic extends Table {
  public static Basic getRootAsBasic(ByteBuffer _bb) { return getRootAsBasic(_bb, new Basic()); }
  public static Basic getRootAsBasic(ByteBuffer _bb, Basic obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Basic __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public int id() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String name() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public boolean isVisible() { int o = __offset(8); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public Car carList(int j) { return carList(new Car(), j); }
  public Car carList(Car obj, int j) { int o = __offset(10); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int carListLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }

  public static int createBasic(FlatBufferBuilder builder,
      int id,
      int name,
      boolean isVisible,
      int carList) {
    builder.startObject(4);
    Basic.addCarList(builder, carList);
    Basic.addName(builder, name);
    Basic.addId(builder, id);
    Basic.addIsVisible(builder, isVisible);
    return Basic.endBasic(builder);
  }

  public static void startBasic(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(0, id, 0); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(1, nameOffset, 0); }
  public static void addIsVisible(FlatBufferBuilder builder, boolean isVisible) { builder.addBoolean(2, isVisible, false); }
  public static void addCarList(FlatBufferBuilder builder, int carListOffset) { builder.addOffset(3, carListOffset, 0); }
  public static int createCarListVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startCarListVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endBasic(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

