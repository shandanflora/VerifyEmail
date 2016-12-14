package com.ecovacs.test;

import com.ecovacs.test.activity.GmailVerify;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/14.
 * handle verify email with gmail
 */
class Gmail {
    private static Gmail gmail = null;
    private static Logger logger = LoggerFactory.getLogger(Gmail.class);
    private WebDriver driver = null;

    private Gmail(){

    }

    static Gmail getInstance(){
        if(gmail == null){
            gmail = new Gmail();
        }
        return gmail;
    }

    void init(WebDriver driver){
        this.driver = driver;
    }

    boolean verifyResEmail(){
        GmailVerify.getInstance().init(driver);
        GmailVerify.getInstance().inputEmail(PropertyData.getProperty("gmail_email"));
        GmailVerify.getInstance().inputPassword(PropertyData.getProperty("gmail_pass"));
        if(!GmailVerify.getInstance().enterMail()){
            return false;
        }
        if(!GmailVerify.getInstance().clickVerify()){
            return false;
        }
        if(!GmailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs web htmail!!!");
            return false;
        }
        return true;
    }

    boolean verifyForgetPass(){
        GmailVerify.getInstance().init(driver);
        GmailVerify.getInstance().inputEmail(PropertyData.getProperty("Gmail_email"));
        GmailVerify.getInstance().inputPassword(PropertyData.getProperty("reset_pass"));
        if(!GmailVerify.getInstance().enterMail()){
            return false;
        }
        if(!GmailVerify.getInstance().clickVerify()){
            return false;
        }
        if(!GmailVerify.getInstance().showDoFindPass()){
            return false;
        }
        GmailVerify.getInstance().doFindPass();
        return GmailVerify.getInstance().showEcovacs();
    }
}
