package com.ecovacs.test.common;

import com.ecovacs.test.activity.GmailVerify;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 17/4/20.
 * verify by firefox including register, forget password
 */
public class MailVerify {
    private static MailVerify mailVerify = null;
    private static Logger logger = LoggerFactory.getLogger(GmailVerify.class);

    @FindBy(xpath = "html/body/div[1]")
    private WebElement rowEcovacs = null;


    @FindBy(xpath = "html/body/form/div[2]/div[1]/input")
    private WebElement editNewPass = null;
    @FindBy(xpath = "html/body/form/div[2]/div[2]/input")
    private WebElement editReNewPass = null;
    @FindBy(xpath = "html/body/form/div[4]")//html/body/form/div[4]/button
    private WebElement btnSave = null;

    private MailVerify(){

    }

    public static MailVerify getInstance(){
        if (mailVerify == null){
            mailVerify = new MailVerify();
        }
        return mailVerify;
    }

    public void init(WebDriver driver){
        PageFactory.initElements(driver, this);
        //this.driver = driver;
    }

    public boolean showEcovacs(){
        return Common.getInstance().loadHtml(rowEcovacs);
    }

    public boolean showDoFindPass(){
        if(!Common.getInstance().loadHtml(editNewPass)){
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
