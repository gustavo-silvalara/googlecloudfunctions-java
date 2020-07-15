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
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108679199_102795501515736_1005443514310814088_n.jpg?_nc_cat=100&_nc_sid=8024bb&_nc_ohc=h9i4zVTVe6kAX_VG5y7&_nc_ht=scontent.fcgb1-1.fna&oh=a3a145b6c2425fe9eb4185f6ff51087e&oe=5F337E9F"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109121327_102795561515730_502558351292667285_n.jpg?_nc_cat=103&_nc_sid=8024bb&_nc_ohc=bl0byXMIMAoAX9GQpaE&_nc_ht=scontent.fcgb1-1.fna&oh=303c2e54ecb9d2c9e0ca7213f798849b&oe=5F32610A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108912600_102795578182395_4549291850005123040_n.jpg?_nc_cat=109&_nc_sid=8024bb&_nc_ohc=QZU1Whsf-aAAX8zmLPK&_nc_ht=scontent.fcgb1-1.fna&oh=289fb6ff92846aeb51bc4c2b4a200862&oe=5F334EF8"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109236629_102796134849006_2265602311635909715_n.jpg?_nc_cat=111&_nc_sid=8024bb&_nc_ohc=l-Ltc73FxPEAX_7R7Ju&_nc_ht=scontent.fcgb1-1.fna&oh=8311d21026765f65181f0d1217a24d09&oe=5F31671A"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108274671_102796198182333_7388681151791472633_n.jpg?_nc_cat=103&_nc_sid=8024bb&_nc_ohc=pfl24xLNTiwAX-ArYfS&_nc_ht=scontent.fcgb1-1.fna&oh=714b6d413a4a506c4468e6211c16c438&oe=5F31CA07"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109230618_102796214848998_7811958068455241291_n.jpg?_nc_cat=110&_nc_sid=8024bb&_nc_ohc=6Rg13mNqjzYAX9cuJTs&_nc_ht=scontent.fcgb1-1.fna&oh=3ec189a9a3fad7b3f573d4c23716e9b7&oe=5F300343"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/108487832_102796261515660_9099855596678652030_n.jpg?_nc_cat=108&_nc_sid=8024bb&_nc_ohc=4V4QkvYHbgcAX8iqXkz&_nc_ht=scontent.fcgb1-1.fna&oh=6ad447c6fbc302817a352d19567578a8&oe=5F322DCF"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109961599_105104054618214_7574600193431005938_n.jpg?_nc_cat=107&_nc_sid=8024bb&_nc_eui2=AeHC-3GYoGB7cZs5xTBKURgM0fYKxtxMS3vR9grG3ExLe-9X_bbznWlCiW4CGg3LRmuqPauqk8vE4XzMYwhYORhc&_nc_ohc=zeC23asp5aQAX-ma_Ll&_nc_ht=scontent.fcgb1-1.fna&oh=26ac7a4d31f872d413ebe3adf8f17e7b&oe=5F33FE20"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109313082_105104021284884_8776167092070014960_n.jpg?_nc_cat=109&_nc_sid=8024bb&_nc_eui2=AeE5nt_lQ3wip9fezUu1hO2ie39CiIoIAuR7f0KIiggC5IlCv-Vf5CxDxdVGqwu5RbHiQmhv7df7RxafenKxi0je&_nc_ohc=5Bk4i9mxDxwAX_9yDCC&_nc_ht=scontent.fcgb1-1.fna&oh=6742ed2470be92cbdcc9807010a6e230&oe=5F3461C8"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109274477_105103967951556_4035174095306496484_n.jpg?_nc_cat=110&_nc_sid=8024bb&_nc_eui2=AeGzofbPLwWZHZwpkoM2r7hErdoCDOUEzqGt2gIM5QTOoaFyw3iIKJnFICtlMZVNalph-KCZC7gzKKaXLCN4dEVc&_nc_ohc=R0BN-WQ0KckAX8uX6Sj&_nc_ht=scontent.fcgb1-1.fna&oh=a0ed4ae70861018456fd9af7017df499&oe=5F33575E"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/110288696_105103927951560_2643383424111454943_n.jpg?_nc_cat=100&_nc_sid=8024bb&_nc_eui2=AeGxJoWYkOtR2_YYnpZvaCcTftBe9-bIyOB-0F735sjI4CPD7N9tFHYez8w8_qQIj4UPKVPkmQuw_VRnKGNR84Sx&_nc_ohc=9s6Smrpq2z0AX_aoXIE&_nc_ht=scontent.fcgb1-1.fna&oh=a724d9d3d2d57e0303bc463ba090a323&oe=5F365971"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109235814_105103847951568_5002705133834032613_n.jpg?_nc_cat=105&_nc_sid=8024bb&_nc_eui2=AeEy95_p0blIavpSmQAdhFXnz267zuEPb3XPbrvO4Q9vdQV1m_YdYuNSNLwtMKbEcirAOTradwmTFp_tY435n_VC&_nc_ohc=Zh9D8pfQp2oAX9yVecP&_nc_ht=scontent.fcgb1-1.fna&oh=7f05e171288adb510b49fcfd5970f1ed&oe=5F3300AF"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109283370_105103807951572_8370835185566854751_n.jpg?_nc_cat=106&_nc_sid=8024bb&_nc_eui2=AeG6VZb0tK7NH3cTEzbsFIiUO5N7hvO4-_w7k3uG87j7_EyNZ6pTwP1AN2yoX9ZHiGTvWgHhSXEakpEVxPg2AxFU&_nc_ohc=LHkp6oYHiKEAX_7mKcw&_nc_ht=scontent.fcgb1-1.fna&oh=28f5730236324943eacabba65f77ee69&oe=5F35D981"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109243316_105103727951580_8699521010025598196_n.jpg?_nc_cat=106&_nc_sid=8024bb&_nc_eui2=AeF2GFRnKJZq3IFmF3GSBOsKVqlEuTivWJ5WqUS5OK9YnjEv6Ahz8OzVIdmFgTRsuxxquZukLWJRmdtv7d9yNXii&_nc_ohc=8AHsqZno6vAAX-3G28n&_nc_ht=scontent.fcgb1-1.fna&oh=46e07a461d2c1c689e5d1aa31026ded9&oe=5F32E509"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109563057_105103664618253_5996136612806192148_n.jpg?_nc_cat=107&_nc_sid=8024bb&_nc_eui2=AeHA1zocEaQ5KDtvlTDwTZVh9wle8KgxBef3CV7wqDEF5w3gtKmyVwnuvwLV69ETx29P627NAdmAp-MePV-jjmDU&_nc_ohc=yKjjmqf7xVIAX9B0mRS&_nc_ht=scontent.fcgb1-1.fna&oh=b7ef1865ab20b8abe6f17a8a992cd788&oe=5F362604"),
            getImage("https://scontent.fcgb1-1.fna.fbcdn.net/v/t1.0-9/109339098_105103561284930_3711286213894012668_n.jpg?_nc_cat=107&_nc_sid=8024bb&_nc_eui2=AeF2L0ZsUA0iHv2EYhK6jR6KNwNROp_ajjo3A1E6n9qOOqzzy43jeO9kOsNEay1My8MqC8oh9x28lO7viXh3wLLJ&_nc_ohc=54g6sLgi8cUAX-Xf75Y&_nc_ht=scontent.fcgb1-1.fna&oh=ea1e180562118bc0d1a60f31893d2317&oe=5F33F1A6"),
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
