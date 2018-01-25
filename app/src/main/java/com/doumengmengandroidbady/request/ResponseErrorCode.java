package com.doumengmengandroidbady.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ResponseErrorCode {

    private static Map<Integer,String> errorCodeMap;
    public static final int SUCCESS = 0;								//成功

    public static final String ERROR_REQUEST_FAILED_MSG = "{\"errorId\":-10000}";
    /*************************************  失败      *****************************************/

    //服务器
    private static final int ERROR_SERVER_MAINTENANCE = -1;				//服务器正在维护
    private static final int ERROR_SERVER_NOT_RESPONDING = -2;			//服务器没有响应
    private static final int ERROR_SERVER_BUSY = -3;						//服务器繁忙
    private static final int ERROR_SERVER_FULL = -4;						//服务器人数已满

    //申请账号
    private static final int ERROR_TWO_INPUTS = -10;						//新密码和老密码一样
    private static final int ERROR_ROLE_NAME_ILLEGAL = -11;				//非法的角色名
    private static final int ERROR_ROLE_NAME_SHORT = -12;				//角色名太短
    private static final int ERROR_ROLE_NAME_LONG = -13;					//角色名太长
    private static final int ERROR_PASSWORD_ILLEGAL = -14;				//非法的密码
    private static final int ERROR_PASSWORD_SHORT = -15;					//密码太短
    private static final int ERROR_PASSWORD_LONG = -16;					//密码太长
    private static final int ERROR_REGISTER_LOGINNAME_EXIST= -18;		//账号已存在
    private static final int ERROR_REGISTER_INIT_MISSION= -19;			//账号已存在

    //登陆
    private static final int ERROR_LOGIN_WRONG_PASSWORD = -20;			//密码错误
    public static final int ERROR_LOGIN_ROLE_NOT_EXIST = -21;			//账号不存在
    private static final int ERROR_LOGIN_ROLE_NAME_EMPTY = -22;			//账号名为空
    private static final int ERROR_LOGIN_ROLE_PWD_EMPTY = -23;			//密码为空
    private static final int ERROR_LOGIN_STOP = -24;						//账号禁用
    private static final int ERROR_LOGIN_LIMIT = -25;					//账号合同已到期
    private static final int ERROR_LOGIN_START = -26;					//账号合同未开始
    private static final int ERROR_LOGIN_NORECORD = -27;					//账号合同未开始
    public static final int ERROR_LOGIN_ROLE_EXIST = -900;				//登陆超时，角色需重新登录

    //注册验证码
    private static final int ERROR_VCODE_WRONG = -50;					//输入的校验码与发送验证码不一致
    private static final int ERROR_CHECKCODE_EMPTY = -51;				//校验码为空
    private static final int ERROR_VCODE_SEND = -52;						//未发送验证码
    private static final int ERROR_PHONENUMBER = -53;					//手机号格式有误
    private static final int ERROR_REGISTER_ROLE_EXIST = -54;			//注册账号已存在

    //角色
    private static final int ERROR_ROLE_GET = -100;						//角色获得失败
    private static final int ERROR_ROLE_RECORD = -101;					//角色获取当月在线记录数据错误
    private static final int ERROR_ROLE_RECORD_TIME = -102;				//角色提交记录时间已超过,需联系客服
    private static final int ERROR_ROLE_SUBMIT_FEEDBACK = -103;			//角色提交反馈失败
    private static final int ERROR_ROLE_SUBMIT_PWD = -104;				//角色提交密码错误
    private static final int ERROR_ROLE_ACCOUNT_EMAIL = -105;			//角色提交绑定邮箱错误
    private static final int ERROR_ROLE_UPDATE_PARENT = -106;			//角色修改父母信息错误
    private static final int ERROR_ROLE_UPDATE_CARER = -107;				//角色修改照养人信息错误
    private static final int ERROR_ROLE_RECORD_STATUS = -108;			//角色记录状态为已提交,不能重复提交//抱歉，提交日未到，不能提交。
    private static final int ERROR_ROLE_UPLOAD_HEAD = -109;				//角色头像上传失败
    private static final int ERROR_ROLE_BODYCHECK_MAX = -110;			//角色体检次数到达上限
    private static final int ERROR_ROLE_TREAT_MAX = -111;				//角色治疗次数到达上限
    private static final int ERROR_ROLE_SALON_MAX = -112;				//角色沙龙次数到达上限
    private static final int ERROR_ROLE_MENU = -113;						//角色套餐信息获得失败
    private static final int ERROR_ROLE_MENU_LIMIT = -114;				//角色套餐即将失效
    private static final int ERROR_ROLE_MENU_OVER = -115;				//角色套餐结束
    private static final int ERROR_ROLE_UPDATE_LINKMAN = -116;			//角色修改联系人信息错误

    //客户端
    private static final int ERROR_CLIENT_PARAMS_EMPTY = -200;			//空参数
    private static final int ERROR_CLIENT_PARAM_FORMAT = -201;			//客户端参数格式错误
    private static final int ERROR_CLIENT_PARAM_COUNT = -202;			//客户端参数数目错误

    //其他
    private static final int ERROR_UNKNOWN = -300;						//未知错误
    private static final int ERROR_MEMCACHE_DATA_NOT_EXIST = -301;		//没有相应的缓存数据
    private static final int ERROR_ILLEGAL_OPERATION = -302;				//非法操作
    private static final int ERROR_COULD_NOT_CONNECT_TO_HOST = -303;		//网络延迟
    private static final int ERROR_WRONG_DATA_STRUCT = -304;				//错误的数据结构
    private static final int ERROR_SENDCODE_UPPERLIMIT = -305;			//短信超上限

    //数据库
    private static final int ERROR_DB_INSERT = -500;						//数据库插入数据失败
    private static final int ERROR_DB_RECORD_HELP = -501;				//数据库获取在线月记录规则失败

    private static final int ERROR_REQUEST_FAILED = -10000;
    public static final int ERROR_ANALYSIS_FAILED = -10001;

    static {
        errorCodeMap = new HashMap<>();
        errorCodeMap.put(ERROR_SERVER_MAINTENANCE,"服务器正在维护");
        errorCodeMap.put(ERROR_SERVER_NOT_RESPONDING,"服务器没有响应");
        errorCodeMap.put(ERROR_SERVER_BUSY,"服务器繁忙");
        errorCodeMap.put(ERROR_SERVER_FULL,"服务器人数已满");

        errorCodeMap.put(ERROR_TWO_INPUTS,"新密码和老密码一样");
        errorCodeMap.put(ERROR_ROLE_NAME_ILLEGAL,"非法的角色名");
        errorCodeMap.put(ERROR_ROLE_NAME_SHORT,"角色名太短");
        errorCodeMap.put(ERROR_ROLE_NAME_LONG,"角色名太长");
        errorCodeMap.put(ERROR_PASSWORD_ILLEGAL,"非法的密码");
        errorCodeMap.put(ERROR_PASSWORD_SHORT,"密码太短");
        errorCodeMap.put(ERROR_PASSWORD_LONG,"密码太长");
        errorCodeMap.put(ERROR_REGISTER_LOGINNAME_EXIST,"账号已存在");
        errorCodeMap.put(ERROR_REGISTER_INIT_MISSION,"账号已存在");

        errorCodeMap.put(ERROR_LOGIN_WRONG_PASSWORD,"密码错误");
        errorCodeMap.put(ERROR_LOGIN_ROLE_NOT_EXIST,"账号不存在");
        errorCodeMap.put(ERROR_LOGIN_ROLE_NAME_EMPTY,"账号名为空");
        errorCodeMap.put(ERROR_LOGIN_ROLE_PWD_EMPTY,"密码为空");
        errorCodeMap.put(ERROR_LOGIN_STOP,"账号禁用");
        errorCodeMap.put(ERROR_LOGIN_LIMIT,"账号合同已到期");
        errorCodeMap.put(ERROR_LOGIN_START,"账号合同未开始");
        errorCodeMap.put(ERROR_LOGIN_NORECORD,"账号合同未开始");
        errorCodeMap.put(ERROR_LOGIN_ROLE_EXIST,"登陆超时，角色需重新登录");

        errorCodeMap.put(ERROR_ROLE_GET,"角色获得失败");
        errorCodeMap.put(ERROR_ROLE_RECORD,"角色获取当月在线记录数据错误");
        errorCodeMap.put(ERROR_ROLE_RECORD_TIME,"角色提交记录时间已超过,需联系客服");
        errorCodeMap.put(ERROR_ROLE_SUBMIT_FEEDBACK,"角色提交反馈失败");
        errorCodeMap.put(ERROR_ROLE_SUBMIT_PWD,"角色提交密码错误");
        errorCodeMap.put(ERROR_ROLE_ACCOUNT_EMAIL,"角色提交绑定邮箱错误");
        errorCodeMap.put(ERROR_ROLE_UPDATE_PARENT,"角色修改父母信息错误");
        errorCodeMap.put(ERROR_ROLE_UPDATE_CARER,"角色修改照养人信息错误");
        errorCodeMap.put(ERROR_ROLE_RECORD_STATUS,"角色记录状态为已提交,不能重复提交");
        errorCodeMap.put(ERROR_ROLE_UPLOAD_HEAD,"角色头像上传失败");
        errorCodeMap.put(ERROR_ROLE_BODYCHECK_MAX,"角色体检次数到达上限");
        errorCodeMap.put(ERROR_ROLE_TREAT_MAX,"角色治疗次数到达上限");
        errorCodeMap.put(ERROR_ROLE_SALON_MAX,"角色沙龙次数到达上限");
        errorCodeMap.put(ERROR_ROLE_MENU,"角色套餐信息获得失败");
        errorCodeMap.put(ERROR_ROLE_MENU_LIMIT,"角色套餐即将失效");
        errorCodeMap.put(ERROR_ROLE_MENU_OVER,"角色套餐结束");
        errorCodeMap.put(ERROR_ROLE_UPDATE_LINKMAN,"角色修改联系人信息错误");

        errorCodeMap.put(ERROR_VCODE_WRONG,"输入的校验码与发送验证码不一致");
        errorCodeMap.put(ERROR_CHECKCODE_EMPTY,"校验码为空");
        errorCodeMap.put(ERROR_VCODE_SEND,"未发送验证码");
        errorCodeMap.put(ERROR_PHONENUMBER,"手机号格式有误");
        errorCodeMap.put(ERROR_REGISTER_ROLE_EXIST,"注册账号已存在");

        errorCodeMap.put(ERROR_CLIENT_PARAMS_EMPTY,"空参数");
        errorCodeMap.put(ERROR_CLIENT_PARAM_FORMAT,"客户端参数格式错误");
        errorCodeMap.put(ERROR_CLIENT_PARAM_COUNT,"客户端参数数目错误");

        errorCodeMap.put(ERROR_UNKNOWN,"未知错误");
        errorCodeMap.put(ERROR_MEMCACHE_DATA_NOT_EXIST,"没有相应的缓存数据");
        errorCodeMap.put(ERROR_ILLEGAL_OPERATION,"非法操作");
        errorCodeMap.put(ERROR_COULD_NOT_CONNECT_TO_HOST,"网络延迟");
        errorCodeMap.put(ERROR_WRONG_DATA_STRUCT,"错误的数据结构");
        errorCodeMap.put(ERROR_SENDCODE_UPPERLIMIT,"错误的数据结构");

        errorCodeMap.put(ERROR_DB_INSERT,"数据库插入数据失败");
        errorCodeMap.put(ERROR_DB_RECORD_HELP,"数据库获取在线月记录规则失败");

        errorCodeMap.put(ERROR_REQUEST_FAILED,"请求失败");
        errorCodeMap.put(ERROR_ANALYSIS_FAILED,"解析失败");
    }

    public static String getErrorMsg(int code){
        if ( errorCodeMap.containsKey(code)) {
            return errorCodeMap.get(code);
        }
        return null;
    }

    public static String getErrorMsg(String errorMsg){
        if ( errorMsg == null ){
            return "数据为空";
        }
        int errorCode = getErrorCode(errorMsg);
        return getErrorMsg(errorCode);
    }

    public static int getErrorCode(String errorMsg){
        int errorCode = 0;
        try {
            JSONObject object = new JSONObject(errorMsg);
            errorCode = object.getInt("errorId");
        } catch (JSONException e) {
            e.printStackTrace();
            errorCode = ERROR_ANALYSIS_FAILED;
        }
        return errorCode;
    }

}
