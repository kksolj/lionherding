package com.lh.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.common.config.exception.userException.UserNoExistException;
import com.lh.common.config.webSocket.WebSocket;
import com.lh.common.constant.CommonConstant;
import com.lh.common.utils.BasisUtil;
import com.lh.common.utils.EncoderUtil;
import com.lh.common.utils.JwtUtil;
import com.lh.common.utils.RedisUtil;
import com.lh.system.entity.SysUser;
import com.lh.system.mapper.SysUserMapper;
import com.lh.system.service.SysLogService;
import com.lh.system.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * 功能描述：
 *
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author xieyc
 * @Date 2019-09-19
 * @Version: 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private WebSocket webSocket;

    @Override
    public JSONObject login(SysUser sysUser) {
        String loginName = sysUser.getLoginName();
        String password = sysUser.getPassword();
        sysUser = this.getUserByName(loginName);
        if (sysUser == null) {
            sysLogService.addLog("登录失败，用户名:" + loginName + "不存在！", CommonConstant.LOG_TYPE_1, "sysUser/login", "loginName:" + loginName + ",password:" + password);
            throw new UserNoExistException("该用户不存在！");
        } else {
            // 密码验证
            String requestPassword = EncoderUtil.encrypt(loginName, password, sysUser.getLoginName());
            String sysPassword = sysUser.getPassword();
            // todo 暂时注释
            // if(!sysPassword.equals(requestPassword)) {
            //     sysLogService.addLog("登录失败，用户:"+loginName+"密码输入错误！", CommonConstant.LOG_TYPE_1, "sysUser/login","loginName:"+loginName+",password:"+password);
            //     throw new UserPasswordException("密码错误！");
            // }
            JSONObject jsonObject = new JSONObject();
            // 生成token
            // todo 暂时放置默认密码 此处应放置系统密码
            String token = JwtUtil.sign(loginName, "123456");
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
            // 设置超时时间
            redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
            jsonObject.put("token", token);
            jsonObject.put("userInfo", sysUser);
            this.dealUser(sysUser);  // 记录登录数据
            sysLogService.addLog("用户名: " + loginName + ",登录成功！", CommonConstant.LOG_TYPE_1, "sysUser/login", "loginName:" + loginName + ",password:" + password);
            return jsonObject;
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        Subject subject = SecurityUtils.getSubject();
        // todo 暂时放置
        // SysUser sysUser = (SysUser)subject.getPrincipal();
        // sysLogService.addLog("用户名: "+sysUser.getLoginName()+",退出成功！", CommonConstant.LOG_TYPE_1, "sysUser/logout","");
        subject.logout();

        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        //清空用户Token缓存
        redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
        //清空用户权限缓存：权限Perms和角色集合
        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_ROLE + /*sysUser.getLoginName()*/ "admin");
        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_PERMISSION + /*sysUser.getLoginName()*/ "admin");
        //    todo 记录日志 和用户信息
    }

    @Override
    public SysUser getUserByName(String loginName) {
        return this.baseMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("login_name", loginName));
    }

    @Override
    public void dealUser(SysUser sysUser) {
        // todo 验证此处几个参数是否准确
        sysUser.setLoginCount(sysUser.getLoginCount() + 1);
        if (BasisUtil.isNotEmpty(sysUser.getLastLoginTime())) {
            if (!sysUser.getLastLoginTime().toString().substring(0, 10)
                    .equalsIgnoreCase(LocalDateTime.now().toString().substring(0, 10))) {// 判断上一次登录时间是当前时间的前一天或更多天则证明今天没登陆过
                sysUser.setTodayLoginCount(1);
            } else {
                sysUser.setTodayLoginCount(sysUser.getTodayLoginCount() + 1);
            }
        } else {
            sysUser.setTodayLoginCount(1);
        }
        if (BasisUtil.isEmpty(sysUser.getFirstLoginTime())) {
            sysUser.setFirstLoginTime(LocalDateTime.now());
        }
        sysUser.setLastLoginTime(LocalDateTime.now());
        this.baseMapper.updateById(sysUser);
    }

    @Override
    public void tempApi() {
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        obj.put("cmd", "user");//业务类型
        obj.put("msgId", "8888888888");//消息id
        obj.put("msgTxt", "欢迎来到新的世界！");//消息内容
        //单个用户发送 (userId为用户id)
        webSocket.sendOneMessage("9999999999999999", obj.toJSONString());
    }
}
