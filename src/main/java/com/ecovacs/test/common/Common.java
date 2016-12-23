package com.ecovacs.test.common;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

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
            if(iLoop > 150){
                break;
            }
            try {
                webElement.isDisplayed();
                bResult = true;
                break;
            }catch (Exception e){
                logger.info("load web html again-" + String.valueOf(iLoop));
                e.printStackTrace();
                Common.getInstance().waitSecond(500);
            }
            iLoop++;
        }
        return bResult;
    }

    /*private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if(children == null){
                return false;
            }
            //recursion delete subfolder
            for(String strFile:children) {
                boolean success = deleteDir(new File(dir, strFile));
                if (!success) {
                    return false;
                }
            }
        }
        //delete empty folder or file
        return dir.delete();
    }*/

    private boolean delAllFile(String path) {
        File file = new File(path);
        File temp;
        String[] tempList = file.list();
        if(tempList == null){
            return false;
        }
        for(String strFile:tempList){
            if (path.endsWith(File.separator)) {
                temp = new File(path + strFile);
            } else {
                temp = new File(path + File.separator + strFile);
            }
            if (temp.isFile()) {
                if(!temp.delete()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean screenShot(String strFileName, WebDriver driver){
        TakesScreenshot screen = (TakesScreenshot ) new Augmenter().augment(driver);
        File directory = new File("");//set current path
        /*String strPath = getClass().getResource("/").getPath()
                + "../" + "screenShots/";*/
        String strPath = "";
        try{
            System.out.println(directory.getCanonicalPath());//get path
            strPath = directory.getCanonicalPath() + "/report/screenShots/";
        }catch(IOException e){
            e.printStackTrace();
        }
        //check
        File folder = new File(strPath);
        if(!folder.exists() && !folder.isDirectory()){
            if(!folder.mkdir()){
                return false;
            }
        }else {
            delAllFile(strPath);
        }
        File ss = new File(strPath + strFileName);
        logger.info("strPath-" + strPath);
        logger.info("strFileName-" + strFileName);
        logger.info(ss.getPath() + "-- " + ss.getName());
        return screen.getScreenshotAs(OutputType.FILE).renameTo(ss);
    }

    public String getEcovacsActiveUrl(String strImapHost, String strEmail, String strPassword){
        String strUrl = null;
        // Setup mail server
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);

        try {
            Store store = session.getStore("imaps");
            store.connect(strImapHost, strEmail, strPassword);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message message[] = folder.getMessages();
            System.out.println("Messages's length: " + message.length);
            ReceiveMailUtil recMailUtil;
            recMailUtil = new ReceiveMailUtil((MimeMessage)message[message.length - 1]);
            if(recMailUtil.getFrom().contains(PropertyData.getProperty("ecovacs_mail"))) {
                recMailUtil.getMailContent(message[message.length - 1]);
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
