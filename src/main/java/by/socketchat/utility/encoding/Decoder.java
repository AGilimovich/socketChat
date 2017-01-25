package by.socketchat.utility.encoding;

import java.io.UnsupportedEncodingException;

/**
 * Created by Администратор on 13.12.2016.
 */
public class Decoder {

//
//    public static String decode(String encoded) {
//        byte[] bytes = new byte[0];
//
//        bytes = encoded.getBytes();
//
//        byte secondByte = bytes[1];
//
//        int length = secondByte & 127; // may not be the actual length in the two special cases
//
//        int indexFirstMask = 2;          // if not a special case
//
//        if (length == 126) {           // if a special case, change indexFirstMask
//            indexFirstMask = 4;
//        } else if (length == 127)       // ditto
//            indexFirstMask = 10;
//
//
//        byte[] masks = new byte[4];
//        for (int i = 0; i < 4; i++) {
//            masks[i] = bytes[indexFirstMask + i];// four bytes starting from indexFirstMask
//        }
//        int indexFirstDataByte = indexFirstMask + 4; // four bytes further
//
//        int decodedLength = bytes.length - indexFirstDataByte;// length of real data
//        byte[] decoded = new byte[decodedLength];
//
//        for (int i = indexFirstDataByte, j = 0; i < bytes.length; i++, j++) {
//            decoded[j] = (byte) (bytes[i] ^ masks[j % 4]);
//        }
//        String decodedString = null;
//        try {
//            decodedString = new String(decoded, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return decodedString;
//    }


    public static String decode(byte[] encoded) {


        byte[] decoded = new byte[encoded.length - 4];
        byte[] mask = new byte[4];
        System.arraycopy(encoded, 0, mask, 0, 4);


        for (int i = 4, j = 0; i < encoded.length; i++, j++) {
            decoded[j] = (byte) (encoded[i] ^ mask[j % 4]);

        }
        String decodedString = null;
        try {
            decodedString = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedString;
    }


}
