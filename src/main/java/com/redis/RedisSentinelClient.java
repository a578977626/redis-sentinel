package com.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class RedisSentinelClient {
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String host = "192.168.115.131";
//		Set<String> sentinels = new HashSet<String>();
//		sentinels.add(new HostAndPort(host, 26379).toString());
//		sentinels.add(new HostAndPort(host, 26479).toString());
//		sentinels.add(new HostAndPort(host, 26579).toString());
//		JedisSentinelPool sentinelPool = new JedisSentinelPool("def_master", sentinels,"123");
//		System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());
//		Jedis master = sentinelPool.getResource();
//		master.set("username", "liangzhichao");
//		sentinelPool.returnResource(master);
//
//		Jedis master2 = sentinelPool.getResource();
//		String value = master2.get("username");
//		System.out.println("username: " + value);
//		master2.close();
//		sentinelPool.destroy();
//	}
}
