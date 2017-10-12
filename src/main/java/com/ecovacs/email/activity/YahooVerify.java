package com.ecovacs.email.activity;

import com.ecovacs.email.common.Common;
import com.ecovacs.email.common.PropertyData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/15.
 * yahoo verify email
 */
public class YahooVerify {
    private static YahooVerify yahooVerify = null;
    private static Logger logger = LoggerFactory.getLogger(GmailVerify.class);
    private WebDriver driver = null;
    private String winHandleBefore = null;

    @FindBy(xpath = "html/body/div[1]/main/div/div[1]/div[1]/div[1]/div[2]/fieldset/form/div[1]/div[1]/input")
    private WebElement yahoo_editEmail = null;
    @FindBy(xpath = "html/body/div[1]/main/div/div[1]/div[1]/div[1]/div[2]/fieldset/form/div[3]/button")
    private WebElement yahoo_btnNext = null;
    @FindBy(xpath = "html/body/div[1]/main/div/div[1]/div[1]/div[1]/div[2]/fieldset/form/div[1]/div[3]/input")
    private WebElement yahoo_editPassword = null;
    @FindBy(xpath = "html/body/div[1]/main/div/div[1]/div[1]/div[1]/div[2]/fieldset/form/div[3]/button")
    private WebElement yahoo_btnSignIn = null;
    @FindBy(xpath = "html/body/div[9]/div[3]/div[4]/div[2]/div[7]/div/div/div[2]/div[1]/div[2]/div/div/div/div/table[1]/tbody/tr[4]/td/span/span/a")
    private WebElement yahoo_btnVerify = null;
    @FindBy(xpath = "html/body/div[1]/main/div/div[1]/div[1]/div[1]/div[2]/div[1]/a")
    private WebElement yahoo_btnBack = null;
    @FindBy(xpath = "html/body/div[1]")
    private WebElement rowEcovacs = null;

    @FindBy(xpath = "html/body/div[9]/div[3]/div[4]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div[1]")
    private WebElement firstRowReci = null;
    @FindBy(xpath = "html/body/form/div[2]/div[1]/input")
    private WebElement editNewPass = null;
    @FindBy(xpath = "html/body/form/div[2]/div[2]/input")
    private WebElement editReNewPass = null;
    @FindBy(xpath = "html/body/form/div[4]")
    private WebElement btnSave = null;

    private YahooVerify(){

    }

    public static YahooVerify getInstance(){
        if (yahooVerify == null){
            yahooVerify = new YahooVerify();
        }
        return yahooVerify;
    }

    public void init(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputEmail(String strEmail){
        logger.info("Show sign in web html!!!!");
        yahoo_editEmail.sendKeys(strEmail);
        logger.info("Fill e-mail!!!");
        //winHandleBefore = driver.getWindowHandle();
        logger.info(winHandleBefore);
        yahoo_btnNext.click();
        logger.info("Click next!!!!");
    }

    public boolean inputPassword(String strPass){
        if(!Common.getInstance().loadHtml(yahoo_btnBack)){
            logger.info("Can not show input password html!!!");
            return false;
        }
        logger.info("Show input password html!!!");
        /*logger.info(String.valueOf(driver.getWindowHandles().size()));
        for(String str:driver.getWindowHandles()){
            logger.info(str);
        }
        logger.info(driver.getCurrentUrl());
        driver.switchTo().window(driver.getWindowHandle());*/
        yahoo_editPassword.sendKeys(strPass);
        logger.info("Filled password!!!!");
        yahoo_btnSignIn.click();
        logger.info("Clicked sign in!!!!");
        return true;
    }

    public boolean enterMail(){
        if(!Common.getInstance().loadHtml(firstRowReci)){
            logger.error("Can not show mail list web html!!!");
            return false;
        }
        logger.info("Show mail list web html!!!");
        firstRowReci.click();
        logger.info("Click first email in list!!!");
        return true;
    }

    public boolean clickVerify(){
        if(!Common.getInstance().loadHtml(yahoo_btnVerify)){
            logger.error("Can not show verify email html!!!");
            return false;
        }
        winHandleBefore = driver.getWindowHandle();
        yahoo_btnVerify.click();
        logger.info("Click button verify and continue!!!");
        return true;
    }

    public boolean showEcovacs(){
        return Common.getInstance().loadHtml(rowEcovacs);
    }
    //forget password
    public void switchNewWin(){
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
