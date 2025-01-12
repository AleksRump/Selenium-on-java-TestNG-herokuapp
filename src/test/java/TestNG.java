import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNG {
	
	private static WebDriver driver=null;
	
	@BeforeMethod
	public static void setUP() {
		
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();

	}
	
	@Test(enabled = false)  // проверка количества объектов
	public static void CountButton() {
		driver.get("https://trytestingthis.netlify.app/");
		
		List<WebElement> listOfInputElements = driver.findElements(By.className("button"));   //этот блок покажет сколько input элементов на странице, так же можно посчитать другие элементы
        int count = listOfInputElements.size();
        System.out.println("Count of button elements : "+count);
        
        Assert.assertTrue(count <= 5, "Number of button elements exceeds 5");       // Проверить, что количество элементов не больше 5
	}
	
	@Test(priority = 1)   // после нажатия на кнопку проверка наличия текста и сммены url
	public static void clickContactAndWatchOtherPage(){
        driver.get("https://trytestingthis.netlify.app/");
        
        driver.findElement(By.cssSelector("a.button.bar-item[href='/contact']")).click();
        
     
        String expectedURL = "https://trytestingthis.netlify.app/contact";        // Ожидаемый URL
        String actualURL = driver.getCurrentUrl();          // Получите текущий URL
        Assert.assertEquals(actualURL, expectedURL, "URL сменился на ожидаемый!");  // Проверьте, что URL сменился на ожидаемый
        System.out.println("Current URL: " + driver.getCurrentUrl());  //отобразит на какой url сменилось

        WebElement header = driver.findElement(By.xpath("//h2[text()='Thank you for using this Website :)']"));   // найти элемент
        String headerText = header.getText();     //проверить, что текст совпадает с ожидаемым
        Assert.assertEquals(headerText, "Thank you for using this Website :)", "Текст заголовка не совпадает!");
        System.out.println("text It matches");

	}
	
	@AfterMethod
	public void tearDownTest() {   // это функция завершения теста
		if (driver != null) {
            driver.quit();         // так писать что бы после всего выполнить quit
        }
	
	}
}
