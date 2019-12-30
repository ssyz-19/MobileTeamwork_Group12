package com.pkuhospital.Utils;

/**
 * 全局变量
 * @author 杨洲
 * @time 2019.12.7
 */
public class GlobalVar {
    private static boolean WHETHER_USER_SIGN_IN  = false;  //用户是否登录,false表示无用户登录
                                                //true表示有用户登录

    private static String STU_ID;

    private static String SERVER_URL = "http://212.64.89.136";  //服务器地址

    public static boolean isWhetherUserSignIn() {
        return WHETHER_USER_SIGN_IN;
    }

    public static void invertWhetherUserSignIn() {
        WHETHER_USER_SIGN_IN = !WHETHER_USER_SIGN_IN;
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static String getStuId() {
        return STU_ID;
    }

    public static void setStuId(String stuId) {
        STU_ID = stuId;
    }
}
