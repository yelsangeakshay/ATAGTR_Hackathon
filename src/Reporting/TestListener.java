package Reporting;

import org.testng.ITestListener;
import static Common_Utilities.GlobalVariables.*;
import static Common_Utilities.JSONUtility.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

	static Workbook workbook ;
	
	public static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}

		 private static boolean hasMatchingName(String str, String[] substrings) {
	        for (String substring : substrings){
	            if (str.contains(substring)) {
	                return true;
	            }
	        }
	        return false;
	    }

	@Override
	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		public static void WriteDataToExcel(String testCaseID, String status, String error) throws IOException, InterruptedException {
			String currentDir = System.getProperty("user.dir");
			String filePath = currentDir+"//ExecutionResultInExcel//ExecutionResult.xlsx" ;
	        Sheet sheet = workbook.getSheet("Result");
	        int rowNum = sheet.getLastRowNum() + 1;
	        Row row = sheet.createRow(rowNum);
	        row.createCell(0).setCellValue(testCaseID);
	        row.createCell(1).setCellValue(status);
	        row.createCell(2).setCellValue(error);
	    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
			Date now = new Date();
	        row.createCell(3).setCellValue(sdfDate.format(now));
	        
	        try {
	            FileOutputStream out = new FileOutputStream(filePath);
	            workbook.write(out);
	            out.close();
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
		}
	@Override
	public void onStart(ITestContext context) {
	        System.out.println("*** Test Suite " + context.getName() + " started ***");
		workbook = new XSSFWorkbook();
		if (workbook.getNumberOfSheets() != 0) {
			workbook.removeSheetAt(0);
		}
		workbook.createSheet("Result");
		Sheet sheet = workbook.getSheet("Result");
		Row row = sheet.createRow(0);
		if (Objects.isNull(row.getCell(0))) {
			row.createCell(0).setCellValue("Test Script Name");
			row.createCell(1).setCellValue("Execution Status");
			row.createCell(2).setCellValue("Error");
			row.createCell(3).setCellValue("Date");
		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testClassFullName = result.getMethod().getRealClass().getName();
		System.out.println("*** Test execution for " + testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1)
				+ " failed...");
		System.out.println("Test Case Failed");
		
		try {
//			GetScreenshot.captureScreen(ExtentReportStatus.FAIL);
//			String dest =GetScreenshot.captureFullScreen(result);
//			ExtentTestManager.getTest().log(Status.FAIL, 
//				"Please navigate to "+dest+" to check the full page screenshot where failure happened");
//			System.out.println("Please navigate to "+dest+" to check the full page screenshot where failure happened");
		} catch (Exception e) {
			e.printStackTrace();
		}

		StackTraceElement[] error = result.getThrowable().getStackTrace() ;
		String[] classNameList = {"GenericFunctions","AdvanceFunctions","ExcelUtility","Assert","Framework"
				,"exceptionhandling","Function_Library","Report","TestBase","Utility",".properties","retryMechanism"};
		
		String errorDetail="";
		String errorDetailForConsole="";
		for(int i=0;i<error.length;i++) {
			String err = error[i].toString();
			if(hasMatchingName(err,classNameList) ) {
				errorDetail= errorDetail + error[i].toString()+"<br />";
				errorDetailForConsole= errorDetailForConsole + error[i].toString()+"\n";
			}
		}
		
		System.out.println(errorDetailForConsole);
		try {
			WriteDataToExcel(testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1), "Failed", result.getThrowable().toString()+"\n"+errorDetailForConsole);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testClassFullName = result.getMethod().getRealClass().getName();
//		ELKUtils.sendDetailsToELK( testClassFullName,
//				"skip");
		System.out.println("*** Test execution for " + testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1)
				+ " skipped...");
		System.out.println("Test Case Skipped");

//		try {
//			GetScreenshot.captureScreen(ExtentReportStatus.SKIP);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		StackTraceElement[] error = result.getThrowable().getStackTrace() ;
		String[] classNameList = {"GenericFunctions","AdvanceFunctions","ExcelUtility","Assert","Framework"
				,"exceptionhandling","Function_Library","Report","TestBase","Utility",".properties","retryMechanism"};
		
		String errorDetail="";
		String errorDetailForConsole="";
		for(int i=0;i<error.length;i++) {
			String err = error[i].toString();
			if(hasMatchingName(err,classNameList) ) {
				errorDetail= errorDetail + error[i].toString()+"<br />";
				errorDetailForConsole= errorDetailForConsole + error[i].toString()+"\n";
			}
		}
		
		System.out.println(errorDetailForConsole);
		
		try {
			WriteDataToExcel(testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1), "Skipped", result.getThrowable().toString()+"\n"+errorDetailForConsole);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onTestStart(ITestResult result) {
		setVariable("TestMethodName", getTestMethodName(result));
		if(!Objects.isNull(getVariable("TestDataRepo")))
		setTestDataMap();
		if(!Objects.isNull(getVariable("LocatorRepo")))
		setLocatorMap();
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
String testClassFullName = result.getMethod().getRealClass().getName();
		System.out.println("*** Executed " + testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1)
				+ " test successfully...");
//		ELKUtils.sendDetailsToELK( testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1),
//				"pass");
//		try {
//			GetScreenshot.captureScreen(ExtentReportStatus.PASS);
//		} catch (IOException e) {
//			log.warn("An exception occurred while taking screenshot " + e.getCause());
//		}
		
		try {
			WriteDataToExcel(testClassFullName.substring(testClassFullName.lastIndexOf(".") + 1), "Passed", "");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}