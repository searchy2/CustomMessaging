package stream.messagingsample;

import android.graphics.Bitmap;
import android.util.Log;

import com.stream.custommessaging.Message;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MessageBuilder {

    private Message message;
    private LinkedHashMap<String, ArrayList<String>> datapaths;
    private Integer groupid;

    public MessageBuilder() { message = new Message(); }

    public Message GetMessage()
    {
        return this.message;
    }

    public MessageBuilder(String text, String address) {
        this(text, address.trim().split(" "));
    }
    
    public MessageBuilder(String text, String[] addresses) {
        message.text = text;
        message.addresses = addresses;
        message.subject = null;
        message.save = true;
        this.groupid = -1;
        message.delay = 0;
    }

    public MessageBuilder(String text, String[] addresses, String subject) {
        message.text = text;
        message.addresses = addresses;
        message.subject = subject;
    }

    public void setID(Integer id) { message.id = id; }
    
    public void setText(String text) { message.setText(text); }
    
    public void setAddresses(String[] addresses) { message.setAddresses(addresses); }
    
    public void setAddress(String address) {
        String[] addresses = new String[1];
        addresses[0] = address;
        message.addresses = addresses;
    }

    public void setDataPaths(LinkedHashMap<String, ArrayList<String>> datapaths) { this.datapaths = datapaths; }

    public void setGroupID(Integer groupID) {
        this.groupid = groupID;
    }

    public Integer getGroupID() { return this.groupid; }
    
    public LinkedHashMap<String, ArrayList<String>> getDataPaths() {
        return this.datapaths;
    }

    public void addImage(Bitmap bitmap, String mimeType, String imageName) {
        message.parts.add(new Message.Part(bitmapToByteArray(bitmap), mimeType, imageName));
    }
    
    public void addMedia(String filePath, String mimeType, String fileName) {
        message.parts.add(new Message.Part(fileToByteArray(filePath), mimeType, fileName));
    }

    public void addMedia(byte[] media, String mimeType, String name) {
        message.parts.add(new Message.Part(media, mimeType, name));
    }

    public void setSubject(String subject) { message.subject = subject; }

    public void setSave(boolean save) { message.save = save; }

    public void setDelay(int delay) { message.delay = delay; }

    public void addAddress(String address) {
        String[] temp = message.addresses;

        if (temp == null) {
            temp = new String[0];
        }

        message.addresses = new String[temp.length + 1];

        for (int i = 0; i < temp.length; i++) {
            message.addresses[i] = temp[i];
        }

        message.addresses[temp.length] = address;
    }

    public void resetText() { message.text = ""; }


    public static byte[] bitmapToByteArray(Bitmap image) {
        byte[] output = new byte[0];
        if (image == null) {
            Log.v("MessageBuilder", "image is null, returning byte array of size 0");
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
            Log.v("MessageBuilder", "null, returning byte array of size 0");
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
