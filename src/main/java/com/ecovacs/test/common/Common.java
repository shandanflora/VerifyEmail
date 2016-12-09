package com.ecovacs.test.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by ecosqa on 16/12/6.
 *
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
