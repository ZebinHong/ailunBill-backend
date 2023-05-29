package com.hunfen.springboot;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class CalendarTest {
    @Test
    public void addTest(){
        Calendar instance = Calendar.getInstance();
        System.out.println(instance.get(Calendar.MONTH));
        instance.set(Calendar.MONTH, 5);
        instance.add(Calendar.MONTH, -5);
        System.out.println(instance.get(Calendar.MONTH));
    }
}
