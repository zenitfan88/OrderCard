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
        void test () {
    driver.get("http://localhost:9999");
    driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
    driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
    driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
    driver.findElement(By.className("button__content")).click();
    String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();

    assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
            text);
}


    @Test
    void incorrectNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("+79278228262");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText().trim();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text);
    }

    @Test
    void incorrectPhoneTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7927822826");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.xpath("//span[text()='Мобильный телефон']/following-sibling:" +
                ":span[contains(@class, 'input__sub')]")).getText().trim();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void nullNameTest () {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7927822826");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.xpath("//span[text()='Фамилия и имя']/following-sibling:" +
                ":span[contains(@class, 'input__sub')]")).getText().trim();

        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void nullPhoneTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.xpath("//span[text()='Мобильный телефон']/following-sibling" +
                "::span[contains(@class, 'input__sub')]")).getText().trim();

        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void absentCheckboxTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278228262");
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }
}
