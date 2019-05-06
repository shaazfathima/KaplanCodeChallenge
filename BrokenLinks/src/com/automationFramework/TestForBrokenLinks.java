package com.automationFramework;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pageObjects.HomePage;

public class TestForBrokenLinks {
	
	private static WebDriver driver;
	
	@BeforeTest
	public void InitiateDriver() throws Exception 
	{
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	
		//Launch the home page
		driver.get("http://www.codetraverse.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void CheckLinks() throws Exception, Exception
	{
		List<WebElement> allLinks=HomePage.AllLinks(driver);
		for(WebElement link:allLinks)
		{
			try

	    	{

		        System.out.println("URL: " + link.getAttribute("href")+ " returned " + isLinkBroken(new URL(link.getAttribute("href"))));

	    	}

	    	catch(Exception exp)

	    	{

	    		System.out.println("At " + link.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());	    		

	    	}
		}
		
	}
	public static String isLinkBroken(URL url) throws Exception

	{

		String response = "";

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try

		{

		    connection.connect();

		     response = connection.getResponseMessage();	        

		    connection.disconnect();

		    return response;

		}

		catch(Exception exp)

		{

			return exp.getMessage();

		}  				

	}
	
	@AfterTest
	public void Close() 
	{
			driver.quit();
	}
}
