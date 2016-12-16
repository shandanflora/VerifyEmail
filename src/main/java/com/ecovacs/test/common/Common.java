package com.ecovacs.test.common;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
                webElement.getText();
                bResult = true;
                break;
            }catch (NoSuchElementException e){
                logger.info("load web html again-" + String.valueOf(iLoop));
                Common.getInstance().waitSecond(500);
            }
            iLoop++;
        }
        return bResult;
    }

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
        String strPath = getClass().getResource("/").getPath()
                + "../" + "screenShots/";
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
        return screen.getScreenshotAs(OutputType.FILE).renameTo(ss);
    }

}
