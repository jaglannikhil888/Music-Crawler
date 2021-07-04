package tech.codingclub.entity;

public class Coders {

    private String name;
    private Long age;

    public Coders(){

    }

    public Coders(String name,Long age){
        this.name=name;
        this.age=age;
    }

    public String getName(){
        return name;
    }

    public Long getAge(){
        return age;
    }
}
