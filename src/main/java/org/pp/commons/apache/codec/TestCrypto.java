package org.pp.commons.apache.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestCrypto {

    /**
     * Base64 hex DES md5等算法加密
     */
    @Test
    public void test() throws DecoderException, EncoderException {
        String encryptStr = null;
        String decryptStr = null;
        String str = "hello world!";

        // base64
        encryptStr = new String(Base64.encodeBase64(str.getBytes(StandardCharsets.UTF_8)));
        decryptStr = new String(Base64.decodeBase64(encryptStr));
        System.out.println(encryptStr);
//        System.out.println(decryptStr);

        // hex
        encryptStr = Hex.encodeHexString(str.getBytes());
        decryptStr = new String(Hex.decodeHex(encryptStr));
        System.out.println(encryptStr);
//        System.out.println(decryptStr);

        // md5
        encryptStr = DigestUtils.md5Hex(str);
        System.out.println(encryptStr);

        encryptStr = DigestUtils.sha1Hex(str); // shaHex过时
        System.out.println(encryptStr);

        encryptStr = new String(DigestUtils.sha256(str));
        System.out.println(encryptStr);

        // url编解码
        URLCodec codec = new URLCodec();
        encryptStr = codec.encode(str);
        decryptStr = codec.decode(str);
        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }

}
