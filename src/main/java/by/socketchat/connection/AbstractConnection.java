package by.socketchat.connection;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Администратор on 29.11.2016.
 */
public abstract class AbstractConnection extends Thread implements IConnection {

    private BufferedReader in;
    private OutputStream out;
    private InputStream byteIn;

    private int DEFAULT_BUF_SIZE = 1024;


    public AbstractConnection(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        byteIn = socket.getInputStream();
        this.in = new BufferedReader(new InputStreamReader(byteIn, "UTF-8"));

    }

    @Override
    public void write(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    protected String readLine() {
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }


//    @Override
//    public synchronized String read() throws IOException {
//        StringBuilder sb = new StringBuilder();
//        char[] buf = new char[DEFAULT_BUF_SIZE];
//        int res = 0;
//        char[] temp = new char[1];
//        byte tt = 0;
//        if (in ==null) {
//
//        }
//
//        while (in.ready() && (res = in.read()) != -1) {
//            temp[0] = (char) res;
//            tt = (byte) res;
//            sb.append((char) res);
//
//        }
//        return sb.toString();
//    }


    @Override
    public synchronized String read() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buf = new byte[DEFAULT_BUF_SIZE];
        char[] temp = new char[1];
        int res = 0;

        while ((res = byteIn.read(buf)) != -1) {
            System.out.println("Reading");
            sb.append(new String(buf, "UTF-8"));
            break;
        }
        return sb.toString();
    }


    //
    @Override
    public synchronized byte[] readEncoded() throws IOException {


        int size = 10;
        byte[] data = new byte[size];
        byte[] output = null;

//        for (int i = 0; i < 10; i++) {
        if (byteIn.read(data, 0, 10) == -1) {
            return null;
        }
//        }
        long dataLen = getDataLen(data);
        long count = 0;
        if (dataLen < 126) {
            output = new byte[(int) dataLen + 4];
            System.arraycopy(data, 2, output, 0, 4); //mask
            if (dataLen <= 4) {
                System.arraycopy(data, 6, output, 4, (int) dataLen);
                count = 0;
            } else {
                System.arraycopy(data, 6, output, 4, 4);
                count = dataLen - 4;
            }

        } else if (dataLen < 65535) {
            output = new byte[(int) dataLen + 4];
            System.arraycopy(data, 2, output, 0, 4); //mask

            System.arraycopy(data, 6, output, 4, 4);
            count = dataLen - 4;


        } else {
            //TODO
        }
        if (count == 0) return output;
        data = new byte[(int) count];

        byteIn.read(data, 0, (int) count);

        System.arraycopy(data, 0, output, 8, (int) count);
        return output;

    }


    private long getDataLen(byte[] encoded) {
        byte secondByte = encoded[1];
        long length = secondByte & 127;
        if (length == 126) {
            length = encoded[2] << 8 | encoded[3];
        } else if (length == 127) {
            length =
                    encoded[2] << 56 |
                            encoded[3] << 48 |
                            encoded[4] << 40 |
                            encoded[5] << 32 |
                            encoded[6] << 24 |
                            encoded[7] << 16 |
                            encoded[8] << 8 |
                            encoded[9];


        }
        return length;
    }

    @Override
    public boolean isActive() {
        try {
            out.write('\u0000');
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public int getDEFAULT_BUF_SIZE() {
        return DEFAULT_BUF_SIZE;
    }

    public void setDEFAULT_BUF_SIZE(int DEFAULT_BUF_SIZE) {
        this.DEFAULT_BUF_SIZE = DEFAULT_BUF_SIZE;
    }


    protected void onDestroy() throws IOException {
        in.close();
        out.close();
        byteIn.close();
    }

}
