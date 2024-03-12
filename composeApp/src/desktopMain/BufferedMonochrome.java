public class BufferedMonochrome {
    public static BufferedImage createMonochromeImage(BufferedImage src) {
        return createMonochromeImage(src, Color.white);
    }

    public static BufferedImage createMonochromeImage(BufferedImage src,
                                                      Color tgt) {
        BufferedImage result = new BufferedImage(src.getWidth(),
                src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int r1 = tgt.getRed();
        int g1 = tgt.getGreen();
        int b1 = tgt.getBlue();

        for (int i = 0; i < src.getWidth(); i++)
            for (int j = 0; j < src.getHeight(); j++) {
                int rgb = src.getRGB(i, j);

                int a = 0xFF & (rgb >> 24);
                int r = 0xFF & (rgb >> 16);
                int g = 0xFF & (rgb >> 8);
                int b = 0xFF & rgb;

                double grey = (76.0 * r + 150.0 * g + 28.0 * b) / 256 / 256;

                r = (int) (grey * r1);
                g = (int) (grey * g1);
                b = (int) (grey * b1);

                rgb = (a << 24) | (r << 16) | (g << 8) | b;
                result.setRGB(i, j, rgb);
            }

        return result;
    }
}
