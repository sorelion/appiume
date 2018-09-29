package com.cmit.clouddetection.request;

import com.cmit.clouddetection.bean.MachineInfo;
import com.cmit.clouddetection.bean.RequestData;
import com.cmit.clouddetection.bean.ResponeData;
import com.cmit.clouddetection.bean.test;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pact on 2018/9/28.
 */

public interface MachineInfoService {
    /**
     * 上传手机信息
     */
    @POST("rphone/upPhoneInfo")
    Observable<test> machineInfoResponse(@Body MachineInfo machineInfo);

    /**
     * 获取任务
     */
    @FormUrlEncoded
    @POST("taskexecute/getTask")
    Observable<ResponeData> getTask(@FieldMap Map<String, String> phoneInfo);

    /**
     * 获取任务
     */
    @FormUrlEncoded
    @POST("taskexecute/getTask")
    Observable<String> getTaskTest(@FieldMap Map<String, String> phoneInfo);
}
