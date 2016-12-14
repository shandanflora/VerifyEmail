package com.ecovacs.test;

import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/5.
 *
 */
public class VerifyEmail {

    private static Logger logger = LoggerFactory.getLogger(VerifyEmail.class);

    public static void main(String args[]){
        //wait for 2 minutes
        //Common.getInstance().waitSecond(2 * 60 * 1000);
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        WebDriver webDriver = new FirefoxDriver();
        logger.info("--------" + args[0] + "--------");
        logger.info("--------" + args[1] + "--------");
        String strUrl;
        if(args[0].equals("hotmail")){
            strUrl = PropertyData.getProperty("hotmailURL");
            webDriver.navigate().to(strUrl);
            if(args[1].equals("Register")){
                HotMail.getInstance().init(webDriver);
                HotMail.getInstance().login();
            }else if(args[1].equals("DoFindPass")){
                HotMail.getInstance().init(webDriver);
                if(!HotMail.getInstance().forgetPass()){
                    logger.error("Do find password failed!!!");
                }else {
                    logger.info("Do find password successfully!!!");
                }
            }
        }else if(args[0].equals("gmail")){
            strUrl = PropertyData.getProperty("gmailURL");
            webDriver.navigate().to(strUrl);
            if(args[1].equals("Register")){
                Gmail.getInstance().init(webDriver);
                Gmail.getInstance().verifyResEmail();
            }else if(args[1].equals("DoFindPass")){
                Gmail.getInstance().init(webDriver);
                if(!Gmail.getInstance().verifyForgetPass()){
                    logger.error("Do find password failed!!!");
                }else {
                    logger.info("Do find password successfully!!!");
                }
            }
        }
        webDriver.quit();
    }
}
