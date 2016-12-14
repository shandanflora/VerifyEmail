package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/5.
 *
 */
public class Login {
    private static Login login = null;
    private static Logger logger = LoggerFactory.getLogger(Login.class);
    private WebDriver driver = null;
    private String winHandleBefore = null;

    @FindBy(css = "#i0116")
    private WebElement editEmail = null;
    @FindBy(css = "#idSIButton9")
    private WebElement btnNext = null;
    @FindBy(css = "#i0118")
    private WebElement editPassword = null;
    @FindBy(css = "#idSIButton9")
    private WebElement btnSignIn = null;
    @FindBy(css = "#idLbl_PWD_KMSI_Cb")
    private WebElement checkKeep = null;
    @FindBy(xpath = ".//*[@id='O365_NavHeader']/div[1]/div[4]/a[2]/span")
    private WebElement btnNew = null;
    @FindBy(xpath = "html/body/div[2]/div/div[3]/div[5]/div/div[1]/div/div[5]/div/div[4]/div[1]/div/div/div[3]/div[2]/div[2]/div[5]/div[1]/div/div/div[1]/div[1]/div[3]/div[4]/div[3]/div[2]/div/div/div/div/table[1]/tbody/tr[4]/td/span/span/a")
    private WebElement btnVerify = null;
    @FindBy(xpath = "html/body/div[1]")
    private WebElement rowEcovacs = null;

    @FindBy(xpath = "html/body/div[2]/div/div[3]/div[5]/div/div[1]/div/div[5]/div/div[1]/div/div/div/div[4]/div[3]/div[1]/div[3]/div[1]/div/div/div[2]/div[1]/div[2]/div[4]/div[3]/div")
    private WebElement firstRowReci = null;
    @FindBy(xpath = "html/body/form/div[2]/div[1]/input")
    private WebElement editNewPass = null;
    @FindBy(xpath = "html/body/form/div[2]/div[2]/input")
    private WebElement editReNewPass = null;
    @FindBy(xpath = "html/body/form/div[4]")
    private WebElement btnSave = null;

    private Login(){

    }

    public static Login getInstance(){
        if (login == null){
            login = new Login();
        }
        return login;
    }

    public void init(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputEmail(String strEmail){
        logger.info("show login html!!!!");
        editEmail.sendKeys(strEmail);
        logger.info("input e-mail!!!!");
        btnNext.click();
        logger.info("click next!!!!");
    }

    private boolean loadHtml(WebElement webElement){

        int iLoop = 0;
        boolean bResult = false;
        while (true){
            if(iLoop > 100){
                logger.info("load web html failed!!!");
                break;
            }
            try {
                webElement.getText();
                logger.info("load web html successfully!!!");
                bResult = true;
                break;
                /*logger.info("bResult: " + Boolean.toString(bResult));
                if (bResult){
                    logger.info("load web html successfully!!!");
                    break;
                }*/
            }catch (NoSuchElementException e){
                logger.info("load web html again!!!");
                Common.getInstance().waitSecond(500);
            }
            iLoop++;
        }
        return bResult;
    }

    public void inputPassword(String strPass){
        loadHtml(checkKeep);
        editPassword.sendKeys(strPass);
        logger.info("input password!!!!");
        btnSignIn.click();
        logger.info("click sign in!!!!");
    }

    public boolean enterMail(){
        if(!loadHtml(btnNew)){
            logger.error("Can not show email list!!!");
            return false;
        }
        logger.info("Show email list!!!");
        /*List<WebElement> listRow = driver.findElements(By.cssSelector("._lvv2_W1>div>div>div>div>div"));
        int iSize = listRow.size();
        boolean bBreak = false;
        logger.info("size: " + String.valueOf(iSize));
        for(int i = 0; i < iSize; i++){
            //logger.info("loop1 i: " + String.valueOf(i));
            List<WebElement> listMail = listRow.get(i).findElements(By.className("_lvv2_c1"));
            logger.info("size list1: " + String.valueOf(listMail.size()));
            int iSize1 = listMail.size();
            for (int j = 0; j < iSize1; j++){
                //logger.info("loop2 j: " + String.valueOf(j));
                logger.info("content text: " + listMail.get(j).getText());
                if(listMail.get(j).getText().equals("feedback@support.ecovacs.com")){
                    listMail.get(j).click();
                    bBreak = true;
                    break;
                }
            }
            if (bBreak){
                break;
            }
        }*/
        firstRowReci.click();
        return true;
    }

    public boolean clickVerify(){
        if(!loadHtml(btnVerify)){
            logger.error("Can not show first email!!!");
            return false;
        }
        logger.info("Show first email!!!");
        winHandleBefore = driver.getWindowHandle();
        btnVerify.click();
        logger.info("click button verify!!!");
        return true;
    }

    public boolean showEcovacs(){
        if(!loadHtml(rowEcovacs)){
            logger.error("Can not show Ecovacs web html!!!");
            return false;
        }
        logger.info("Show Ecovacs web html!!!");
        return true;
    }

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
        if(!loadHtml(btnSave)){
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
