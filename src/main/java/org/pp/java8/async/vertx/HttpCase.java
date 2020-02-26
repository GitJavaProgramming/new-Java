package org.pp.java8.async.vertx;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;

public class HttpCase {

    private static Vertx vertx = Vertx.vertx();

    public static void server() {
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(request -> {
            // 请求头
            MultiMap headers = request.headers();
            // Get the User-Agent:
            System.out.println("User agent is " + headers.get("user-agent"));
            // You can also do this and get the same result:
//            System.out.println("User agent is " + headers.get("User-Agent"));
//            request.getParam("");

            // 从请求体读取数据
            Buffer totalBuffer = Buffer.buffer();
            request.handler(buffer -> {
                System.out.println("I have received a chunk of the body of length " + buffer.length());
                totalBuffer.appendBuffer(buffer);
            });
            request.endHandler(v -> {
                System.out.println("Full body received, length = " + totalBuffer.length());
            });

            // 发送响应
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/html").putHeader("other-header", "wibble");
            response.end("Hello world");
        });
        server.listen(8080, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }

    public static void client() {
        HttpClientOptions options = new HttpClientOptions()
                .setDefaultHost("localhost")
                .setDefaultPort(8080)
                .setKeepAlive(false);
        // Can also set default port if you want...
        HttpClient client = vertx.createHttpClient(options);

        // 发送请求
        client.request(HttpMethod.GET, "/home", response -> {
            System.out.println(response.getHeader("other-header"));
            response.bodyHandler(buffer -> {
                System.out.println(buffer.toString());
            });
        }).end(); // 请求必须结束

        client.post("some-uri", response -> {
            System.out.println("Received response with status code " + response.statusCode());
        }).putHeader("content-type", "text/plain").end("request body");
    }

    public static void main(String[] args) throws InterruptedException {
        server();
        Thread.sleep(1000);
        client();
        vertx.setTimer(5000, h -> {
            vertx.close();
        });
    }
}
