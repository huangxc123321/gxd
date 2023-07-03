package com.gxa.jbgsw.common.utils;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @Author: huangxc
 * @Description： PeelingMachine-MP-Server
 * @Date： 2020/10/14 14:43
 */
public class DataObject implements Serializable {
    public DataObject() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, false);
    }
}
