package com.eighteen.goradar.util;

public class Constant {


    public interface Key_sp {
        String SUB_STATUS = "sub_status";//订阅的状态
    }

    public interface Name_sp {
        String BUY = "buy";
    }

    public interface Event{
        int SHOW_DIALOG = 1;//显示弹窗
        int DISS_DIALOG = 2;//关闭弹窗
        int QUERY_SUB = 3;//查询订阅
        int SUB = 4;//订阅
        int QUERY_SUB_AND_BUY = 5;//查询订阅和够买

    }

}
