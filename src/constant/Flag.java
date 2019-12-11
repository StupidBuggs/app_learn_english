/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

/**
 *
 * @author Computer's Tien
 */
public class Flag {
    public static final int MAXLESSON = 4;
    private static int level = 0;
    private static int lesson = 0;
    
    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        Flag.level = level;
    }

    public static int getLesson() {
        return lesson;
    }

    public static void setLesson(int lesson) {
        Flag.lesson = lesson;
    }
    
    
}
