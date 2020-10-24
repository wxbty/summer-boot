package ink.zfei.test;


import ink.zfei.test.unpack.RedisProties;
import ink.zfei.summer.annation.Import;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

@Configuration
@Import(MyImportSelect.class)
public class MybatisAutoConfiguation {


    @Bean
    public RedisProties redisProties() {
        return new RedisProties();
    }


}
