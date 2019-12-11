/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_learn_english;

import gui.main.Home;

/**
 *
 * @author Computer's Tien
 */
public class Main {
     public Main()
    {
    }

    public static void main(String args[]){
        Frame jf = new Frame("English Vocul");
        jf.setTitle("ENGLISH APP");
        
        jf.add(new Home(jf));
        jf.setVisible(true);
        jf.setSize(650, 550);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setAlwaysOnTop(false);
        jf.setDefaultCloseOperation(3);
    }
}
