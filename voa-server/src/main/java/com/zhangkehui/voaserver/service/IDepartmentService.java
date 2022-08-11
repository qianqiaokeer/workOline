package com.zhangkehui.voaserver.service;

import com.zhangkehui.voaserver.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangkehui.voaserver.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kehui
 * @since 2022-07-03
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDep(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    RespBean deleteDep(Integer id);

}
