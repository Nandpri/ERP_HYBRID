package driverFactory;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunctions.Functionlibrary;
import utilities.Excelfileutil;

public class DriverScript {
	
		public WebDriver driver;
		String inputpath = "./FileInput/HybridFramework.xlsx";
		String outputpath = "./FileOutput/HybridResults.xlsx";
		String Testcases = "MasterTestCases";
		ExtentReports reports;
		ExtentTest logger;

		public void startTest() throws IOException, Throwable
		{
			String Module_status = "";
			Excelfileutil xl = new Excelfileutil(inputpath);
			for (int i=1;i<=xl.rowcount(Testcases);i++)
			{

				String Execution_status = xl.getCellData(Testcases, i, 2);
				if (Execution_status .equalsIgnoreCase("Y"))
				{
					String Tcmodule = xl.getCellData(Testcases, i, 1);
					reports = new ExtentReports("./ExtentReports/hybrid/"+Tcmodule+Functionlibrary.generateDate()+"html");
					logger=reports.startTest(Tcmodule);
					logger.assignAuthor("priya");
					for (int j=1;j<=xl.rowcount(Tcmodule);j++)
					{
						String Description = xl.getCellData(Tcmodule, j, 0);
						String Object_type = xl.getCellData(Tcmodule, j, 1);
						String Locator_type = xl.getCellData(Tcmodule, j, 2);
						String Locator_value = xl.getCellData(Tcmodule, j, 3);
						String Test_data = xl.getCellData(Tcmodule, j, 4);
						try 
						{
							if (Object_type.equalsIgnoreCase("startBrowser"))
							{
								driver = Functionlibrary.startBrowser();
								logger.log(LogStatus.INFO, Description);
								
							}
							if (Object_type.equalsIgnoreCase("openurl"))
							{
								Functionlibrary.openurl(driver);
								logger.log(LogStatus.INFO, Description);
							}

							if (Object_type.equalsIgnoreCase("waitforElement"))
							{
								Functionlibrary.waitForElement(driver, Locator_type, Locator_value, Test_data);
								logger.log(LogStatus.INFO, Description);
							}

							if (Object_type.equalsIgnoreCase("typeAction"))
							{
								Functionlibrary.typeAction(driver, Locator_type, Locator_value, Test_data);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("clickAction"))
							{
								Functionlibrary.clickAction(driver, Locator_type, Locator_value, Test_data);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("validateTitle"))
							{
								Functionlibrary.validateTitle(driver, Test_data);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("closeBrowser"))
							{
								Functionlibrary.closeBrowser(driver);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("mouseClick"))
							{
								Functionlibrary.mouseClick(driver);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("category"))
							{
								Functionlibrary.category(driver, Test_data);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("dropdownAction"))
							{
								Functionlibrary.dropdownAction(driver, Locator_type, Locator_value, Test_data);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("capturestock"))
							{
								Functionlibrary.capturestock(driver, Locator_type, Locator_value);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("stockTable"))
							{
								Functionlibrary.stockTable(driver);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("capturesupp"))
							{
								Functionlibrary.capturesupp(driver, Locator_type, Locator_value);
								logger.log(LogStatus.INFO, Description);
							}
							if (Object_type.equalsIgnoreCase("supplierTable"))
							{
								Functionlibrary.supplierTable(driver);
								logger.log(LogStatus.INFO, Description);
							}
							if(Object_type.equalsIgnoreCase("capturecustomer"))
							{
								Functionlibrary.capturecustomer(driver, Locator_type, Locator_value);
								logger.log(LogStatus.INFO, Description);
							}
							if(Object_type.equalsIgnoreCase("customerTable"))
							{
								Functionlibrary.customerTable(driver);
								logger.log(LogStatus.INFO, Description);
							}
							xl.setCelldata(Tcmodule, j, 5, "pass",outputpath);
							logger.log(LogStatus.PASS, Description);
							Module_status = "True";
						}catch (Exception e)
						{
							xl.setCelldata(Tcmodule, j, 5, "fail", outputpath);
							logger.log(LogStatus.FAIL, Description);
							System.out.println(e.getMessage());
						}
						
					}
					if (Module_status.equalsIgnoreCase("true"))	
					{
						xl.setCelldata(Testcases, i, 3, "pass",outputpath);
					}else if (Module_status .equalsIgnoreCase("false"))
					{
						xl.setCelldata(Testcases, i, 3, "fail",outputpath);
					}else
					{
						xl.setCelldata(Testcases, i, 3, "blocked",outputpath);
					}
				}
			}
			reports.endTest(logger);
			reports.flush();
		}

	}




