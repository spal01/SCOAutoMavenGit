package com.scoqa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
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
//button[@type='submit']
import com.pageobject.LoginPage;
import com.pageobject.HomePage;
import com.pageobject.UserPage;

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
static Logger log=Logger.getLogger(LoginM.class);

int time=2000;
//==========Page Object Component===========
LoginPage loginPage; 
HomePage homePage;
UserPage userPage;

@BeforeTest
public void setUp() throws IOException{
	    	    
	    log.info("Inside the setup method");
		String relativePath="xls/TestCase_BOSS.xlsx";
		String absolutePath=new File(relativePath).getAbsolutePath();
		obx=new ReadWriteXlsx(absolutePath);
		//===========Set the URL=======================
		url="http://10.198.105.68/UserAccount/LogOn?ReturnUrl=%2f";
}
	
	
	
	@BeforeMethod
	public void openUrl(){
		actualText="";
		expectedText="";
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
		
		//==========Creating object of Each Page=============
		loginPage=new LoginPage(cdriver);
		homePage=new HomePage(cdriver);	
		userPage=new UserPage(cdriver);
		
		//============Launch the url===========
		cdriver.get(url);	
		cdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
	} 


/*@Test(priority=1)
public void openLoginPage()
{
	String expectedText="ShoreTel Sky Portal Login";
	String actualText=cdriver.getTitle();
	//System.out.println("Actual Text " + actualText);
	try{	
		Assert.assertEquals(actualText, expectedText);
		log.info("Login page has open");
		}
	catch(AssertionError n){
		log.info("Login page does not open");	
		}
	
}	
*/	
//================Test Login Operation========================
  @Test(dataProvider="loginDP", dataProviderClass=LoginDataProvider.class,enabled=true,priority=1)
  public void login(String username,String password,String TC_ID,String rIndex,String resultCol) throws Exception {
	  log.info(TC_ID+" startes the execution"); 
	  rowIndex=Integer.parseInt(rIndex);
	  colIndex=Integer.parseInt(resultCol);
	
	  try{
	  
	  actualText=cdriver.getTitle();
	  expectedText="ShoreTel Sky Portal Login";
		//System.out.println("Actual Text " + actualText);
	  Assert.assertEquals(actualText, expectedText);
	  log.info("Login page has open");
		  
	  //===================Login to the portal============================
	  loginOperation(username,password);	 	
	  //====================End of Login Operation===============
	  
	  //=============Take a png for failure test case and place it BossScreenshot Folder ==============
	  //Validation for Authorized User.
		
		try{
			cdriver.findElement(By.id("UserName"));	
			//======TakeScreenShot of failure====================
			createScreenShot(cdriver,TC_ID);
			//=============Write to xlsx sheet================
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			log.info(TC_ID +" is Fail");
			System.out.println( TC_ID +" is Fail");
			createScreenShot(cdriver,TC_ID);
			throw new Exception("Login operation is failed");				
		}
		catch(NoSuchElementException ex){		
			log.info(TC_ID +" is pass");
			System.out.println( TC_ID +" is pass");
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
		}
		finally{
			   log.info(TC_ID+" ends the execution");
		   }
	  }
	  
	  //===========Catch when no internet connection or web page does not opens=========
	  catch(AssertionError n){
		  createScreenShot(cdriver,TC_ID);
		//  System.out.println("This site can’t be reached -- Check network connectivity");
		  log.info("This site can’t be reached Check network connectivity");
		  Assert.fail("This site can’t be reached  Check network connectivity");
		  //  throw new Exception("This site can’t be reached -- Check network connectivity");
	  }
	  	  
  }
  
  
  
  
//==================================Test Switch Tenant Operation=================================
  @Test(dataProvider="switchTenantDP",dataProviderClass=LoginDataProvider.class,priority=2,enabled=true)
  public void switchToTenant(String username,String password,String tenantName,String TC_ID,String rIndex,String resultCol) throws Exception{ 
	  
	    
	  	log.info(TC_ID+" startes the execution");
	  	rowIndex=Integer.parseInt(rIndex);
		colIndex=Integer.parseInt(resultCol);

		
		try{
			  
			  actualText=cdriver.getTitle();
			  expectedText="ShoreTel Sky Portal Login";
				//System.out.println("Actual Text " + actualText);
			  Assert.assertEquals(actualText, expectedText);
			  log.info("Login page has open");
				
		
		//===================Operation on Login Page============================
		loginOperation(username,password);	
		//================End of Login==================================
		
		//=========================Operation on Home Page==============================
		//================Mouse move to Right top icon============================= 
		 actions=new Actions(cdriver);
		 wb1=null;	 
		 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getTopIcon()));
		 action=actions.moveToElement(wb1).build();
		 action.perform();
		 //===============End of Mouse move to Right top icon======================
		
		//================Mouse move and Click on Switch Account=============================
		 wb1=null; 
		 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSwitchAccount()));
		 actions.moveToElement(wb1).build().perform();
		 actions.click(wb1).build().perform();
		//================End of Mouse move and Click on Switch Account=============================	
		
		 //================Start of switch operation========================
		//================Enter the tenant Name=============================
		wb1=null;		
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getAutoComplete()));
		wb1.sendKeys(tenantName);
		
		//Thread.sleep(time);
		wb1=null; 
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/strong[contains(text(),'"+tenantName+"')]")));
		wb1.click();
		//===========================Selection of tenant done==================
		
		Thread.sleep(time);
		wb1=null; 		
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSelectedAccount()));
		wb1.click();
		Select sel=new Select(wb1);
		sel.selectByIndex(1);
		
		wb1=null; 
		//wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("SwitchAccount_OK")));
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSwitchAccountOk()));
		wb1.click();
		
		//===============End of switch operation====================================
		
		//=============Verify Switch has done properly or not==============
		Thread.sleep(time);
		wb1=null;		
			
		String expectedText=tenantName;	
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getTenantName()));
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
		
		
	  try{
			
			Assert.assertEquals(actualText, expectedText);
			log.info(TC_ID +" is pass");
			System.out.println( TC_ID +" is pass");
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
		}
		catch(AssertionError n){
			log.info(TC_ID +" is Fail");
			System.out.println( TC_ID +" is Fail");
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			//throw new Exception("Switch operation is fail");
			Assert.fail("Actual Text " + actualText +" is not matching" + expectedText+"both are not matching");
			
		}
		finally{
			   log.info(TC_ID+" ends the execution");
		   }
		}
		//===========Catch when no internet connection or web page does not opens=========
		  catch(AssertionError n){
			  createScreenShot(cdriver,TC_ID);
			  log.info("This site can’t be reached Check network connectivity");
			  Assert.fail("This site can’t be reached  Check network connectivity");
			 
			  // throw new Exception("This site can’t be reached -- Check network connectivity");
		  }
		
  }
  
  
  
  
 //==================================Test Log Off operation=================================
 @Test(dataProvider="logoffDP",dataProviderClass=LoginDataProvider.class,priority=4,enabled=true)
 public void logoff(String username,String password,String TC_ID,String rIndex,String resultCol) throws Exception{ 
	 log.info(TC_ID+" startes the execution");
	 rowIndex=Integer.parseInt(rIndex);
	 colIndex=Integer.parseInt(resultCol);
		
	 try{
			  actualText=cdriver.getTitle();
			  expectedText="ShoreTel Sky Portal Login";	
			  Assert.assertEquals(actualText, expectedText);
			  log.info("Login page has open"); 
	//===================Login to the portal============================
	 loginOperation(username,password);	
	//================End of Login==================================
		
	//====================Perform on Home Page============================= 
	 
	 actions=new Actions(cdriver);
	 wb1=null;
	 
	 //wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='top-options-icon']")));
	 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getTopIcon()));
	 action=actions.moveToElement(wb1).build();
	 action.perform();
	 
	 wb1=null;
	 //wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Log Off')]")));
	 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getLogOff()));
	 action=actions.moveToElement(wb1).build();
	 action.perform();
	 action=actions.click(wb1).build();
	 action.perform();
	 
	 Thread.sleep(4000);
	 
	 try{
			cdriver.findElement(By.id("UserName"));
			//loginPage.getUsername();
			log.info(TC_ID +" is pass");
			System.out.println( TC_ID +" is pass");
			obx.writeData("Test_Case",rowIndex,colIndex,"Pass");				
		}
		catch(NoSuchElementException ex){	
			log.info(TC_ID +" is Fail");
			System.out.println( TC_ID +" is Fail");
			obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
			throw new Exception("Logoff operation is failed");
		}
	 
	   finally{
		   log.info(TC_ID+" ends the execution");
	   }

 }
//===========Catch when no internet connection or web page does not opens=========
 catch(AssertionError n){
	  createScreenShot(cdriver,TC_ID);
	  log.info("This site can’t be reached Check network connectivity");
	  Assert.fail("This site can’t be reached  Check network connectivity"); 
	  // throw new Exception("This site can’t be reached -- Check network connectivity");
	}
} 
  
  
//================Add only User without Profile============================================ 
@Test(dataProvider="userCreation",dataProviderClass=LoginDataProvider.class,priority=3,enabled=true)
public void userWithoutProfile(String username,String password,String tenantName,String firstName,String lastName,String businessMail,String userPassword,String TC_ID,String rIndex,String resultCol) throws Exception{
    
	  	log.info(TC_ID+" startes the execution");
	  	rowIndex=Integer.parseInt(rIndex);
		colIndex=Integer.parseInt(resultCol);

		
		try{
			  
			  actualText=cdriver.getTitle();
			  expectedText="ShoreTel Sky Portal Login";
				//System.out.println("Actual Text " + actualText);
			  Assert.assertEquals(actualText, expectedText);
			  wb1=null;	 
			  System.out.println("BOSS BUILD VERSION IS " + cdriver.findElementByCssSelector(".m5-version").getText());
			  log.info("Login page has open");
	
		}
		//===========Catch when no internet connection or web page does not opens=========
		catch(AssertionError n){
			  createScreenShot(cdriver,TC_ID);
			//  System.out.println("This site can’t be reached -- Check network connectivity");
			  log.info("This site can’t be reached -- Check network connectivity");
			  throw new Exception("This site can’t be reached -- Check network connectivity");
		  }

		try{
		//===================Operation on Login Page============================
		loginOperation(username,password);	
		//================End of Login==================================
		
		//=========================Operation on Home Page==============================
		//================Mouse move to Right top icon============================= 
		 actions=new Actions(cdriver);
		 wb1=null;	 
		 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getTopIcon()));
		 action=actions.moveToElement(wb1).build();
		 action.perform();
		 //===============End of Mouse move to Right top icon======================
		
		//================Mouse move and Click on Switch Account=============================
		 wb1=null; 
		 wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSwitchAccount()));
		 actions.moveToElement(wb1).build().perform();
		 actions.click(wb1).build().perform();
		//================End of Mouse move and Click on Switch Account=============================	
		
		 //================Start of switch operation========================
		//================Enter the tenant Name=============================
		wb1=null;		
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getAutoComplete()));
		wb1.sendKeys(tenantName);
		
		//Thread.sleep(time);
		wb1=null; 
		wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/strong[contains(text(),'"+tenantName+"')]")));
		wb1.click();
		//===========================Selection of tenant done==================	
		Thread.sleep(time);
		wb1=null; 		
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSelectedAccount()));
		wb1.click();
		Select sel=new Select(wb1);
		sel.selectByIndex(1);
		
		wb1=null; 	
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getSwitchAccountOk()));
		wb1.click();
		
		//===============End of switch operation====================================
		
		//=============Verify Switch has done properly or not==============
		Thread.sleep(time);
		wb1=null;		
			
		String expectedText=tenantName;	
		wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getTenantName()));
		String actualText=wb1.getText();
		System.out.println("Actual text is " + actualText);
	   
	  
	  //======================Select Users Option================
	  
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getPhoneSystem()));
	  actions.moveToElement(wb1).build().perform();
	  actions.click(wb1).build().perform();
	  
	  //============Select the partition=============
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'(BOSS QA) ')]")));
	  actions.moveToElement(wb1).build().perform();
	  actions.click(wb1).build().perform();
	  
	 
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(homePage.getUsers()));
	  actions.moveToElement(wb1).build().perform();
	  actions.click(wb1).build().perform();
	  
	  Thread.sleep(6000);
	  //=========Operation on user page============
	  	
	  wb1=null;
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getAddUserButton()));
	  actions.moveToElement(wb1).build().perform();
	  actions.click(wb1).build().perform();
	  
	  //============Enter the data for User creation=============
	  Thread.sleep(6000);
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserFirstName()));
	  wb1.sendKeys(firstName);
	  
	  
	  wb1=null;
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserLastName()));
	  wb1.sendKeys(lastName);
	  
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserBusinessEmail()));
	  wb1.sendKeys(businessMail);
	  
	 /* wb1=null;
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserName()));
	  wb1.sendKeys(businessMail);*/
	    
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserPassword()));
	  wb1.sendKeys(userPassword);
	  
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getUserConfirmPassword()));
	  wb1.sendKeys(userPassword);
	  
	  //===========Click on Next Button ========
	  	
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getNext()));
	  wb1.click();
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getNext()));
	  wb1.click();
	  
	  wb1=null; 
	  wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getNext()));
	  wb1.click();
	  
	  
	  //======================Now we are in Review page=========================
		wb1=null; 
		wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getRequestBy()));
		sel=new Select(wb1);
		sel.selectByIndex(1);;

		wb1=null; 
		wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getRequestSources()));
		sel=new Select(wb1);
		sel.selectByVisibleText("Phone");
	
		wb1=null; 
		wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getFinish()));	
		wb1.click();	
		//==================End of Adding User========================================
		//===================Now verify the user if the user is  present
			Thread.sleep(4000);
		//In business email field enter the email address
		wb1=null; 
		wb1=wait.until(ExpectedConditions.visibilityOf(userPage.getSearchBMail()));	
		wb1.sendKeys(businessMail);
		
			try{
	
			//Check if the User is present in User Grid 
				wb1=null; 
				wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'"+businessMail+"')]")));	
				log.info(TC_ID +" is pass");
				System.out.println( TC_ID +" is pass");
				obx.writeData("Test_Case",rowIndex,colIndex,"Pass");
		
				}
			catch(NoSuchElementException ex){	
				createScreenShot(cdriver,TC_ID);
				log.info(TC_ID +" is Fail");
				System.out.println( TC_ID +" is Fail");
				obx.writeData("Test_Case",rowIndex,colIndex,"Fail");
				throw new Exception("User is not getting added");
				}
		//End of try catch block
		

		}
		catch(NoSuchElementException no){
			  createScreenShot(cdriver,TC_ID);
			//  System.out.println("This site can’t be reached -- Check network connectivity");
			  log.info("Not able to find the element");
			  Assert.fail("Not able to find the element"); 
			  //throw new Exception("Not able to find the element");
		  }

		catch(TimeoutException no){
			  createScreenShot(cdriver,TC_ID);
			//  System.out.println("This site can’t be reached -- Check network connectivity");
			  log.info("TimeOut Exception");
			  Assert.fail("TimeOut Exception");
			  //throw new Exception("Not able to find the element");
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
	File f2=new File(absolutePath);
	if(f2.exists()){
		f2.delete();
		f2.createNewFile();
		FileUtils.copyFile(f1,f2);	
	}
	
	
}
  

//===========================Login in to portal==============
  
public void loginOperation(String userName,String passWord){
		
	wb1=null;
  	//wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
  	wb1=wait.until(ExpectedConditions.visibilityOf(loginPage.getUsername()));
  	//wb1=loginPage.getUsername();
  	wb1.sendKeys(userName);
	
	wb1=null;
	//wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Password")));
    wb1=wait.until(ExpectedConditions.visibilityOf(loginPage.getPassword()));	
	wb1.sendKeys(passWord);
	
	
	wb1=null;
	//wb1=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
	wb1=wait.until(ExpectedConditions.visibilityOf(loginPage.getSubmit()));	
	wb1.click();
	
	
}












}
