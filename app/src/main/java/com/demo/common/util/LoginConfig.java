package com.demo.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.MyApplicaction;


//http://blog.csdn.net/xiaanming/article/details/9339515
public final class LoginConfig {

    private static SharedPreferences perference;
    private static LoginConfig instance;
    private String image_head = "bitmap";
    public static String share_context = "Hello，我是一起记，最美的相遇，便是久别重逢，期待重新认识你。";
    public static String share_url = "http://api.yiqijiba.com/common/index/download";

    /**
     * 是否是第一次启动app
     */
    final static String IS_STARTE_APP = "is_starte_app";

    private LoginConfig(Context context) {
        perference = context.getSharedPreferences("login_prefrence", Context.MODE_PRIVATE);
    }

    public static synchronized LoginConfig getInstance() {
        if (instance == null) {
            instance = new LoginConfig(MyApplicaction.getContext());
        }
        return instance;
    }

    public void setIsStarteApp(boolean value) {
        perference.edit().putBoolean(IS_STARTE_APP, value).commit();
    }

    public boolean getIsStarteApp() {
        return perference.getBoolean(IS_STARTE_APP, true);
    }



    /**
     * 登录凭证
     */
    final static String TOKENID = "tokenid";
    /**
     * 七牛token
     */
    final static String TOKENIDQINIU = "tokenid_qiniu";
    /**
     * 七牛key
     */
    final static String TOKENIDKEY = "tokenid_key";
    /**
     * 七牛host
     */
    final static String TOKENIDHOST = "tokenid_host";
    /**
     * 登录成功后，用户名
     */
    final static String USERNAME = "userName";
    final static String MOBILE = "Mobile";
    final static String truename = "truename";
    final static String identityno = "identityno";
    final static String isActivity = "isActivity";
    /**
     * 是否设置交易密码
     */
    final static String isSetingPay = "hastradepass";
    /**
     * 是否实名认证
     */
    final static String isHasidentity = "hasidentity";
    /**
     * 是否设置过密码
     */
    final static String isPassword = "password";
    /**
     * 是否是第一次启动app
     */
    final static String isForstStartApp = "startapp";
    /**
     * 是否是第一次使用操作账本
     */
    final static String isFisrtBook = "isfisrtbook";
    /**
     * 手势密码登入后返回Key
     */
    final static String FINGERDRAWSIGN = "fingerdrawsign";

    final static String FINGERDRAWRLS = "fingerdrawrls";

    /**
     * 是否设置手势密码
     */
    final static String isSettingsGesturePassword = "isSettingsGesturePassword";
    /**
     * 是否隐藏资金额显示
     */
    final static String IS_SHOW_ACCOUNTBALANCE = "is_show_accountbalance";
    private final static String AVATAR_URL = "avatar_url";

    /**
     * 是否显示手势密码痕迹
     */
    private static String ISTRAJECTORY = "trajectory";

    /**
     * 手势密码输入错误次数,，默认5次
     */
    private static String ISERRORCOUNT = "isErrorCount";

    private static String INMAINSHOWGESTUREPASSWORD = "inMainShowGesturePassword";
    private static String LATITUDEANDLONGITUDE = "Latitudeandlongitude";//定位经纬度
    private static String LOCATIONCITY = "LocationCity";//定位城市

    /**
     * 检查更新
     */
    private static String LASTUPDATEAPPTIMESTAMP = "last_update_app_timestamp";

    public void isActivty(boolean isActivty) {
        perference.edit().putBoolean(isActivity, isActivty).commit();
    }

    public boolean getIsActivity() {
        return perference.getBoolean(isActivity, false);
    }

    public void isFirstStartApp(boolean isStart) {
        perference.edit().putBoolean(isForstStartApp, isStart).commit();
    }

    public boolean getIsFirstStartApp() {
        return perference.getBoolean(isForstStartApp, true);
    }

    public void isFisrtbook(boolean isfisrtbook) {
        perference.edit().putBoolean(isFisrtBook, isfisrtbook).commit();
    }

    public boolean getIsFisrtbook() {
        return perference.getBoolean(isFisrtBook, true);
    }

    public void isPassword(String isPassword) {
        perference.edit().putString(LoginConfig.isPassword, isPassword).commit();
    }

    public String getIsPassword() {
        return perference.getString(isPassword, "");
    }

    public void isSettingPay(String isSetingPay) {
        perference.edit().putString(LoginConfig.isSetingPay, isSetingPay).commit();
    }

    public String getIsSettingPay() {
        return perference.getString(isSetingPay, "");
    }

    public void setIdentityno(String identityno) {
        perference.edit().putString(LoginConfig.identityno, identityno).commit();
    }

    public String getIdentityno() {
        return perference.getString(identityno, "");
    }

    public void setTruename(String truename) {
        perference.edit().putString(LoginConfig.truename, truename).commit();
    }

    public String getTruename() {
        return perference.getString(truename, "");
    }


    public void setTokenId(String tokenid) {
        perference.edit().putString(TOKENID, tokenid).commit();
    }

    public String getTokenId() {
        return perference.getString(TOKENID, "");
    }

    /**
     * 手势密码签名字符串
     */
    public String getFingerdrawsign() {
        return perference.getString(FINGERDRAWSIGN, "");
    }

    public void setFingerdrawsign(String fingerdrawsign) {
        perference.edit().putString(FINGERDRAWSIGN, fingerdrawsign).commit();
    }

    /**
     * 手势密码签名字符串
     */
    public String getFingerdrawrls() {
        return perference.getString(FINGERDRAWRLS, "");
    }

    public void setFingerdrawrls(String fingerdrawrls) {
        perference.edit().putString(FINGERDRAWRLS, fingerdrawrls).commit();
    }

    /**
     * 是否设置手势密码，默认设置
     *
     * @param isSettings
     */
    public void isSettingsGesturePassword(boolean isSettings) {
        perference.edit().putBoolean(isSettingsGesturePassword, isSettings).commit();
    }

    public boolean getIsSettingsGesturePassword() {
        return perference.getBoolean(isSettingsGesturePassword, true);
    }

    public void setShowAccountBalance(boolean isShow) {
        perference.edit().putBoolean(IS_SHOW_ACCOUNTBALANCE, isShow).commit();
    }

    public boolean isShowAccountBalance() {
        return perference.getBoolean(IS_SHOW_ACCOUNTBALANCE, true);
    }

    public String getUserAvatarUrl() {
        return perference.getString(AVATAR_URL, "");
    }

    public void saveUserAvatarUrl(String avatar) {
        perference.edit().putString(AVATAR_URL, avatar).commit();
    }

    public void setTrajectory(boolean isTrajectory) {
        perference.edit().putBoolean(ISTRAJECTORY, isTrajectory).commit();
    }

    public boolean getTrajectory() {
        return perference.getBoolean(ISTRAJECTORY, true);
    }

    public void setErrorCount(int errorCount) {
        perference.edit().putInt(ISERRORCOUNT, errorCount).commit();
    }

    public int getErrorCount() {
        return perference.getInt(ISERRORCOUNT, 5);
    }

    public void setIsHasidentity(String ishasidentity) {
        perference.edit().putString(isHasidentity, ishasidentity).commit();
    }

    public String getIsHasidentity() {
        return perference.getString(isHasidentity, "");
    }

    public void setinMainShowGesturePassword(boolean inMainShowGesturePassword) {
        perference.edit().putBoolean(INMAINSHOWGESTUREPASSWORD, inMainShowGesturePassword).commit();
    }

    public boolean getinMainShowGesturePassword() {
        return perference.getBoolean(INMAINSHOWGESTUREPASSWORD, true);
    }

    public void setLastCheckUpdateTime(long timestamp) {
        perference.edit().putLong(LASTUPDATEAPPTIMESTAMP, timestamp).commit();
    }

    public long getLastCheckUpdateTime() {
        return perference.getLong(LASTUPDATEAPPTIMESTAMP, 0l);
    }

    /**
     * 是否显示Home界面的活动
     */
    final static String ISSHOWACTIVITY = "isShowActivity";

    public void setisShowActivity(boolean isShowActivity) {
        perference.edit().putBoolean(ISSHOWACTIVITY, isShowActivity).commit();
    }

    public boolean getisShowActivity() {
        return perference.getBoolean(ISSHOWACTIVITY, true);
    }

    /**
     * 首页活动计数，4次后不在弹出
     */
    final static String ACTIVITYSUM = "activitysum";

    public void setActivitySum(int activitysum) {
        perference.edit().putInt(ACTIVITYSUM, activitysum).commit();
    }

    public int getActivitySum() {
        return perference.getInt(ACTIVITYSUM, 0);
    }

    /**
     * 活动版本version
     */
    final static String ACTIVITYVERSION = "version";

    public void setActivityversion(String version) {
        perference.edit().putString(ACTIVITYVERSION, version).commit();
    }

    public String getActivityversion() {
        return perference.getString(ACTIVITYVERSION, 1.1 + "");
    }


    /**
     * 用户名：昵称
     */
    public void setUserName(String userName) {
        perference.edit().putString(USERNAME, userName).commit();
    }

    public String getUserName() {
        return perference.getString(USERNAME, "");
    }


    /**
     * 服务器返回的头像地址
     */
    final static String USERICON = "usericon";

    public void setUsericon(String usericon) {
        perference.edit().putString(USERICON, usericon).commit();
    }

    public String getUsericon() {
        return perference.getString(USERICON, "");
    }

    /**
     * 服务器返回的用户id
     */
    final static String USERIID = "userid";

    public void setUserid(String userid) {
        perference.edit().putString(USERIID, userid).commit();
    }

    public String getUserid() {
        return perference.getString(USERIID, "");
    }


    /**
     * 手机号
     *
     * @param mobile
     */
    public void setMobile(String mobile) {
        perference.edit().putString(MOBILE, mobile).commit();
    }

    public String getMobile() {
        return perference.getString(MOBILE, "");
    }


    /**
     * 设备唯一标示
     */

    final static String DEVICE_ID = "deviceid";

    public void setDeviceid(String deviceid) {
        perference.edit().putString(DEVICE_ID, deviceid).commit();
    }

    public String getDeviceid() {
        return perference.getString(DEVICE_ID, "");
    }

    public final static String PLAT = "android";// 设备标识，android |ios

    public void setPlat(String plat) {
        perference.edit().putString(PLAT, plat).commit();
    }

    public String getPlat() {
        return perference.getString(PLAT, "");
    }

    public final static String APPVER = "appver";// app版本号

    public void setAppver(String appver) {
        perference.edit().putString(APPVER, appver).commit();
    }

    public String getAppver() {
        return perference.getString(APPVER, "");
    }

    public final static String OSVER = "osver";// 系统版本

    public void setOsver(String osver) {
        perference.edit().putString(OSVER, osver).commit();
    }

    public String getOsver() {
        return perference.getString(OSVER, "");
    }

    public final static String MACHINE = "machine";// 手机型号

    public void setMachine(String machine) {
        perference.edit().putString(MACHINE, machine).commit();
    }

    public String getMachine() {
        return perference.getString(MACHINE, "");
    }

    public final static String BUDGET = "budget";// 设置预算

    public void setBudget(String id, String budget) {
        perference.edit().putString(BUDGET + "_" + id, budget).commit();
    }

    public String getBudget(String id) {
        return perference.getString(BUDGET + "_" + id, "0.00");
    }

    public final static String MEMBERID = "memberid";// 手动添加成员id设置

    public void setMemberid(long memberid) {
        perference.edit().putLong(MEMBERID, memberid).commit();
    }

    public long getMemberid() {
        return perference.getLong(MEMBERID, 1);
    }

    public final static String BOOKNAME = "bookname";// 账本名称

    public void setBookname(String bookname) {
        perference.edit().putString(BOOKNAME, bookname).commit();
    }

    public String getBookname() {
        return perference.getString(BOOKNAME, "日常账本");
    }

    public final static String BOOKID = "bookid";// 账本名称

    public void setBookId(String bookid) {
        perference.edit().putString(BOOKID, bookid).commit();
    }

    public String getBookId() {
        return perference.getString(BOOKID, "");
    }

    // 账本json字符创
    public final static String MYJSONGBOOKINFO = "myjsongbookinfo";// 存储账本类型jsong字符创字段

    public void setJsonbook(String jsonbookid, String jsonbookString) {
        perference.edit().putString(jsonbookid, jsonbookString).commit();
    }

    public String getJsonbook(String jsonbookid) {
        return perference.getString(jsonbookid, "");
    }

    // 账本分类列表
    public final static String JSONGBOOKLIST = "jsongbooklist";

    public void setJsonbookList(String jsonbooklistString) {
        perference.edit().putString(JSONGBOOKLIST, jsonbooklistString).commit();
    }

    public String getJsonbookList() {
        return perference.getString(JSONGBOOKLIST, "");
    }

    public void setQiniuToken(String QiniuTokenvalue) {
        perference.edit().putString(TOKENIDQINIU, QiniuTokenvalue).commit();
    }

    public String getQiniuToken() {
        return perference.getString(TOKENIDQINIU, "");
    }

    public void setQiniuKey(String qiniuKey) {
        perference.edit().putString(TOKENIDKEY, qiniuKey).commit();
    }

    public String getQiniuKey() {
        return perference.getString(TOKENIDKEY, "");
    }

    public void setQiniuHost(String qiniuHost) {
        perference.edit().putString(TOKENIDHOST, qiniuHost).commit();
    }

    public String getQiniuHost() {
        return perference.getString(TOKENIDHOST, "");
    }

    public void setLatitudeAndLongitude(String latitudeAndLongitude) {
        perference.edit().putString(LATITUDEANDLONGITUDE, latitudeAndLongitude).commit();
    }

    public String getLatitudeAndLongitude() {
        return perference.getString(LATITUDEANDLONGITUDE, "");
    }

    public void setLocationCity(String locationCity) {
        perference.edit().putString(LOCATIONCITY, locationCity).commit();
    }

    public String getLocationCity() {
        return perference.getString(LOCATIONCITY, "");
    }

    final static String IS_LOGIN = "is_login";

    public void setIsLogin(boolean value) {
        perference.edit().putBoolean(IS_LOGIN, value).commit();
    }

    public boolean getIsLogin() {
        return perference.getBoolean(IS_LOGIN, false);
    }

}
