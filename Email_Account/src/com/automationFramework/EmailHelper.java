package com.automationFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class EmailHelper {
    /**
* Searches for e-mail messages containing the specified keyword in
* Subject field.
* @param host
* @param port
* @param userName
* @param password
* @param keyword
* @throws IOException
*/
@SuppressWarnings("unused")
private boolean textIsHtml = false;
    /**
* Return the primary text content of the message.
*/
    private String getText(Part p) throws MessagingException,IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }
    public boolean searchEmail(String userName,String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) throws IOException {
        Properties properties = new Properties();
        boolean val = false;
        // server setting
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", 993);
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",String.valueOf(993));
        Session session = Session.getDefaultInstance(properties);
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
            System.out.println("Connected to Email server….");
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            //create a search term for all “unseen” messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
            //create a search term for all recent messages
            Flags recent = new Flags(Flags.Flag.RECENT);
            FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm,recentFlagTerm);
            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchTerm);
            System.out.println("Total Messages Found :"+foundMessages.length);
            for (int i=foundMessages.length-1 ; i>=foundMessages.length-10;i--) {
                    Message message = foundMessages[i];
                    Address[] froms = message.getFrom();
			String email = froms == null ? null : ((InternetAddress)froms[0]).getAddress();
			if(message.getSubject()==null){
			continue;
}
Date date = new Date();//Getting Present date from the system
long diff = date.getTime()-message.getReceivedDate().getTime();//Get The difference between two dates
long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute
// if(diffMinutes>2){
// diffMinutes=2;
// }
System.out.println("Difference in Minutes b/w present time & Email Recieved time :" +diffMinutes);
try {
if(message.getSubject().contains(subjectKeyword) &&email.equals(fromEmail) && getText(message).contains(bodySearchText) && diffMinutes<=3){
String subject = message.getSubject();
// System.out.println(getText(message));
System.out.println("Found message #" + i + ": ");
System.out.println("At "+ i + " :"+ "Subject:"+ subject);
System.out.println("From: "+ email +" on : "+message.getReceivedDate());
if(getText(message).contains(bodySearchText)== true){
System.out.println("Message contains the search text "+bodySearchText);
val=true;
}
else{
val=false;
}
break;
}
} catch (NullPointerException expected) {
// TODO Auto-generated catch block
expected.printStackTrace();
}
System.out.println("Searching.…" +"At "+ i );
            }
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
return val;
    }
    /**
* Test this program with a Gmail’s account
* @throws IOException
*/
    @Test
    public void checkMail() throws IOException {
    	
    	String FilePath=System.getProperty("user.dir")+"\\src\\resources";
		readExcel(FilePath, "Email_Details.xlsx", "Sheet1");
    }
    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

	    //Create an object of File class to open xlsx file
    	Boolean val;
    	val=false;
	    File file =    new File(filePath+"\\"+fileName);

	    //Create an object of FileInputStream class to read excel file

	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook Workbook = null;

	    //Find the file extension by splitting file name in substring  and getting only extension name

	    String fileExtensionName = fileName.substring(fileName.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    Workbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of XSSFWorkbook class

	        Workbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name

	    Sheet Sheet = Workbook.getSheet(sheetName);

	    //Find number of rows in excel file

	    int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it

	    for (int i = 1; i < rowCount+1; i++) {

	        Row row = Sheet.getRow(i);
	        String userName = row.getCell(0).getStringCellValue();
	        String password = row.getCell(1).getStringCellValue();
	        String subjectKeyword = row.getCell(2).getStringCellValue();
	        String fromEmail=row.getCell(3).getStringCellValue();
	        String bodySearchText =row.getCell(4).getStringCellValue();
	        //Enter data from excel
	        val=searchEmail(userName,password,subjectKeyword,fromEmail, bodySearchText);
	        if(val==false)
	        {
	        	System.out.println("Failed to find the expected keyword in mail");
	        }
	        else
	        {
	        	System.out.println("Keyword found sucessfully in the mail");
	        }	
	    }

	    } 
}
