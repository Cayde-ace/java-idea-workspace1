package com.ckr.servlet;

import java.util.Random;

/**
 * @author Shadowckr
 * @create 2021-09-07 18:09
 */
public class TestGenerateRandomNumber {
    public static void main(String[] args) {
        System.out.println(GenerateRandomNumber());
    }

    // 生成随机数
    private  static String GenerateRandomNumber(){
        Random random = new Random();
        String num = random.nextInt(6666666) + "";
        System.out.println(num);
        if(num.length() == 7){
            System.out.println("我不进入for循环");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++) {
            stringBuffer.append("0");
            System.out.println(stringBuffer);
        }
        System.out.println(num);
        num = num + stringBuffer.toString();
        return num;
    }
}
