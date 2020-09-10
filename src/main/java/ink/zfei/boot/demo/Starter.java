package ink.zfei.boot.demo;

import ink.zfei.boot.SpringApplication;
import ink.zfei.boot.autoconfigure.SpringBootApplication;
import ink.zfei.boot.autoconfigure.SpringBootConfiguration;
import ink.zfei.summer.core.annation.Configuration;

import java.util.Arrays;

@SpringBootApplication
public class Starter {

    public static void main(String[] args) {

        SpringApplication.run(Starter.class, args);
    }
}
