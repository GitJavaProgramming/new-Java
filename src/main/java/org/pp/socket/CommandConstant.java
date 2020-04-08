package org.pp.socket;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandConstant {

    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 8000;

    public static final String CLOSE = "SHUTDOWN";

//    public static final Map<Integer, SocketChannel> CACHE = new ConcurrentHashMap<>();
}
