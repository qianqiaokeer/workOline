package com.zhangkehui.voaserver.controller;


import com.zhangkehui.voaserver.pojo.Admin;
import com.zhangkehui.voaserver.pojo.RespBean;
import com.zhangkehui.voaserver.pojo.Role;
import com.zhangkehui.voaserver.service.IAdminService;
import com.zhangkehui.voaserver.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kehui
 * @since 2022-04-20
 */
@Api(tags = "AdminController")
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;
    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllHrs(String keywords){
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Admin admin){
        if (adminService.updateById(admin)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteHr(@PathVariable Integer id){
        if (adminService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public RespBean updateHrRole(Integer adminId,Integer[] rids){
        return adminService.updateAdminRole(adminId,rids);
    }
}
