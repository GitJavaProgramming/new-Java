package org.pp.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaRedisTestCase {

    private static final String passWord = "Redis!12345";

    private static ShardedJedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(100);
        config.setMaxIdle(50);
        config.setMaxWait(3000);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        // 集群
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.0.129", 6379);
        jedisShardInfo1.setPassword(passWord);
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);
    }

    public static void main(String[] args) {
        System.out.println("中国".length());
        ShardedJedis jedis = pool.getResource();
        String str = jedis.get("platform:info");
        System.out.println("String value platform:info=" + str);
        String keys = "website"; // hash
        String vaule = jedis.hget(keys, "access");
        System.out.println("hash value website=" + vaule);
        Map<String, String> map = jedis.hgetAll(keys);
        System.out.println("hash value website all value=" + map);
        Set<String> stringSet = jedis.smembers("ip");
        System.out.println("set value ip=" + stringSet);
    }
}
