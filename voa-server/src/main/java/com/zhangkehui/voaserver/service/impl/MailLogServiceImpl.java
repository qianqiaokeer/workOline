package com.zhangkehui.voaserver.service.impl;

import com.zhangkehui.voaserver.pojo.MailLog;
import com.zhangkehui.voaserver.mapper.MailLogMapper;
import com.zhangkehui.voaserver.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kehui
 * @since 2022-08-19
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
