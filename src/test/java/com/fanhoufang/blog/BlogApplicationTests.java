package com.fanhoufang.blog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootTest
public class BlogApplicationTests {
	// @Test
	// void generator() {
	// 	CodeGenerator.build("blog").generate();
	// }
	@Test
    void set(@Autowired RedisTemplate<Object,Object> redisTemplate) {
        ValueOperations<Object,Object> ops = redisTemplate.opsForValue();;
        ops.set("a","123");

    }
    @Test
    void get(@Autowired RedisTemplate<Object,Object> redisTemplate) {
        ValueOperations<Object,Object> ops = redisTemplate.opsForValue();
        System.out.println(ops.get("a"));

    }

    /**
     * jwt生成token
     */

    @Test
    void generateToken() {
        Map<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 2000);

        String token = JWT.create().withHeader(map) //header
                .withClaim("userId", 1)//payload
                .withClaim("username", "ww")//payload
                .withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256("!Q@W#E$R")) //签名
                ;
        System.out.println(token);
    }

        /**
     * jwt解析token
     */

     @Test
     void analysisToken() {

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODU4OTAzMjYsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJ3dyJ9.8bq_g2qUzJ2H-Enl02F5L97kQDWOUkxjG_le0RfG1ms";
 
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#E$R")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println("用户Id: " + decodedJWT.getClaim("userId").asInt());
        System.out.println("用户名：" + decodedJWT.getClaim("username").asString());
        System.out.println("过期时间：" + decodedJWT.getExpiresAt());
     }

    
    @Test
    void test() {
        int[] ids = {1,2,3,7,4,0};
        quickSort(ids, 0, ids.length-1);
        System.out.println(Arrays.toString(ids));

    }

    public static void quickSort(int[] arr,int low,int high) {
        int p,i,j,temp;
        
        if(low >= high) {
            return;
        }
        //p就是基准数,这里就是每个数组的第一个
        p = arr[low];
        i = low;
        j = high;
        while(i < j) {
            //右边当发现小于p的值时停止循环
            while(arr[j] >= p && i < j) {
                j--;
            }
                            
            //这里一定是右边开始，上下这两个循环不能调换（下面有解析，可以先想想）    
    
            //左边当发现大于p的值时停止循环
            while(arr[i] <= p && i < j) {
                i++;
            }
            
                temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
        }
        arr[low] = arr[i];//这里的arr[i]一定是停小于p的，经过i、j交换后i处的值一定是小于p的(j先走)
        arr[i] = p; 
        quickSort(arr,low,j-1);  //对左边快排
        quickSort(arr,j+1,high); //对右边快排
        
        }

}
