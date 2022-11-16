package com.zhangkehui.voaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangkehui.voaserver.pojo.*;
import com.zhangkehui.voaserver.mapper.EmployeeMapper;
import com.zhangkehui.voaserver.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhangkehui.voaserver.service.IMailLogService;
import com.zhangkehui.voaserver.utils.EasyPoiUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kehui
 * @since 2022-07-17
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMailLogService mailLogService;
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取工号
     * @return
     */
    @Override
    public RespBean maxworkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null,String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    @Transactional
    public RespBean insertEmployee(Employee employee) {
        //设置合同期限 获取合同开始日期、终止日期
        //开始到结束时间 这段中有多少天 除以365
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if (1 == employeeMapper.insert(employee)){
            //发送邮件
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);

            /*数据库记录发送消息*/
            MailLog mailLog = new MailLog();
            String msgId = UUID.randomUUID().toString();
            mailLog.setMsgId(msgId);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogService.save(mailLog);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    /**
     * 查询员工
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    /**
     * 获取所有员工工资账套
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeWithSalary = employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean = new RespPageBean(employeeWithSalary.getTotal(), employeeWithSalary.getRecords());
        return respPageBean;
    }

    @Override
    public void setHiddColumn(List<Employee> list, Boolean value,List<String> columnNameList) {
        if (list.size()>0) {
            for (Employee vo : list) {
                EasyPoiUtil<Employee> easyPoiUtil = new EasyPoiUtil<>();
                easyPoiUtil.t = vo;
                // 是否导出 参与状态 一列  true 导出   false 不导出
                try {
                    for (String columnName : columnNameList) {
                        easyPoiUtil.hiddColumn(columnName, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
