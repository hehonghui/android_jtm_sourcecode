/**
 *
 *	created by Mr.Simple, Oct 31, 20142:40:18 PM.
 *	Copyright (c) 2014, hehonghui@umeng.com All Rights Reserved.
 *
 *                #####################################################
 *                #                                                   #
 *                #                       _oo0oo_                     #   
 *                #                      o8888888o                    #
 *                #                      88" . "88                    #
 *                #                      (| -_- |)                    #
 *                #                      0\  =  /0                    #   
 *                #                    ___/`---'\___                  #
 *                #                  .' \\|     |# '.                 #
 *                #                 / \\|||  :  |||# \                #
 *                #                / _||||| -:- |||||- \              #
 *                #               |   | \\\  -  #/ |   |              #
 *                #               | \_|  ''\---/''  |_/ |             #
 *                #               \  .-\__  '-'  ___/-. /             #
 *                #             ___'. .'  /--.--\  `. .'___           #
 *                #          ."" '<  `.___\_<|>_/___.' >' "".         #
 *                #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 *                #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 *                #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 *                #                       `=---='                     #
 *                #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 *                #                                                   #
 *                #               佛祖保佑         永无BUG              #
 *                #                                                   #
 *                #####################################################
 */

package com.techfrontier.demo.test.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mrsimple
 */
public class AssetsUtil {

    private static String streamToString(InputStream inputStream) {
        try {
            byte[] strByte = new byte[inputStream.available()];
            inputStream.read(strByte);
            return new String(strByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStringFromAsset(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            if (inputStream != null) {
                return streamToString(inputStream);
            }
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }
    
    public static JSONObject jsonObjectFromAssets(Context context, String fileName)
            throws JSONException {
        String result = getStringFromAsset(context, fileName) ;
        System.out.println("### json结果: " + result);
        JSONObject jsonObject = new JSONObject(getStringFromAsset(context, fileName));
        return jsonObject;
    }
    
    public static JSONArray jsonArrayFromAssets(Context context, String fileName)
            throws JSONException {
        String result = getStringFromAsset(context, fileName) ;
        System.out.println("### json结果: " + result);
        JSONArray jsonObject = new JSONArray(getStringFromAsset(context, fileName));
        return jsonObject;
    }
}
