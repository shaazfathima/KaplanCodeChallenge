package com.pageObjects;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
public class ConfirmationPage {

	private static WebElement element = null;
	public static WebElement ThankYouMsg(WebDriver driver){
		element = driver.findElement(By.id("txt"));
		return element;
		}
}
