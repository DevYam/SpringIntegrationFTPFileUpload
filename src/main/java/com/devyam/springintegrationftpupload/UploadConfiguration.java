package com.devyam.springintegrationftpupload;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import java.time.LocalDateTime;

@Configuration
public class UploadConfiguration {

    @Bean
    public DefaultFtpSessionFactory ftpSessionFactory(){
        DefaultFtpSessionFactory factory =
                new DefaultFtpSessionFactory();
        factory.setHost("HOST_NAME");
        factory.setPort(21); // 22 for SFTP
        factory.setUsername("USER_NAME");
        factory.setPassword("PASSWORD");
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "uploadFile")
    public FtpMessageHandler uploadHandler(){

        FtpMessageHandler messageHandler = new FtpMessageHandler(ftpSessionFactory());
        messageHandler.setRemoteDirectoryExpression(new LiteralExpression("/"));
        messageHandler.setFileNameGenerator(message -> String.format("myTestFile_%s", LocalDateTime.now()));
        return  messageHandler;

    }
}
