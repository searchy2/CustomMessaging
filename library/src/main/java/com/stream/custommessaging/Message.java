package com.stream.custommessaging;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Message {

    public static final class Part {
        private byte[] media;
        private String contentType;
        private String name;
        public Part(byte[] media, String contentType, String name) {
            this.media = media;
            this.contentType = contentType;
            this.name = name;
        }

        public byte[] getMedia() {
            return media;
        }

        public String getContentType() {
            return contentType;
        }

        public String getName() {
            return name;
        }
    }
    private Integer id;
    private String text;
    private String subject;
    private String[] addresses;
    private Bitmap[] images;
    private String[] imagePaths;
    private String[] imageNames;
    private List<Part> parts = new ArrayList<Part>();
    private boolean save;
    private int delay;

    public Message() {
    }

    public Message(String text, String address) {
        this(text, address.trim().split(" "));
    }

    public Message(String text, String[] addresses) {
        this.text = text;
        this.addresses = addresses;
        this.images = new Bitmap[0];
        this.subject = null;
        this.save = true;
        this.delay = 0;
    }

    public Integer getID() { return this.id; }

    public String getText() {
        return this.text;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public Bitmap[] getImages() {
        return this.images;
    }

    public String[] getImageNames() {
        return this.imageNames;
    }

    public List<Part> getParts() {
        return this.parts;
    }

    public String getSubject() {
        return this.subject;
    }

    public boolean getSave() {
        return this.save;
    }

    public int getDelay() {
        return this.delay;
    }

    public static byte[] bitmapToByteArray(Bitmap image) {
        byte[] output = new byte[0];
        if (image == null) {
            Log.v("Message", "image is null, returning byte array of size 0");
            return output;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            output = stream.toByteArray();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {}
        }

        return output;
    }
}
