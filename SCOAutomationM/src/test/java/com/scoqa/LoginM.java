package com.scoqa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.dataprovider.LoginDataProvider;
import com.xls.ReadWriteXlsx;

public class LoginM {
	



//Instance Variable	
ChromeDriver cdriver=null;
ChromeOptions options=null;

WebElement wb1=null;
WebDriverWait wait=null;
EventFiringWebDriver efw=null;

String url="";
String actualText="";
String expectedText="";
String relativePath="";
String absolutePath="";

int rowIndex=-1;
int colIndex=-1;

Actions actions=null;
Action action=null;
	  
ReadWriteXlsx obx=null;
SoftAssert sAssert=new SoftAssert(); 

	
	@BeforeTest
	public void setUp() throws IOException{
		String relativePath="xls/TestCase_BOSS.xlsx";
		String absolutePath=new File(relativePath).getAbsolutePath();
		obx=new ReadWriteXlsx(absolutePath);
		//===========Set the URL=======================
		url="http://10.198.105.68/UserAccount/LogOn?ReturnUrl=%2f";
}
	
	
	
	@BeforeMethod
	public void openUrl(){
		//=============Set the chrome options===================
		options = new ChromeOptions();
		 
		/*options.addArguments("--start-maximized");
		  options.addArguments("--disable-web-security");
		  options.addArguments("--no-proxy-server");
		  */
		  
		 Map<String, Object> prefs = new HashMap<String, Object>(); 
		  prefs.put("credentials_enable_service", false);
		  prefs.put("profile.password_manager_enabled", false);
		  options.setExperimentalOption("prefs", prefs);
		  
		
		
		//============Set the chrome driver====================
		relativePath="Driver_March_2017/chromedriver.exe";
		absolutePath= new File(relativePath).getAbsolutePath();
		System.setProperty("webdriver.chrome.driver",absolutePath);
		cdriver=new ChromeDriver(options);	
		
		cdriver.manage().window().maximize();	
		wait=new WebDriverWait(cdriver,20);
			
		cdriver.get(url);
		System.out.println("Driver get title " + cdriver.getTitle());
	} 
	
//================Test Method for Login using Data Provider========================
  @Test(dataProvider="loginDP", dataProviderClass=LoginDataProvider.class,enabled=true)
  public void login(String username,String password,String TC_ID,String rIndex,String resultCol) throws Exception {
	
	  //System.out.println("TC_ID is "+TC_ID+" Username is " + username + "Password is " + password);
	  rowIndex=Integer.parseInt(rIndex);
	  colIndex=Integer.parseInt(resultCol);
	  
	  
	  //===================Login to the portal============================
	  	wb1=null;
	  	wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
		wb1.sendKeys(username);
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Password")));
		wb1.sendKeys(password);
		
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
		wb1.click();
		
		
	  
	  
	  //=============Take a png for failure test case and place it BossScreenshot Folder ==============
	  //Validation for Authorized User.
		
		try{
			cdriver.findElement(By.id("UserName"));
			//======TakeScreenShot of failure====================
			createScreenShot(cdriver,TC_ID);
			//=============Write to xlsx sheet================
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			throw new Exception("Login operation is failed");				
		}
		catch(NoSuchElementException ex){		
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
		}
	  	  
  }
  
  
  
  
//==================================Switch Tenant Method will execute if Login is Success=================================
  @Test(dataProvider="switchTenantDP",dataProviderClass=LoginDataProvider.class,enabled=true)
  public void switchToTenant(String username,String password,String tenantName,String TC_ID,String rIndex,String resultCol) throws Exception{ 
	  	
	  	rowIndex=Integer.parseInt(rIndex);
		colIndex=Integer.parseInt(resultCol);

		
		//===================Login to the portal============================
	  	wb1=null;
	  	wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
		wb1.sendKeys(username);
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Password")));
		wb1.sendKeys(password);
		
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
		wb1.click();
		
		//================End of Login==================================
		
		
		 actions=new Actions(cdriver);
		 wb1=null;
		 wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='top-options-icon']")));
		 action=actions.moveToElement(wb1).build();
		 action.perform();
		 
		
		 String switchTenantPath="//a[@id='switch-account-text']";
		 wb1=null;
		 wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(switchTenantPath)));
		 actions.moveToElement(wb1).build().perform();
		 actions.click(wb1).build().perform();
			
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("companiesAutocomplete")));
		wb1.sendKeys(tenantName);
		
		//Thread.sleep(2000);
		wb1=null; 
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/strong[contains(text(),'"+tenantName+"')]")));
		wb1.click();
		
		Thread.sleep(4000);
		wb1=null; 
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("peopleForSelectedAccount")));
		wb1.click();
		Select sel=new Select(wb1);
		sel.selectByIndex(1);
		
		wb1=null; 
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("SwitchAccount_OK")));
		wb1.click();
		
		
		
		
		//Verify the account has been switched properly
		Thread.sleep(2000);
		wb1=null;
		
		
			
			
		String expectedText=tenantName;	
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='company_name']/a")));
		String actualText=wb1.getText();
		System.out.println("Actual text is " + actualText);
		
		
		
		/*if(actualText.equalsIgnoreCase(expectedText))
		   obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
  		else{
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			//sAssert.fail("Actual Text " + actualText +"is not matching" + expectedText+"both are not matching");
			throw new Exception("Switch tenant operation is failed");	
		}
  */
		
		//===========Verify the text=================
		try{
			
			Assert.assertEquals(actualText, expectedText);
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
		}
		catch(AssertionError n){
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			//Assert.fail("Actual Text " + actualText +" is not matching" + expectedText+"both are not matching");
			//throw new Exception("Switch tenant operation is failed");
		}
		finally{
			System.out.println("Switch Account is done");
		}
		
  }
  
  
  
  
 //==================================Log off Method will execute if Login is Success=================================
 @Test(dataProvider="logoffDP",dataProviderClass=LoginDataProvider.class,enabled=true)
 public void logoff(String username,String password,String TC_ID,String rIndex,String resultCol) throws Exception{ 
	 
	 rowIndex=Integer.parseInt(rIndex);
	 colIndex=Integer.parseInt(resultCol);
	 
	 
	//===================Login to the portal============================
	  	wb1=null;
	  	wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
		wb1.sendKeys(username);
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Password")));
		wb1.sendKeys(password);
		
		
		wb1=null;
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
		wb1.click();
		
	  //================End of Login==================================
		
	 
	 
	 actions=new Actions(cdriver);
	 wb1=null;
	 wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='top-options-icon']")));
	 action=actions.moveToElement(wb1).build();
	 action.perform();
	 
	 wb1=null;
	 wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Log Off')]")));
	 action=actions.moveToElement(wb1).build();
	 action.perform();
	 action=actions.click(wb1).build();
	 action.perform();
	 
	 Thread.sleep(4000);
	 
	 try{
			cdriver.findElement(By.id("UserName"));
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");				
		}
		catch(NoSuchElementException ex){		
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			throw new Exception("Logoff operation is failed");
		}
	 
	 
	 
} 
  
  
  
  
	
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 //================It will run after each test method and close the browser========================
  @AfterMethod(alwaysRun=true)
  public void tearDown(){
	  cdriver.close();
	  
  }
  
  
  
//=========Create Screen Shot for failure test case==================  
  
public void createScreenShot(ChromeDriver ccDriver,String TC_ID) throws IOException{
   efw=new EventFiringWebDriver(cdriver);
	File f1=efw.getScreenshotAs(OutputType.FILE);
	relativePath="BossScreenshot/"+TC_ID+".png";
	absolutePath=new File(relativePath).getAbsolutePath();
	FileUtils.copyFile(f1, new File(absolutePath));
}
  
  
}
