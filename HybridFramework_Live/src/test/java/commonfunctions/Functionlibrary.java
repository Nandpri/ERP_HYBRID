package commonfunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Functionlibrary {

	public static WebDriver driver;
	public static Properties conpro;

	public static WebDriver startBrowser() throws Throwable, IOException
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if (conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		}else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}else
		{
			Reporter.log("Browser value not matching", true);
		}
		return driver;	

	}

	public static void openurl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}

	public static void waitForElement(WebDriver driver , String Locator_type , String Locator_value , String Test_data)
	{


		WebDriverWait  mywait = new WebDriverWait(driver,10);

		if (Locator_type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_value)));
		}
		if (Locator_type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_value)));
		}

		if (Locator_type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_value)));
		}
	}


	public static void typeAction(WebDriver driver , String Locator_type ,String Locator_value,String Test_data)
	{
		if (Locator_type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).clear();
			driver.findElement(By.id(Locator_value)).sendKeys(Test_data);
		}
		if (Locator_type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).clear();
			driver.findElement(By.name(Locator_value)).sendKeys(Test_data);
		}
		if (Locator_type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).clear();
			driver.findElement(By.xpath(Locator_value)).sendKeys(Test_data);
		}
	}

	public static void clickAction(WebDriver driver , String Locator_type ,String Locator_value,String Test_data)
	{
		if (Locator_type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).click();
		}
		if (Locator_type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).click();
		}
		if (Locator_type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).click();
		}
	}

	public static void validateTitle( WebDriver driver, String Exp_title)
	{
		String Act_title = driver.getTitle();
		try 
		{
			Assert.assertEquals(Act_title ,Exp_title,"Title is not matching");	
		}catch (Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}

	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}

	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
		return df.format(date);

	}

	public static void mouseClick(WebDriver driver) throws InterruptedException
	{
		Actions act = new Actions (driver);
		act.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]")));
		act.pause(3000).click().perform();
	}

	public static void category(WebDriver driver , String Test_data ) throws InterruptedException
	{
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Test_data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();

			Thread.sleep(2000);
			String Act = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
			Reporter.log(Test_data+Act, true);
			try
			{
				Assert.assertEquals(Act,Test_data,"category is not matching");
			}catch (Throwable t)
			{
				System.out.println(t.getMessage())	;
			}
		}
	}

	public static void dropdownAction(WebDriver driver , String Locator_type ,String Locator_value,String Test_data)
	{
		if (Locator_type.equalsIgnoreCase("id")) 
		{
			int value = Integer.parseInt(Test_data);
			WebElement element = driver.findElement(By.id(Locator_value));
			Select select = new Select (element);
			select.selectByIndex(value);
		}
		if (Locator_type.equalsIgnoreCase("name")) 
		{
			int value = Integer.parseInt(Test_data);
			WebElement element = driver.findElement(By.name(Locator_value));
			Select select = new Select (element);
			select.selectByIndex(value);
		}
		if (Locator_type.equalsIgnoreCase("xpath")) 
		{
			int value = Integer.parseInt(Test_data);
			WebElement element = driver.findElement(By.xpath(Locator_value));
			Select select = new Select (element);
			select.selectByIndex(value);
		}
	}

	public static void capturestock(WebDriver driver , String Locator_type ,String Locator_value) throws IOException
	{
		String stocknum = "";

		if (Locator_type.equalsIgnoreCase("id"))
		{
			stocknum = driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		if (Locator_type.equalsIgnoreCase("name"))
		{
			stocknum = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		if (Locator_type.equalsIgnoreCase("xpath"))
		{
			stocknum = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter (fw);
		bw.write(stocknum);
		bw.flush();
		bw.close();
	}

	public static void stockTable(WebDriver driver) throws IOException, InterruptedException
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp+"      "+Act_Data,true);
		try {
		Assert.assertEquals(Exp, Act_Data, "Stock Number is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}

		}
	

		public static void capturesupp(WebDriver driver ,String Locator_type  , String Locator_value) throws IOException
		{
			String  suppliernumber = "" ;
			if (Locator_type.equalsIgnoreCase("id"))
			{
				suppliernumber = driver.findElement(By.id(Locator_value)).getAttribute("value");
			}
			if (Locator_type.equalsIgnoreCase("name"))
			{
				suppliernumber = driver.findElement(By.name(Locator_value)).getAttribute("value");
			}
			if (Locator_type.equalsIgnoreCase("xpath"))
			{
				suppliernumber = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
			}
			FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			bw.write(suppliernumber);
			bw.flush();
			bw.close();
		}

		public static void supplierTable(WebDriver driver)throws Throwable
		{
			FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String Exp_Data =br.readLine();
			if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_Data, "Supplier number Not Matching");
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
			}
		}
		public static void capturecustomer(WebDriver driver ,String Locator_type  , String Locator_value) throws IOException
		{
			String  customernumber = "" ;
			if (Locator_type.equalsIgnoreCase("id"))
			{
				customernumber = driver.findElement(By.id(Locator_value)).getAttribute("value");
			}
			if (Locator_type.equalsIgnoreCase("name"))
			{
				customernumber = driver.findElement(By.name(Locator_value)).getAttribute("value");
			}
			if (Locator_type.equalsIgnoreCase("xpath"))
			{
				customernumber = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
			}
			FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			bw.write(customernumber);
			bw.flush();
			bw.close();
		}
		public static void customerTable(WebDriver driver)throws Throwable
		{
			FileReader fr = new FileReader("./CaptureData/customernumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String Exp_Data =br.readLine();
			if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/form/div/div[2]/table/tbody/tr/td[5]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_Data, "customer number Not Matching");
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
			}
		}
	}