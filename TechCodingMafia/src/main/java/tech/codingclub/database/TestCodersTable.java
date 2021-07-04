package tech.codingclub.database;

import tech.codingclub.entity.Coders;

public class TestCodersTable {

    public static void main(String[] args) {

        Coders coder = new Coders("Nikhil Jaglan", 21L );

        // Insert this !
        new GenericDB<Coders>().addRow(tech.codingclub.tables.Coders.CODERS,coder);

        Coders spammer=new Coders("SPAMMER",50L);
        // Insert !
        new GenericDB<Coders>().addRow(tech.codingclub.tables.Coders.CODERS,spammer);

        // CRUD Operations !
        // Create Read Update Delete

        //Insert this object into DB !

//           < ENTITY CLASS>
//        new GenericDB<Coders>().addRow(tech.codingclub.tables.Coders.CODERS, akshat);
    }
}