package com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LoginPage {
WebDriver cdriver=null;

	public LoginPage(WebDriver cdriver){
		this.cdriver=cdriver;
		PageFactory.initElements(cdriver, this);

	}
  
	
	@FindBy(id="UserName")
	private WebElement username;
	
	public WebElement getUsername() {	
		return username;
	}

	
	@FindBy(id="Password")
	private WebElement password;
	
	public WebElement getPassword() {
		return password;
	}


	

	@FindBy(xpath="//button[@type='submit']")
	private WebElement submit;
	
	
	public WebElement getSubmit() {
		return submit;
	}

	
	

}
