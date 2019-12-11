/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_learn_english;
import gui.main.*;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Computer's Tien
 */
public class Frame extends JFrame implements ActionListener{
    AboutUs aboutUs;
    JPanel mainpn;
    CardLayout card;
    Home home;
    ChoseLevel choseLevel;
    ChoseLesson choseLesson;
    PlayLesson playLesson;
    History history;
    Help help;
    /**
     * @param args the command line arguments
     */
    public Frame(String s){
        card = new CardLayout();
        aboutUs = new AboutUs(this);
        home = new Home(this);       
        choseLesson = new ChoseLesson(this);
        choseLevel = new ChoseLevel(this,choseLesson);
        playLesson = new PlayLesson(this,choseLesson);
        history = new History(this);
        help = new Help(this);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setLocation(screenSize.width/3 + 100, screenSize.height/3 - 100);
        mainpn = (JPanel)getContentPane();
        mainpn.setLayout(card);
        mainpn.add("home",home);
        mainpn.add("about", aboutUs);
        mainpn.add("start",choseLevel);
        mainpn.add("choseLesson",choseLesson);   
        mainpn.add("play",playLesson);
        mainpn.add("history",history);
        mainpn.add("help",help);
    }
    public void play(){
        card.show(mainpn, "play");
    }
    public void choseLesson(){
        card.show(mainpn, "choseLesson");
    }   
    public void choseLevel(){
        card.show(mainpn,"start");
    }
    public void home(){
        card.show(mainpn, "home");
    }
    public void about()
    {
        card.show(mainpn, "about");
    }
    public void history(){
        card.show(mainpn, "history");
    }
    public void help(){
        card.show(mainpn, "help");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
}
