package com.hit.chatserverside;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class ChatServerActivity extends Activity {

    Socket socket=null;
    ServerSocket server=null;
    InputStream is=null;
    DataInputStream dis=null;
    OutputStream os=null;
    DataOutputStream dos=null;
    public TextView log;
    Handler mHandler=new Handler(); ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_server);
        log=(TextView)findViewById(R.id.log);


        log.setText("server start \n");
        log.setText(getIpAddress()+"\n");

        new Thread(new server()).start();
        // log.setText(server.getLocalPort());
    }
    public class server implements Runnable{
        @Override
        public void run() {
            try {
                server=new ServerSocket(8080);
                int port=server.getLocalPort();
                mHandler.post(new updateUIThread("server port: "+port));

                Log.i("info", "ip address : " + getLocalIpAddress());
                while (true){
                    socket=server.accept();
                    Log.i("info", "have connection");
                    mHandler.post(new updateUIThread("have connection"));
                    is=socket.getInputStream();
                    dis=new DataInputStream(is);

                    os=socket.getOutputStream();
                    dos=new DataOutputStream(os);

                    String userName=dis.readUTF();
                    String reply="server: "+userName;
                    mHandler.post(new updateUIThread(userName));
                    dos.writeUTF(reply);
                    Log.i("info", reply);

                }
            }catch (IOException e){e.printStackTrace();}finally {
                //close resources
            }
        }
    }
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Address: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {

            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    public class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override

        public void run() {
            log.setText(log.getText()+msg+"\n");
        }

    }
}
