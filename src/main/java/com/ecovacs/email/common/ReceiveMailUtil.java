package com.ecovacs.email.common;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/*import javax.mail.internet.MimeUtility;*/
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ecosqa on 16/12/20.
 * receive e-mail
 */
public class ReceiveMailUtil {

        private MimeMessage mimeMessage=null;
        //private String saveAttachPath="";//附件下载后的存放目录
        private StringBuffer bodytext= new StringBuffer();
        //存放邮件内容的StringBuffer对象
        private String dateformat="yyyy-MM-dd HH:mm:ss";


        /**
         * 构造函数,初始化一个MimeMessage对象
         */
        ReceiveMailUtil(MimeMessage mimeMessage){
            this.mimeMessage = mimeMessage;
            System.out.println("create a ParseMimeMessage object........");
        }


        /*public void setMimeMessage(MimeMessage mimeMessage){
            this.mimeMessage=mimeMessage;
        }*/


        /**
         * 获得发件人的地址和姓名
         */
        String  getFrom(){
            String fromAddr = null;
            try {
                InternetAddress address[] = (InternetAddress[])mimeMessage.getFrom();
                String from = address[0].getAddress();
                if(from == null) from="";
                String personal = address[0].getPersonal();
                if(personal == null) personal="";
                fromAddr = personal+"<"+from+">";
            }catch (Exception e){
                e.printStackTrace();
            }
            return fromAddr;
        }

        /*
        public String getMailAddress(String type)throws Exception{
            String mailaddr = "";
            String addtype = type.toUpperCase();
            InternetAddress []address = null;
            if(addtype.equals("TO") || addtype.equals("CC") ||addtype.equals("BCC")){
                if(addtype.equals("TO")){
                    address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.TO);
                }else if(addtype.equals("CC")){
                    address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.CC);
                }else{
                    address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.BCC);
                }
                if(address != null){
                    for(int i=0;i<address.length;i++){
                        String email=address[i].getAddress();
                        if(email==null) email="";
                        else{
                            email=MimeUtility.decodeText(email);
                        }
                        String personal=address[i].getPersonal();
                        if(personal==null) personal="";
                        else{
                            personal=MimeUtility.decodeText(personal);
                        }
                        String compositeto=personal+"<"+email+">";
                        mailaddr+=","+compositeto;
                    }
                    mailaddr=mailaddr.substring(1);
                }
            }else{
                throw new Exception("Error emailaddr type!");
            }
            return mailaddr;
        }*/


        /*public String getSubject(){
            String subject = "";
            try{
                subject = MimeUtility.decodeText(mimeMessage.getSubject());
                if(subject == null) subject="";
            }catch(Exception e){
                e.printStackTrace();
            }
            return subject;
        }*/


        String getSentDate(){
            Date sentDate = null;
            try {
                sentDate = mimeMessage.getSentDate();

            }catch (Exception e){
                e.printStackTrace();
            }
            SimpleDateFormat format = new SimpleDateFormat(dateformat);
            return format.format(sentDate);
        }

    /**
     * get mail content
     * @return String
     */

    String getBodyText(){
            return bodytext.toString();
        }


        /**
         * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
         * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
         */

        void getMailContent(Part part){
            try {
                String contenttype = part.getContentType();
                int nameindex = contenttype.indexOf("name");
                boolean conname =false;
                if(nameindex != -1) conname=true;
                //System.out.println("CONTENTTYPE: "+contenttype);
                if(part.isMimeType("text/plain") && !conname){
                    bodytext.append((String)part.getContent());
                }else if(part.isMimeType("text/html") && !conname){
                    bodytext.append((String)part.getContent());
                }else if(part.isMimeType("multipart/*")){
                    Multipart multipart = (Multipart)part.getContent();
                    int counts = multipart.getCount();
                    for(int i=0;i<counts;i++){
                        getMailContent(multipart.getBodyPart(i));
                    }
                }else if(part.isMimeType("message/rfc822")){
                    getMailContent((Part)part.getContent());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /*public String getMessageId()throws MessagingException{
            return mimeMessage.getMessageID();
        }*/

        /*public boolean isNew(){
            boolean isNew = false;
            Flags flags = null;
            try {
                flags = mimeMessage.getFlags();
            }catch (MessagingException e){
                e.printStackTrace();
            }
            Flags.Flag []flagList = flags.getSystemFlags();
            System.out.println("flags's length: "+flagList.length);
            for(Flags.Flag flag:flagList){
                if(flag == Flags.Flag.SEEN){
                    isNew=true;
                    System.out.println("seen Message.......");
                    break;
                }
            }
            return isNew;
        }*/

        /*public boolean isContainAttach(Part part)throws Exception{
            boolean attachflag = false;
            String contentType = part.getContentType();
            if(part.isMimeType("multipart/*")){
                Multipart mp = (Multipart)part.getContent();
                for(int i=0;i<mp.getCount();i++){
                    BodyPart mpart = mp.getBodyPart(i);
                    String disposition = mpart.getDisposition();
                    if((disposition != null) &&((disposition.equals(Part.ATTACHMENT)) ||(disposition.equals(Part.INLINE))))
                        attachflag = true;
                    else if(mpart.isMimeType("multipart/*")){
                        attachflag = isContainAttach((Part)mpart);
                    }else{
                        String contype = mpart.getContentType();
                        if(contype.toLowerCase().indexOf("application") != -1) attachflag=true;
                        if(contype.toLowerCase().indexOf("name") != -1) attachflag=true;
                    }
                }
            }else if(part.isMimeType("message/rfc822")){
                attachflag = isContainAttach((Part)part.getContent());
            }
            return attachflag;
        }*/

/*
        public void saveAttachMent(Part part)throws Exception{
            String fileName = "";
            if(part.isMimeType("multipart/*")){
                Multipart mp = (Multipart)part.getContent();
                for(int i=0;i<mp.getCount();i++){
                    BodyPart mpart = mp.getBodyPart(i);
                    String disposition = mpart.getDisposition();
                    if((disposition != null) &&((disposition.equals(Part.ATTACHMENT)) ||(disposition.equals(Part.INLINE)))){
                        fileName = mpart.getFileName();
                        if(fileName.toLowerCase().indexOf("gb2312") != -1){
                            fileName = MimeUtility.decodeText(fileName);
                        }
                        saveFile(fileName,mpart.getInputStream());
                    }else if(mpart.isMimeType("multipart/*")){
                        saveAttachMent(mpart);
                    }else{
                        fileName = mpart.getFileName();
                        if((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)){
                            fileName=MimeUtility.decodeText(fileName);
                            saveFile(fileName,mpart.getInputStream());
                        }
                    }
                }
            }else if(part.isMimeType("message/rfc822")){
                saveAttachMent((Part)part.getContent());
            }
        }
*/


/*
        public void setAttachPath(String attachpath){
            this.saveAttachPath = attachpath;
        }*/



        /*public void setDateFormat(String format)throws Exception{
            this.dateformat = format;
        }*/


/*
        public String getAttachPath(){
            return saveAttachPath;
        }*/


/*
        private void saveFile(String fileName,InputStream in)throws Exception{
            String osName = System.getProperty("os.name");
            String storedir = getAttachPath();
            String separator = "";
            if(osName == null) osName="";
            if(osName.toLowerCase().indexOf("win") != -1){
                separator="\\";
                if(storedir == null || storedir.equals(""))
                    storedir="c:\\tmp";
            }else{
                separator = "/";
                storedir = "/tmp";
            }
            File storefile = new File(storedir+separator+fileName);
            System.out.println("storefile's path: "+storefile.toString());
//for(int i=0;storefile.exists();i++){
//storefile = new File(storedir+separator+fileName+i);
//}
            BufferedOutputStream bos = null;
            BufferedInputStream  bis = null;
            try{
                bos = new BufferedOutputStream(new FileOutputStream(storefile));
                bis = new BufferedInputStream(in);
                int c;
                while((c=bis.read()) != -1){
                    bos.write(c);
                    bos.flush();
                }
            }catch(Exception exception){
                exception.printStackTrace();
                throw new Exception("文件保存失败!");
            }finally{
                bos.close();
                bis.close();
            }
        }*/

}
