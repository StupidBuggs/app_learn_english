/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Computer's Tien
 */
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.DataLine;
// 
//public class Server {
//    
//    public final static int SERVER_PORT = 7; // Cổng mặc định của Echo Server
//    public final static byte[] BUFFER = new byte[4096]; // Vùng đệm chứa dữ liệu cho gói tin nhận
//    
//    
//    public boolean LoadSound(String filePath){
//        boolean result=false;
//        File file=new File(filePath);
//        Object currentFile = null;
//        if(!file.exists())
//            System.err.print("Can't find file: "+filePath);
//        else{
//            try {
//                currentFile = AudioSystem.getAudioInputStream(file);
//            } catch (Exception ex) {                
//                System.out.print("LoadSound_1_Error: "+ex);
//                ex.printStackTrace();
//               
//            }
//            try {
//                AudioInputStream stream=(AudioInputStream)currentFile;
//                AudioFormat format=stream.getFormat();
//                if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
//                        (format.getEncoding() == AudioFormat.Encoding.ALAW))
//                    {
//                        AudioFormat tmp = new AudioFormat(
//                                                  AudioFormat.Encoding.PCM_SIGNED,
//                                                  format.getSampleRate(),
//                                                  format.getSampleSizeInBits() * 2,
//                                                  format.getChannels(),
//                                                  format.getFrameSize() * 2,
//                                                  format.getFrameRate(),
//                                                  true);
//                        stream = AudioSystem.getAudioInputStream(tmp, stream);
//                        format = tmp;
//                    }
//                    DataLine.Info info = new DataLine.Info(
//                                              Clip.class,
//                                              stream.getFormat(),
//                                              ((int) stream.getFrameLength() *
//                                                  format.getFrameSize()));
//
////                    clip = (Clip) AudioSystem.getLine(info);
////                    clip.open(stream);
////                    currentFile = clip;
////                    setDuration((int)clip.getMicrosecondLength()/1000000);
////                    sliderMusic.setMaximum(getDuration());
//                    result=true;
//            } catch (Exception e) {currentFile=null;   System.out.print("LoadSound_2_Error: "+e);    e.printStackTrace();    return false;}
//        }
//        return result;
//    }
//    
//    
//    
//    public static void main(String[] args) {
//        DatagramSocket ds = null;
//        try {
//            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
//            ds = new DatagramSocket(SERVER_PORT); // Tạo Socket với cổng là 7
//            System.out.println("Server started ");
//            System.out.println("Waiting for messages from Client ... ");
// 
//            while (true) { // Tạo gói tin nhận
//                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
//                ds.receive(incoming); // Chờ nhận gói tin gởi đến
//                System.out.println("reciecves");
////                ByteArrayInputStream bInput = new ByteArrayInputStream(incoming.getData());
////                AudioInputStream stream = new AudioInputStream(AudioSystem.getAudioInputStream(bInput));
//                // Lấy dữ liệu khỏi gói tin nhận
//                FileOutputStream fos= null;
//                try {           
//            // convert array of bytes into file
//            fos = new FileOutputStream("D:\\test2.wav",true);
//            fos.write(incoming.getData());
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        };
//                String message = new String(incoming.getData(), 0, incoming.getLength());
//                System.out.println("Received: " + message);
// 
//                // Tạo gói tin gởi chứa dữ liệu vừa nhận được
//                DatagramPacket outsending = new DatagramPacket(message.getBytes(), incoming.getLength(),
//                        incoming.getAddress(), incoming.getPort());
//                ds.send(outsending);
////                break;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ds != null) {
//                ds.close();
//            }
//        }
//        
//    }
//}
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Server {
 
    public final static int SERVER_PORT = 5000;
 
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client accepted: " + socket);
 
                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    int ch = 0;
                    while (true) {
                        ch = is.read(); // Receive data from client
                        if (ch == -1) {
                            break;
                        }
                        os.write(ch); // Send the results to client
                    }
                    socket.close();
                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
 
}