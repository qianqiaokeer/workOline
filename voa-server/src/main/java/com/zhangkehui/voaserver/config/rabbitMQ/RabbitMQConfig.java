package com.zhangkehui.voaserver.config.rabbitMQ;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhangkehui.voaserver.pojo.MailConstants;
import com.zhangkehui.voaserver.pojo.MailLog;
import com.zhangkehui.voaserver.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
    private static final Logger Logger = LoggerFactory.getLogger(RabbitTemplate.class);

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 消息确认 回调
         * data 消息数据
         * ack 确认结果
         * cause 失败原因
         */
        rabbitTemplate.setConfirmCallback(
                (data,ack,cause) ->{
                    String msgId = data.getId();
                    if (ack){
                        Logger.info("{}========>消息发送成功",msgId);
                        /*更新数据库消息记录*/
                        mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",msgId));
                    }else {
                        Logger.info("{}========>消息发送失败",msgId);
                    }
                }
        );
        /**
         * 消息失败 回调 例如：route找不到queue 这种情况回调
         * msg 消息
         * repCode 响应码
         * repText 响应描述
         * exchange 交换机
         * routingKey 路由键
         */
        rabbitTemplate.setReturnCallback(
                (msg,repCode,repText,exchange,routingKey)->{
                    Logger.info("{}========>消息发送到queue时失败",msg.getBody());
                }
        );
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
