package com.shuiyujie.test;

import java.util.HashMap;

/**
 * @author shui
 * @create 2020-04-02
 **/
public class HashMapTest {
    public static void main(String[] args) {
        HashMap <String, Integer> map = new HashMap <>();
        map.put("aa", 1);
        System.out.println(map.get("aa"));
    }
}
