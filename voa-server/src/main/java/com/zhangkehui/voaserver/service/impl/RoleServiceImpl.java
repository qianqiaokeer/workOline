package com.zhangkehui.voaserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangkehui.voaserver.mapper.RoleMapper;
import com.zhangkehui.voaserver.pojo.Role;
import com.zhangkehui.voaserver.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kehui
 * @since 2022-06-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
