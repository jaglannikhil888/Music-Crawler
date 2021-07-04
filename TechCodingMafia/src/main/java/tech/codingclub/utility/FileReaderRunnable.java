package tech.codingclub.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReaderRunnable implements Runnable{
    File file;
    Map<String, Integer> wordFreq;
    ArrayList<MyPair> arrayList;
    Scanner scanner;

    public FileReaderRunnable(String filePath){
        file=new File(filePath);
        wordFreq=new HashMap<String, Integer>();
        arrayList=new ArrayList<MyPair>();
        scanner=null;
    }

    public void run(){

        try {
            scanner=new Scanner(file);
            while(scanner.hasNext()){
                String x=scanner.next();
                x=x.toLowerCase();
                if(wordFreq.containsKey(x)){
                    wordFreq.put(x,wordFreq.get(x)+1);
                }
                else{
                    wordFreq.put(x,1);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(scanner!=null){
                scanner.close();
            }
        }

        for(String key : wordFreq.keySet()){
            MyPair mp=new MyPair(wordFreq.get(key),key);
            arrayList.add(mp);
        }

        Collections.sort(arrayList,new MyPairComparator());
        for(int i=0;i<9;i++){
            System.out.println(arrayList.get(i).getX()+" "+arrayList.get(i).getS());
        }
    }

    public static void main(String[] args) {
        String filePath="C:\\Users\\lenovo\\IdeaProjects\\TechCodingMafia\\data\\practice\\file\\National-Anthem.txt";
        FileReaderRunnable frr=new FileReaderRunnable(filePath);
        TaskManager taskManager=new TaskManager(10);
        taskManager.waitTillQueueIsFreeAndTask(frr);
    }
}
