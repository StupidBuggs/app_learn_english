/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduleObject;

import moduleObject.Lesson;
import constant.parameters;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import connectdatabase.QueryDatabase;
/**
 *
 * @author Computer's Tien
 */
public class Level {
    private int levelId;
    private ArrayList<Lesson> level;
    
    public Level(int levelId){
        this.levelId =  levelId;
        level = new ArrayList<>();
    }
    
    public void init(){
        setLevel();
    }
    
    public void setLevelId(int levelId){
        this.levelId = levelId;
    }
    
    public int getLevelId() {
        return levelId;
    }
    
    private void setLevel(){
    
        try {
            ResultSet rs = QueryDatabase.select("*", "lesson", "level_id = " + levelId);
            
            while(rs.next()){
                
                Lesson lesson = new Lesson(levelId,rs.getInt("lesson_id") , rs.getString("topic"), 
                        rs.getString("all_time"), "nothing :D", setParts(rs.getInt("lesson_id")));
                level.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Lesson> getLevel() {
        return level;
    }

    public ArrayList<Part> setParts(int lessonId) {
        
        ArrayList<Part> parts =  new ArrayList<>();
        try {
            ResultSet rs = QueryDatabase.select("*", "part", "lesson_id = " + lessonId);
            while(rs.next()){
                
                Part part = new Part(rs.getInt("lesson_id"), rs.getInt("part_id"), rs.getString("path"), 
                        rs.getString("ans"), rs.getString("rcmt"));
                parts.add(part);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return parts;
    }
    
    
    
    public static void main(String[] args) {
        int count = 0;
        Level easy = new Level(constant.parameters.flagMediumLevel);
        easy.init();
        for(Lesson l : easy.getLevel()){
            System.out.println(l.getLevelId() + " " + l.getLessonId() + " " + l.getNameLesson() +" "
            + l.getTimeLesson());
            for(Part p: l.getPaths()){
                count ++;
                System.out.println(count);
                System.out.println(p.getLessonId() + " " + p.getPartId() + " " + p.getPath());
            }
        }
    }
    
}
