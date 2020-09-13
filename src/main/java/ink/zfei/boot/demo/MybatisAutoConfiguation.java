package ink.zfei.boot.demo;


import ink.zfei.summer.annation.Import;
import ink.zfei.summer.core.annation.Bean;
import ink.zfei.summer.core.annation.Configuration;

@Configuration
@Import(MyImportSelect.class)
public class MybatisAutoConfiguation {


    @Bean
    public RedisProties person() {
        return new RedisProties();
    }


}
