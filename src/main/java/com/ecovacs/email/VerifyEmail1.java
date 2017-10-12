package com.ecovacs.email;

import com.ecovacs.email.common.ImapMailBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/12/5.
 *
 */
public class VerifyEmail1 {

    private static Logger logger = LoggerFactory.getLogger(VerifyEmail1.class);

    public static void main(String args[]){
        //wait for 2 minutes
        //Common.getInstance().waitSecond(2 * 60 * 1000);
        //System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        // [0:country][1:type][2:imap_server][3:e-mail][4:password][5:time(click send email)]
        logger.info("--------" + args[0] + "--------");
        logger.info("--------" + args[1] + "--------");
        logger.info("--------" + args[2] + "--------");
        logger.info("--------" + args[3] + "--------");
        logger.info("--------" + args[4] + "--------");
        logger.info("--------" + args[5] + "--------");
        ImapMailBox.getInstance().verifyEmail(args);
    }
}
