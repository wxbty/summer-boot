package ink.zfei.boot;


import ink.zfei.summer.annation.Import;
import ink.zfei.summer.core.annation.Bean;
import ink.zfei.summer.core.annation.Configuration;

@Configuration
@Import(MyImportSelect.class)
public class MybatisAutoConfiguation {


    @Bean
    public Oppps person() {
        return new Oppps();
    }


}
