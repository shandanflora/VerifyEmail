package com.ecovacs.test;

import com.ecovacs.test.activity.Login;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebDriver;

/**
 * Created by ecosqa on 16/12/5.
 *
 */
class HotMail {
    private static HotMail hotmail = null;
    private WebDriver driver = null;

    private HotMail(){

    }

    static HotMail getInstance(){
        if(hotmail == null){
            hotmail = new HotMail();
        }
        return hotmail;
    }

    void init(WebDriver driver){
        this.driver = driver;
        //PageFactory.initElements(driver, Login.getInstance());
    }

    boolean login(){
        Login.getInstance().init(driver);
        Login.getInstance().inputEmail(PropertyData.getProperty("hotmail_email"));
        Login.getInstance().inputPassword(PropertyData.getProperty("hotmail_pass"));
        return Login.getInstance().enterMail() && Login.getInstance().clickVerify();
    }

    boolean forgetPass(){
        Login.getInstance().init(driver);
        Login.getInstance().inputEmail(PropertyData.getProperty("hotmail_email"));
        Login.getInstance().inputPassword(PropertyData.getProperty("hotmail_pass"));
        if(!Login.getInstance().enterMail()){
            return false;
        }
        if(!Login.getInstance().clickVerify()){
            return false;
        }
        if(!Login.getInstance().showDoFindPass()){
            return false;
        }
        Login.getInstance().doFindPass();
        return Login.getInstance().showEcovacs();
    }
}
