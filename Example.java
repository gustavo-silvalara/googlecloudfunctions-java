package com.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.io.OutputStream;
import java.util.Random;

public class Example implements HttpFunction {
    private final BufferedImage[] images = new BufferedImage[]{
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108679199_102795501515736_1005443514310814088_n.jpg?_nc_cat=100&_nc_sid=8024bb&_nc_ohc=h9i4zVTVe6kAX_VG5y7&_nc_ht=scontent.fcgb1-1.fna&oh=a3a145b6c2425fe9eb4185f6ff51087e&oe=5F337E9F"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109121327_102795561515730_502558351292667285_n.jpg?_nc_cat=103&_nc_sid=8024bb&_nc_ohc=bl0byXMIMAoAX9GQpaE&_nc_ht=scontent.fcgb1-1.fna&oh=303c2e54ecb9d2c9e0ca7213f798849b&oe=5F32610A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108912600_102795578182395_4549291850005123040_n.jpg?_nc_cat=109&_nc_sid=8024bb&_nc_ohc=QZU1Whsf-aAAX8zmLPK&_nc_ht=scontent.fcgb1-1.fna&oh=289fb6ff92846aeb51bc4c2b4a200862&oe=5F334EF8"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109236629_102796134849006_2265602311635909715_n.jpg?_nc_cat=111&_nc_sid=8024bb&_nc_ohc=l-Ltc73FxPEAX_7R7Ju&_nc_ht=scontent.fcgb1-1.fna&oh=8311d21026765f65181f0d1217a24d09&oe=5F31671A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108274671_102796198182333_7388681151791472633_n.jpg?_nc_cat=103&_nc_sid=8024bb&_nc_ohc=pfl24xLNTiwAX-ArYfS&_nc_ht=scontent.fcgb1-1.fna&oh=714b6d413a4a506c4468e6211c16c438&oe=5F31CA07"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109230618_102796214848998_7811958068455241291_n.jpg?_nc_cat=110&_nc_sid=8024bb&_nc_ohc=6Rg13mNqjzYAX9cuJTs&_nc_ht=scontent.fcgb1-1.fna&oh=3ec189a9a3fad7b3f573d4c23716e9b7&oe=5F300343"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108487832_102796261515660_9099855596678652030_n.jpg?_nc_cat=108&_nc_sid=8024bb&_nc_ohc=4V4QkvYHbgcAX8iqXkz&_nc_ht=scontent.fcgb1-1.fna&oh=6ad447c6fbc302817a352d19567578a8&oe=5F322DCF")
    };

    private final Random generator = new Random();

    public Example() throws IOException {
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        OutputStream out = response.getOutputStream();
        response.appendHeader("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
        response.appendHeader("Pragma", "no-cache");
        response.appendHeader("Expires", "0");
        response.appendHeader("Surrogate-Control", "no-store");
        response.setContentType("image/jpeg");
        BufferedImage image = getRandomImage();
        ImageIO.write(image, "jpeg", out);
        out.close();
    }

    private BufferedImage getImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url.openStream());
        return image;
    }

    private BufferedImage getRandomImage() {
        int i = generator.nextInt(images.length);
        return images[i];
    }
}