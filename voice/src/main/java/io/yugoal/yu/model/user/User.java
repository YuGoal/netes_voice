package io.yugoal.yu.model.user;

import io.yugoal.yu.model.BaseModel;

/**
 * 用户数据协议
 */
public class User extends BaseModel {
  public int ecode;
  public String emsg;
  public UserContent data;
}
