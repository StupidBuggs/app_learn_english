/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduleObject;

import java.util.ArrayList;


/**
 *
 * @author Computer's Tien
 */
public class Lesson {
    private int levelId;
    private int lessonId;
    private String topicLesson;
    private String timeLesson;
    private String firstSentence;
    private ArrayList<Part> parts;
    
    public Lesson(){
        
    }
    
    
    /// khởi tạo path trước khi khởi tạo lesson.
    public Lesson(int levelId,int lessonId,String topicLessonString,String timeLessonString,String firstSentenceString,ArrayList<Part> parts){
        this.levelId = levelId;
        this.lessonId = lessonId;
        this.parts = parts;
        this.topicLesson = topicLessonString;
        this.timeLesson = timeLessonString;
        this.firstSentence  = firstSentenceString;
    }
    
    public int getLessonId(){
        return this.lessonId;
    }

    public int getLevelId() {
        return levelId;
    }

    public ArrayList<Part> getPaths() {
        return parts;
    }

    public String getNameLesson() {
        return topicLesson;
    }

    public String getTimeLesson() {
        return timeLesson;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(String firstSentence) {
        this.firstSentence = firstSentence;
    }
    
}
