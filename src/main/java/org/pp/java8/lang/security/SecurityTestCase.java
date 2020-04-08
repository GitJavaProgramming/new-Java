package org.pp.java8.lang.security;

import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.*;
import java.util.Arrays;

public class SecurityTestCase {

    /**
     * SecurityManager
     * https://www.cnblogs.com/itplay/p/10732329.html
     */
    @Test
    public void testSecurityManager() {
        System.loadLibrary("");
        Runtime.getRuntime().loadLibrary("");
//        ClassLoader.loadLibrary
    }

    @Test
    public void testSsl() throws IOException {
        URL url = new URL("https://www.baidu.com");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setSSLSocketFactory(/*getSSLSocketFactory(password, keyStorePath, trustKeyStorePath)*/HttpsURLConnection.getDefaultSSLSocketFactory());

        InputStream is = conn.getInputStream();
        int len = conn.getContentLength();
//        System.out.println(len);
        byte[] content = new byte[len];
        is.read(content, 0, len);
        System.out.println(new String(content));

        conn.getHeaderFields().forEach((k, s) -> {
            if (k == null || k.isEmpty()) {
                System.out.println(s);
            } else {
                System.out.println(k + ":" + s);
            }
        });

        is.close();
    }

    /**
     * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
     * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
     */
    @Test
    public void testMessageDigest() throws NoSuchAlgorithmException, IOException {
        final byte[] inBytes = "Hello World!".getBytes(/*Charset.defaultCharset()*/);

        // SHA
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(inBytes);
        byte[] outBytes = md.digest();

        // SHA out
        String outStr = new String(outBytes/*, Charset.defaultCharset()*/);
        System.out.println(outStr);
        outStr = outBytes + "";
        System.out.println(outStr);

        // MD5
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(inBytes), md5);
        dis.on(false);
        dis.read(inBytes, 0, inBytes.length);
        outBytes = dis.getMessageDigest().digest();
        // 更新的消息摘要
        System.out.println(inBytes);
        System.out.println(outBytes);
        dis.close();

        DigestOutputStream dous = new DigestOutputStream(new ByteArrayOutputStream(), md5);
        dous.write(inBytes, 0, inBytes.length);
        outBytes = dous.getMessageDigest().digest();
        outStr = new String(outBytes);
        System.out.println(outStr);
        dous.flush();
        dous.close();
    }

    /**
     * Provider extends Properties
     * Properties extends Hashtable<Object,Object>
     * <p>
     * provider 可能实现的服务包括：
     * 算法（如 DSA、RSA、MD5 或 SHA-1）。
     * 密钥的生成、转换和管理设施（如用于特定于算法的密钥）。
     * 每个 provider 有一个名称和一个版本号，并且在每个它装入运行时中进行配置。
     */
    @Test
    public void testPrintProviders() {
        Provider[] providers = Security.getProviders(); // 当前环境安全提供者
        Arrays.stream(providers).parallel().forEach(provider -> {
            System.out.println(provider);
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(provider.getClass());
                PropertyDescriptor[] propertyDescriptor = beanInfo.getPropertyDescriptors();
//                System.out.println("\t" + "Properties:");
//                Arrays.stream(propertyDescriptor).forEach(pd -> System.out.println("\t\t" + pd.getDisplayName() + "-->" + pd.getPropertyType()));
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }

//            Set<Map.Entry<Object, Object>> entrySet = provider.entrySet();
//            entrySet.parallelStream().forEach(entry -> System.out.println("\t" + entry.getKey()));
        });
    }
}
