package textfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReadTextfile {
	WebDriver driver;

	By username_field = By.xpath("//*[@id=\'username\']");
	By password_field = By.xpath("//*[@id=\'password\']");
	By signin_field = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboard_hearder_field = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By PAGE_ICON_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[1]/nav/div/a");
	By CUSTOMERS_FIELD = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER_FIELD = By.xpath("//a[text()='Add Customer']");
	By addcontactheader_field = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULLNAME_FIELD = By.xpath("//input[@id='account']");
	By company_drop_field = By.xpath("//select[@id='cid']");
	By EMAIL_FIELD = By.xpath("//input[@id='email']");
	By PHONE_NUMBER_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@id='address']");
	By CITY_FIELD = By.xpath("//input[@id='city']");
	By STATE_REGION_FIELD = By.xpath("//input[@id='state']");
	By ZIPCODE_FIELD = By.xpath("//input[@id='zip']");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");
	By SUBMIT_BUTTON_FIELD = By.xpath("//button[@id='submit']");

	// Test data
	String browser;
	String url;
	String username;
	String password;
	String fullname;
	String email;
	String phoneNum;
	String Address;
	String city;
	String state;
	String zipcode;
	String country;
	String company;
	String contactheaderText = "Contacts";

	@BeforeClass
	public void configReader() {
		// types of file reader
		// filereader, InputStream, scanner, buffered reader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used:" + browser);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			fullname = prop.getProperty("fullname");
			email = prop.getProperty("email");
			phoneNum = prop.getProperty("phoneNum");
			Address = prop.getProperty("Address");
			city = prop.getProperty("city");
			state = prop.getProperty("state");
			zipcode = prop.getProperty("zipcode");
			country = prop.getProperty("country");
			company = prop.getProperty("company");

		} catch (IOException e) {

		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\gecko.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void addcustomer() {

		// loging into to techfios ibilling page and getting to the
		driver.findElement(username_field).sendKeys(username);
		driver.findElement(password_field).sendKeys(password);
		driver.findElement(signin_field).click();
		driver.findElement(PAGE_ICON_FIELD).click();
		driver.findElement(CUSTOMERS_FIELD).click();
		driver.findElement(ADD_CUSTOMER_FIELD).click();

		//assertation
		Assert.assertEquals(driver.findElement(dashboard_hearder_field).getText(), contactheaderText, "Wrong Page!!!");
		
		//getting unique number
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);

		// passing fullname
		driver.findElement(FULLNAME_FIELD).sendKeys(fullname);

		// company drop down
		selectfromdropdown(driver.findElement(company_drop_field), company);

		// passing other required variables
		driver.findElement(EMAIL_FIELD).sendKeys(randomNum + email);
		driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNum + randomNum);
		driver.findElement(ADDRESS_FIELD).sendKeys(randomNum + " " + Address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(STATE_REGION_FIELD).sendKeys(state);
		driver.findElement(ZIPCODE_FIELD).sendKeys(zipcode);

		// country drop down
		selectfromdropdown(driver.findElement(COUNTRY_DROPDOWN_FIELD), country);

		//Adding new customer
		 //driver.findElement(SUBMIT_BUTTON_FIELD).click();

	}

	// dropdown method
	public void selectfromdropdown(WebElement element, String visibleText) {

		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}

}
