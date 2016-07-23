package com.mideas.fio.interfaceclass;


/**
 * the hien ket qua nhan ve
 * <p/>
 * Created by dunghv10 on 6/19/2015.
 */
public abstract class BusinessListener {

    // khoi tao truoc khi request
    public abstract void initBeforeRequest();

    // tra ve ket qua thanh cong
    public abstract void onResponse(Object response);

    //tra ve ket qua that bai
    public abstract void onError(Object error);


}
