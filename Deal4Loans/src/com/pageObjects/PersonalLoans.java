package com.pageObjects;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
public class PersonalLoans {

	private static WebElement element = null;

	public static WebElement EducationalLoanMenu(WebDriver driver){
	element = driver.findElement(By.linkText("Education Loan"));
	return element;

	}
	
	public static WebElement EducationalLoanOption(WebDriver driver){
		element = driver.findElement(By.xpath("//a[@href='https://www.deal4loans.com/loans/education-loan/education-loan-student-loan/']"));
		return element;

		}
	
}
