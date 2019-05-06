package com.pageObjects;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

public class HomePage {

private static WebElement element = null;

public static WebElement AllAboutPersonalLoans(WebDriver driver){
element = driver.findElement(By.xpath("//a[@href='/personal-loan.php']"));

return element;

}

}