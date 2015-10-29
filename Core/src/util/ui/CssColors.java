package util.ui;

/**
 * Created by Andrii.Bakhtiozin on 08.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class CssColors {

    private static String[] parseStringToArray(String rgb) {
        return rgb.replaceAll("[A-z\\s]", "").replaceAll("\\(|\\)", "").split(",");
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    private static int[] convertToIntArray(String[] strings) {
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    public static String getHex(String rgb) {
        String[] strings = parseStringToArray(rgb);
        int[] ints = convertToIntArray(strings);
        if (ints.length == 3) return toHex(ints[0], ints[1], ints[2]);
        if (ints.length == 2) return toHex(ints[0], ints[1]);
        if (ints.length == 1) return toHex(ints[0]);
        return null;
    }

    private static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }
    private static String toHex(int r, int g) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g);
    }
    private static String toHex(int r) {
        return "#" + toBrowserHexValue(r);
    }
}
