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
//import com.sun.media.sound.DataPusher;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
// 
//public class Client {
//    
//   
//    
//    
//    public final static String SERVER_IP = "192.168.1.9";
//    public final static int SERVER_PORT = 7; // Cổng mặc định của Echo Server
//    public final static byte[] BUFFER = new byte[4096]; // Vùng đệm chứa dữ liệu cho gói tin nhận
// 
//    public static void main(String[] args) {
//        String filePath = "C:\\Users\\Computer's Tien\\Documents\\NetBeansProjects\\App_Learn_English\\src\\audio\\test.wav";
//        File file = new File(filePath);
////        Object currentFile = null;
////        AudioFormat tmp ;
////        try {
////                currentFile = AudioSystem.getAudioInputStream(file);
////            } catch (Exception ex) {                
////                System.out.print("LoadSound_1_Error: "+ex);
////                ex.printStackTrace();
////            }
////        try {
////                AudioInputStream stream=(AudioInputStream)currentFile;
////                AudioFormat format=stream.getFormat();
////                if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
////                        (format.getEncoding() == AudioFormat.Encoding.ALAW))
////                    {
////                        tmp = new AudioFormat(
////                                                  AudioFormat.Encoding.PCM_SIGNED,
////                                                  format.getSampleRate(),
////                                                  format.getSampleSizeInBits() * 2,
////                                                  format.getChannels(),
////                                                  format.getFrameSize() * 2,
////                                                  format.getFrameRate(),
////                                                  true);
////                        stream = AudioSystem.getAudioInputStream(tmp, stream);
////                        format = tmp;
////                    }
//////                    DataLine.Info info = new DataLine.Info(
//////                                              Clip.class,
//////                                              stream.getFormat(),
//////                                              ((int) stream.getFrameLength() *
//////                                                  format.getFrameSize()));
////
////                    
////            } catch (Exception e) {
////                currentFile=null;   
////                System.out.print("LoadSound_2_Error: "+e);    
////                e.printStackTrace();
////            
////            }
//        
//        
//        
//        
//        DatagramSocket ds = null;
//        FileInputStream fis = null;
//        byte[] data = new byte[(int) file.length()]; // Đổi chuỗi ra mảng bytes
//                try {
//            // convert file into array of bytes
//            fis = new FileInputStream(file);
//            fis.read(data);
//            fis.close();
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        };
//        try {
//            ds = new DatagramSocket(); // Tạo DatagramSocket
//            System.out.println("Client started ");
//            
//            InetAddress server = InetAddress.getByName(SERVER_IP);
//            byte[] dataTemp = new byte[10000];
//            int check  = data.length;
//            int start = 0;
//            while (true) {
//                int j=0;
//                for(int i=start;i<start+10000;i++){
//                    
//                 dataTemp[j] = data[i];
//                 j++;
//                }
//                start = start + 10000;
//                check = check - 10000;
//                // Tạo gói tin gởi
//                DatagramPacket dp = new DatagramPacket(dataTemp, dataTemp.length, server, SERVER_PORT);
//                ds.send(dp); // Send gói tin sang Echo Server
//                System.out.println("send");
//                // Gói tin nhận
//                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
//                ds.receive(incoming); // Chờ nhận dữ liệu từ EchoServer gởi về
// 
//                // Đổi dữ liệu nhận được dạng mảng bytes ra chuỗi và in ra màn hình
//                System.out.println("Received: " + new String(incoming.getData(), 0, incoming.getLength()));
//                if(check < 10000) break;
//            }
//        } catch (IOException e) {
//            System.err.println(e);
//        } finally {
//            if (ds != null) {
//                ds.close();
//            }
//        }
//    }
//}
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
 
public class Client {
    public final static String SERVER_IP = "10.5.51.217";
    public final static int SERVER_PORT = 5000;
 
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
            System.out.println("Connected: " + socket);
 
            InputStream is = socket.getInputStream();
            
            OutputStream os = socket.getOutputStream();
            File file = new File("F:\\englishApp\\test.wav");
            byte[] dataFile = new byte[(int)file.length()];
//            for (int i = '0'; i <= '9'; i++) {
//            for(int i=0; i<dataFile.length;i++){
            os.write(dataFile); // Send each number to the server
//                System.out.println((int)dataFile[i]);
//                int ch = is.read(); // Waiting for results from server
//                System.out.print((int) ch + " "); // Display the results received from the server
//                Thread.sleep(200);
//            }
//            }
//            os.write(-1);
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
