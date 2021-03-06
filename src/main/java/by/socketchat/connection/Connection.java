package by.socketchat.connection;

import java.io.*;
import java.net.Socket;

/**
 * Created by Администратор on 29.11.2016.
 */
public class Connection extends Thread implements IConnection {

    private BufferedReader in;
    private OutputStream out;
    private InputStream byteIn;
    private int buffSize;


    private final int DEFAULT_BUF_SIZE = 1024;


    public Connection(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        byteIn = socket.getInputStream();
        this.in = new BufferedReader(new InputStreamReader(byteIn, "UTF-8"));
        buffSize = DEFAULT_BUF_SIZE;

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


    @Override
    public synchronized String read() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buf = new byte[buffSize];
        char[] temp = new char[1];
        int res = 0;

        while ((res = byteIn.read(buf)) != -1) {
            sb.append(new String(buf, "UTF-8"));
            break;
        }
        return sb.toString();
    }


    public synchronized byte[] readEncoded() throws IOException {


        int size = 10;
        byte[] data = new byte[size];
        byte[] output = null;

        if (byteIn.read(data, 0, 10) == -1) {
            return null;
        }
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


    public int getBuffSize() {
        return buffSize;
    }

    public void setBuffSize(int bufSize) {
        this.buffSize = bufSize;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        byteIn.close();
    }

}
