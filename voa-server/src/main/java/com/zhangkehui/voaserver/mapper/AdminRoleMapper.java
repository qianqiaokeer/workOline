package com.zhangkehui.voaserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangkehui.voaserver.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kehui
 * @since 2022-07-13
 */
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 添加操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addRole(@Param("adminId") Integer adminId,@Param("rids") Integer[] rids);
}
