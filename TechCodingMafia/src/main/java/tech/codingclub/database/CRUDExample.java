package tech.codingclub.database;

import tech.codingclub.entity.Coders;

import java.util.Date;
import java.util.List;

public class CRUDExample {
    public static void main(String[] args) {

        System.out.println("This side is Nikhil Jaglan");
        System.out.println("Reading rows at "+new Date().toString());

        // One Row
        Coders coder=new GenericDB<Coders>().getRow(tech.codingclub.tables.Coders.CODERS,Coders.class,null);

        // To get multiple Rows
        List<Coders> rows=(List<Coders>) GenericDB.getRows(tech.codingclub.tables.Coders.CODERS,Coders.class,null,null);

        readTableCoders();

        /*for(Coders row: rows){
            System.out.println("Row :"+row.getName()+" "+row.getAge());
        }*/

        new GenericDB<String>().updateColumn(tech.codingclub.tables.Coders.CODERS.NAME,"Coding Mafia",tech.codingclub.tables.Coders.CODERS,tech.codingclub.tables.Coders.CODERS.NAME.eq("Nikhil"));
        readTableCoders();

        // to be done in next class
        //new GenericDB<>().delete();
    }

    private static void readTableCoders() {
        List<Coders> rows=(List<Coders>) GenericDB.getRows(tech.codingclub.tables.Coders.CODERS,Coders.class,null,null);

        for(Coders row: rows){
            System.out.println("Row :"+row.getName()+" "+row.getAge());
        }
    }
}
