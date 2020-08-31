package test;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.reflect.CatchClauseSignature;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;


public class DemoTestIOS {
	IOSDriver driver;

	public static String getDayOfWeek(String string) {
		String regex = " (\\D+),";
		Pattern pattern = Pattern.compile(regex); 
		Matcher matcher = pattern.matcher(string);
		if (matcher.find() == true) {
			matcher.group(1);
		}
		return matcher.group(1);
	}

	@Test
	public void testCase01() throws InterruptedException {
		System.out.println("---------Starting test-----------");
		String dayOfWeek = null;
		MobileElement todayButton = (MobileElement) driver.findElementByAccessibilityId("Today");
		todayButton.click();
		Thread.sleep(1000);
		todayButton.click();


		List <WebElement> names = driver.findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther[starts-with(@name,'Today')]");
		for(int i=0; i<names.size();i++) {
			dayOfWeek = names.get(i).getText();
			System.out.println("Get current date in iOS Device: " + dayOfWeek);
		}


		String daysArray[] = {"?","Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("Get current day in System: " + daysArray[day]);
		System.out.println("Compare Day vs Day");
		
		assertEquals(daysArray[day],getDayOfWeek(dayOfWeek));
	}



	@BeforeMethod
	public void firstTest() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "iOS");
		caps.setCapability("platformVersion", "12.1.4");
		caps.setCapability("deviceName", "iPhone");
		caps.setCapability("udid", "61444cbc20b57b3228a62c10b7c65e224c7414df");
		caps.setCapability("app", "com.apple.mobilecal");
		URL url = new URL("http://10.10.31.173:4723/wd/hub");
		driver = new IOSDriver(url,caps);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
		System.out.println("-------------------End Test-------------------------");
	}
}

