package com.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	private static WebElement element = null;

	public static List<WebElement> AllLinks(WebDriver driver){
	List<WebElement> allLinks = driver.findElements(By.tagName("a"));
	return allLinks;

	}

}
