package com.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.io.OutputStream;
import java.util.Random;

public class Example implements HttpFunction {
    private final RenderedImage[] images = new RenderedImage[]{
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109339098_105103561284930_3711286213894012668_n.jpg?_nc_cat=107&ccb=2&_nc_sid=730e14&_nc_ohc=5DEv4G9OxSAAX-5_DWW&_nc_ht=scontent.fcgb1-1.fna&oh=02550ce084643bea449c87c950369705&oe=5FEDD526"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109563057_105103664618253_5996136612806192148_n.jpg?_nc_cat=107&ccb=2&_nc_sid=730e14&_nc_ohc=fJfP-alwIl0AX83wvpU&_nc_ht=scontent.fcgb1-1.fna&oh=bde9a073e2c54286f96d9d5498b3b57e&oe=5FF00984"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109243316_105103727951580_8699521010025598196_n.jpg?_nc_cat=106&ccb=2&_nc_sid=730e14&_nc_ohc=T8BFl92P5sQAX-szoTk&_nc_oc=AQmoXrOmrfi8mjpgD30hGwadsgZ9I1qI6SglKgMpMArYoiHHi-f27VWzagQ60LEQKBBv179VojdE_WXNp3Fg4YPP&_nc_ht=scontent.fcgb1-1.fna&oh=82ac37e3e178af86e3250b9d494515ee&oe=5FECC889"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109283370_105103807951572_8370835185566854751_n.jpg?_nc_cat=106&ccb=2&_nc_sid=730e14&_nc_ohc=P1jXxArrc20AX9Po30j&_nc_ht=scontent.fcgb1-1.fna&oh=bfa7502883c87a468b1f0b63d48c50d3&oe=5FEFBD01"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109235814_105103847951568_5002705133834032613_n.jpg?_nc_cat=105&ccb=2&_nc_sid=730e14&_nc_ohc=zOJuQS4tlD0AX__o4xj&_nc_ht=scontent.fcgb1-1.fna&oh=7e8783b0915a13e1fb321c16aa8cd529&oe=5FECE42F"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/110288696_105103927951560_2643383424111454943_n.jpg?_nc_cat=100&ccb=2&_nc_sid=730e14&_nc_ohc=RAhGApLbhccAX9CBMEH&_nc_ht=scontent.fcgb1-1.fna&oh=ba6dfd7caa70e0edb289b0dc8bf8919a&oe=5FF03CF1"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109313082_105104021284884_8776167092070014960_n.jpg?_nc_cat=109&ccb=2&_nc_sid=730e14&_nc_ohc=zCEs9fwsN9sAX99QePF&_nc_ht=scontent.fcgb1-1.fna&oh=74116701ec8f9db685f394dd87409d7d&oe=5FEE4548"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109961599_105104054618214_7574600193431005938_n.jpg?_nc_cat=107&ccb=2&_nc_sid=730e14&_nc_ohc=CImjaZJVoCAAX8g5lgL&_nc_ht=scontent.fcgb1-1.fna&oh=0f4e1bf9dcf7c4ee33188cff47de6a81&oe=5FEDE1A0"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108679199_102795501515736_1005443514310814088_n.jpg?_nc_cat=100&ccb=2&_nc_sid=730e14&_nc_ohc=uECPyJ6tdcoAX_3D2Fp&_nc_ht=scontent.fcgb1-1.fna&oh=a9a67ecd02c1f9787bb7fa12c1befa49&oe=5FED621F"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109121327_102795561515730_502558351292667285_n.jpg?_nc_cat=103&ccb=2&_nc_sid=730e14&_nc_ohc=ix_T-XP0kO4AX9xRN5Q&_nc_ht=scontent.fcgb1-1.fna&oh=fd7d6856dc9fdf459404179ba07a168a&oe=5FF0390A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108912600_102795578182395_4549291850005123040_n.jpg?_nc_cat=109&ccb=2&_nc_sid=730e14&_nc_ohc=vU29aGd0zB8AX8R8pIF&_nc_ht=scontent.fcgb1-1.fna&oh=22bde183138cbb800dae8153f580391c&oe=5FED3278"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109236629_102796134849006_2265602311635909715_n.jpg?_nc_cat=111&ccb=2&_nc_sid=730e14&_nc_ohc=vWiwSaqbP5sAX-p50dU&_nc_ht=scontent.fcgb1-1.fna&oh=26c8e7de7f9ba7edcc3cc13a287c5c73&oe=5FEF3F1A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108274671_102796198182333_7388681151791472633_n.jpg?_nc_cat=103&ccb=2&_nc_sid=730e14&_nc_ohc=tghfi_6cRqsAX97foiG&_nc_ht=scontent.fcgb1-1.fna&oh=39cc931c7e07140e0dec7081bd20d477&oe=5FEFA207"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109230618_102796214848998_7811958068455241291_n.jpg?_nc_cat=110&ccb=2&_nc_sid=730e14&_nc_ohc=9fGneq1sRfYAX_6-REN&_nc_ht=scontent.fcgb1-1.fna&oh=024a1436c36cb025473c0349d42e8ef4&oe=5FEDDB43"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108487832_102796261515660_9099855596678652030_n.jpg?_nc_cat=108&ccb=2&_nc_sid=730e14&_nc_ohc=PyZBbfppiysAX9B9gA5&_nc_ht=scontent.fcgb1-1.fna&oh=4fc77eb69b3a5aad9f9c8f6d6bb4511f&oe=5FF005CF"),
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
        RenderedImage image = getRandomImage();
        ImageIO.write(image, "jpeg", out);
        out.close();
    }

    private RenderedImage getImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        RenderedImage image = ImageIO.read(url.openStream());
        return image;
    }

    private RenderedImage getRandomImage() {
        int i = generator.nextInt(images.length);
        return images[i];
    }
}
