/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduleObject;

/**
 *
 * @author Computer's Tien
 */


// đây là 1 phần nghe.
public class Part {
    private int lessonId;
    private int partId;
    private String answer;
    private String path;
    private String recommend;
 
    
    public Part(){}
    
    public Part(int lessonId,int partId, String path, String answer,String recommend){
        this.lessonId = lessonId;
        this.partId = partId;
        this.answer = answer;
        this.path = path;
        if(recommend == null){
            this.recommend = "";
        }else
            this.recommend = recommend;
    }
    
    public int getLessonId() {
        return lessonId;
    }
    
    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    
    public int getPartId(){
        return this.partId;
    }
    
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
