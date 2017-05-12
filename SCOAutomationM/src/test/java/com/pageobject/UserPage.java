package com.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage {
	
	WebDriver cdriver=null;
	
	public UserPage(WebDriver cdriver){
		this.cdriver=cdriver;
		PageFactory.initElements(cdriver, this);
		}
	
	

	public WebElement getAddUserButton() {
		return addUserButton;
	}

	public WebElement getUserFirstName() {
		return userFirstName;
	}

	public WebElement getUserLastName() {
		return userLastName;
	}

	public WebElement getUserBusinessEmail() {
		return userBusinessEmail;
	}

	public WebElement getUserPersonalEmail() {
		return userPersonalEmail;
	}

	public WebElement getUserLocation() {
		return userLocation;
	}

	public WebElement getUserName() {
		return userName;
	}

	public WebElement getUserPassword() {
		return userPassword;
	}

	public WebElement getUserConfirmPassword() {
		return userConfirmPassword;
	}

	public WebElement getRequestBy() {
		return requestBy;
	}

	public WebElement getRequestSources() {
		return requestSources;
	}

	public WebElement getNext() {
		return next;
	}

	public WebElement getFinish() {
		return finish;
	}

	public WebElement getYesMsgBox() {
		return yesMsgBox;
	}

	public WebElement getNoMsgBox() {
		return noMsgBox;
	}

	@FindBy(id="usersDataGridNewPersonButton")
	private WebElement addUserButton;
	
	
	@FindBy(id="Person_FirstName")
	private WebElement userFirstName;
	
	@FindBy(id="Person_LastName")
	private WebElement userLastName;
	
	
	@FindBy(id="Person_BusinessEmail")
	private WebElement userBusinessEmail;
	
	
	@FindBy(id="Person_PersonalEmail")
	private WebElement userPersonalEmail;
	
	@FindBy(id="Person_LocationId")
	private WebElement userLocation;

	@FindBy(id="Person_Username")
	private WebElement userName;
	
	@FindBy(id="Person_Password")
	private WebElement userPassword;
	
	@FindBy(id="confirmPassword")
	private WebElement userConfirmPassword;


	
	@FindBy(id="RequestedBy")
	private WebElement requestBy;
	
	@FindBy(id="RequestSources")
	private WebElement requestSources;
	
	@FindBy(id="addEditPersonWizard_next")
	private WebElement next;
	
	@FindBy(id="addEditPersonWizard_finish")
	private WebElement finish;
	
	@FindBy(id="fnMessageBox_Yes")
	private WebElement yesMsgBox;
	
	@FindBy(id="fnMessageBox_No")
	private WebElement noMsgBox;
	
	@FindBy(id="headerRow_BusinessEmail")
	private WebElement searchBMail;

	public WebElement getSearchBMail() {
		return searchBMail;
	}
	
	
	
	
	

}
