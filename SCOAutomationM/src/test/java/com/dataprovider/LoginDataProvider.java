package com.dataprovider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xls.ReadWriteXlsx;

public class LoginDataProvider {
  
	static  int testCaseRowIndex=-1;
	
	static  int testCaseIdCol=0;
	static  int numberOfTestParamCol=4;
	static  int testParamsCol=5;
	static  int resultCol=8;
	
	
	static ArrayList<String[]>loginData=null;
	static ReadWriteXlsx obx=null;
	
	

	@DataProvider(name="loginDP")
	public static Iterator<String[]> getLogin() throws IOException{
		
			
		testCaseRowIndex=1;
		
		loginData=new ArrayList<String[]>();
		String relativePath="xls/TestCase_BOSS.xlsx";
		String absolutePath=new File(relativePath).getAbsolutePath();
		obx=new ReadWriteXlsx(absolutePath);
		
		String testCaseId=obx.getData("Test_Case",testCaseRowIndex, testCaseIdCol);
		int numberOfTestParam=Integer.parseInt((obx.getData("Test_Case", testCaseRowIndex, numberOfTestParamCol).replaceAll(".0","")));
		
		String[] finalData=new String[numberOfTestParam+3];
		String testData=obx.getData("Test_Case", testCaseRowIndex, testParamsCol);

		//=========PICK UP THE TEST DATA =====================
		String[] data=null;
		testData=testData.trim();
		data=testData.split(",");
		if(numberOfTestParam>=1)
			data=testData.split(",");
		else
			data[0]=testData;

		int i=0;
		for(String d :data){
			d=d.replaceAll(" ","");
			finalData[i]=d;
			i++;		
		}
		finalData[i++]=testCaseId;
		finalData[i++]=String.valueOf(testCaseRowIndex);
		finalData[i++]=String.valueOf(resultCol);
		
		//System.out.println("Print the final data");
		/*for(String fd :finalData){
		System.out.println("fd " + fd);
		}*/
		
		
		loginData.add(finalData);

		
		return loginData.iterator();
	}

	
	
	
	
	//=============Data Provider for SwitchTenant=====================
	@DataProvider(name="switchTenantDP")
	public static Iterator<String[]>getTenant() throws IOException{
			loginData=null;
			obx=null;
			
			loginData=new ArrayList<String[]>();
		
			String relativePath="xls/TestCase_BOSS.xlsx";
			String absolutePath=new File(relativePath).getAbsolutePath();
			//System.out.println("Absolute Path " + absolutePath);
			obx=new ReadWriteXlsx(absolutePath);
			testCaseRowIndex=2;
			
			String testCaseId=obx.getData("Test_Case",testCaseRowIndex, testCaseIdCol);
			int numberOfTestParam=Integer.parseInt((obx.getData("Test_Case", testCaseRowIndex, numberOfTestParamCol).replaceAll(".0","")));			
			String[] finalData=new String[numberOfTestParam+3];
 	
			String testData=obx.getData("Test_Case", testCaseRowIndex, testParamsCol);
		
			String[] data=null;
			testData=testData.trim();
			if(numberOfTestParam>=1)
				data=testData.split(",");
			else
				data[0]=testData;
					
	
			
			int i=0;
			for(String d :data){
				d=d.replaceAll(" ","");
				finalData[i]=d;
				i++;		
			}
			finalData[i++]=testCaseId;
			finalData[i++]=String.valueOf(testCaseRowIndex);
			finalData[i++]=String.valueOf(resultCol);
			
			
			loginData.add(finalData);
			
			return loginData.iterator();
		
		}

	
	
		//=============Data Provider for LogOff=====================
		@DataProvider(name="logoffDP")
		public static Iterator<String[]>getData() throws IOException{
			loginData=null;
			obx=null;
			
			loginData=new ArrayList<String[]>();
		
			String relativePath="xls/TestCase_BOSS.xlsx";
			String absolutePath=new File(relativePath).getAbsolutePath();
			//System.out.println("Absolute Path " + absolutePath);
			obx=new ReadWriteXlsx(absolutePath);
			testCaseRowIndex=3;
			
			String testCaseId=obx.getData("Test_Case",testCaseRowIndex, testCaseIdCol);
			int numberOfTestParam=Integer.parseInt((obx.getData("Test_Case",testCaseRowIndex, numberOfTestParamCol).replaceAll(".0","")));
			String[] finalData=new String[numberOfTestParam+3];
	
			String testData=obx.getData("Test_Case", testCaseRowIndex, testParamsCol);	
			
			String[] data=null;
			testData=testData.trim();
			if(numberOfTestParam>=1)
				data=testData.split(",");
			else
				data[0]=testData;
	
			
			
			
			int i=0;
			for(String d :data){
				d=d.replaceAll(" ","");
				finalData[i]=d;
				i++;		
			}
			finalData[i++]=testCaseId;
			finalData[i++]=String.valueOf(testCaseRowIndex);
			finalData[i++]=String.valueOf(resultCol);
			
			loginData.add(finalData);
			
			return loginData.iterator();
		
		}
		

		
//==================DataProvider for 		
		@DataProvider(name="userCreation")
		public static Iterator<String[]> getUserData() throws IOException{
			
				
			testCaseRowIndex=4;
			
			loginData=new ArrayList<String[]>();
			String relativePath="xls/TestCase_BOSS.xlsx";
			String absolutePath=new File(relativePath).getAbsolutePath();
			obx=new ReadWriteXlsx(absolutePath);
			
			String testCaseId=obx.getData("Test_Case",testCaseRowIndex, testCaseIdCol);
			int numberOfTestParam=Integer.parseInt((obx.getData("Test_Case", testCaseRowIndex, numberOfTestParamCol).replaceAll(".0","")));
			
			String[] finalData=new String[numberOfTestParam+3];
			String testData=obx.getData("Test_Case", testCaseRowIndex, testParamsCol);

			//=========PICK UP THE TEST DATA =====================
			String[] data=null;
			testData=testData.trim();
			data=testData.split(",");
			if(numberOfTestParam>=1)
				data=testData.split(",");
			else
				data[0]=testData;

			int i=0;
			for(String d :data){
				d=d.replaceAll(" ","");
				finalData[i]=d;
				i++;		
			}
			finalData[i++]=testCaseId;
			finalData[i++]=String.valueOf(testCaseRowIndex);
			finalData[i++]=String.valueOf(resultCol);
			
			//System.out.println("Print the final data");
			for(String fd :finalData){
			System.out.println("fd " + fd);
			}
			
			
			loginData.add(finalData);

			
			return loginData.iterator();
		}
	
	
	
	
}
