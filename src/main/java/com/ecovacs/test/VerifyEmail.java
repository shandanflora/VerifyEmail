package com.ecovacs.test;

import com.ecovacs.test.common.Common;
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
        Common.getInstance().waitSecond(2 * 60 * 1000);
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        WebDriver webDriver = new FirefoxDriver();
        if(args[0].equals("Register")){
            logger.info("--------" + args[0] + "----c----");
            webDriver.navigate().to(PropertyData.getProperty("hotmailURL"));
            HotMail.getInstance().init(webDriver);
            HotMail.getInstance().login();
        }else if(args[0].equals("DoFindPass")){
            logger.info("--------" + args[0] + "--------");
            webDriver.navigate().to(PropertyData.getProperty("hotmailURL"));
            HotMail.getInstance().init(webDriver);
            if(!HotMail.getInstance().forgetPass()){
                logger.error("Do find password failed!!!");
            }
            logger.info("Do find password successfully!!!");
        }
        webDriver.quit();
    }
}
