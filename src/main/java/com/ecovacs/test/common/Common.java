package com.ecovacs.test.common;


/**
 * Created by ecosqa on 16/12/6.
 * common function
 */
public class Common {
    private static Common common = null;

    private Common(){

    }

    public static Common getInstance(){
        if(common == null){
            common = new Common();
        }
        return common;
    }

    public void waitSecond(int milSec){
        try {
            Thread.sleep(milSec);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

}
