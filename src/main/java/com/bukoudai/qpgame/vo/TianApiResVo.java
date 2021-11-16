package com.bukoudai.qpgame.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TianApiResVo<T> implements Serializable {
  /**
   * 序列化ID
   */
  private static final long serialVersionUID = -5809782578272943239L;
  Integer code;
  String msg;
  List<T> newslist;
}
