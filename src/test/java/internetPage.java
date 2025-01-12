import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class internetPage {
	
	static WebDriver driver;
	
	@BeforeMethod
	public static void setUP() {
		WebDriverManager.chromedriver().setup();    //  метод драйвера управления браузерами
		driver = new ChromeDriver(); 
	}
	
	@Test
	public static void countObjects() {
		driver.get("https://the-internet.herokuapp.com/");
		
		List<WebElement> listOfInputElements = driver.findElements(By.tagName("a"));   //этот блок покажет сколько input элементов на странице, так же можно посчитать другие элементы
        int count = listOfInputElements.size();
        System.out.println("Count of button elements : "+count);
        
        Assert.assertTrue(count >= 10, "Number of button elements more 10");       // Проверить, что количество элементов не больше 10
	}
	
	@Test
	public static void clickAllObjects() {
		driver.get("https://the-internet.herokuapp.com/");
		
		List<WebElement> allLinks = driver.findElements(By.tagName("a"));   //этот блок покажет сколько input элементов на странице, так же можно посчитать другие элементы
		 // Сохраняем идентификатор текущего окна
	    //String parentWindow = driver.getWindowHandle();

	    // Проходим по всем ссылкам
	    for (int i = 0; i < allLinks.size(); i++) {
	        // Повторно находим элементы (чтобы избежать StaleElementReferenceException)
	        allLinks = driver.findElements(By.xpath("//div/ul/li/a"));

	        // Клик на элемент
	        WebElement link = allLinks.get(i);
	        String linkText = link.getText();
	        link.click();
	        System.out.println("clicked");

//	        // Переключение на новую вкладку
//	        Set<String> allWindows = driver.getWindowHandles();
//	        for (String window : allWindows) {
//	            if (!window.equals(parentWindow)) {
//	                driver.switchTo().window(window);
//	                break;
//	            }
//	        }
//	        // Проверяем, что на новой странице есть информация
//	        WebElement infoElement = driver.findElement(By.tagName("p")); // Замените на подходящий локатор
//	        Assert.assertTrue(infoElement.getText().length() > 0, "Информация отсутствует на странице для: " + linkText);
//
//	        System.out.println("Страница для \"" + linkText + "\" содержит информацию.");

	        // Закрыть вкладку и вернуться к исходной
//	        driver.close();
//	        driver.switchTo().window(parentWindow);
	        driver.navigate().back(); // Возвращаемся назад
	        System.out.println("go back");
	    }
	}
	
	@Test     
	public static void eachObjectOpen() {
		driver.get("https://the-internet.herokuapp.com/");
		
		
		// 1 abtest
		driver.findElement(By.xpath("//a[@href='/abtest']")).click();   
		
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1)); // 10 секунд
        //WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3:contains('A/B Test Control')")));
        //System.out.println("нашёл элемент");
        
        String expectedURL = "https://the-internet.herokuapp.com/abtest";        // Ожидаемый URL
        String actualURL = driver.getCurrentUrl();          // Получите текущий URL
        Assert.assertEquals(actualURL, expectedURL, "URL сменился на ожидаемый!");  // Проверьте, что URL сменился на ожидаемый
        System.out.println("Current URL: " + driver.getCurrentUrl());  //отобразит на какой url сменилось
        System.out.println("сменил урл");
        
       // WebElement element = driver.findElement(By.xpath("//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-pre-wrap' and text()='DevOps']"));   // найти элемент
        //Assert.assertNotNull(element,"Элемент найден на странице");
       // System.out.println("Элемент найден: " + element.getText());
        System.out.println("AB");
        driver.navigate().back(); // Возвращаемся назад
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // 2 Add/Remove Elements
driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]")).click();   
        
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(1)); // 10 секунд
        WebElement element1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add Element')]")));
        System.out.println("нашёл элемент");
        
        String expectedURL1 = "https://the-internet.herokuapp.com/add_remove_elements/";        // Ожидаемый URL
        String actualURL1 = driver.getCurrentUrl();          // Получите текущий URL
        Assert.assertEquals(actualURL1, expectedURL1, "URL сменился на ожидаемый!");  // Проверьте, что URL сменился на ожидаемый
        System.out.println("Current URL: " + driver.getCurrentUrl());  //отобразит на какой url сменилось
        System.out.println("сменил урл");
        
       // WebElement element = driver.findElement(By.xpath("//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-pre-wrap' and text()='DevOps']"));   // найти элемент
        Assert.assertNotNull(element1,"Элемент найден на странице");
        System.out.println("Элемент найден: " + element1.getText());
        
        driver.findElement(By.xpath("//button[contains(text(),'Add Element')]")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
        System.out.println("click 2 button(add / remove)");
        
        
        driver.navigate().back(); // Возвращаемся назад
        
    
        // 3 Basic Auth
     // Основной URL    вот это пришлось добавить [
	    String baseUrl = "https://the-internet.herokuapp.com/";
	    driver.get(baseUrl);
	    
	    // Basic Auth
	    System.out.println("Начинаем работу с Basic Auth");
	    driver.findElement(By.xpath("//a[contains(text(),'Basic Auth')]")).click();
	    System.out.println("Ссылка Basic Auth нажата");        // вот это пришлось добавить 

//System.err.println("click");

String basicAuthUrl = "http://admin:admin@the-internet.herokuapp.com/basic_auth/";  // вот так писать если есть всплывающее окно от браузера и нужно авторизоваться admin это и логин и пароль но это для этого варианта

//Переход по URL с авторизацией
driver.get(basicAuthUrl);  // Открываем страницу с Basic Auth
System.out.println("Страница загружена");

// Ожидаем загрузки страницы и проверяем наличие элемента
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Basic Auth')]")));
System.out.println("Элемент найден: " + element.getText());

// Переходим назад
driver.get(baseUrl);
System.out.println("Вернулись на предыдущую страницу");


          // 4 Broken Images

driver.findElement(By.xpath("//a[contains(text(),'Broken Images')]")).click();
System.out.println("Click to Broken Images page");

//Ожидаем загрузки страницы и проверяем наличие элемента
WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(1));
WebElement element11 = wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Broken Images')]")));
System.out.println("Элемент найден: " + element11.getText());

// Список XPath для изображений
String[] imageXPaths = {
    "//body/div[2]/div[1]/div[1]/img[3]",
    "//body/div[2]/div[1]/div[1]/img[2]",
    "//body/div[2]/div[1]/div[1]/img[1]"
};

// Проверка каждого изображения
for (String xpath : imageXPaths) {
    try {
        WebElement image = new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        System.out.println("Image found: " + xpath);

        // Проверка через JavaScript: является ли изображение сломанным
        boolean isImageBroken = (boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].naturalWidth === 0;", image);

        if (isImageBroken) {
            System.out.println("Image is broken: " + xpath);
        } else {
            System.out.println("Image is loaded correctly: " + xpath);
        }
    } catch (Exception e) {
        System.out.println("Image not found or timeout: " + xpath);
    }
}

// Возвращаемся назад
driver.navigate().back();
System.out.println("Returned to the previous page");


   // 5 Challenging DOM  статические объекты
driver.findElement(By.xpath("//a[contains(text(),'Challenging DOM')]")).click();
System.out.println("Click to Challenging DOM");

//Ожидаем загрузки страницы и проверяем наличие элемента
WebDriverWait wait111 = new WebDriverWait(driver, Duration.ofSeconds(1));
WebElement element111 = wait111.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Challenging DOM')]")));
System.out.println("Элемент найден: " + element111.getText());

//Находим все кнопки с классом 'button'
List<WebElement> buttons = driver.findElements(By.cssSelector("a.button"));
Assert.assertTrue(buttons.size() > 0, "Кнопки не найдены!");

// Итерируем по каждой кнопке, обновляя список элементов после клика
for (int i = 0; i < buttons.size(); i++) {
    try {
        // Обновляем список кнопок после каждого действия
        buttons = driver.findElements(By.cssSelector("a.button"));
        WebElement button = buttons.get(i);

        // Клик по кнопке
        System.out.println("Найдена кнопка с текстом: " + button.getText());
        button.click();
        System.out.println("Кнопка нажата.");

        // Проверяем, что изменения произошли (например, через изменения на canvas)
        WebElement canvas = driver.findElement(By.id("canvas"));
        Object canvasData = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].toDataURL('image/png');", canvas);
        System.out.println("Canvas обновлен: " + canvasData);

    } catch (StaleElementReferenceException e) {
        System.out.println("Элемент устарел, обновляем список кнопок.");
        buttons = driver.findElements(By.cssSelector("a.button"));
        i--; // Вернуться на шаг назад
    }
    }

//  Проверить таблицу
WebElement table = driver.findElement(By.tagName("table"));
List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
Assert.assertTrue(rows.size() > 0, "Таблица пуста!");

for (WebElement row : rows) {
    List<WebElement> cells = row.findElements(By.tagName("td"));
    System.out.println("Содержимое строки: ");
    for (WebElement cell : cells) {
        System.out.print(cell.getText() + " | ");
    }
    System.out.println();
    // Выполнить действие edit
    WebElement editLink = row.findElement(By.xpath(".//a[text()='edit']"));
    editLink.click();
    System.out.println("Edit выполнено для строки.");
}

// Проверить количество строк
Assert.assertEquals(rows.size(), 10, "Количество строк в таблице неверное!");
System.out.println("Количество строк в таблице: " + rows.size());

//Переходим назад
driver.navigate().back();
System.out.println("Вернулись на предыдущую страницу");

// 6 


        
	}
	
	
	@Test  //  почему то здесь перестал переходить на первую страницу и по этому исправляем
	public static void AutorizationsALL() {
		 // Основной URL    вот это пришлось добавить [
	    String baseUrl = "https://the-internet.herokuapp.com/";
	    driver.get(baseUrl);
	    
	    // Basic Auth
	    System.out.println("Начинаем работу с Basic Auth");
	    driver.findElement(By.xpath("//a[contains(text(),'Basic Auth')]")).click();
	    System.out.println("Ссылка Basic Auth нажата");        // вот это пришлось добавить ]


		String basicAuthUrl = "http://admin:admin@the-internet.herokuapp.com/basic_auth/";  // вот так писать если есть всплывающее окно от браузера и нужно авторизоваться admin это и логин и пароль но это для этого варианта

		//Переход по URL с авторизацией
		driver.get(basicAuthUrl);  // Открываем страницу с Basic Auth
		System.out.println("Страница загружена");

		// Ожидаем загрузки страницы и проверяем наличие элемента
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Basic Auth')]")));
		System.out.println("Элемент найден: " + element.getText());

		// Переходим назад
		driver.get(baseUrl);
		System.out.println("Вернулись на предыдущую страницу");
		
		
		
		           // 8 Digest Authentication
		System.out.println("Начинаем работу с Digest Authentication");
		driver.findElement(By.xpath("//a[contains(text(),'Digest Authentication')]")).click();   
		//System.err.println("click");
		System.out.println("click Digest Authentication");


		String basicAuthUrl1 = "http://admin:admin@the-internet.herokuapp.com/digest_auth/";  // вот так писать если есть всплывающее окно от браузера и нужно авторизоваться admin это и логин и пароль но это для этого варианта

		//Переход по URL с авторизацией
		driver.get(basicAuthUrl1);  // Открываем страницу с Basic Auth
		System.out.println("Страница загружена");

		// Ожидаем загрузки страницы и проверяем наличие элемента
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(1));
		WebElement element1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Digest Auth')]")));
		System.out.println("Элемент найден: " + element1.getText());

		// Переходим назад
		driver.get(baseUrl);
		System.out.println("Вернулись на предыдущую страницу");
		
		
		     // 21 Form Authentication  простая аутентификация с полями ввода на странице БЕЗ ВСПЛЫВАЮЩЕГО ОКНА
		System.out.println("Начинаем работу с Form Authentication");
		driver.findElement(By.xpath("//a[contains(text(),'Form Authentication')]")).click();
		
		// Ожидаем загрузки страницы и проверяем наличие элемента
				WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(1));
				WebElement element11 = wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Login Page')]")));
				System.out.println("Элемент найден: " + element11.getText());
		
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("tomsmith");		
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.xpath("//body/div[2]/div[1]/div[1]/form[1]/button[1]")).click();
		
		// Ожидаем загрузки страницы и проверяем наличие элемента
		WebDriverWait wait111 = new WebDriverWait(driver, Duration.ofSeconds(1));
		WebElement element111 = wait111.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[i[@class='icon-lock']]")));
		System.out.println("Элемент найден: " + element111.getText());
		
		driver.findElement(By.xpath("//a[contains(., 'Logout')]")).click();
		System.out.println(" click logout");
		
		// Переходим назад
				driver.get(baseUrl);
				System.out.println("Вернулись на предыдущую страницу");
		
	}
	
	
	@AfterMethod
	public static void thearDown() {
		if (driver != null) {
            driver.quit();         // так писать что бы после всего выполнить quit
        }
	}

}
