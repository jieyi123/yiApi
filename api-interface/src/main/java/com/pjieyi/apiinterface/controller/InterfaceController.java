package com.pjieyi.apiinterface.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.pjieyi.apiinterface.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pjieyi
 * @description
 */

@RestController
@RequestMapping("/interface")
public class InterfaceController {

    /**
     * 获取每日壁纸
     * @return 图片路径
     */
    @PostMapping("/day/wallpaper")
    public String getDayWallpaperUrl(){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("format","json");
        HttpResponse response = HttpRequest.post("https://tenapi.cn/v2/bing")
                .form(paramMap)
                .execute();
        String body = response.body();
        BaseResponse baseResponse = JSONUtil.toBean(body, BaseResponse.class);
        return baseResponse.getData().getUrl();
    }

    /**
     * 随机获取文本
     * @return 文本
     */
    @GetMapping("/random/word")
    public String getRandomWork(){
        HttpResponse response = HttpRequest.get("https://tenapi.cn/v2/yiyan")
                .execute();

        return response.body();
    }

    /**
     * 随机获取动漫图片
     * @return 图片地址
     */
    @PostMapping("/random/image")
    public String getRandomImageUrl(){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("format","json");
        HttpResponse response = HttpRequest.post("https://tenapi.cn/v2/acg")
                .form(paramMap)
                .execute();
        String body = response.body();
        BaseResponse baseResponse = JSONUtil.toBean(body, BaseResponse.class);
        return baseResponse.getData().getUrl();
    }

    /**
     * 百度每日热点
     * @return
     */
    @GetMapping("/baiduHot")
    public String getBaiduHot(){
        HttpResponse response = HttpRequest.get("https://tenapi.cn/v2/baiduhot")
                .execute();
        String body = response.body();
        BaiduHotResponse baseResponse = JSONUtil.toBean(body, BaiduHotResponse.class);
        BaiduHot[] datas = baseResponse.getData();
        List<String> names = Arrays.stream(datas).map(data -> data.getName()).collect(Collectors.toList());
        return JSONUtil.toJsonStr(names);
    }

    /**
     * https://api.aa1.cn/
     * KFC疯狂星期四文案
     * @return
     */
    @GetMapping("/kfc")
    public String getKfc(){
        HttpResponse response = HttpRequest.get("https://api.khkj6.com/kfc/")
                .execute();
        String body = response.body();
        KfcResponse bean = JSONUtil.toBean(body, KfcResponse.class);
        System.out.println(bean.getMsg());
        return bean.getMsg();
    }


    /**
     * 每日英文
     * @return
     */
    @GetMapping("/oneDayEnglish")
    public String getOneDayEnglish(){
        HttpResponse response = HttpRequest.get("https://api.oioweb.cn/api/common/OneDayEnglish")
                .execute();
        String body = response.body();
        DayEnglishResponse bean = JSONUtil.toBean(body, DayEnglishResponse.class);
        OneDayEnglish result = bean.getResult();
        String res=result.getContent()+"/n"+result.getNote();
        System.out.println(res);
        return res;
    }

}
