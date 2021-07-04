package tech.codingclub.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.database.GenericDB;
import tech.codingclub.entity.Music;

import java.util.ArrayList;

public class SongListDownloader implements Runnable{

    private final String filePath;

    public SongListDownloader(String filePath){
        this.filePath=filePath;
    }

    @Override
    public void run() {
        try {
            String songResponseHTML= HttpURLConnectionExample.sendGet(filePath);
            Document document= Jsoup.parse(songResponseHTML,"https://songspk.mobi");

            Elements childElements=document.body().select(".archive-body > *");

            for(Element childElement :childElements){
                //System.out.println(childElement.className());
                //String x=childElement.className();
                //System.out.println(childElement.getElementsByClass("thumb-image").get(0).attr("href"));
                    if(childElement.tagName().equals("figure")){
                        String x=childElement.getElementsByClass("image-hover").get(0).attr("href");
                        String y="https://songspk.mobi"+x;

                        String ChildSongResponseHTML= HttpURLConnectionExample.sendGet(y);
                        Document ChildDocument= Jsoup.parse(ChildSongResponseHTML,"https://songspk.mobi");

                        System.out.println(x);
                        String imageUrl=ChildDocument.body().select(".thumbnail").get(0).attr("src");
                        //System.out.println("Image Url of "+y+" "+imageUrl);

                        Elements ChildChildElements=ChildDocument.body().select(".list-group.page-meta-body > *");
                        //System.out.println(ChildChildElements);

                        String Album="";
                        String Duration="";
                        String Singers="";
                        String Lyricist="";
                        String Music_Director="";

                        for(Element child : ChildChildElements){
                            //System.out.println(child);
                            String l=child.getElementsByClass("col-md-3 col-xs-6 text-right").text();
                            String r=child.getElementsByClass("col-md-9 col-xs-6 text-left").text();
                            //System.out.println("l and r "+l+" "+r);
                            if(l.equals("Album")){
                                Album=r;
                            }
                            else if(l.equals("Duration")){
                                Duration=r;
                            }
                            else if(l.equals("Singers")){
                                Singers=r;
                            }
                            else if(l.equals("Lyricist")){
                                Lyricist=r;
                            }
                            else if(l.equals("Music Director")){
                                Music_Director=r;
                            }
                        }

//                        System.out.println("Album "+y+" "+Album);
//                        System.out.println("Duration "+y+" "+Duration);
//                        System.out.println("Singers "+y+" "+Singers);
//                        System.out.println("Lyricist "+y+" "+Lyricist);
//                        System.out.println("Music_Director "+y+" "+Music_Director);

                        Elements ChildChildChildElements=ChildDocument.body().select(".col-body > *");
                        int cnt=0;
                        String download_128="";
                        String download_256="";

                        for(Element child : ChildChildChildElements){
                            String z=child.getElementsByAttribute("href").attr("href");
                            String z1="";

                            // Test purpose
                            // https://dl.mp3slash.xyz/320z/Singles/Ashq%20Na%20Ho%20-%20Asees%20Kaur%20%20[Songspk.LINK].mp3
                            // https://dl.mp3slash.xyz/320z/Singles/Ashq%20Na%20Ho%20-%20Asees%20Kaur%20[Songspk.LINK].mp3
                            for(int i=0;i<z.length();i++){
                                // Not required
                                /*if(z.charAt(i)=='['){
                                    z1+="%20";
                                }*/
                                if(z.charAt(i)!=' '){
                                    z1+=z.charAt(i);
                                }
                                else{
                                    z1+="%20";
                                }
                            }
                            //System.out.println("Downloadable link of "+x+" "+z1);
                            cnt++;
                            if(cnt==1){
                                download_128=z1;
                            }
                            if(cnt==2){
                                download_256=z1;
                                break;
                            }
                        }

                        //System.out.println("download_128 "+y+" "+download_128);
                        //System.out.println("download_256 "+y+" "+download_256);

                        Music music = new Music(filePath,y,Album,Duration,Singers,Lyricist,Music_Director,download_128
                                ,download_256,imageUrl);

                        // Insert this !
                        new GenericDB<Music>().addRow(tech.codingclub.tables.Music.MUSIC,music);
                    }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TaskManager taskManager=new TaskManager(15);
        ArrayList<String> FileParentUrl=FileUtility.readAndPrintFile("C:\\Users\\lenovo\\IdeaProjects\\TechCodingMafia\\data\\practice\\file\\crawl-song-list.txt");

        for(String row : FileParentUrl){
            SongListDownloader songDownloader=new SongListDownloader(row);
            taskManager.waitTillQueueIsFreeAndTask(songDownloader);
        }
    }
}
