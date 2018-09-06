package com.test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyBrowserClass 
{
	WebDriver driver;
	String driverPath;
	String baseUrl;
	String email;
	String pass ;
	@BeforeTest
	public void setup() throws IOException
	{
		Reader reader = Files.newBufferedReader(Paths.get("Resource/Data.csv"));
		 @SuppressWarnings("resource")
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				  .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
		 for (CSVRecord csvRecord : csvParser) {
             // Accessing values by the names assigned to each column
			 driverPath = csvRecord.get("driverPath");
			 baseUrl  = csvRecord.get("baseUrl");
			 email= csvRecord.get("email");
			 pass= csvRecord.get("pass");
		 }
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver();
		
		driver.get(baseUrl);
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void signUpFunction()
	{

		SaleLoginPage log= new SaleLoginPage(driver);
		log.loginMethod( email,  pass);
		
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
		
	}
}
