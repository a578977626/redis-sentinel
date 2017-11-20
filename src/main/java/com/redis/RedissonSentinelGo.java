package com.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RDeque;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RQueue;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;

public class RedissonSentinelGo {

//	 public static void main(String[] args) {
//	 Config config = new Config();
//	 SentinelServersConfig sentinelConfig = config.useSentinelServers();
//	 sentinelConfig.setMasterName("def_master");
//	 sentinelConfig.addSentinelAddress("redis://192.168.115.131:26379",
//	 "redis://192.168.115.131:26479","redis://192.168.115.131:26459");
//	 sentinelConfig.setPassword("123");
//	 /*
//	 * eadMode（读取操作的负载均衡模式）
//	 默认值： SLAVE（只在从服务节点里读取）
//	 注：在从服务节点里读取的数据说明已经至少有两个节点保存了该数据，确保了数据的高可用性。
//	 设置读取操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里读取。 MASTER - 只在主服务节点里读取。
//	 MASTER_SLAVE - 在主从服务节点里都可以读取
//	 */
//	 sentinelConfig.setReadMode(ReadMode.SLAVE);
//	 RedissonClient redisson = Redisson.create(config);
//	
//	 //
//	 RBucket<String> rBucket=redisson.getBucket("testBucket");
//	 //同步放置
//	 rBucket.set("redisBucketASync");
//	 //异步放置
//	 rBucket.setAsync("测试");
//	 String bucketString=rBucket.get();
//	 System.out.println(bucketString);
//	 }

	//
	private static RedissonClient redissonClient = null;
	
	static{
		 Config config = new Config();
		 SentinelServersConfig sentinelConfig = config.useSentinelServers();
		 sentinelConfig.setMasterName("def_master");
		 sentinelConfig.addSentinelAddress("redis://192.168.115.131:26379",
		 "redis://192.168.115.131:26479","redis://192.168.115.131:26459");
		 sentinelConfig.setPassword("123");
		 /*
		 * eadMode（读取操作的负载均衡模式）
		 默认值： SLAVE（只在从服务节点里读取）
		 注：在从服务节点里读取的数据说明已经至少有两个节点保存了该数据，确保了数据的高可用性。
		 设置读取操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里读取。 MASTER - 只在主服务节点里读取。
		 MASTER_SLAVE - 在主从服务节点里都可以读取
		 */
		 sentinelConfig.setReadMode(ReadMode.SLAVE);
		 redissonClient = Redisson.create(config);
	}
	//
	private static RedissonSentinelGo RedissonSentinelGo;

	private RedissonSentinelGo() {
	}

	/**
	 * 提供单例模式
	 * 
	 * @return
	 */
	public static RedissonSentinelGo getInstance() {
		if (RedissonSentinelGo == null)
			synchronized (RedissonSentinelGo.class) {
				if (RedissonSentinelGo == null)
					RedissonSentinelGo = new RedissonSentinelGo();
			}
		return RedissonSentinelGo;
	}

	/**
	 * 使用config创建Redisson Redisson是用于连接Redis Server的基础类
	 * 
	 * @param config
	 * @return
	 */
	public RedissonClient getRedisson(Config config) {
		RedissonClient redisson = Redisson.create(config);
		System.out.println("成功连接Redis Server");
		return redisson;
	}
	/**
	 * 直接从本实例获取RedissonClient
	 * @param config
	 * @return
	 */
	public  RedissonClient getRedissonIM() {
		
		return this.redissonClient;
	}

	/**
	 * 使用ip地址和端口创建Redisson
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public RedissonClient getRedisson(String ip, String port) {
		Config config = new Config();
		config.useSingleServer().setAddress(ip + ":" + port);
		RedissonClient redisson = Redisson.create(config);
		System.out.println("成功连接Redis Server" + "\t" + "连接" + ip + ":" + port + "服务器");
		return redisson;
	}

	/**
	 * 关闭Redisson客户端连接
	 * 
	 * @param redisson
	 */
	public void closeRedisson(Redisson redisson) {
		redisson.shutdown();
		System.out.println("成功关闭Redis Client连接");
	}

	/**
	 * 获取字符串对象
	 * 
	 * @param redisson
	 * @param t
	 * @param objectName
	 * @return
	 */
	public <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName) {
		RBucket<T> bucket = redisson.getBucket(objectName);
		return bucket;
	}

	/**
	 * 获取Map对象
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <K, V> RMap<K, V> getRMap(Redisson redisson, String objectName) {
		RMap<K, V> map = redisson.getMap(objectName);
		return map;
	}

	/**
	 * 获取有序集合
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RSortedSet<V> getRSortedSet(Redisson redisson, String objectName) {
		RSortedSet<V> sortedSet = redisson.getSortedSet(objectName);
		return sortedSet;
	}

	/**
	 * 获取集合
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RSet<V> getRSet(Redisson redisson, String objectName) {
		RSet<V> rSet = redisson.getSet(objectName);
		return rSet;
	}

	/**
	 * 获取列表
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RList<V> getRList(Redisson redisson, String objectName) {
		RList<V> rList = redisson.getList(objectName);
		return rList;
	}

	/**
	 * 获取队列
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RQueue<V> getRQueue(Redisson redisson, String objectName) {
		RQueue<V> rQueue = redisson.getQueue(objectName);
		return rQueue;
	}

	/**
	 * 获取双端队列
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RDeque<V> getRDeque(Redisson redisson, String objectName) {
		RDeque<V> rDeque = redisson.getDeque(objectName);
		return rDeque;
	}

	/**
	 * 此方法不可用在Redisson 1.2 中 在1.2.2版本中 可用
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	/**
	 * public <V> RBlockingQueue<V> getRBlockingQueue(Redisson redisson,String
	 * objectName){ RBlockingQueue rb=redisson.getBlockingQueue(objectName);
	 * return rb; }
	 */

	/**
	 * 获取锁
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RLock getRLock(Redisson redisson, String objectName) {
		RLock rLock = redisson.getLock(objectName);
		return rLock;
	}

	/**
	 * 获取原子数
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RAtomicLong getRAtomicLong(Redisson redisson, String objectName) {
		RAtomicLong rAtomicLong = redisson.getAtomicLong(objectName);
		return rAtomicLong;
	}

	/**
	 * 获取记数锁
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RCountDownLatch getRCountDownLatch(Redisson redisson, String objectName) {
		RCountDownLatch rCountDownLatch = redisson.getCountDownLatch(objectName);
		return rCountDownLatch;
	}

	/**
	 * 获取消息的Topic
	 * 
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <M> RTopic<M> getRTopic(Redisson redisson, String objectName) {
		RTopic<M> rTopic = redisson.getTopic(objectName);
		return rTopic;
	}

}
