package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.atguigu.java.Order.*;

/** 如何自定义泛型结构：泛型类、泛型接口；泛型方法。
 *
 * 1. 关于自定义泛型类、泛型接口。
 *
 *
 *
 * @author shkstart
 * @create 2019 上午 11:09
 */
public class GenericTest1 {

    @Test
    public void test1(){
        //如果定义了泛型类，实例化没有指明类的泛型，则认为此泛型类型为Object类型。
        //要求：如果大家定义了类是带泛型的，建议在实例化时要指明类的泛型。
        Order order = new Order();
        order.setOrderT(123);
        order.setOrderT("ABC");

        //建议：实例化时指明类的泛型
        Order<String> order1 = new Order<String>("orderAA",1001,"order:AA");

        order1.setOrderT("AA:hello");
        System.out.println(order1.getOrderT());

    }

    @Test
    public void test2(){
        SubOrder sub1 = new SubOrder();
        //由于子类在继承带泛型的父类时，指明了泛型类型。则实例化子类对象时，不再需要指明泛型。
        sub1.setOrderT(1122);
        //由于子类在继承带泛型的父类时，没有指明泛型类型。则实例化子类对象时，需要指明泛型。
        SubOrder1<String> sub2 = new SubOrder1<>();
        sub2.setOrderT("order2...");
    }

    @Test
    public void test3(){

        ArrayList<String> list1 = null;
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        //泛型不同的引用不能相互赋值。
//        list1 = list2;

        Person p1 = null;
        Person p2 = null;
        p1 = p2;

    }

    //测试泛型方法
    @Test
    public void test4(){
        Order<String> order = new Order<>();
        Integer[] arr = new Integer[]{1,2,3,4};
        //泛型方法在调用时，指明泛型参数的类型。
        List<Integer> list = order.copyFromArrayToList(arr);

        System.out.println(list);

        String[] arr1 = new String[]{"Cayde 6","Ash","Evil"};
        List<String> list1 = copyFromArrayToList(arr1);

        System.out.println(list1);

        Double[] arr2 = new Double[]{0.01,0.1,0.222};
        List<Double> list2 = order.copyFromArrayToList(arr2);

        System.out.println(list2);

        Integer[] arr3 = new Integer[]{6,6,6};
        List<Integer> list3 = SubOrder.copyFromArrayToList(arr3);

        System.out.println(list3);
    }
}
