/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websystems;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


class crawlerthreat extends Thread{
String url;
    public crawlerthreat(String url) {
        this.url=url;
    }

    @Override
    public void run() {
    try {
        new Crawler(url);
    } catch (IOException e) {
       JOptionPane.showMessageDialog(new Component() {
       } , e,"Information",JOptionPane.INFORMATION_MESSAGE);
    }
    }
    
}//end class

/**
 *
 * @author KOURSOS
 */
public class Mainframe  extends JFrame {

   private JLabel infolabel=new JLabel("Insert Your URI (example.xxx):");
    private JTextField urlfield=new JTextField(20);
    private JPanel urlpanel=new JPanel();
    private JButton search=new JButton("Search");
    private JButton cancel=new JButton("Cancel/clear");
    private JPanel optionpanel=new JPanel();
    private JCheckBox checkboxbrokenlinks=new JCheckBox("Broken Links");
    private JCheckBox checkboxbrokenimages=new JCheckBox("Broken Images");
    private JCheckBox checkboxtextfrequency=new JCheckBox("Text Frequency");
   private JCheckBox checkboxfollow_index=new JCheckBox("Follow/Index");
   private JCheckBox checkboxall=new JCheckBox("All");
   public static boolean brokenimg=false;
   public static  boolean  brokenlinks=false;
   public static boolean textfrequency=false;
   public static  boolean  followindex=false;
   private  boolean all=false;

     private JScrollPane resultpanel ;
     public static JTextArea resultsarea=new JTextArea(46,70);
     public static JTextPane  resultpanetext=new JTextPane();
     private crawlerthreat mycrawlerthreat;
     public Mainframe() {
    super("Web Systems");
        setSize(1000,860);
         setMinimumSize(new Dimension(1000,860));
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);            //center the app to the screen
        setLayout(new BorderLayout() );
        urlpanel.add(infolabel);
        urlpanel.add(urlfield);
        urlfield.setText("URI");
        urlpanel.add(search);
        search.setEnabled(false);
        urlpanel.add(cancel); 
        cancel.setEnabled(false);
        add(urlpanel,BorderLayout.NORTH);

        optionpanel.add(checkboxbrokenlinks);
        optionpanel.add(checkboxbrokenimages);
        optionpanel.add(checkboxtextfrequency);
        optionpanel.add(checkboxfollow_index);
        optionpanel.add(checkboxall);
        add(optionpanel,BorderLayout.CENTER);
        
        resultpanel=new JScrollPane(resultsarea);
        resultsarea.setEditable(false);
        //resultsarea.setForeground(Color.red);
        resultpanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultpanel.setAutoscrolls(true);
        optionpanel.add(resultpanel);
        
                            
    checkboxall.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()==ItemEvent.SELECTED ){
            checkboxbrokenlinks.setSelected(true);
            checkboxbrokenimages.setSelected(true);
            checkboxtextfrequency.setSelected(true);
            checkboxfollow_index.setSelected(true);
            brokenimg=true;
            brokenlinks=true;
            textfrequency=true;
            followindex=true;
             }
            else{
           checkboxbrokenlinks.setSelected(false);
           checkboxbrokenimages.setSelected(false);
           checkboxtextfrequency.setSelected(false);
           checkboxfollow_index.setSelected(false);
            brokenimg=false;
            brokenlinks=false;
            textfrequency=false;
            followindex=false;
            search.setEnabled(false);
            
            }
        }
    });
    
                  // urlfield.setText("koutsouvanos.biz/");
           // urlfield.setText("oil-eshop.gr/");
        
urlfield.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
          if(!urlfield.getText().equals("") && (brokenimg==true || brokenlinks==true || textfrequency==true || followindex==true)  ){
          search.setEnabled(true);
          }else {
          search.setEnabled(false);
          }
          
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(!urlfield.getText().equals("") &&  (brokenimg==true || brokenlinks==true || textfrequency==true || followindex==true)){
          search.setEnabled(true);
          }else {
          search.setEnabled(false);
          }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
           if(!urlfield.getText().equals("") && (brokenimg==true || brokenlinks==true || textfrequency==true || followindex==true)){
          search.setEnabled(true);
          }else {
          search.setEnabled(false);
          }
        }
    });

urlfield.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if(urlfield.getText().equals("URI")){
            urlfield.setText("");
           search.setEnabled(false);
            }
       }

        @Override
        public void focusLost(FocusEvent e) {
          if(urlfield.getText().equals("")){
              urlfield.setText("URI");
          
          }
        }
      
    });
    search.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if( brokenimg==false && brokenlinks==false && textfrequency==false && followindex==false){

             JOptionPane.showMessageDialog(Mainframe.this, "Please Select An Option","Information",JOptionPane.INFORMATION_MESSAGE);
             return;
                }
            if(urlfield.getText().equals("URI")){
            JOptionPane.showMessageDialog(Mainframe.this, "Please Give A URI","Information",JOptionPane.INFORMATION_MESSAGE);
             return;
            
            }
            
            cancel.setEnabled(true);
          //  search.setEnabled(false);
              String http="https://";
            String userurl =urlfield.getText();
           mycrawlerthreat=new crawlerthreat(http+userurl);
            mycrawlerthreat.start();
                
            
        }//end listener
    });
    
    checkboxbrokenimages.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(e.getStateChange()==ItemEvent.SELECTED){
            brokenimg=true;
             if(!urlfield.getText().equals(""))search.setEnabled(true);
              if(brokenlinks==true && textfrequency==true && followindex==true){ all=true; checkboxall.setSelected(true);}
           }
           else{
              brokenimg=false;
            if(all){
            all=false;
            checkboxall.setSelected(false);
           checkboxbrokenlinks.setSelected(true);
            checkboxtextfrequency.setSelected(true);
            checkboxfollow_index.setSelected(true);
           }           
             if(brokenlinks==false && urlfield.getText().equals("")  && textfrequency==false && followindex==false){
              search.setEnabled(false);
           }
           }
        }
    });
    checkboxbrokenlinks.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(e.getStateChange()==ItemEvent.SELECTED){
               brokenlinks=true;
               if(!urlfield.getText().equals(""))search.setEnabled(true);
        if(brokenimg==true && textfrequency==true && followindex==true){ all=true; checkboxall.setSelected(true);}

           }else{
           brokenlinks=false;
              if(all){
            all=false;
            checkboxall.setSelected(false);
            checkboxbrokenimages.setSelected(true);
            checkboxtextfrequency.setSelected(true);
            checkboxfollow_index.setSelected(true);
           }           
           if(brokenimg==false && urlfield.getText().equals("") && textfrequency==false && followindex==false){
              search.setEnabled(false);
           }
           
           if( all ){}
               
           }
        }
    });
        checkboxtextfrequency.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
          if(e.getStateChange()==ItemEvent.SELECTED){
              textfrequency=true;
               if(!urlfield.getText().equals(""))search.setEnabled(true);
               if(brokenlinks==true && brokenimg==true && followindex==true){ all=true; checkboxall.setSelected(true);}

          }else{
          textfrequency=false;
            if(all){
            all=false;
            checkboxall.setSelected(false);
           checkboxbrokenlinks.setSelected(true);
            checkboxbrokenimages.setSelected(true);
            checkboxfollow_index.setSelected(true);
           }       
            if(brokenimg==false && urlfield.getText().equals("")  && brokenlinks==false && followindex==false){
              search.setEnabled(false);
           }
          } 
          
        }
        });
        checkboxfollow_index.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED){
            followindex=true;
             if(!urlfield.getText().equals(""))search.setEnabled(true);
             if(brokenlinks==true && textfrequency==true && brokenimg==true){all=true; checkboxall.setSelected(true);}

        }else{
            followindex=false;
           if(all){
            all=false;
            checkboxall.setSelected(false);
           checkboxbrokenlinks.setSelected(true);
            checkboxbrokenimages.setSelected(true);
            checkboxtextfrequency.setSelected(true);
           }
            if(brokenimg==false && urlfield.getText().equals("")  && textfrequency==false && brokenlinks==false){
              search.setEnabled(false);
           }
              
        }
        }
        });
            
    cancel.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.exit(0);
            mycrawlerthreat.stop();
            //mycrawlerthreat.interrupt();
               

            resultsarea.setText("");
            Crawler.allurls.clear();
            Crawler.brokenimgs.clear();
            Crawler.brokenrls.clear();
            Crawler.words.clear();
            cancel.setEnabled(false);
            search.setEnabled(true);
            
//System.exit(0);
            
        }
    });
    
    
      addWindowListener(new WindowAdapter()  {

            @Override
            public void windowClosing(WindowEvent e)
            {
                int answer = JOptionPane.showConfirmDialog(Mainframe.this, "Are you sure you want to close the window?", "Window closing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION)  {
                    Mainframe.this.dispose();
                    System.exit(0);
                }
                
            }
            
        });
    }//end con    
}//end class
