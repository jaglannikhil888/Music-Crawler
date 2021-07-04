package tech.codingclub.utility;

import java.util.Comparator;

public class MyPair {
    private Integer x;
    private String s;

    public MyPair(Integer x,String s){
        this.x=x;
        this.s=s;
    }

    public int getX(){
        return x;
    }

    public String getS(){
        return s;
    }
}

class MyPairComparator implements Comparator<MyPair> {
    public int compare(MyPair mp1,MyPair mp2){
        return mp1.getX()< mp2.getX()?1:-1;
    }
}
