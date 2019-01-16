package com.dinfo.fi.utils;

public class TwoTuple<A, B> {

    public  A first;

    public  B second;

    public TwoTuple(A a, B b){
        first = a;
        second = b;
    }

    public TwoTuple() {
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public String toString(){
        return "(" + first + ", " + second + ")";
    }

}
