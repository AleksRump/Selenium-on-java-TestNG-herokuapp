import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testNGwithSteobyStep {
private static WebDriver driver=null;
WebDriverWait wait;
	
	@BeforeMethod
	public static void setUP() {
		
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();

	}
	
	@Test(enabled = false)  // проверка количества объектов
	public static void countPosters() {
		
        driver.get("https://automationstepbystep.com/");
        
        List<WebElement> imgElements = driver.findElements(By.tagName("img"));  //этот блок покажет сколько input элементов на странице, так же можно посчитать другие элементы
        int count = imgElements.size();
        System.out.println("Количество элементов <img>: " + count);
        Assert.assertTrue(count >= 8, "Number of button elements exceeds >=8");       // Проверить, что количество элементов не больше 5

	}
	
	@Test
	public static void clickAndChangePage() {
		
        driver.get("https://automationstepbystep.com/");
        
     // Сохранение идентификатора текущего окна
        String parentWindow = driver.getWindowHandle();
        
        driver.findElement(By.xpath("//a[@title='What is DevOps']")).click();  //этот блок покажет сколько input элементов на странице, так же можно посчитать другие элементы
        System.out.println("нажато");
        
     // Получаем все открытые окна/вкладки
        Set<String> allWindows = driver.getWindowHandles();
        
     // Переключаемся на новую вкладку (если она не родительская вкладка)
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
                
            }
            
        }
        
     // Явное ожидание загрузки страницы (например, элемент, который появится на новой вкладке)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 секунд
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='What is DevOps | Explain DevOps | How DevOps work | Beginners']")));
        System.out.println("нашёл элемент");
        
        String expectedURL = "https://www.youtube.com/playlist?list=PLhW3qG5bs-L8HKqfIp6qcAeGb3FAGDNLA";        // Ожидаемый URL
        String actualURL = driver.getCurrentUrl();          // Получите текущий URL
        Assert.assertEquals(actualURL, expectedURL, "URL сменился на ожидаемый!");  // Проверьте, что URL сменился на ожидаемый
        System.out.println("Current URL: " + driver.getCurrentUrl());  //отобразит на какой url сменилось
        System.out.println("сменил урл");
        
       // WebElement element = driver.findElement(By.xpath("//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-pre-wrap' and text()='DevOps']"));   // найти элемент
        Assert.assertNotNull(element,"Элемент найден на странице");
        System.out.println("Элемент найден: " + element.getText());
        
        
     // Вернуться к родительской вкладке
        driver.switchTo().window(parentWindow);

	}
		
	@AfterMethod
	public void tearDownTest() {   // это функция завершения теста
		if (driver != null) {
            driver.quit();         // так писать что бы после всего выполнить quit
        }
	
	}
}

