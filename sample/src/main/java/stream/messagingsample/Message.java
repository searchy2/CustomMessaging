package stream.messagingsample;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Message extends com.stream.custommessaging.Message{

    private Integer id;
    private String text;
    private String subject;
    private String[] addresses;
    private LinkedHashMap<String, ArrayList<String>> datapaths;
    private List<Part> parts = new ArrayList<Part>();
    private Integer groupid;
    private boolean save;
    private int delay;

    public Message() {}

    public Message(String text, String address) {
        this(text, address.trim().split(" "));
    }
    
    public Message(String text, String[] addresses) {
        this.text = text;
        this.addresses = addresses;
        this.subject = null;
        this.save = true;
        this.groupid = -1;
        this.delay = 0;
    }

    public Message(String text, String[] addresses, String subject) {
        this.text = text;
        this.addresses = addresses;
        this.subject = subject;
        this.save = true;
        this.delay = 0;
    }

    public void setID(Integer id) { this.id = id; }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }
    
    public void setAddress(String address) {
        this.addresses = new String[1];
        this.addresses[0] = address;
    }

    public void setDataPaths(LinkedHashMap<String, ArrayList<String>> datapaths) {
        this.datapaths = datapaths;
    }

    public void setGroupID(Integer groupID) {
        this.groupid = groupID;
    }
    
    public LinkedHashMap<String, ArrayList<String>> getDataPaths() {
        return this.datapaths;
    }

    public void addImage(Bitmap bitmap, String mimeType, String imageName) {
        this.parts.add(new Part(bitmapToByteArray(bitmap), mimeType, imageName));
    }
    
    public void addMedia(String filePath, String mimeType, String fileName) {
        this.parts.add(new Part(fileToByteArray(filePath), mimeType, fileName));
    }

    public void addMedia(byte[] media, String mimeType, String name) {
        this.parts.add(new Part(media, mimeType, name));
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addAddress(String address) {
        String[] temp = this.addresses;

        if (temp == null) {
            temp = new String[0];
        }

        this.addresses = new String[temp.length + 1];

        for (int i = 0; i < temp.length; i++) {
            this.addresses[i] = temp[i];
        }

        this.addresses[temp.length] = address;
    }
    
    public Integer getID() { return this.id; }

    public void resetText() {
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public List<com.stream.custommessaging.Message.Part> getParts() {
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

    public Integer getGroupID() { return this.groupid; }

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
    
    public static byte[] fileToByteArray(String audioPath) {
        byte[] output = new byte[0];
        if (audioPath == null) {
            Log.v("Message", "null, returning byte array of size 0");
            return output;
        }
        try {
            FileInputStream fis = new FileInputStream(audioPath);
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int count = 0;
            while ( (count = fis.read(buffer)) != -1 ) {
                fos.write(buffer, 0, count);
            }
            fos.close();

            return fos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
