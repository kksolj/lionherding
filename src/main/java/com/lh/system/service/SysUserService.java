package com.lh.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.system.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述：
 *
 *  <p>版权所有：</p>
 *  未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author xieyc
 * @Date 2019-09-19
 * @Version: 1.0.0
 *
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     */
    JSONObject login(SysUser sysUser);

    /**
     * 登出
     * @param request
     * @param response
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据账号查询用户
     * @param loginName
     * @return
     */
    SysUser getUserByName(String loginName);

    /**
     * 处理用户数据
     */
    void dealUser(SysUser sysUser);

    /**
     * 加载用户数据
     * @param page
     * @param param
     * @return
     */
    Page<SysUser> departUserList(Page<SysUser> page, JSONObject param);

    /**
     * 添加用户
     */
    void addUserWithRole(JSONObject jsonObject);

    /**
     * 修改用户
     * @param jsonObject
     */
    void editUserWithRole(JSONObject jsonObject);

    /**
     * 登录账号唯一性检测
     * @param loginName
     */
    void checkIsOnly(String loginName);

    /**
     * 单个删除
     * @param id
     */
    void deleteUser(String id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(String ids);
}
