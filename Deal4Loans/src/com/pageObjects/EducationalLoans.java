package com.pageObjects;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
public class EducationalLoans {

	private static WebElement element = null;

	public static WebElement FullName(WebDriver driver){
	element = driver.findElement(By.id("Name"));
	return element;

	}
	
	public static WebElement CoBorrrowerInc(WebDriver driver){
		element = driver.findElement(By.name("Coborrower_Income"));
		return element;

		}
	public static WebElement Email(WebDriver driver){
		element = driver.findElement(By.id("Email"));
		return element;

		}
	public static WebElement LoanAmount(WebDriver driver){
		element = driver.findElement(By.id("Loan_Amount"));
		return element;

		}
	public static WebElement PhoneNumber(WebDriver driver){
		element = driver.findElement(By.id("Phone"));
		return element;

		}
	public static WebElement Course_Name(WebDriver driver){
		element = driver.findElement(By.name("Course_Name"));
		return element;

		}

	public static WebElement AcceptTerms(WebDriver driver){
		element = driver.findElement(By.name("accept"));
		return element;

		}
	
	public static WebElement Quote(WebDriver driver){
		element = driver.findElement(By.id("education_loan_btn"));
		return element;

		}
	
	public static Select CountryOfStudy(WebDriver driver){
		Select element = new Select(driver.findElement(By.name("Country")));
		return element;

	}
	public static Select StudyCourse(WebDriver driver){
		Select element = new Select(driver.findElement(By.name("Course")));
		return element;

	}
	
	public static Select ResCity(WebDriver driver){
		Select element = new Select(driver.findElement(By.id("City")));
		return element;

	}
	public static Select EmpStatus(WebDriver driver){
		Select element = new Select(driver.findElement(By.name("Employment_Status")));
		return element;

	}
}
