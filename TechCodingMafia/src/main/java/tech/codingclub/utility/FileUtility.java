package tech.codingclub.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileUtility {
    public static boolean createFile(String fileNameWithPath){
        File file=new File(fileNameWithPath);
        boolean fileCreated=false;

        try{
            fileCreated=file.createNewFile();
            // returns true if file was successfully created
            // otherwise exception is thrown
        }catch(IOException e){
            e.printStackTrace();
        }

        return fileCreated;
    }

    public static ArrayList<String> readAndPrintFile(String fileName) {
        Scanner scanner=null;

        ArrayList<String> strings=new ArrayList<String>();
        try{
            File file=new File(fileName);
            scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String line=scanner.nextLine();
                //System.out.println(line);
                strings.add(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(scanner!=null){
                // This is very important
                scanner.close();
            }
        }
        return strings;
    }

    public static boolean writeToFile(String fileNameWithPath,String content){
        // BufferedWriter is better than FileWriter
        //BufferedWriter bw=null;
        try{
            //File file=new File(fileNameWithPath);

            /*if(!file.exists()){
                file.createNewFile();
            }*/

            //FileWriter fw=new FileWriter(file.getAbsoluteFile());
            FileWriter fw=new FileWriter(fileNameWithPath);
            fw.append(content);
            fw.close();
            //bw=new BufferedWriter(fw);
            //bw.write(content);
        }catch(Exception e){
            return false;
        }/*finally{
            if(bw!=null){
                try{
                    bw.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }*/

        return true;
    }

    public static boolean appendToFile(String fileName,String content){

        try{
            FileWriter fw=new FileWriter(fileName,true);
            fw.append("\n");
            fw.append(content);
            fw.close();
        }catch(Exception ioe){
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        System.out.println("This side is Nikhil Jaglan");
        System.out.println("Running FileUtility at "+new Date().toString());

        String nameofNewFile = "C:\\Users\\lenovo\\IdeaProjects\\TechCodingMafia\\data\\practice\\file\\"+"create-file.txt";
        Boolean created=createFile(nameofNewFile);
        System.out.println("File Created : "+created);

         ArrayList<String> stringArrayList=readAndPrintFile(nameofNewFile);
         for(String x : stringArrayList){
             System.out.println("Line  : "+x);
         }

         System.out.println("No of lines in file : "+stringArrayList.size());

        String nameofWriteFile = "C:\\Users\\lenovo\\IdeaProjects\\TechCodingMafia\\data\\practice\\file\\"+"write-file.txt";
        boolean writeToFile=writeToFile(nameofWriteFile,"This file is generated on Nikhil's System automatically");
        System.out.println(writeToFile);

        for(int i=1;i<=100;i++){
            String data=i+"";
            // to make i as String --> this will give if like i is 5
            // it will make it 5
            appendToFile(nameofWriteFile,data);
        }

        System.out.println("Appended file length "+readAndPrintFile(nameofWriteFile).size());
    }

}
