package org.pp.decompile;

import lombok.Getter;

@Getter
public class MessageInfoComplex implements java.io.Serializable {
    String hostName;
    String userName;

    public MessageInfoComplex(String hostName, String userName) {
        this.hostName = hostName;
        this.userName = userName;
    }

    public String getDisplayName() {
        return getUserName() + "(" + getHostName() + ")";
    }

    public String generateMessageId() {
        StringBuffer id = new StringBuffer(22);
        String systemTime = "" + System.currentTimeMillis();
        id.append(systemTime.substring(0, 6));
        if(userName != null && userName.length() > 0) {
            id.append('_');
            int maxChars = Math.min(userName.length(), 8);
            id.append(userName, 0, maxChars);
        }
        if(hostName != null && hostName.length() > 0) {
            id.append('_');
            int maxChars = Math.min(hostName.length(), 8);
            id.append(hostName, 0, maxChars);
        }
        return id.toString();
    }

    public static void main(String[] args) {
        new Thread(()-> {
            System.out.println("Running test.");
            MessageInfoComplex info = new MessageInfoComplex("JSMAICA", "Kalinovsky");
            System.out.println("Message id = " + info.generateMessageId());
            info = new MessageInfoComplex(null, "JAMAICA");
            System.out.println("Message id = " + info.generateMessageId());
        }).start();
    }

    public static class MessageInfoPK implements java.io.Serializable {
        public String id;
    }
}

















