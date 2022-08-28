package com.zhangkehui.mail;

import com.zhangkehui.voaserver.pojo.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 邮件
 *
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VoaMailApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(VoaMailApplication.class,args);
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
