package com.bigbass.recex.recipes;

public enum VoltageLevel {
  ULV, LV, MV, HV, EV, IV, LUV, ZPM, UV, UHV, UEV, UIV;

  public static VoltageLevel getFromEuPerTick(int euPerTick) {
    // EU = 8 * 4^T
    int index = (int)(Math.log(euPerTick / 8d) / Math.log(4));
    return VoltageLevel.values()[Math.max(index, 0)];
  }
}
