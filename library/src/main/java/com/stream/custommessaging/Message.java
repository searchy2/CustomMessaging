package com.stream.custommessaging;

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

    public Integer id;
    public String text;
    public String subject;
    public String[] addresses;
    public List<Part> parts = new ArrayList<Part>();
    public boolean save = true;
    public int delay = 0;

    public Message() {
    }

    public Message(String text, String address) {
        this(text, address.trim().split(" "));
    }

    public Message(String text, String[] addresses) {
        this.text = text;
        this.addresses = addresses;
        this.subject = null;
        this.save = true;
        this.delay = 0;
    }

    public void setId(Integer id) { this.id = id;}

    public void setText(String text) { this.text = text; }

    public void setSubject(String subject) { this.subject = subject; }

    public void setAddresses(String[] addresses) { this.addresses = addresses; }

    public void setParts(List<Part> parts) { this.parts = parts; }

    public void setSave(boolean save) { this.save = save; }

    public void setDelay(int delay) { this.delay = delay; }

    public Integer getID() { return this.id; }

    public String getText() {
        return this.text;
    }

    public String[] getAddresses() {
        return this.addresses;
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

}
