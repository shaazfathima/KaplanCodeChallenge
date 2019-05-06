package com.automationFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//Import package com.pageObject.*

import com.pageObjects.HomePage;
import com.pageObjects.PersonalLoans;
import com.pageObjects.EducationalLoans;
import com.pageObjects.ConfirmationPage;


public class LoanApplication {

	private static WebDriver driver;
	private static JavascriptExecutor js;
	
	@BeforeTest
	public void InitiateDriver() throws Exception 
	{
		driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	
		//Launch the home page
		driver.get("https://www.deal4loans.com/");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//This will scroll the page till the element is found		
		js.executeScript("arguments[0].scrollIntoView();",HomePage.AllAboutPersonalLoans(driver));
		
		//Click All about personal loans link
		HomePage.AllAboutPersonalLoans(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void ApplyLoan() throws Exception 
	{
		
		//Click on Education loans
		PersonalLoans.EducationalLoanMenu(driver).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		PersonalLoans.EducationalLoanOption(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Retreive data from excel and enter applicants details
		String FilePath=System.getProperty("user.dir")+"\\src\\resources";
		readExcel(FilePath, "Loan_Applicants_Details.xlsx", "Sheet1");
		EducationalLoans.AcceptTerms(driver).click();
		EducationalLoans.Quote(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//validate response thank you message
		Assert.assertEquals(true, ConfirmationPage.ThankYouMsg(driver).isDisplayed());
	}
	
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

	    //Create an object of File class to open xlsx file

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

	        //Enter data from excel
	        	EducationalLoans.FullName(driver).sendKeys(row.getCell(0).getStringCellValue());
	        	EducationalLoans.CountryOfStudy(driver).selectByVisibleText(row.getCell(1).getStringCellValue());
	        	EducationalLoans.StudyCourse(driver).selectByVisibleText(row.getCell(2).getStringCellValue());
	        	EducationalLoans.CoBorrrowerInc(driver).sendKeys(row.getCell(3).getStringCellValue());
	        	EducationalLoans.Email(driver).sendKeys(row.getCell(4).getStringCellValue());
	        	EducationalLoans.EmpStatus(driver).selectByVisibleText(row.getCell(5).getStringCellValue());
	        	EducationalLoans.LoanAmount(driver).sendKeys(row.getCell(6).getStringCellValue());
	        	EducationalLoans.PhoneNumber(driver).sendKeys(row.getCell(7).getStringCellValue());
	        	EducationalLoans.ResCity(driver).selectByVisibleText(row.getCell(8).getStringCellValue());
	        	EducationalLoans.Course_Name(driver).sendKeys(row.getCell(9).getStringCellValue());
	    	} 

	    }  
	@AfterTest
	public void Close() 
	{
		driver.quit();
	}
}