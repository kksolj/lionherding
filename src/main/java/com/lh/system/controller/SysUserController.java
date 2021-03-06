package com.lh.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.common.config.exception.RunException.RunningException;
import com.lh.common.constant.CommonConstant;
import com.lh.common.log.WriteLog;
import com.lh.system.entity.SysUser;
import com.lh.system.model.query.UserQuery;
import com.lh.system.model.vo.SysUserVO;
import com.lh.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 功能描述：系统用户前端控制器
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
@RestController
@RequestMapping("/sysUser")
@Slf4j
@Api(tags="系统用户")
public class SysUserController {

    private final SysUserService service;

    @Autowired
    public SysUserController(SysUserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public JSONObject login(@RequestBody SysUser sysUser){
        try{
            return service.login(sysUser);
        }catch(Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @PostMapping(value = "/logout")
    @ApiOperation(value = "用户登出",notes = "用户登出")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try{
            service.logout(request,response);
        }catch(Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @GetMapping(value = "/userList")
    @ApiOperation(value = "查询用户",notes = "查询某个部门下的有效用户")
    @WriteLog(opPosition = "查询用户" ,optype = CommonConstant.OPTYPE_READ)
    public Page<SysUserVO> userList(Page<SysUserVO> page, UserQuery userQuery) {
        try {
            return service.userList(page, userQuery);
        }catch (Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "用户添加",notes = "用户添加")
    @WriteLog(opPosition = "用户添加" ,optype = CommonConstant.OPTYPE_CREATE)
    public void add(@RequestBody JSONObject jsonObject) {
        try {
            service.add(jsonObject);
        } catch (Exception e) {
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @PutMapping(value = "/edit")
    @ApiOperation(value = "用户修改",notes = "用户修改")
    @WriteLog(opPosition = "用户修改" ,optype = CommonConstant.OPTYPE_UPDATE)
    public void edit(@RequestBody JSONObject jsonObject) {
        try {
            service.edit(jsonObject);
        } catch (Exception e) {
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @GetMapping("/checkIsOnly")
    @ApiOperation(value = "账号唯一性检测",notes = "登录账号唯一性检测")
    @WriteLog(opPosition = "账号唯一性检测" ,optype = CommonConstant.OPTYPE_READ)
    public void checkIsOnly(String loginName){
        try {
            service.checkIsOnly(loginName);
        }catch (Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    @WriteLog(opPosition = "删除用户" ,optype = CommonConstant.OPTYPE_DELETE)
    public void delete(@RequestParam("sysUserId") String sysUserId) {
        try {
            service.deleteUser(sysUserId);
        } catch (Exception e) {
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除用户",notes = "批量删除用户")
    @WriteLog(opPosition = "批量删除用户" ,optype = CommonConstant.OPTYPE_DELETE)
    public void deleteBatch(@RequestParam("sysUserIds") String ids) {
        try {
            service.deleteBatch(ids);
        } catch (Exception e) {
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    @GetMapping(value = "/queryUserRole")
    @ApiOperation(value = "查询用户拥有角色",notes = "查询用户拥有角色")
    @WriteLog(opPosition = "查询用户拥有角色" ,optype = CommonConstant.OPTYPE_READ)
    public List<String> queryUserRole(@RequestParam("sysUserId") String userId) {
        try{
            return service.queryUserRole(userId);
        }catch(Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }


    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "重置密码",notes = "重置密码")
    @WriteLog(opPosition = "重置密码" ,optype = CommonConstant.OPTYPE_UPDATE)
    public void resetPassword(@RequestBody SysUser sysUser){
        try{
            service.resetPassword(sysUser.getSysUserId());
        }catch(Exception e){
            throw new RunningException("".equals(e.getMessage()) ?  "系统错误,请联系管理员！" : e.getMessage());
        }
    }

    /**
     * todo 放到常见案例菜单
     * 导出Word
     * @param response 响应
     */
    @PostMapping(value = "/exportWord")
    public void export(HttpServletResponse response) {
        service.export(response);
    }


}
