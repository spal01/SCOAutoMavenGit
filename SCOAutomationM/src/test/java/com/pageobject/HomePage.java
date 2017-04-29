package com.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class HomePage {
	WebDriver cdriver=null;
	
	public HomePage(WebDriver cdriver){
		this.cdriver=cdriver;
		PageFactory.initElements(cdriver, HomePage.class);
	}
  
	
}
