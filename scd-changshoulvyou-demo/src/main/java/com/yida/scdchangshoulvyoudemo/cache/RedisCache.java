package com.yida.scdchangshoulvyoudemo.cache;


import com.yida.scdchangshoulvyoudemo.util.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

/**
 * 自定义Redis缓存
 */

public class RedisCache implements Cache {

    //获取redisTemplate单例对象
    public RedisTemplate getRedisTemplate(){
       RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
       redisTemplate.setKeySerializer(new StringRedisSerializer());//key设置字符串类型
       redisTemplate.setHashKeySerializer(new StringRedisSerializer());//hash key设置字符串类型
       return  redisTemplate;
    }
  /*封装一个私有方法，专门把key进行md5加密，让key更简短!
    注意：Md5加密算法的特点:
        1. 一切文件或字符串都能生成32位16进制的md5字符串；
        2. 不同文件或字符串生成的md5字符串一定不同；
        3. 相同文件或字符串，多次生成的md5结果一致。
     */
  private String md5Key(String key){
      String k = DigestUtils.md5DigestAsHex(key.getBytes());
      return  k;
  }

   //这里的id，实际上是mapper里的namespace的值
    private  final String id;
    public  RedisCache(String id){this.id=id;}
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
         System.out.println("-----key="+md5Key(key.toString())+",value="+value);
         getRedisTemplate().opsForHash().put(id,md5Key(key.toString()),value);
    }

    @Override
    public Object getObject(Object key) {
        System.out.println("----->key="+key);
        return getRedisTemplate().opsForHash().get(id,md5Key(key.toString()));
    }

    @Override
    public Object removeObject(Object key) {
        System.out.println("清除指定的key");
        getRedisTemplate().opsForHash().delete(id,md5Key(key.toString()));
        return null;
    }

    @Override
    public void clear() {
   System.out.println("清空数据库");
getRedisTemplate().delete(id);//删除指定大key下的所有key-value对
    }

    @Override
    public int getSize() {
       System.out.println("获取总大小");
        return getRedisTemplate().opsForHash().size(id).intValue();
    }
}
