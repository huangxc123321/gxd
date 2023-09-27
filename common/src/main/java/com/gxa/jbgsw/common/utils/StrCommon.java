package com.gxa.jbgsw.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * @Author Mr. huang
 * @Date 2023/9/23 0023 21:55
 * @Version 2.0
 */
public class StrCommon {

    public static String  clear(String str){
        str = StrUtil.removeAll(str,";");
        str = StrUtil.removeAll(str,"；");
        str = StrUtil.removeAll(str,"，");
        str = StrUtil.removeAll(str,",");

        return str;
    }

    public static String getResult(String result){
        result = StrUtil.removeAll(result,"技术");
        result = StrUtil.removeAll(result,"制造业");
        result = StrUtil.removeAll(result,";");
        result = StrUtil.removeAll(result,"；");
        result = StrUtil.removeAll(result,",");
        result = StrUtil.removeAll(result,",");
        result = StrUtil.removeAll(result,"（");
        result = StrUtil.removeAll(result,"）");
        // 去掉重复的
        result = countDupChars(result);

        return result;
    }

    public static String getAddress(String result){
        result = StrUtil.removeAll(result,"地区");
        result = StrUtil.removeAll(result,"市");
        result = StrUtil.removeAll(result,"省");

        return result;
    }


    public static String countDupChars(String str){

        //创建一个HashMap对象
        Map<Character, Character> map = new HashMap<Character, Character>();
        List<Character> list = new ArrayList<>();

        //将字符串转换为char数组
        char[] chars = str.toCharArray();

        /* logic: 将每个字符插入到map中，map中的每个元素是[key,value]的组合，
         * key记录字符，value记录这个字符出现的次数。
         * 如果map中已经存在ch，则修改该字符出现的次数（原来次数+1）。
         * 如果map中还没有ch,则将ch插入到map中，key为ch的值，value为1*/
/*
        for(Character ch:chars){
            map.put(ch, ch);
        }
*/

        for(int i=0; i<chars.length; i++){
            if(!list.contains(chars[i])){
                list.add(chars[i]);
            }
        }

        StringBuffer sb = new StringBuffer();
/*        Set<Character> keys = map.keySet();
        keys.forEach(s->{
            sb.append(s);
        });*/

        for(int n=0; n<list.size(); n++){
            sb.append(list.get(n));
        }

        return sb.toString();

    }


    public static void main(String[] args) {
        String str = "新能源与节能,新型高效能量转换与储存技术,新型动力电池（组）与储能电池技术";
        String val = countDupChars(str);

        System.out.println(val);
    }


}
