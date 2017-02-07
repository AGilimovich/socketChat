package by.socketchat.utility.encoding;

//UTILITY CLASS
import java.io.UnsupportedEncodingException;

public class Decoder {

    private Decoder() {
        throw new AssertionError();
    }


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
