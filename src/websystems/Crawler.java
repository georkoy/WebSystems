/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websystems;

import java.awt.Component;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author KOURSOS
 */
public class Crawler {
private static Document document;
  public static  Set<String> allurls = new LinkedHashSet<>();
  public static HashSet<String> brokenrls = new HashSet<>(); 
   public static HashSet<String> brokenimgs = new HashSet<>();
   public static HashMap<String, Integer> words = new HashMap<>();
   private int nexturlcounter=0;
   private int i=0;
  private int counter=0;
private String[] starturl;
private String domain;
    public Crawler(String url) throws IOException {
        System.out.println("Fetching site "+url);
       this.starturl=url.split("\\.");
       domain=starturl[0];//be sure to use to same domain and not crawl other sites, get the domain name
           
        Mainframe.resultsarea.append("Fetching "+url+"\n\n");   
       
        url=canocikalurl(url);
          allurls.add(url);
          
      init(url);
       
  
  
  }//end con
        
  int tmp=0;
    private void init (String url) {
   try {
    document = Jsoup.connect(url).get();
    follow_index(url);
    brokenimg(url);
    vocabulary();
    brokenlinks(url);
    nexturl();
            
   }
       catch(Exception e){
          
           System.out.println("index "+e.getMessage());
      JOptionPane.showMessageDialog(new Component() {
      }, e+"","Information",JOptionPane.INFORMATION_MESSAGE);
          
   }
    }
    
    
    
 private void listadd(String linkURL){
   
    if( linkURL.contains(domain)  && !linkURL.contains("#")   && !linkURL.contains("page") && !linkURL.endsWith(".jpg") && !linkURL.endsWith(".jpeg") && !linkURL.endsWith(".png") && !linkURL.endsWith(".webp") && !linkURL.endsWith(".svg") ){
                        
              if(!allurls.contains(linkURL)){
              allurls.add(linkURL); //add urls to set
              }               
     }//end if
 
 }
    private String canocikalurl(String linkURL){ //make url to a spicific format
        
                  if(linkURL.endsWith("/")){
                     linkURL=linkURL.replaceFirst(".$", ""); //remove last character
                             
                       
                     }
                  //remove www.
                  try{
                  String[] spl1=linkURL.split("//");     
                  if( spl1[1].substring(0,4).equals("www.")   ){
                     linkURL=spl1[0]+"//"+spl1[1].substring(4, spl1[1].length());
                                       
                  }
                  }//end try
                  catch (ArrayIndexOutOfBoundsException e){System.err.println(e.getMessage());}
  return linkURL;
        
        
    }
    
    private void brokenlinks(String url) throws IOException {
        
      // document = Jsoup.connect(linkURL).get();
       document = Jsoup.connect(url).get();
     Elements links = document.select("a[href]");
        for (Element link : links) {
         String  linkURL = link.absUrl("href");// get only url 
//System.out.println("links "+linkURL);
        //broken url
  	try {
	          Jsoup.connect(linkURL).ignoreContentType(true).execute();
                  linkURL=canocikalurl(linkURL);
                  listadd(linkURL);
	  
		} catch (HttpStatusException e) {
                    if(!brokenrls.contains(linkURL)){
                        brokenrls.add(linkURL);
                        if(Mainframe.brokenlinks==true){
			System.out.println("Found broken link: " + linkURL + " with status code " + e.getStatusCode());
                        Mainframe.resultsarea.append("Broken Link "+linkURL+" with status "+e.getStatusCode()+" in "+url+"\n");
                        }
                    }
		} catch (UnknownHostException e) {
                 if(Mainframe.brokenlinks==true){
			System.out.println("Found broken link: " + linkURL + " with unknown host");
                  Mainframe.resultsarea.append("Broken Link "+linkURL+" with  unknow host"+" in "+url+"\n");
                    }
		}
               
                catch (Exception e){
                    System.err.println("links brk"+e.toString());
                }
                                       
	}//end for 
    
    }//end fun brokenlinks
    
    private void brokenimg(String url) throws IOException{
                          
 Elements img = document.getElementsByTag("img");
   for (Element imgs : img) {
      
       
 try{
  //for each element get the src url
    String src= imgs.absUrl("src");
      //  System.out.println("src1 "+src);    
 
 Jsoup.connect(src).ignoreContentType(true).execute();
   //Exctract the name of the image from the src attribute
/*   int indexname = src.lastIndexOf("/");
             if (indexname == src.length()) {
            src = src.substring(1, indexname);
                 System.out.println(src);
                 System.out.println("");
            
        }*/
             }
            catch (HttpStatusException e){
                if(!brokenimgs.contains(e.getUrl())){
                   brokenimgs.add(e.getUrl());
                  if(Mainframe.brokenimg==true){
             System.out.println("Found broken image "+e.getUrl());
              Mainframe.resultsarea.append("Found broken image with name "+e.getUrl()+" in url "+url+"\n");
                }     
                }
            }
 catch (Exception ex){
     System.out.println("ex "+ex.getMessage());
 }
                     }//end for                       
    
    }//end broken iamges

private void  nexturl() throws IOException{
//for(String nexturlindex : allurls){
String[] arrayurls=allurls.toArray(new String[allurls.size()]);
   if(i<allurls.size()-1){ //while i have urls. 
       i++;
    System.out.println("neχt is "+arrayurls[i]);
    Mainframe.resultsarea.append("Explore "+arrayurls[i]+"\n");
    init(arrayurls[i]);
     
   }
 printvocabulary();

}
private void printvocabulary (){
/*for (String word: words.keySet()){
    System.out.println(word+" -> "+ words.get(word));}*/

Map<String, Integer> sortedMap = words.entrySet().stream()
        .sorted(Comparator.comparingInt(e -> -e.getValue()))
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> { throw new AssertionError(); },
                LinkedHashMap::new
        ));



if(Mainframe.textfrequency==true){
 System.out.println("size is :"+sortedMap.size());
 Mainframe.resultsarea.append("\nVocabulary size is: "+sortedMap.size());
for(String wr:sortedMap.keySet()){
    if(counter<101){
   
      System.out.println(wr+"->"+sortedMap.get(wr));
      Mainframe.resultsarea.append("\n"+wr+"->"+sortedMap.get(wr));
        counter++; 
    }
    else{
        counter=0;
    break;
    }
}

}// end class
/*sortedMap.entrySet().forEach(  System.out::println);*/
   // System.out.println(counter);

}
private void vocabulary() throws IOException{
  
    //  document = Jsoup.connect(url).get();
//String pagetitle = document.select("title").first().text();
    CountWords(document.select("title").first().text());
    //System.out.println("title "+document.select("title").first().text());
    CountWords(document.body().text());
 // System.out.println("body "+document.body().text());
    System.out.println("");

    
   // System.out.println(pagetitle);
}

   private void CountWords(String text) {
        String[] wordisolate = text.split(" ");
        int wordcounter;
        for (String word : wordisolate) {
          if(!word.matches("\\p{Punct}") && !word.matches("\\p{Digit}")){// not count special character or numbers
            if (words.containsKey(word)) {
                wordcounter = words.get(word);
                wordcounter ++;
               words.put(word, wordcounter );
            } else {
                words.put(word, 1);
            }
            }
        }//end for
    }//edn fun


   private void follow_index (String url){
    
      String[] mt=document.select("meta[name=robots]").attr("content").split(",");
try{
      String tmp=mt[0]+""+mt[1];

      
      if(!tmp.contains("followindex") ){ //i have not follow or notindex
              if(mt[0].equals("noindex") || mt[1].equals("noindex") ){ //has noindex
                Mainframe.resultsarea.append("The "+url+" is noindex\n");
                       
              }
              else if( mt[0].equals("nofollow") || mt[1].equals("nofollow") ){ //has nofollow
                Mainframe.resultsarea.append("The "+url+" is nofollow\n");

              }       
      }
      
}
catch (ArrayIndexOutOfBoundsException e){
    if(Mainframe.followindex==true){
    System.out.println("No metada found about index/follow in "+url);
    Mainframe.resultsarea.append("No metada found about index/follow in "+url+"\n");
    }
}

}//end fun
   
   
 }//end class
