package com.xuexiang.templateproject;

import static org.junit.Assert.assertEquals;

import com.xuexiang.templateproject.core.http.entity.TipInfo;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xutil.net.JsonUtil;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static String newsKey = "bec633393690881151584f0ce9462ecf";//新闻key
    EventHandler eventHandler;//事件处理

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);


        TipInfo info = new TipInfo();
        info.setTitle("微信公众号");
        info.setContent("获取更多资讯，欢迎关注我的微信公众号：【我的Android开源之旅】");
        List<TipInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(info);
        }
        ApiResult<List<TipInfo>> result = new ApiResult<>();
        result.setData(list);
        System.out.println(JsonUtil.toJson(result));
    }

    @Test
    public void message() {
        //mobsdk隐私政策
        ///   MobSDK.submitPolicyGrantResult(true);

        String phone = "18682675515";
        VerifyCode.getVerificationCode("86", phone);
    }

    @Test
    public void news() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //聚合数据请求api
                OkhttpUtils.get("http://v.juhe.cn/toutiao/index?type=top&key=" + newsKey + "&page_size=5", new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        System.err.println(JsonOperate.newsParseJsonData(result, "result"));
                    }
                });

            }
        }.start();
    }

    private static class VerifyCode {
        /**
         * 请求文本验证码
         *
         * @param country 国家区号
         * @param phone   手机号
         */
        public static void getVerificationCode(String country, String phone) {
            //获取短信验证码
            SMSSDK.getVerificationCode(country, phone);
        }

        /**
         * 提交验证码
         *
         * @param country 国家区号
         * @param phone   手机号
         * @param code    验证码
         */
        public static void submitVerificationCode(String country, String phone, String code) {
            SMSSDK.submitVerificationCode(country, phone, code);
        }
    }

}