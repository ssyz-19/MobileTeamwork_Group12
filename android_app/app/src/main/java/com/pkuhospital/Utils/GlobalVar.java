package com.pkuhospital.Utils;

/**
 * 全局变量
 * @author 杨洲
 * @time 2019.12.7
 */
public class GlobalVar {
    private static boolean WHETHER_USER_SIGN_IN  = false;  //用户是否登录,false表示没有用户登录
                                                //true表示有用户登录

    public static boolean isWhetherUserSignIn() {
        return WHETHER_USER_SIGN_IN;
    }

    public static void invertWhetherUserSignIn() {
        WHETHER_USER_SIGN_IN = !WHETHER_USER_SIGN_IN;
    }
}
