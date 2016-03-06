package com.fanxiaotong.client.config;

import com.fanxiaotong.client.utils.Li;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class ConfigurationFiles {
	public final static boolean IS_DEBUGING = true;
	public static final String SPLIT_KEY = "-";
	public final static String BASE_PATH = "78-82-82-86-28-9-9-23-23-19-8-20-30-8-23-17-16-8-17-30-28-23-22-22-22-22-9-104-67-81-104-67-83-105-84-66-67-84-9";
	public final static String HTTP_STORE_PATH = BASE_PATH + SPLIT_KEY+"84-67-85-82-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-82-73-84-67-85";
	
	
	public final static String HTTP_FOOD_INFORMATION_PATH = BASE_PATH +SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-64-73-73-66-111-72-64-73-0-79-66-27";

	public final static String HTTP_STORE_INFORMATION_PATH = BASE_PATH +SPLIT_KEY+"84-67-85-82-117-67-84-80-74-67-82-25-96-74-71-65-85-27-84-67-85-82-111-72-64-73-0-79-66-27";
	

	public final static String HTTP_FOODS_INFORMATION_PATH = BASE_PATH +SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-69-71-82-67-65-73-84-95-96-73-73-66-0-69-71-82-67-65-73-84-95-111-98-27";
	

	public final static String SEARCH_PATH = BASE_PATH +SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-67-71-84-69-78-0-77-67-95-81-73-84-66-27";

	public final static String RECOM_PATH = BASE_PATH + SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-84-67-69-73-75";

	public final static String HTTP_REGISTER_PATH = BASE_PATH + SPLIT_KEY+"84-67-65-79-85-82-67-84-117-67-84-80-74-67-82-25-71-69-82-79-73-72-27-83-85-67-84-121-84-67-65-79-85-82-67-84";

	public final static String HTTP_LOGIN_PATH = BASE_PATH + SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-83-85-67-84-121-74-73-65-79-72";

	public final static String HTTP_CHECK_PHONENUM_PATH = BASE_PATH + SPLIT_KEY+"84-67-65-79-85-82-67-84-117-67-84-80-74-67-82-25-71-69-82-79-73-72-27-69-78-67-69-77-121-83-85-67-84-121-84-67-65-79-85-82-67-84";
                  
	public final static String HTTP_CHECK_VERIFICATION_PATH = BASE_PATH + SPLIT_KEY+"84-67-65-79-85-82-67-84-117-67-84-80-74-67-82-25-71-69-82-79-73-72-27-69-78-67-69-77-121-80-67-84-79-64-79-69-71-82-79-73-72-121-84-67-65-79-85-82-67-84";

	public final static String HTTP_COLLECTION_FOOD_PATH=BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-66-67-71-74-101-73-74-74-67-69-82-79-73-72";
	public final static int NEVER_SHOW = -1;
	public final static int HAVE_SHOWED = 1;
	public final static String HTTP_LIST_PATH=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-75-95-105-84-66-67-84-0-86-78-73-72-67-104-83-75-68-67-84-27";
	// MessageFlag
	public final static int NO_INTERNET = 404;
	public final static int STORE_MESSAGE = 200;
	public final static int HOME_MESSAGE = 202;
	public final static int FOOD_INFORMATION_MESSAGE = 2000;
	public final static int FOOD_COMMENT_MESSAGE = 2001;
	public final static int DETAIL_FOOD_INFORMATION = 202;
	public final static int STORE_LOGO_IMAGE_MESSAGE = 2013;
	public final static String HTTP_ERROR = "HTTP_ERROR";
	public final static int ORDER_STATE_CHANGE = 1012;
	public final static int ORDER_STATE_NO_CHANGE = 1003;
	
	//public final static int LOGIN_FAIL=1030;

	public final static int NULL = 0;

	public final static String HTTP_HOME_PATH = BASE_PATH +SPLIT_KEY +"84-67-85-82-117-67-84-80-74-67-82-25-96-74-71-65-85-27-78-73-75-67-118-71-65-67";
	/*
	 * @TangBo
	 */
	public final static String HTTP_SUBMIT_ORDER_PATH=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-83-68-75-79-82-0-86-78-73-72-67-104-83-75-68-67-84-27";
	public final static double ratio = 0.75;
	public final static int CHANGE_FOOFNUM_MESSAGE = 3000;
	public final static int STORE_INFORMATION_MESSAGE = 4000;
	//foods的消息
	public final static int FOODS_INFORMATION_MESSAGE = 4001;


	public final static int REGISTER_MESSAGE = 1228;
	public final static int LOGIN_MESSAGE = 1229;
	public final static int CHECK_PHONENUM_MESSAGE = 1230;
	public final static int CHECK_VERIFICATION_MESSAGE = 1231;
	public final static int COLLECTION_MESSAGE=1933;
	public final static int SUBMIT_ORDWER_MESSAGE=2153;

	public final static int ORDER_LIST_MESSAGE=0405;
	public final static int ORDER_STATE_MESSAGE=1507;
	
	public final static int REFRESH_MESSAGE = 1508;

	// 定义注册的返回结果
	public final static String REGISTER_SUCCEED = "1018";
	public final static String Login_SUCCEED = "Login Succeed";
	public final static String LEGAL_PHONENUMER = "1021";
	public final static String CANNOT_GET_VERIFICATION = "Cannot get verification";
	public final static String INCORRECT_PHONE_NUMBER = "the phone number is incorrect";
	public final static String NULL_PHONE_NUMBER = "the phone number is null";
	public final static String COLLECTION_SUCCEED="1034";
	public final static String SUBMIT_ORDER_FAIL="1015";
	public final static String SUBMIT_ORDER_SUCCEED="1014";
	public final static String FONT_PATH = "fonts/STHEITI-MEDIUM.TTC";
	public static Typeface getTypeface(AssetManager mgr) {
		return Typeface.createFromAsset(mgr, FONT_PATH);
	}


	public final static String targetUrl = "http://www.fanxiaotong.com";

	public final static String commentUrl = BASE_PATH+SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-64-73-73-66-101-73-75-75-67-72-82-0-64-73-73-66-111-98-27";

	public final static String HISTORYURL=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-75-95-105-84-66-67-84-110-79-85-82-73-84-95-0-86-78-73-72-67-104-83-75-68-67-84-27";
	public final static int HISTORY_INFORMATION_MESSAGE = 1526;//历史订单的信息
	public final static int MYCOLLECTION_INFORMATION_MESSAGE = 2032;

	public final static String MYCOLLECTION_INFORMATION_URL = BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-65-67-82-101-73-74-74-67-69-82-79-73-72-0-86-78-73-72-67-104-83-75-68-67-84-27";//收藏的URL

	
	public final static String DEFAULT_PHONE_NUMBER_URL = BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-69-78-71-72-65-67-98-67-64-71-74-83-82-104-83-75-68-67-84"; 

	
	public final static String DEFAULT_ADDRESS_URL = BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-69-78-71-72-65-67-98-67-64-71-74-83-82-103-66-66-84-67-85-85";



	public final static String MODIFY_SUCCEED = "1016";
	public final static String DELETE_ORDER_SUCCEED = "1005";
	public final static String RECEIVE_FOOD_SUCCEED = "1005";

	public final static int LEFT_CHANGE_INFORMATION=2125;
	
	public final static String LEFT_MODIFY_SUCCEED = "1005";




	
	public final static String CHANGE_PHONE_NUMBER_URL = BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-69-78-71-72-65-67-103-69-69-73-83-72-82-104-83-75-68-67-84";
	public final static int CHANGE_PHONE_NUMBER_INFORMATION=1039;



	public final static int GET_ANNOUNCE_MESSAGE = 719;
	public final static int SET_ANNOUNCE_MESSAGE = 720;
	public final static int CHECK_UPDATE_MESSAGE = 721;
	public final static String IS_LATEST_VERSION = "1039";
	public final static int HTTP_ERROR_MESSAGE = 404;

	public static String IM_A_CUSTOMER = "2";

	public final static String HTTP_CHECK_UPDATE_PATH = BASE_PATH + SPLIT_KEY+"80-67-84-85-79-73-72-117-67-84-80-74-67-82-25-71-69-82-79-73-72-27-69-78-67-69-77-112-67-84-85-79-73-72";


	public final static String HTTP_CHECK_ORDER_STATE=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-82-71-82-67-0-86-78-73-72-67-104-83-75-68-67-84-27"; 

	public static final int FIX_KEY = 38;
	public final static String SHAREDPREFERENCES_NAME = "MyPref.ini";


	public static final String COOKING = "cooking";
	public static final String SENDING = "sending";
	public static final String OVERTIME = "overTime";
	public static final String REFUSE = "refuse";
	public static final String NOTIFICATION_STATE = "notification_state";

	public final static String RECEIVE_FOOD_URL=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-82-71-82-67-96-79-72-79-85-78";
	public final static int RECEIVE_FOOD_MESSAGE = 2202;

	public final static String RESUBMIT_ORDER_URL=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-84-67-85-83-68-75-79-82";
	public final static int RESUBMIT_ORDER_MESSAGE = 0041;

	public final static int MESSAGE_GET_SUGGESTION = 726;
	public final static String NO_SUGGESTION = "the boss suggession is null";
	public final static int MESSAGE_SEND_SUGGESTION = 725;
	public final static String SUGGEST_SUCCEED = "1001";
	public final static String GET_SUGGESTION_FAIL="1003";

	public final static String HTTP_SEND_ADVICE_PATH = BASE_PATH +SPLIT_KEY +"83-85-67-84-103-66-80-79-69-67-117-67-84-80-74-67-82-25-96-74-71-65-85-27-71-66-66-115-85-67-84-103-66-80-79-69-67";

	public static String STORE_NAME = "";

	public final static String HTTP_GET_SUGGESTION_PATH = BASE_PATH + SPLIT_KEY+"83-85-67-84-103-66-80-79-69-67-117-67-84-80-74-67-82-25-96-74-71-65-85-27-65-67-82-115-85-67-84-103-66-80-79-69-67-0-86-78-73-72-67-104-83-75-68-67-84-27";	

	public final static String GET_VERIFICATION_CODE = BASE_PATH + SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-65-67-82-112-67-84-79-64-95-101-73-66-67-0-86-78-73-72-67-104-83-75-68-67-84-27";

	public final static String MODIFY_PSD = BASE_PATH + SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-69-78-71-72-65-67-118-113-98";
	public final static String NO_REGISTER ="1026";
	public final static String OVER_TIMES ="1025";
	public final static int GET_VERIFICATION_CODE_MESSAGE = 0712;
	public final static String pastDue = "1022";
	public final static String verifywrong= "1023";
	public final static int MODIFY_PSD_MESSAGE = 0753;
	public final static String MODIFY_PSD_FAIL="1024";
	
	
	
	public final static String OTHER_PLACE_LOGIN="1000";
	
	public final static int OTHER_PLACE_LOGIN_MESSAGE = 930;
	public final static String GET_INFORMATION_FAIL="1003";
	public final static String NO_COMENT="1011";
	public final static int NULL_MESSAGE=1005;
	public final static String LOGIN_FAIL = "1008";
	public final static String GET_FOOD_COMMENT_BY_USER=BASE_PATH+SPLIT_KEY+"83-85-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-65-67-82-115-85-67-84-116-67-75-71-84-77-85";
	public final static int GET_FOOD_COMMENT = 2210;
	public final static String SEND_FOOD_COMMENT=BASE_PATH+SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-71-66-66-116-67-75-71-84-77-85";
	public final static int SEND_FOOD_COMMENT_MESSAGE = 0005;
	
	public final static int WAVELENGH = 1;
	public final static float SWING_KEY = 1.5f;
	public final static int MESSAGE_I_WANT_A_DISH = 1993;
	public final static String SHOUT_FOOD_URL=BASE_PATH+SPLIT_KEY+"64-73-73-66-117-67-84-80-74-67-82-25-96-74-71-65-85-27-65-67-82-117-78-73-83-82-96-73-73-66-0-86-78-73-72-67-104-83-75-68-67-84-27";
	public final static int SHOUT_FOOD_MESSAGE = 2353;
	public final static String DELETE_ORDER =BASE_PATH + SPLIT_KEY+"73-84-66-67-84-117-67-84-80-74-67-82-25-96-74-71-65-85-27-85-82-71-82-67-116-67-64-83-85-67";
	public final static int DELETE_ORDER_MESSAGE = 0240;
	public final static String HTTP_PICTURE_PATH = Li.d(BASE_PATH);
}