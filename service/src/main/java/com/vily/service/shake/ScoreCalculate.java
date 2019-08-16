package com.vily.service.shake;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculate {

    static Map<Integer, Integer> map;

    private static Map<Integer, Integer> getMap() {
        map = new HashMap<>();
        map.put(0,  20);
        map.put(1,  30);
        map.put(2,  40);
        map.put(3,  50);
        map.put(4,  60);
        map.put(5,  70);
        map.put(6,  80);
        map.put(7,  90);
        map.put(8,  90);
        map.put(9,  80);
        map.put(10, 80);
        map.put(11, 70);
        map.put(12, 71);
        map.put(24, 72);
        return map;
    }

    /**
     * 18-25岁大龄青少年,26-70岁 适宜睡眠时间7-8小时
     * @param sleep_time 睡眠时间，单位小时
     * @return
     */
    public static int calculate(double sleep_time) {
//        double l = 21-6*Math.pow(age, 0.3)+age/10;
        int score = 8;
        if(sleep_time <= 0) {
            return score;
        }
        if(map == null) {
            map = getMap();
        }

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){

            if(sleep_time < entry.getKey()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
                score = (int) (entry.getValue()-(entry.getKey()-sleep_time)*10);
                break;
            }
        }
        return score;
    }

    public static void main(String s[]) {
        int score = calculate(3.1);
        System.out.println(score);
    }
}
