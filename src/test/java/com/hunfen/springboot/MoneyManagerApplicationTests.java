package com.hunfen.springboot;

import com.hunfeng.money.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoneyManagerApplicationTests {
    @Test
    void contextLoads() {
        String jwt = JwtTokenUtils.generateToken("hzb");
        System.out.println(jwt);
    }

}
