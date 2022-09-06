package com.zhangkehui.voaserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhangkehui.voaserver.pojo.Employee;
import com.zhangkehui.voaserver.pojo.RespBean;
import com.zhangkehui.voaserver.pojo.RespPageBean;
import com.zhangkehui.voaserver.pojo.Salary;
import com.zhangkehui.voaserver.service.IEmployeeService;
import com.zhangkehui.voaserver.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *员工工资账套
 * @author kehui
 * @since 2022-09-02
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;


    @ApiOperation(value = "获取所有员工工资账套")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage
    ,@RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation(value = "更新员工工资账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid,Integer sid){
        boolean update = employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid));
        if (update){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
