package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/14.
 * verify for gmail
 */
public class GmailVerify {
        private static GmailVerify gmailVerify = null;
        private static Logger logger = LoggerFactory.getLogger(GmailVerify.class);
        private WebDriver driver = null;
        private String winHandleBefore = null;

        @FindBy(xpath = "html/body/div[1]/div[2]/div[2]/div[1]/form/div[1]/div/div[1]/div/div/input[1]")
        private WebElement gmail_editEmail = null;
        @FindBy(xpath = "html/body/div[1]/div[2]/div[2]/div[1]/form/div[1]/div/input")
        private WebElement gmail_btnNext = null;
        @FindBy(xpath = "html/body/div[1]/div[2]/div[2]/div[1]/form/div[2]/div/div[2]/div/div/input[2]")
        private WebElement gmail_editPassword = null;
        @FindBy(xpath = "html/body/div[1]/div[2]/div[2]/div[1]/form/div[2]/div/input[1]")
        private WebElement gmail_btnSignIn = null;
        @FindBy(xpath = "html/body/div[7]/div[3]/div/div[2]/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/div/div")
        private WebElement gmail_btnWrite = null;
        @FindBy(xpath = "html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div/table/tr/td[1]/div[2]/div[2]/div/div[3]/div/div/div/div/div/div[1]/div[2]/div[7]/div/div[1]/table[1]/tbody/tr[4]/td/span/span/a")
        private WebElement gmail_btnVerify = null;
        @FindBy(xpath = "html/body/div[1]")
        private WebElement rowEcovacs = null;

        @FindBy(xpath = "html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div[1]/div[1]/div/div/div[7]/div/div[1]/div[2]/div/table/tbody/tr/td[4]/div[2]")
        private WebElement firstRowReci = null;
        @FindBy(xpath = "html/body/form/div[2]/div[1]/input")
        private WebElement editNewPass = null;
        @FindBy(xpath = "html/body/form/div[2]/div[2]/input")
        private WebElement editReNewPass = null;
        @FindBy(xpath = "html/body/form/div[4]")
        private WebElement btnSave = null;

        private GmailVerify(){

        }

        public static GmailVerify getInstance(){
            if (gmailVerify == null){
                gmailVerify = new GmailVerify();
            }
            return gmailVerify;
        }

        public void init(WebDriver driver){
            PageFactory.initElements(driver, this);
            this.driver = driver;
        }

        public void inputEmail(String strEmail){
            logger.info("Show sign in web html!!!!");
            gmail_editEmail.sendKeys(strEmail);
            logger.info("Fill e-mail!!!");
            winHandleBefore = driver.getWindowHandle();
            logger.info(winHandleBefore);
            gmail_btnNext.click();
            logger.info("Click next!!!!");
        }

        public boolean inputPassword(String strPass){
            if(!Common.getInstance().loadHtml(gmail_editPassword)){
                logger.error("Can not show input password html!!!");
                return false;
            }
            logger.info("Show input password html!!!");
            logger.info(String.valueOf(driver.getWindowHandles().size()));
            for(String str:driver.getWindowHandles()){
                logger.info(str);
            }
            logger.info(driver.getCurrentUrl());
            driver.switchTo().window(driver.getWindowHandle());
            gmail_editPassword.sendKeys(strPass);
            logger.info("Filled password!!!!");
            gmail_btnSignIn.click();
            logger.info("Clicked sign in!!!!");
            return true;
        }

        public boolean enterMail(){
            if(!Common.getInstance().loadHtml(gmail_btnWrite)){
                logger.error("Can not show mail list!!!");
                return false;
            }
            firstRowReci.click();
            logger.info("Click first email in list!!!");
            return true;
        }

        public boolean clickVerify(){
            if(!Common.getInstance().loadHtml(gmail_btnVerify)){
                logger.error("Can not show verify email!!!");
                return false;
            }
            winHandleBefore = driver.getWindowHandle();
            gmail_btnVerify.click();
            logger.info("Click button verify and continue!!!");
            return true;
        }

        public boolean showEcovacs(){
            return Common.getInstance().loadHtml(rowEcovacs);
        }
        //forget password
        private void switchNewWin(){
            for(String winHandle : driver.getWindowHandles()){
                if(winHandle.equals(winHandleBefore)){
                    continue;
                }
                driver.switchTo().window(winHandle);
            }
        }

        public boolean showDoFindPass(){
            switchNewWin();
            if(!Common.getInstance().loadHtml(btnSave)){
                logger.error("Can not show do find password html!!!");
                return false;
            }
            return true;
        }

        public void doFindPass(){
            editNewPass.sendKeys(PropertyData.getProperty("reset_pass"));
            editReNewPass.sendKeys(PropertyData.getProperty("reset_pass"));
            btnSave.click();
            logger.info("Click save in do find password html!!!");
        }

}
