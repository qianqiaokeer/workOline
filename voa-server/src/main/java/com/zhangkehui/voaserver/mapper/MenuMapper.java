package com.zhangkehui.voaserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangkehui.voaserver.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  菜单 Mapper 接口
 * </p>
 *
 * @author kehui
 * @since 2022-06-13
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户id获取菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 通过角色获取菜单列表
     * @return
     */
    List<Menu> getAllMenusWithRole();

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
