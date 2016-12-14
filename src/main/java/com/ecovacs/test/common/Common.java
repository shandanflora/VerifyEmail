package com.ecovacs.test.common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/6.
 * common function
 */
public class Common {
    private static Common common = null;
    private static Logger logger = LoggerFactory.getLogger(Common.class);

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

    public boolean loadHtml(WebElement webElement){
        int iLoop = 0;
        boolean bResult = false;
        while (true){
            if(iLoop > 100){
                break;
            }
            try {
                webElement.getText();
                bResult = true;
                break;
            }catch (NoSuchElementException e){
                logger.info("load web html again!!!");
                Common.getInstance().waitSecond(500);
            }
            iLoop++;
        }
        return bResult;
    }

}
