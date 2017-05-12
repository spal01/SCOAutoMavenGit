package com.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class HomePage {
	WebDriver cdriver=null;
	
	public HomePage(WebDriver cdriver){
		this.cdriver=cdriver;
		PageFactory.initElements(cdriver, this);
	}
	
	public WebDriver getCdriver() {
		return cdriver;
	}

	public WebElement getTopIcon() {
		return topIcon;
	}

	public WebElement getLogOff() {
		return logOff;
	}

	public WebElement getSwitchAccount() {
		return switchAccount;
	}

	public WebElement getAutoComplete() {
		return autoComplete;
	}

	public WebElement getSelectedAccount() {
		return selectedAccount;
	}

	public WebElement getSwitchAccountOk() {
		return switchAccountOk;
	}

	public WebElement getTenantName() {
		return tenantName;
	}
	
	
	public WebElement getPhoneSystem() {
		return phoneSystem;
	}

	public WebElement getUsers() {
		return users;
	}


	@FindBy(xpath="//span[@id='top-options-icon']")
	private WebElement topIcon;
	
	@FindBy(xpath="//a[contains(text(),'Log Off')]")
	private WebElement logOff;
	
	@FindBy(xpath="//a[@id='switch-account-text']")
	private WebElement switchAccount;
	
	@FindBy(id="companiesAutocomplete")
	private WebElement autoComplete;
	
	@FindBy(id="peopleForSelectedAccount")
	private WebElement selectedAccount;
	
	@FindBy(id="SwitchAccount_OK")
	private WebElement switchAccountOk;
	
	@FindBy(xpath="//div[@id='company_name']/a")
	private WebElement tenantName;

	
	@FindBy(xpath="//ul[@id='menu']/descendant::a[contains(text(),'Phone System')]")
	private WebElement phoneSystem;
	
	
	@FindBy(xpath="//ul[@id='menu']/descendant::ul/li/a[contains(text(),'Users')]")
	private WebElement users;






}
