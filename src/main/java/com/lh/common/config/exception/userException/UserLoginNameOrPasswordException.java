package com.lh.common.config.exception.userException;

import com.lh.common.config.exception.ApiException;
import com.lh.common.config.response.ResponseCode;
import com.lh.common.utils.ResultUtils;

/**
 * 功能描述：用户名或密码错误
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2019-09-25
 * @Version: 1.0.0
 */
public class UserLoginNameOrPasswordException extends ApiException {

    public UserLoginNameOrPasswordException() {
        super(ResponseCode.USER_LOGINNAME_OR_PWD_EXCEPTION, "用户名或密码错误");
    }

    public UserLoginNameOrPasswordException(String msg, Object... params) {
        super(ResponseCode.USER_LOGINNAME_OR_PWD_EXCEPTION, ResultUtils.formatMsg(msg, params));
    }
}
