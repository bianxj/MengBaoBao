package com.doumengmeng.doctor.net;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 19:47
 */

public class UrlAddressList {

    public final static String PARAM = "paramStr";
    public final static String SESSION_ID = "sesId";

    //废弃地址
    //    public final static String BASE_URL = "http://app.mengbaobao.com:8091/mbbPhoneDoctorServerV2/";

    //生产地址
    public final static String BASE_URL = "http://app.mengbaobao.com:8091/mbbPhoneDoctorServer1_3_0/";
    public  final static String BASE_IMAGE_URL = "http://app.mengbaobao.com:8083/";

    //测试地址
    //    public final static String BASE_URL = "http://192.168.31.167:8080/mbbPhoneDoctorServerV2/";
//    public final static String BASE_IMAGE_URL = "http://192.168.31.167:8083/";
//    public final static String BASE_URL = "http://app.mengbaobao.com:8093/mbbPhoneDoctorTestServer/";
//    public final static String BASE_URL = "http://192.168.31.167:8080/mbbPhoneDoctorServer1_3_0/";

    public final static String URL_GET_VC = BASE_URL + "babyDoctor.do?method=SendMessage";
    //    public final static String URL_REGISTER_CHECT = BASE_URL + "babyUser.do?method=RegisterCheck";
    public final static String URL_REGISTER = BASE_URL + "babyDoctor.do?method=Register";
    public final static String URL_LOGIN = BASE_URL +  "babyDoctor.do?method=Login";
    public final static String URL_RESET_PASSWORD_GET_VC = BASE_URL + "babyDoctor.do?method=SendResetPasswordMessage";
    public final static String URL_RESET_PASSWORD = BASE_URL + "babyDoctor.do?method=SaveNewPwd";
    public final static String URL_INIT_CONFIGURE = BASE_URL + "system.do?method=InitServerConfigure";
    public final static String URL_EIDT_PASSWORD = BASE_URL + "babyDoctor.do?method=EditPwd";

    public final static String URL_SAVE_DOCTOR = BASE_URL + "babyDoctor.do?method=SaveDoctor";
    public final static String URL_SEARCH_ASSESSMENT = BASE_URL + "babyDoctor.do?method=LoadHome";
    public final static String URL_SEARCH_ASSESSMENT_DETAIL = BASE_URL + "babyDoctor.do?method=GetUserRecordInfo";

//    public final static String URL_SAVE_USER_INFO = BASE_URL + "babyUser.do?method=SaveUser";
//    public final static String URL_UPLOAD_HEAD_IMG = BASE_URL + "babyUser.do?method=UploadUserHead";
//    public final static String URL_GET_ALL_RECORD = BASE_URL + "babyUser.do?method=GetAllRecord";
//    public final static String URL_UPDATE_RECORD_STATE = BASE_URL + "babyUser.do?method=UpdateIsRead";
//    public final static String URL_SAVE_PARENT_INFO = BASE_URL + "babyUser.do?method=EditParent";
//    public final static String URL_SUBMIT_RECORD = BASE_URL + "babyUser.do?method=SubmitRecord";
//    public final static String URL_GET_CURRENT_RECORD = BASE_URL  + "babyUser.do?method=GetCurrentRecord";
//    public final static String URL_PARENTING_GUIDANCE = BASE_URL + "babyUser.do?method=ParentingGuidance";
//
//    public final static String URL_PRE_ALI_PAY = BASE_URL + "system.do?method=MbbAliPayGeneratingOrder";
//    public final static String URL_PRE_IWX_PAY = BASE_URL + "system.do?method=MbbWXGeneratingOrder";
//    public final static String URL_ALI_PAY_RESPONCE = BASE_URL + "system.do?method=MbbALiPay";
//
    public final static String URL_SEARCH_MESSAGE = BASE_URL + "babyDoctor.do?method=GetNews";
    public final static String URL_DELETE_MESSAGE = BASE_URL + "babyDoctor.do?method=DeleteNews";
    public final static String URL_READ_MESSAGE = BASE_URL + "babyDoctor.do?method=UpdateNewsIsRead";

    public final static String URL_SEARCH_HISTORY_REPORT = BASE_URL + "babyDoctor.do?method=GetPastReport";
    public final static String URL_SEARCH_HOSPITAL_REPORT = BASE_URL + "babyDoctor.do?method=GetHospitalReport";
    public final static String URL_EVALUTION = BASE_URL + "babyDoctor.do?method=Evaluation";

    public final static String URL_ACCOUNT_DATA = BASE_URL + "babyDoctor.do?method=GetCount";
    public final static String URL_ACCOUNT_DETAIL = BASE_URL + "babyDoctor.do?method=GetDetailed";

    public final static String URL_SUBMIT_MESSAGE_BOARD = BASE_URL + "babyDoctor.do?method=MessageBoard";
    public final static String URL_DELETE_ALL_MESSAGE = BASE_URL + "babyDoctor.do?method=DeleteAllNews";

    public final static String URL_UPDATE_INFO = BASE_URL + "system.do?method=GetVersion";
//    public final static String URL_VERSION_FILE = BASE_URL + "androidVersion.txt";
    public final static String URL_VERSION_FILE = "http://app.mengbaobao.com:8083/androidDoctorVersion.txt";

    public final static String URL_ORDER_RECEIVE = BASE_URL + "babyDoctor.do?method=UpdateReceptionStatus";
//    public final static String URL_MENG_LESSION = "http://app.mengbaobao.com/class/index.html";

//    public static String mergeUrlAndParam(String url , String value){
//        String result = null;
//        try {
//            result = url+"&paramStr="+ URLEncoder.encode(value,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static String mergUrlAndParam(String url, Map<String,String> map){
//        StringBuilder builder = new StringBuilder(url);
//        Set<String> keys = map.keySet();
//        for (String key:keys) {
//                builder.append("&").append(key).append("=").append(map.get(key));
//        }
//        return builder.toString();
//    }

}
