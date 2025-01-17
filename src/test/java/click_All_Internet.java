import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import io.github.bonigarcia.wdm.WebDriverManager;   // открывает все объекты одного типа и проверяет наличие объеуктов другого типа на 
                                                    // открывшейся странице, так же шагает назад после выполнения первых 2-х действий

public class click_All_Internet {
	static WebDriver driver;
	static ChromeOptions options;
	
	@BeforeMethod
	public static void setUP() {
		options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
	}
	
	@Test
	public static void ckickALLaObjects() {
		driver.get("https://the-internet.herokuapp.com/");
		
		 // Находим все элементы типа <a>
        List<WebElement> links = driver.findElements(By.tagName("a"));
        int count = links.size();
        System.out.println("Количество ссылок: " + count);

        // Проверяем, что ссылки найдены
        Assert.assertTrue(count > 0, "Ссылки не найдены!");

        // Проходим по каждой ссылке
        for (int i = 0; i < count; i++) {
            try {
                // Обновляем список ссылок
                links = driver.findElements(By.tagName("a"));
                WebElement link = links.get(i);

                // Проверяем, что ссылка видима и кликабельна
                if (link.isDisplayed() && link.getSize().getHeight() > 0 && link.getSize().getWidth() > 0) {
                    System.out.println("Найдена ссылка с текстом: " + link.getText());
                    link.click();
                    System.out.println("Ссылка нажата.");

                    // Проверяем наличие объекта (например, <h3>)
                    try {
                        WebElement h3 = driver.findElement(By.tagName("h3"));
                        System.out.println("Элемент <h3> найден: " + h3.getText());
                    } catch (NoSuchElementException e) {
                        System.out.println("Элемент <h3> не найден.");
                    }

                    // Возвращаемся назад
                    driver.navigate().back();
                    System.out.println("Вернулись на предыдущую страницу.");
                } else {
                    System.out.println("Ссылка с индексом " + i + " не видима или имеет нулевой размер, пропускаем.");
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("Элемент устарел, обновляем список.");
                i--; // Возвращаемся на шаг назад
            }
        }
    }
	@AfterMethod
	public static void thearDown() {
		if (driver != null) {
            driver.quit();         // так писать что бы после всего выполнить quit
        }
	}

}