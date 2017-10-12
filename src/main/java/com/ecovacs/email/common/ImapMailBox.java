package com.ecovacs.email.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


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

    private void init(WebDriver driver){
        this.driver = driver;
    }

    private boolean verifyResEmail(String strCountry){
        MailVerify.getInstance().init(driver);
        if(!MailVerify.getInstance().showEcovacs()){
            logger.info("Can not show ecovacs html!!!");
            return false;
        }
        Common.getInstance().screenShot("Register-" + strCountry + ".png", driver);
        return true;
    }

    private boolean verifyForgetPass(String strCountry){
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

    private int getEmailIndex(Message message[]){
        int iIndex = 0;
        ReceiveMailUtil recMailUtil;
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date dateTimePre = null;
        Date dateTimeCur;
        try {
            for (int i = 0; i < message.length; i++){
                recMailUtil = new ReceiveMailUtil((MimeMessage)message[i]);
                //get latest mail including flag(ecovacs)
                if(recMailUtil.getFrom().contains(PropertyData.getProperty("ecovacs_mail"))) {
                    if (dateTimePre == null){
                        dateTimePre = dateFormat.parse(recMailUtil.getSentDate());
                        iIndex = i;
                        continue;
                    }
                    dateTimeCur = dateFormat.parse(recMailUtil.getSentDate());
                    int iResult = dateTimeCur.compareTo(dateTimePre);
                    if (iResult > 0){
                        dateTimePre = dateTimeCur;
                        iIndex = i;
                    }
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return iIndex;
    }

    /**
     *
     * @param strTime1 String
     * @param strTime2 String
     * @return if strTime1 < strTime2, return -1; if strTime1 = strTime2, return 0;
     *         if strTime1 > strTime2, return 1;
     */

    private int compareTo(String strTime1, String strTime2){
        int iResult = -2;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(strTime1);
            Date dt2 = df.parse(strTime2);
            //compare time, if dt1 > dt2
            if(dt1.getTime() > dt2.getTime()){
                iResult = 1;
            }
            else if (dt1.getTime() == dt2.getTime()){
                iResult = 0;
            }else {
                iResult = -1;//dt1 < dt2;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return iResult;
    }

    private String getEcovacsActiveUrl(String strImapHost, String strEmail, String strPassword, String strSendTime){
        String strUrl = null;
        // Setup mail server
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        try {
            Store store = session.getStore("imaps");
            store.connect(strImapHost, strEmail, strPassword);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message message[] = folder.getMessages();
            logger.info("Messages's length: " + message.length);
            int iIndex = getEmailIndex(message);
            ReceiveMailUtil recMailUtil = new ReceiveMailUtil((MimeMessage)message[iIndex]);
            String strDate = recMailUtil.getSentDate();
            if(-1 == compareTo(strDate, strSendTime)){
                return "";
            }
            recMailUtil.getMailContent(message[iIndex]);
            int iBegin = recMailUtil.getBodyText().indexOf("href=") + 6;
            int iEnd = recMailUtil.getBodyText().indexOf("\"", recMailUtil.getBodyText().indexOf("href") + 6);
            strUrl = recMailUtil.getBodyText().substring(iBegin, iEnd);
            logger.info("Message " + message.length + " " + strUrl);

        }catch (Exception e){
            e.printStackTrace();
        }
        return strUrl;
    }

    /**
     *
     * @param args:
     *            [0:country]
     *            [1:type]
     *            [2:imap_server]
     *            [3:e-mail]
     *            [4:password]
     *            [5:time(click send email)]
     * @return 1: success
     *         -1: Can not receive new email
     *         -2: modify password failed
     */
    public int verifyEmail(String args[]){
        //wait for 2 minutes
        Common.getInstance().waitSecond(2 * 60 * 1000);
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        // [0:country][1:type][2:imap_server][3:e-mail][4:password][5:time(click send email)]
        logger.info("--------" + args[0] + "--------");
        logger.info("--------" + args[1] + "--------");
        logger.info("--------" + args[2] + "--------");
        logger.info("--------" + args[3] + "--------");
        logger.info("--------" + args[4] + "--------");
        logger.info("--------" + args[5] + "--------");
        String strUrl;
        strUrl = getEcovacsActiveUrl(args[2], args[3], args[4], args[5]);
        if (strUrl == null || strUrl.length() == 0){
            logger.error("Can not receive new email!!!");
            return -1;
        }
        WebDriver webDriver = new FirefoxDriver();
        webDriver.navigate().to(strUrl);
        init(webDriver);
        if(args[1].equals("Register")){
            verifyResEmail(args[0]);
        }else if(args[1].equals("DoFindPass")){
            if(!verifyForgetPass(args[0])){
                logger.error("Do forget password failed!!!");
                return -2;
            }else {
                logger.info("Do forget password successfully!!!");
            }
        }
        webDriver.quit();
        logger.info("Close all windows!!!");
        return 1;
    }

}
