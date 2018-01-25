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
        int first = 0x01;
        int second = 0x10;
        int third = first|second;
        System.out.println(first&third);
        System.out.println(second&third);
    }
}