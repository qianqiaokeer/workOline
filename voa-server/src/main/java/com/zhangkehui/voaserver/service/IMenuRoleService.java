package com.zhangkehui.voaserver.service;

import com.zhangkehui.voaserver.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangkehui.voaserver.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kehui
 * @since 2022-06-30
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid,Integer[] mids);

}
