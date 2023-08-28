public class PhotoMagicDeluxe {

    private static final String password = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void main(String[] args) {

        char[] chars = new char[10];
        String[] strings = new String[10];
        int[] x = new int[10];
        int[] y = new int[6];
        Picture picture  = new Picture("pipe.png");

        String BinaryInput = "";
        String binaryPass = args[1];
        int tap = Integer.parseInt(args[2]);

        for (int i = 0; i < binaryPass.length(); i++) {

            chars[i] = binaryPass.charAt(i);
            x[i] = password.indexOf(chars[i]);

            for (int l = 5; l >= 0; l--) {

                if (x[i] > Math.pow(2, l)) {
                    y[l] = 1;
                    x[i] = x[i] - (int) Math.pow(2, l);
                }

                strings[i] = Integer.toString(y[l]);
                BinaryInput = BinaryInput + strings[i];
            }
        }
        LFSR lfsr = new LFSR(BinaryInput, tap);
        PhotoMagic.transform(picture, lfsr);
    }
}