package com.zhangkehui.voaserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangkehui.voaserver.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kehui
 * @since 2022-06-16
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id获取权限列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
