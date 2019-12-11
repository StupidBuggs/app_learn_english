/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;
import constant.Flag;
import moduleObject.Part;
import moduleObject.Lesson;
import myexception.SoundFilter;
import app_learn_english.Frame;
import constant.parameters;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.sound.sampled.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import connectdatabase.QueryDatabase;



public class PlayLesson  extends JPanel implements Runnable,MouseListener,KeyListener,ActionListener,ChangeListener{
    private javax.swing.JButton backButton;
    private Frame jf;
    private ChoseLesson choseLesson;
    private JPanel p1;
    private JLabel label1,lbFileName,lbDuration,lbTotalTime,lbResult,lbTopic;
    private JSlider sliderMusic;
    private JButton play,stop,next,previous,recommend;
    private JPanel splitPane;
    private moduleObject.Level myLevel;
    private String fileName;
    private boolean pause=false,playing=false;
    private Thread thread;
    private Timer timer;
    private int second,minute,sliderValue,duration,trackNum=0;
    private Object currentFile;
    private Clip clip;
    private Vector path;
    private JTextArea textArea;
    private JLabel lbRecommend;
    private int partPlaying;
    private int lessonPlaying;
    private String answer;
    private ArrayList<String> myList;
    private String[] troll; 
    private int conditionRecommend; // điều kiện bắt người dùng phải suy nghĩ trước khi dùng gợi ý
    private long milis1;
    private long milis2;
    private double score;
    private long timeLesson;
    private String dayNow;
    private String hourNow;
    private String timeNow;
    private File loopFile;
    private boolean answered;
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //Contruction
    public PlayLesson(Frame jf,ChoseLesson choseLesson){
        this.jf = jf;
        this.choseLesson = choseLesson;
        path=new Vector();
        myList = new ArrayList<>();
        troll = new String[5];
        troll[0] = "Oops! This sentence no have recommend";
        troll[1] = "Hmmm! You must pay for recommend";
        troll[2] = "The recommend is in your mind :D";
        troll[3] = "Why don't you think before click this :v";
        troll[4] = "Loading...! Oops, I don't know :v";
        answered = false;
        partPlaying = 0;
        lessonPlaying = Flag.getLesson()-1;
        p1=new JPanel();
        p1.add(getPanelNorth());
        p1.add(getPanelCenter());
        p1.add(getPanelSouth());
        p1.add(getPanelLast());
        p1.setPreferredSize(new Dimension(300,550));
        splitPane=new JPanel();
        splitPane.add(p1);
        //splitPane.add(backButton);
//        splitPane.setOneTouchExpandable(false);
//        splitPane.setDividerLocation(350);
//        splitPane.setBackground(Color.yellow);
        add(splitPane);
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //getPanelNorth
    public JPanel getPanelNorth(){
        JPanel p=new JPanel();
        label1=new JLabel("");
            label1.setFont(new Font("Tahoma",Font.BOLD, 12));
            label1.setBorder(new EmptyBorder(0,100,0,100));
        lbFileName=new JLabel("Click play to start");
            lbFileName.setFont(new Font("Tahoma",Font.PLAIN, 12));
            lbFileName.setForeground(Color.blue);
        lbTopic=new JLabel("Topic of lesson: ");
            lbTopic.setFont(new Font("Tahoma",Font.PLAIN, 16));
            lbTopic.setForeground(Color.blue);
        p.add(label1);
        p.add(lbFileName);
        p.add(lbTopic);
        p.setPreferredSize(new Dimension(340,50));
        return p;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public JPanel getPanelCenter(){
        JPanel p=new JPanel();        
        lbDuration=new JLabel();
            lbDuration.setForeground(Color.blue);
        sliderMusic=new JSlider(JSlider.HORIZONTAL, 0,100,0);
            sliderMusic.addMouseListener(this);
            sliderMusic.setToolTipText("Stopping");
        lbTotalTime=new JLabel();
            lbTotalTime.setForeground(Color.blue);
        
        p.add(lbDuration);
        p.add(sliderMusic);
        p.add(lbTotalTime);
        p.setPreferredSize(new Dimension(340,100));
        //p.setBorder(new TitledBorder(new EtchedBorder()));
        p.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(new EtchedBorder()),new EmptyBorder(30,0,10,0)));
        return p;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public JPanel getPanelSouth(){
        JPanel p=new JPanel();
        //p.setLayout(new GridLayout(3, 4, 5, 5));
        previous=new JButton(new ImageIcon(getClass().getResource("/img/Previus.png")));
            previous.setToolTipText("B\340i tr\u01B0\u1EDBc!");
            previous.setActionCommand("Previous");
            previous.addActionListener(this);
        play=new JButton( new ImageIcon(getClass().getResource("/img/Play.png")));
            play.setToolTipText("Ch\u1EA1y!");
            play.setActionCommand("Play");
            play.addActionListener(this);
        stop=new JButton( new ImageIcon(getClass().getResource("/img/Stop.png")));
            stop.setToolTipText("D\u1EEBng!");
            stop.setActionCommand("Stop");
            stop.addActionListener(this);
        next=new JButton(new ImageIcon(getClass().getResource("/img/Next.png")));
            next.setToolTipText("B\340i ti\u1EBFp theo!");
            next.setActionCommand("Next");
            next.addActionListener(this);  
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(300, 100));
        textArea.setAutoscrolls(true);
        textArea.addKeyListener(this);
        lbResult = new JLabel("Nhập kết quả vào ô dưới đây");
        
        p.add(previous);
        p.add(play);
        p.add(next);
        p.add(stop);
        p.add(lbResult);
        p.add(textArea);
        
        p.setPreferredSize(new Dimension(300,200));
        return p;
    }
    public JPanel getPanelLast(){

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1));
        JPanel p1 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p2 = new JPanel();
        p.add(p1);
        p.add(p3);
        p.add(p2);
        
        recommend = new JButton();
        recommend.setBackground(new java.awt.Color(204,204,204));
        recommend.setForeground(new java.awt.Color(204,204,204));
        recommend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/idea.jpg"))); // NOI18N
        recommend.setToolTipText("recommend!");
        recommend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                recommendButtonMouseReleased(evt);
            }
        });
        p1.add(recommend);
        
        lbRecommend = new JLabel("");
        p3.add(lbRecommend);
        
        backButton = new JButton();
        backButton.setBackground(new java.awt.Color(204,204, 204));
        backButton.setForeground(new java.awt.Color(204,204,204));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/backiconsmall.png"))); // NOI18N
        backButton.setToolTipText("Quit!");
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backButtonMouseReleased(evt);
            }
        });
        p2.add(backButton);
        
        p.setPreferredSize(new Dimension(300,100));
        p.setLocation(100, 500);
        return p;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //hàm tính điểm nghe =))
    public void score(long time1, long time2){
        this.score =10*(11-((double)((time2-time1)/timeLesson)));
        System.out.println("time working: " + (time2-time1) + " " + "sumtime: " + timeLesson);
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void recommendButtonMouseReleased(java.awt.event.MouseEvent evt){
        if(myLevel == null){
            lbRecommend.setText("Hey! Are you kidding me? Click play before!");
        }
        else if(Flag.getLevel() > 1){
            lbRecommend.setText("Oops! Sorry, This level doesn't recommended hihi");
        }
        else{
            Random rd = new Random();
            int a = rd.nextInt(5);
            String rcm = myLevel.getLevel().get(lessonPlaying).getPaths().get(partPlaying).getRecommend();
            if(conditionRecommend > 0 || rcm.equals("not rcmt")){ // nếu ko có gợi ý thì sẽ in ra cái này
                lbRecommend.setText(troll[a]);
            }else if(conditionRecommend <= 0){
                lbRecommend.setText(rcm);
            }
        }
    }
    private void backButtonMouseReleased(java.awt.event.MouseEvent evt) {                                         
        Stop();
        // xóa list các lesson
        reset();
        jf.choseLesson();
    }
    public void mouseClicked(MouseEvent me) {
        
    }
    public void mousePressed(MouseEvent me) {

    }

    public void mouseReleased(MouseEvent me) {
        if(me.getSource()==sliderMusic)
            if(sliderMusic.getToolTipText().equals("Playing"))
                setMusicPosition();
    }

    public void mouseEntered(MouseEvent me) {
       
    }

    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //kết quả kiểm tra ở đây
        // lấy ra đối tượng đang phát và lấy ra kết quả của đối tượng đó
        //textArea.getText().equals(lesson[lessonPlaying].getPart[partPlaying].getAnswer()];
        if(myLevel != null){
            String result = "";
            String current = textArea.getText();           
            answer = myLevel.getLevel().get(lessonPlaying).getPaths().get(partPlaying).getAnswer();
            if(current.length() > answer.length()){
                textArea.setText(result);
            }
              else
            for(int i=0;i<current.length();i++){
                if(current.charAt(i) == answer.charAt(i)){
                    result = result + current.charAt(i);
                    
                }
            }
            textArea.setText(result);
            conditionRecommend--;
            System.out.println(conditionRecommend);
        if(textArea.getText().equals(answer)){
            textArea.setText("");
            answered = true;
            Random rd = new Random();
            int j = 10 + rd.nextInt(16);
            conditionRecommend = j;
            if(partPlaying == myList.size()-1){
                System.out.println("đã nhấn");
                Stop();
                reset();
                milis2 = System.currentTimeMillis();
                score(milis1,milis2);
                System.out.println("Score: " + this.score);
                String message = "List answer";
                int i=1;
                for(Part p : myLevel.getLevel().get(lessonPlaying).getPaths()){
                    message = message + "\n" + i + "-" + p.getAnswer();
                    i++;
                }
                message = message + "\n" + "Your score: " + this.score + "\nDo you want continue?";
                updateHistory();
                
                int n = JOptionPane.showConfirmDialog(this.p1,message,"YOUR IQ", // n=0 là yes
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); // n = 1 là no
                if(n == 1){
                    Stop();
                    reset();
                    jf.choseLesson();
                }else{
                    Next();
                }
            }
            else if(partPlaying < myList.size()-1){
                    partPlaying++;               
                System.out.println("đã nhấn");
                Stop();
                thread=new Thread(this);
                thread.start();
            }
        }
        }
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void actionPerformed(ActionEvent ae) {
        

        if(ae.getActionCommand().equals("Play")){            
            if(pause==false && playing==false){
                if(!myList.isEmpty()){                    
                    thread=new Thread(this);
                    thread.start();
                }
                else {
                    
                    addMusic();
                    thread=new Thread(this);
                    thread.start();
                }
            }
            else if(pause==true && playing==true){
                Continuous();
            }            
        }

        if(ae.getActionCommand().equals("Pause")){
            Pause();
        }

        if(ae.getActionCommand().equals("Stop")){
            Stop();
        }

        if(ae.getSource()==next){
            Next();
        }

        if(ae.getSource()==previous){
            Previous();
        }
        
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void stateChanged(ChangeEvent ce) {
    }
/*******************************************************************************/
    public void reset(){
        textArea.setEditable(false);
        myList.clear();
        partPlaying = 0;
    }
/*******************************************************************************/
    //nạp các lesson và các file vào path
    private void addMusic(){
        //hàm khởi tạo các lesson
        //lưu ý: trước khi khởi tạo các lesson thì sẽ phải khởi tạo các phần nghe
        
        // add các lesson vào defaultList 
        // tìm cờ hiệu và setList các bài nghe
        myLevel = new moduleObject.Level(Flag.getLevel());
        myLevel.init();
        textArea.setEditable(true);
        Random rd = new Random();
        conditionRecommend = 10 + rd.nextInt(10);
        milis1 = System.currentTimeMillis();
        timeLesson = 0;
        System.out.println(conditionRecommend);
        dayNow = java.time.LocalDate.now().toString();
        hourNow = java.time.LocalTime.now().getHour() + ":" + java.time.LocalTime.now().getMinute() + ":" +
                java.time.LocalTime.now().getSecond();
        timeNow = dayNow + " " + hourNow;
        switch(Flag.getLesson()){
            case parameters.flagLesson1:
                lessonPlaying = Flag.getLesson()-1;
                Lesson lesson = myLevel.getLevel().get(lessonPlaying);
//                myList.add("F:/englishApp/test.wav");
//                myList.add("F:/englishApp/test2.wav");
                myList.clear(); // xóa danh sách nhạc đang phát nếu có
                for(Part p: lesson.getPaths()){
                    myList.add((parameters.path + p.getPath()));
                }
                break;
            case parameters.flagLesson2:
                //code add nhạc ở đây
                lessonPlaying = Flag.getLesson()-1;
                Lesson lesson2 = myLevel.getLevel().get(lessonPlaying);
//                myList.add("F:/englishApp/test2.wav");
                myList.clear(); // xóa danh sách nhạc đang phát nếu có
                for(Part p: lesson2.getPaths()){
                    myList.add((parameters.path + p.getPath()));
                }
                break;
            case parameters.flagLesson3:
                //code add nhạc ở đây
                lessonPlaying = Flag.getLesson()-1;
                Lesson lesson3 = myLevel.getLevel().get(lessonPlaying);
//                myList.add("F:/englishApp/test.wav");
                myList.clear(); // xóa danh sách nhạc đang phát nếu có
                for(Part p: lesson3.getPaths()){
                    myList.add((parameters.path + p.getPath()));
                }
                break;
            case parameters.flagLesson4:
                //code add nhạc ở đây
                lessonPlaying = Flag.getLesson()-1;
                Lesson lesson4 = myLevel.getLevel().get(lessonPlaying);
//                myList.add("F:/englishApp/test2.wav");
                myList.clear(); // xóa danh sách nhạc đang phát nếu có
                for(Part p: lesson4.getPaths()){
                    myList.add((parameters.path + p.getPath()));
                }
                break;
        }
        updateHistory();
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //open dialog OPEN FILE and add list file selected to PlayList
    
//    private void openMusic(){
//        String p,name="";
//         JFileChooser fileChooser = new JFileChooser();
//         fileChooser.setMultiSelectionEnabled(true);
//         fileChooser.setFileFilter(new SoundFilter());
//         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//         int r=fileChooser.showDialog(PlayLesson.this,"Add File(s)");
//         if(r==fileChooser.APPROVE_OPTION){
//             if(fileChooser.isMultiSelectionEnabled()){
//                File []listFile=fileChooser.getSelectedFiles();
//                if(listFile!=null && listFile.length>0){                    
//                    for(int i=0;i<listFile.length;i++){
//                        if(SoundFilter.isMusicFile(listFile[i].getName())){
//                            p=listFile[i].getPath();
//                            p=p.substring(0,p.lastIndexOf("\\")+1);
//                            path.add(p);
//
//                            name=listFile[i].getName();
//                            System.out.println(name);
//                            listModel.addElement(name);
//                        }
//                    }
//                    if(listModel.isEmpty())
//                        JOptionPane.showMessageDialog(this,"B\u1EA1n \u0111\343 ch\u1ECDn c\341c t\u1EC7p kh\364ng \u0111\u01B0\u1EE3c h\u1ED7 tr\u1EE3!","Th\364ng b\341o l\u1ED7i!",JOptionPane.OK_OPTION);
//                    else{
//                        if(pause==false && playing==false){
//                            thread=new Thread(this);
//                            thread.start();
//                        }
//                    }
//                }
//             }
//         }
//    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void updateHistory(){
        try {
            String table = "history";
            String row = "level_id,lesson_id,topic,score,created_at";
            String values = Flag.getLevel() + "," + Flag.getLesson() + "," + "'"+
                    myLevel.getLevel().get(lessonPlaying).getNameLesson()+"'"+"," + this.score +
                    ","+ "'" + this.timeNow + "'";
            QueryDatabase.insert(table, row, values);
        } catch (SQLException ex) {
            Logger.getLogger(PlayLesson.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayLesson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void run() {
        do{
            // load các path file nhạc 
//            System.out.println(partPlaying);
//            for(String l : myList){
//                System.out.println(l);
//            }
            if(LoadSound(myList.get(partPlaying))){
                answered = false;
                Play();
            }   
            try { 
                thread.sleep(50); 
            } catch (InterruptedException e) 
            {
                System.out.print("Run_Error: "+e); 
                e.printStackTrace();   
                Stop();
                break;
            }
            System.out.println("Không tìm thấy file");
        }while(thread!=null);
        thread = new Thread(this);
        thread.start();
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //load các file nhạc
    private boolean LoadSound(String filePath){
        boolean result=false;
        loopFile =new File(filePath);
        if(!loopFile.exists())
            System.err.print("Can't find file: "+filePath);
        else{
            SoundFilter sf = new SoundFilter();
            try{
                sf.isMusicFile(loopFile.getName());
                
            }catch(myexception.MyException me){
                me.getMessage();
                Stop();
        // xóa list các lesson
                reset();
                jf.choseLesson();
            }
            try {
                currentFile = AudioSystem.getAudioInputStream(loopFile);
            } catch (Exception ex) {                
                System.out.print("LoadSound_1_Error: "+ex);
                ex.printStackTrace();
                Stop();
            }
            try {
                AudioInputStream stream=(AudioInputStream)currentFile;
                AudioFormat format=stream.getFormat();
                if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                        (format.getEncoding() == AudioFormat.Encoding.ALAW))
                    {
                        AudioFormat tmp = new AudioFormat(
                                                  AudioFormat.Encoding.PCM_SIGNED,
                                                  format.getSampleRate(),
                                                  format.getSampleSizeInBits() * 2,
                                                  format.getChannels(),
                                                  format.getFrameSize() * 2,
                                                  format.getFrameRate(),
                                                  true);
                        stream = AudioSystem.getAudioInputStream(tmp, stream);
                        format = tmp;
                    }
                    DataLine.Info info = new DataLine.Info(
                                              Clip.class,
                                              stream.getFormat(),
                                              ((int) stream.getFrameLength() *
                                                  format.getFrameSize()));

                    clip = (Clip) AudioSystem.getLine(info);
                    clip.open(stream);
                    currentFile = clip;
                    setDuration((int)clip.getMicrosecondLength()/1000000);
                    sliderMusic.setMaximum(getDuration());
                    result=true;
            } catch (Exception e) {currentFile=null;   System.out.print("LoadSound_2_Error: "+e);    e.printStackTrace();    return false;}
        }
        return result;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void Play(){
        Clip clip = getClip();
        timeLesson += (long)(clip.getMicrosecondLength()/1000);
        getMusicPosition();
        setTimer();
        setStatus();
        lbTotalTime.setText(getTotalTime());
        timer.start();
        clip.start();
        sliderMusic.setToolTipText("Playing");
        playing=true;
        label1.setText("You are doing lesson: " + Flag.getLesson() + " \npart of " + (partPlaying+1));
        lbTopic.setText("Topic of lesson: " + myLevel.getLevel().get(lessonPlaying).getNameLesson());
        lbFileName.setText(fileName);
        play.setIcon(new ImageIcon(getClass().getResource("/img/Pause.png")));
        play.setActionCommand("Pause");
        play.setToolTipText("T\u1EA1m d\u1EEBng!");
        
        try {
            getThread().sleep(50); //
        } catch (InterruptedException e) { System.out.print("PlaySound_1_Error: "+e);e.printStackTrace();}
        System.out.println("check Bug");
        Stop();
        System.out.println("check Bug2"); // cái này ko chạy
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // tiếp tục play
    public void Continuous(){
        pause=false;
        getClip().start();
        getTimer().start();
        play.setActionCommand("Pause");
        play.setIcon(new ImageIcon(getClass().getResource("/img/Pause.png")));
        play.setToolTipText("T\u1EA1m d\u1EEBng!");
        textArea.setEditable(true);
        try {
            getThread().sleep(50);
        } catch (InterruptedException ex) {Logger.getLogger(PlayLesson.class.getName()).log(Level.SEVERE, null, ex);}
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //dừng nhạc
    public void Stop(){
        Thread thread=getThread();
        if(thread!=null){
            thread.stop();
            thread = null;
            pause=false;
            playing=false;
            play.setIcon(new ImageIcon(getClass().getResource("/img/Play.png")));
            play.setActionCommand("Play");
            play.setToolTipText("");
            label1.setText("");
            lbTopic.setText("Topic of lesson:");
            lbFileName.setText("click to play");
            getTimer().stop();
            lbDuration.setText("");
            lbTotalTime.setText("");
            second=0;
            minute=0;
            sliderValue=0;
            sliderMusic.setValue(0);
            clip.stop();
            clip.close();
            currentFile=null;
            sliderMusic.setToolTipText("Stopping");
        }
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // tạm dừng
    public void Pause(){
        pause=true;
        play.setIcon(new ImageIcon(getClass().getResource("/img/Play.png")));
        play.setActionCommand("Play");
        play.setToolTipText("Ch\u1EA1y ti\u1EBFp!");
        timer.stop();
        thread.stop();
        clip.stop();
        textArea.setEditable(false);
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // button next để next các lesson, không dùng với các phần nghe
    public void Next(){
        // nếu ấn next thì set cờ hiệu là lại cờ hiệu
        milis1 = System.currentTimeMillis();
        
        if(partPlaying < myList.size()-1){
            this.score =0;
            Stop();
            JOptionPane.showMessageDialog(this.p1,"Bạn chưa làm xong nên Your Score = 0! Thân","Your IQ",1);
        }
        reset();
        timeLesson = 0;
        
        if(Flag.getLesson() < Flag.MAXLESSON){
            int flag = Flag.getLesson() + 1;
            Flag.setLesson(flag);
            lessonPlaying = Flag.getLesson()-1;
            Stop();
            addMusic();
            thread=new Thread(this);
            thread.start();
        }
        //nếu đã tới giới hạn thì quay trở lại ban đầu
        else if(Flag.getLesson() == Flag.MAXLESSON){
            Flag.setLesson(parameters.flagLesson1);
            lessonPlaying = Flag.getLesson()-1;
            Stop();
            addMusic();
            thread=new Thread(this);
            thread.start();
        }
        
        System.out.println("Đã next");
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // buttton pre để pre các lesson không dùng với các phần nghe.
    public void Previous(){
        // nếu ấn pre thì set cờ hiệu là lại cờ hiệu
        milis1 = System.currentTimeMillis();
        if(partPlaying < myList.size()-1){
            this.score =0;
            Stop();
            JOptionPane.showMessageDialog(this.p1,"Bạn chưa làm xong nên Your Score = 0! Thân","Your IQ",1);
        }
        reset();
        timeLesson = 0;
        if(Flag.getLesson() > 1){
            int flag = Flag.getLesson() - 1;
            Flag.setLesson(flag);
            lessonPlaying = Flag.getLesson()-1;
            Stop();
            addMusic();
            thread=new Thread(this);
            thread.start();
        }
        else if(Flag.getLesson() == 1){
            Flag.setLesson(parameters.flagLesson4);
            lessonPlaying = Flag.getLesson()-1;
            Stop();
            addMusic();
            thread=new Thread(this);
            thread.start();
        }
        
        System.out.println("Đã next");
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void loop(){
        if(!myList.isEmpty()){
            thread = new Thread(this);
            thread.start();
        }
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setMusicPosition(){
        setSliderMusicValue(sliderMusic.getValue());
        getClip().setMicrosecondPosition(getSliderMusicValue()*1000000);
        getTimer().stop();
        getMusicPosition();
        setTimer();
        getTimer().start();
    }
    
    public void getMusicPosition(){
        int s=0;
        s=(int)(getClip().getMicrosecondPosition()/1000000);
        setMinute(s/60);
        second=s-(getMinute()*60);
    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // set timer start to get time play when click play button
    public void setTimer(){
        timer=new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setSecond();
            }
        });
    }

    public Timer getTimer(){
        return timer;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public void setSecond(){
	if(second==59){
            second=-1;
            minute++;
	}
	second++;
        sliderValue++;
        setStatus();
    }

    public int getSecond(){
        return second;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void setStatus(){
        String s,m;
        int sec=getSecond();
        int min=getMinute();
        int sldValue=getSliderMusicValue();
        if(sec<10)
            s="0"+sec;
        else s=""+sec;
        if(min<10)
            m="0"+min;
        else m=""+min;
        if(sldValue==getDuration()+1){
            Stop();
            loop();
        }
        else{
            setLbDurationValue(m+":"+s);
            sliderMusic.setValue(sldValue);
        }
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setLbDurationValue(String value){
        lbDuration.setText(value);
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setDuration(int value){
        duration=value;
    }

    public int getDuration(){
        return duration;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setTrackNum(int value){
        trackNum=value;
    }
     public int getTrackNum(){
        return trackNum;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getTotalTime(){
        int sec,min;
        String s,m;
        min=getDuration()/60;
        sec=getDuration()-(min*60);
        if(min<10)
            m="0"+min;
        else m=""+min;
        if(sec<10)
            s="0"+sec;
        else s=""+sec;
        return m+":"+s;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
//    private String getPath(){
//        String f=null;
//        //if PlayList is not empty and none item selected, select item first in PlayList and return item selected
//        if(!listModel.isEmpty()){
//            setTrackNum(0);
//            
//            f=(String)path.get(getTrackNum());
//
//        }
//        // if PlayList is not empty and selected item, return item selected
//        else if(!listModel.isEmpty()){
//            f=(String)path.get(getTrackNum());
//
//        }
//        return f;
//    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setFileName(String value){
        fileName=value;
    }
    public String getFileName(){
        return fileName;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Clip getClip(){
        return (Clip)getCurrentFile();
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Object getCurrentFile(){
        return currentFile;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setSliderMusicValue(int value){
        sliderValue=value;
    }
    
    public int getSliderMusicValue(){
        return sliderValue;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setMinute(int value){
        minute=value;
    }

    public int getMinute(){
        return minute;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Thread getThread(){
        return thread;
    }
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
//    public static void main(String[]a){
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        JDialog.setDefaultLookAndFeelDecorated(true);
//	JFrame f=new JFrame();        
//        f.setTitle("Nghe nh\u1EA1c v\u1EDBi Java!");
//        f.setBackground(Color.yellow);
//        f.setResizable(false);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	JComponent js = new PlayLesson(jf);
//        js.setOpaque(true);
//        f.setContentPane(js);
//        f.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//        f.setBounds(330,180,550,300);
//	f.setVisible(true);
//    }
}
