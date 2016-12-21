package com.ecovacs.test;

import com.ecovacs.test.activity.GmailVerify;
import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import com.ecovacs.test.common.ReceiveMailUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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

    boolean verifyResEmail(String strCountry){
        GmailVerify.getInstance().init(driver);
        /*GmailVerify.getInstance().inputEmail(PropertyData.getProperty("gmail_email"));
        GmailVerify.getInstance().inputPassword(PropertyData.getProperty("gmail_pass"));
        if(!GmailVerify.getInstance().enterMail()){
            return false;
        }
        if(!GmailVerify.getInstance().clickVerify()){
            return false;
        }*/
        if(!GmailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs web htmail!!!");
            return false;
        }
        Common.getInstance().screenShot("EmailVerify-" + strCountry + ".png", driver);
        return true;
    }

    boolean verifyForgetPass(String strCountry){
        GmailVerify.getInstance().init(driver);
        /*GmailVerify.getInstance().inputEmail(PropertyData.getProperty("gmail_email"));
        GmailVerify.getInstance().inputPassword(PropertyData.getProperty("reset_pass"));
        if(!GmailVerify.getInstance().enterMail()){
            return false;
        }
        if(!GmailVerify.getInstance().clickVerify()){
            return false;
        }*/
        if(!GmailVerify.getInstance().showDoFindPass()){
            return false;
        }
        GmailVerify.getInstance().doFindPass();
        if(!GmailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs verify result web htmail!!!");
            return false;
        }
        Common.getInstance().screenShot("EmailVerify-" + strCountry + ".png", driver);
        return true;
    }

    String getEcovacsActiveUrl(){
        String strUrl = null;
        // Setup mail server
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);

        try {
            Store store = session.getStore("imaps");
            store.connect(PropertyData.getProperty("gmail_popHost"),
                          PropertyData.getProperty("gmail_email"),
                          PropertyData.getProperty("gmail_pass"));
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message message[] = folder.getMessages();
            System.out.println("Messages's length: " + message.length);
            ReceiveMailUtil recMailUtil;
            recMailUtil = new ReceiveMailUtil((MimeMessage)message[message.length - 1]);
            if(recMailUtil.getFrom().contains(PropertyData.getProperty("ecovacs_mail"))) {
                recMailUtil.getMailContent(message[message.length - 1]);
                System.out.println("****************************************");
                int iBegin = recMailUtil.getBodyText().indexOf("href=") + 6;
                int iEnd = recMailUtil.getBodyText().indexOf("\"", recMailUtil.getBodyText().indexOf("href") + 6);
                strUrl = recMailUtil.getBodyText().substring(iBegin, iEnd);
                System.out.println("Message " + message.length + " " + strUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return strUrl;
    }
}
