package tech.codingclub.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.utility.HttpURLConnectionExample;
import tech.codingclub.utility.TaskManager;
import tech.codingclub.utility.WikiResult;

import java.util.Date;

public class WikepediaDownloader implements Runnable{
    // we do not want seperate task Manager for everything
    // so we declared it as static
    private String keyword;
    private String result;

    public WikepediaDownloader(){

    }

    public WikepediaDownloader(String keyword){
        this.keyword=keyword;
    }

    @Override
    public void run() {
        // 1.Get clean keyword
        // 2.Get the url for Wikepedia
        // 3.Make a GET request to wikepedia
        // 4.Parsing the useful result using jsoup(like u only want the first paragraph of a website)
        // 5. Showing results!

        if(this.keyword==null || this.keyword.length()==0){
            return;
        }

        // we will clean the keyword
        // we want to remove empty spaces from front and back of word
        // we want to remove all spaces with _ for wikepdia search
        // here we will use regex(regular expression)
        // we will replace one and more continuos occurences of spaces with _
        // STEP 1
        this.keyword=this.keyword.trim().replaceAll("[ ]+","_");

        // STEP 2 (naming the url)
        String wikiUrl=getWikipediaUrlForQuery(this.keyword);
        String response="";
        String imageUrl = null;

        try {
            // STEP 3
            String wikipediaResponseHTML= HttpURLConnectionExample.sendGet(wikiUrl);
            //System.out.println(wikipediaResponseHTML);

            // STEP 4
            // first parameter of parse is http and second is base url
            // Document is class defined in jsoup.nodes
            Document document= Jsoup.parse(wikipediaResponseHTML,"https://en.wikipedia.org");

            Elements childElements=document.body().select(".mw-parser-output > *");

            int state=0;
            for(Element childElement :childElements){
                //System.out.println(childElement.tagName());
                if(state==0){
                    if(childElement.tagName().equals("table")){
                        state=1;
                    }
                }else if(state==1){
                    if(childElement.tagName().equals("p")){
                        state=2;
                        response=childElement.text();
                        break;
                    }
                }
            }

            try{
                imageUrl=document.body().select(".infobox img").get(0).attr("src");
            }catch(Exception ex){
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*if(imageUrl.startsWith("//")){
            imageUrl="https:"+imageUrl;
        }*/

        WikiResult wikiResult=new WikiResult(this.keyword,response,imageUrl);
        // PUSH RESULT INTO DATABASE
        //System.out.println(new Gson().toJson(wikiResult));
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String json=gson.toJson(wikiResult);
        System.out.println(json);

        /*Image image=null;
        try{
            URL url=new URL(imageUrl);
            image= ImageIO.read(url);
        }catch(IOException e){

        }*/
    }

    private String getWikipediaUrlForQuery(String cleanKeyword) {
        return "https://en.wikipedia.org/wiki/"+cleanKeyword;
    }

    public static void main(String[] args) {
        TaskManager taskManager=new TaskManager(20);

        String arr[]={"India","United States"};
        System.out.println("This side is Nikhil Jaglan");
        System.out.println("Running WikepdiaDownloader.java at "+new Date().getTime());

        for(String keyword : arr){
            WikepediaDownloader wikepediaDownloader=new WikepediaDownloader(keyword);
            taskManager.waitTillQueueIsFreeAndTask(wikepediaDownloader);
        }

        /*WikepediaDownloader wikepediaDownloader=new WikepediaDownloader("Albert Einstein");
        taskManager.waitTillQueueIsFreeAndTask(wikepediaDownloader);*/
        // here our console does not stop bcz we did not shut down executorService
    }
}
