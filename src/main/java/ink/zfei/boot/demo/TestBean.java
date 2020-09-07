package ink.zfei.boot.demo;

import ink.zfei.summer.annation.Component;

@Component
public class TestBean {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
