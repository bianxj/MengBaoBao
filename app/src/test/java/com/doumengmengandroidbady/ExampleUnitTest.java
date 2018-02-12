package com.doumengmengandroidbady;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        String test = "0.0.1";

        String[] tt = test.split(".");
        for (String t:tt) {
            System.out.println(t);
        }

//        int first = 0x01;
//        int first_a = 0x02;
//        int second = 0x10;
//        int third = first_a|second;
//        System.out.println(first&third);
//        System.out.println(first_a&third);
//        System.out.println(second&third);
    }
}