package com.zhangkehui.voaserver.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhangkehui.voaserver.pojo.Employee;
import com.zhangkehui.voaserver.pojo.MailConstants;
import com.zhangkehui.voaserver.pojo.MailLog;
import com.zhangkehui.voaserver.service.IEmployeeService;
import com.zhangkehui.voaserver.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 */
@Component
public class MailTask {
    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        /*状态为0 且重试时间小于当前时间的 需要重新发送*/
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            if (mailLog.getCount() >= 3){
                /*重试次数超过3次，判定为失败，不再重试发送*/
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            /*更新重试次数、更新时间、重试时间*/
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount() + 1).set("updateTime",LocalDateTime.now())
            .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq("msgId",mailLog.getMsgId()));
            /*发送邮件*/
            Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,
                    employee,new CorrelationData(mailLog.getMsgId()));
        });
    }
}
