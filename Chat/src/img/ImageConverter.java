package img;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * @author marco
 */
public class ImageConverter {

    File imagePath;
    BufferedImage bufferedImage;
    ByteArrayOutputStream baos;
    ByteArrayInputStream bais;
    int width, heigth;

    /**
     * Costruttore.
     */
    public ImageConverter() {
    }

    /**
     *
     * @param imageByteArray
     * @return
     */
    public BufferedImage convertByteToBufferedImage(byte[] imageByteArray) {
        try {
            bais = new ByteArrayInputStream(imageByteArray);
            bufferedImage = ImageIO.read(bais);
        } catch (IOException e) {
        }
        return bufferedImage;
    }

    /**
     * Converte un percorso in una stringa di byte.
     *
     * @param absolutePath
     * @param extension
     * @return
     */
    public String getStringImage(String absolutePath, String extension) {
        byte[] stringToByte = null;
        try {
            imagePath = new File(absolutePath);
            bufferedImage = ImageIO.read(imagePath);

            width = bufferedImage.getWidth();
            heigth = bufferedImage.getHeight();
            bufferedImage = scale(bufferedImage, 250, 250 * heigth / width);

            baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, baos);

            stringToByte = baos.toByteArray();

        } catch (IOException ex) {
        }
        return new String(stringToByte);
    }

    /**
     * Converte un percorso in un array di byte.
     *
     * @param absolutePath
     * @param extension
     * @return
     */
    public byte[] getByteImage(String absolutePath, String extension) {
        byte[] stringToByte = null;
        try {
            imagePath = new File(absolutePath);
            bufferedImage = ImageIO.read(imagePath);

            width = bufferedImage.getWidth();
            heigth = bufferedImage.getHeight();
            bufferedImage = scale(bufferedImage, 250, 250 * heigth / width);

            baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, baos);

            stringToByte = baos.toByteArray();

        } catch (IOException ex) {
        }
        return stringToByte;
    }

    /**
     * Scala le dimensione dell'immagine.
     *
     * @param img
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;

        int w = img.getWidth();
        int h = img.getHeight();

        int prevW = w;
        int prevH = h;

        do {
            if (w > targetWidth) {
                w /= 2;
                w = (w < targetWidth) ? targetWidth : w;
            }

            if (h > targetHeight) {
                h /= 2;
                h = (h < targetHeight) ? targetHeight : h;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }

        return ret;

    }

}
