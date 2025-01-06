package com.miku.minadevelop.redis;


import com.miku.minadevelop.common.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/vote")
@Slf4j
@AllArgsConstructor
public class voteDemo {
   private  StringRedisTemplate stringRedisTemplate;

   @PostMapping("/like")
   public Result vote(@RequestParam("id")String id,@RequestParam("article") String article,@RequestParam("user") String user){
       //判断库中是否拥有改数据
       Long rank = stringRedisTemplate.opsForZSet().rank("article",id);
       System.out.println(rank);
       if (rank == null){
            //未获取到初始化
           stringRedisTemplate.opsForZSet().add("article",id,1);
           stringRedisTemplate.opsForSet().add("article"+id,user);
        }else{
           //判断用户是否点赞过了
           if (!stringRedisTemplate.opsForSet().isMember("article"+id,user)){
               stringRedisTemplate.opsForSet().add("article"+id,user);
               //获取分数
               Double currentScore = stringRedisTemplate.opsForZSet().score("article", id);
               stringRedisTemplate.opsForZSet().add("article",id,currentScore+1);
           }else{
               return Result.ok("已经点赞过了");
           }
        }

       return Result.ok("点赞成功");
   }


}
