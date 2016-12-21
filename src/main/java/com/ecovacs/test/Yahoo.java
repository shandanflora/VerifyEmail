package com.ecovacs.test;

import com.ecovacs.test.activity.YahooVerify;
import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/15.
 * handle yahoo email
 */
class Yahoo {
    private static Yahoo yahoo = null;
    private static Logger logger = LoggerFactory.getLogger(Gmail.class);
    private WebDriver driver = null;

    private Yahoo(){

    }

    static Yahoo getInstance(){
        if(yahoo == null){
            yahoo = new Yahoo();
        }
        return yahoo;
    }

    void init(WebDriver driver){
        this.driver = driver;
    }

    boolean verifyResEmail(String strCountry){
        YahooVerify.getInstance().init(driver);
        YahooVerify.getInstance().inputEmail(PropertyData.getProperty("yahoo_email"));
        YahooVerify.getInstance().inputPassword(PropertyData.getProperty("yahoo_pass"));
        if(!YahooVerify.getInstance().enterMail()){
            return false;
        }
        if(!YahooVerify.getInstance().clickVerify()){
            return false;
        }
        YahooVerify.getInstance().switchNewWin();
        if(!YahooVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs web htmail!!!");
            return false;
        }
        logger.info("Show ecovacs web htmail!!!");
        Common.getInstance().screenShot("Register-" + strCountry + ".png", driver);
        return true;
    }

    boolean verifyForgetPass(String strCountry){
        YahooVerify.getInstance().init(driver);
        YahooVerify.getInstance().inputEmail(PropertyData.getProperty("yahoo_email"));
        YahooVerify.getInstance().inputPassword(PropertyData.getProperty("yahoo_pass"));
        if(!YahooVerify.getInstance().enterMail()){
            return false;
        }
        if(!YahooVerify.getInstance().clickVerify()){
            return false;
        }
        if(!YahooVerify.getInstance().showDoFindPass()){
            return false;
        }
        YahooVerify.getInstance().doFindPass();
        if(!YahooVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs web html!!!");
            return false;
        }
        logger.info("Show ecovacs doResetPass html!!!");
        Common.getInstance().screenShot("doResetPass-" + strCountry + ".png", driver);
        return true;
    }
}
