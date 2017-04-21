package com.ecovacs.test.common;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by ecosqa on 17/4/20.
 * mail handle by imap
 */
public class ImapMailBox {

    private static ImapMailBox imapMailBox = null;
    private static Logger logger = LoggerFactory.getLogger(ImapMailBox.class);
    private WebDriver driver = null;

    private ImapMailBox(){

    }

    public static ImapMailBox getInstance(){
        if(imapMailBox == null){
            imapMailBox = new ImapMailBox();
        }
        return imapMailBox;
    }

    public void init(WebDriver driver){
        this.driver = driver;
    }

    public boolean verifyResEmail(String strCountry){
        MailVerify.getInstance().init(driver);
        if(!MailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs html!!!");
            return false;
        }
        Common.getInstance().screenShot("Register-" + strCountry + ".png", driver);
        return true;
    }

    public boolean verifyForgetPass(String strCountry){
        MailVerify.getInstance().init(driver);
        if(!MailVerify.getInstance().showDoFindPass()){
            return false;
        }
        MailVerify.getInstance().doFindPass();
        if(!MailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs verify result web htmail!!!");
            return false;
        }
        Common.getInstance().screenShot("doResetPass-" + strCountry + ".png", driver);
        return true;
    }

    public String getEcovacsActiveUrl(String strImapHost, int iPort,String strEmail, String strPassword){
        return Common.getInstance().getEcovacsActiveUrl(strImapHost, iPort, strEmail, strPassword);
    }

}
