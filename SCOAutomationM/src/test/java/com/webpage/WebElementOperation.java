package com.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.pageobject.LoginPage;

public class WebElementOperation {
	
WebDriver cdriver=null;
	
	public WebElementOperation(WebDriver cdriver){
		this.cdriver=cdriver;
		PageFactory.initElements(cdriver, this);
	}
  
	
	

}
