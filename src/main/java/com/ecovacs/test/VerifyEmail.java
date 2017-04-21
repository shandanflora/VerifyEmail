package com.ecovacs.test;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.ImapMailBox;
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
        // [0:country][1:type][2:server][2:server][3:e-mail][4:password]
        logger.info("--------" + args[0] + "--------");
        logger.info("--------" + args[1] + "--------");
        logger.info("--------" + args[2] + "--------");
        logger.info("--------" + args[3] + "--------");
        logger.info("--------" + args[4] + "--------");
        logger.info("--------" + args[5] + "--------");
        String strUrl;
        /*if(args[1].equals("hotmail")){
            strUrl = HotMail.getInstance().getEcovacsActiveUrl();
            webDriver.navigate().to(strUrl);
            HotMail.getInstance().init(webDriver);
            if(args[2].equals("Register")){
                HotMail.getInstance().login(args[0]);
            }else if(args[2].equals("DoFindPass")){
                if(!HotMail.getInstance().forgetPass(args[0])){
                    logger.error("Do find password failed!!!");
                }else {
                    logger.info("Do find password successfully!!!");
                }
            }
        }else if(args[1].equals("gmail")){
            strUrl = Gmail.getInstance().getEcovacsActiveUrl();
            webDriver.navigate().to(strUrl);
            if(args[2].equals("Register")){
                Gmail.getInstance().init(webDriver);
                Gmail.getInstance().verifyResEmail(args[0]);
            }else if(args[2].equals("DoFindPass")){
                Gmail.getInstance().init(webDriver);
                if(!Gmail.getInstance().verifyForgetPass(args[0])){
                    logger.error("Do find password failed!!!");
                }else {
                    logger.info("Do find password successfully!!!");
                }
            }
        }else if(args[1].equals("yahoo")){
            strUrl = PropertyData.getProperty("yahooURL");
            webDriver.navigate().to(strUrl);
            if(args[2].equals("Register")){
                Yahoo.getInstance().init(webDriver);
                Yahoo.getInstance().verifyResEmail(args[0]);
            }else if(args[2].equals("DoFindPass")){
                Yahoo.getInstance().init(webDriver);
                if(!Yahoo.getInstance().verifyForgetPass(args[0])){
                    logger.error("Do find password failed!!!");
                }else {
                    logger.info("Do find password successfully!!!");
                }
            }
        }*/
        System.out.println(Integer.parseInt(args[3]));
        strUrl = ImapMailBox.getInstance().getEcovacsActiveUrl(args[2], Integer.parseInt(args[3]), args[4], args[5]);
        webDriver.navigate().to(strUrl);
        ImapMailBox.getInstance().init(webDriver);
        if(args[1].equals("Register")){
            ImapMailBox.getInstance().verifyResEmail(args[0]);
        }else if(args[1].equals("DoFindPass")){
            if(!ImapMailBox.getInstance().verifyForgetPass(args[0])){
                logger.error("Do find password failed!!!");
            }else {
                logger.info("Do find password successfully!!!");
            }
        }
        webDriver.quit();
        logger.info("Close all windows!!!");
    }
}
