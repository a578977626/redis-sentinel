package com.controller;

import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redis.RedissonSentinelGo;
import com.redis.SentinelGo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

@Controller
@RequestMapping("/redisOP")
public class RedisCallController {

	
	@Autowired
	private JavaMailSender mailSender;
	@ResponseBody
	@RequestMapping(value = "/setValue", method = RequestMethod.GET)
	public String setValue(String key, String value) {
		try {
			Jedis jedis = SentinelGo.getInstance().getJedis();
			jedis.set(key, value);
			SentinelGo.getInstance().returnJedis(jedis);
		} catch (JedisConnectionException e) {
			sendNewsEmail();
			return "redis connect error and sned an e-mial";
		}catch ( JedisException je) {
			return "redis  error";
		}
		return "return LIVE!! IS BEAtiful!!";
	}

	@ResponseBody
	@RequestMapping(value = "/getValue", method = RequestMethod.GET)
	public String getValue(String key) {
		try {
			Jedis jedis = SentinelGo.getInstance().getJedis();
			String value = jedis.get(key);
			SentinelGo.getInstance().returnJedis(jedis);
			return value;
		} catch (JedisConnectionException e) {
			sendNewsEmail();
			return "redis connect error and sned an e-mial";
		}catch ( JedisException je) {
			return "redis  error";
		}

		
	}
	
	/**
	 * Redisson client
	 */
	@ResponseBody
	@RequestMapping(value = "/getValueRediSson", method = RequestMethod.GET)
	public String setValueRediSson(String key, String value) {
		try {//
			 RBucket<String> rBucket=RedissonSentinelGo.getInstance().getRBucket(RedissonSentinelGo.getInstance().getRedissonIM(), "testBucket");  
//		        //同步放置  
//		        rBucket.set("redisBucketASync");  
//		        //异步放置  
//		        rBucket.setAsync("测试");  
		        String bucketString=rBucket.get();  
		        System.out.println(bucketString);  
			return bucketString;
		} catch (JedisConnectionException e) {
			sendNewsEmail();
			return "redis connect error and sned an e-mial";
		}catch ( JedisException je) {
			return "redis  error";
		}
	}
	
	/**
	 * 发送邮件通知Redis-mster is down!!
	 */
	public void sendNewsEmail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("XXXXX@163.com");
		message.setTo("XXXXX@qq.com");
		message.setSubject("注意，您的Redis master已挂!");
		message.setText("等着被祭天吧  ヾ(◍°∇°◍)ﾉﾞ");
		mailSender.send(message);
	}
}
