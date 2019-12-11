package myexception;


import java.io.File;

public class SoundFilter extends MyException{
    public boolean accept(File file) throws MyException{
        if(file.isDirectory())
            return true;
        String extension=getExtension(file);
        if(!extension.equals("wav")){
            throw new MyException("This is not a WAV file music");
        }
        else return true;
    }

    public String getDescription() {
        return "*.wav";
    }
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    public boolean isMusicFile(String name) throws MyException{
            boolean is=false;
            if (name.endsWith(".wav")) {
                is= true;
            }else {
                throw new MyException("This is not a WAV file!");
//                JOptionPane.showMessageDialog(parent,"This is not a WAV file!");
            }
            return is;
    }
    
}
