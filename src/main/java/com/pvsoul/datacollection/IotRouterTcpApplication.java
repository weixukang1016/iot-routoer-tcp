package com.pvsoul.datacollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IotRouterTcpApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(IotRouterTcpApplication.class, args);
        applicationContext.getBean(SocketServer.class).start();
    }

}
