package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class orderCardTest {
    WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void test() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("+79278228262");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7927822826");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.xpath("//span[text()='Мобильный телефон']/following-sibling:" +
                ":span[contains(@class, 'input__sub')]")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                text.trim());
    }

    @Test
    void testNameNull() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7927822826");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.xpath("//span[text()='Фамилия и имя']/following-sibling:" +
                ":span[contains(@class, 'input__sub')]")).getText();
        assertEquals("Поле обязательно для заполнения",
                text.trim());
    }

    @Test
    void testPhoneNull() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.xpath("//span[text()='Мобильный телефон']/following-sibling" +
                "::span[contains(@class, 'input__sub')]")).getText();
        assertEquals("Поле обязательно для заполнения",
                text.trim());
    }

    @Test
    void testCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text.trim());
    }
}
