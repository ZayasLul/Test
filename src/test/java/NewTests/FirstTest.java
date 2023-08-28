package NewTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;


class Locator {
 static By button_open_catalogue = By.xpath("//div[@data-apiary-widget-id='/header/catalogEntrypoint']");
 static By button_electronics_in_catalogue = By.xpath("//li[contains(@data-zone-name,'category')]//a[contains(@href, 'catalog') and contains(@href, 'elektronika')]");
 static By button_smartphone_in_catalogue = By.xpath("(//ul[@data-autotest-id='subItems']//li)[1]");
 static By button_open_all_filters = By.xpath("//aside//a//button");
 static By input_price_max = By.xpath("//div[@id='glprice']//input[@data-auto='range-filter-input-max']");
 static By input_screen_diagonal_min = By.xpath("//div[@id='14805766']//input[@data-auto='range-filter-input-min']");
 static By input_screen_diagonal_max = By.xpath("//div[@data-filter-id='14805766']//input[@data-auto='range-filter-input-max']");
 static By check_boxs_manufacturer = By.xpath("//button[@aria-controls='7893318']//..//label//input[@type='checkbox']");
 static By button_show_results_filters = By.xpath("//a[@data-autotest-id='result-filters-link']");
 static By count_name_smartphone_in_page = By.xpath("//article[@data-autotest-id='product-snippet']//h3//a");
 static By rating_smartphone = By.xpath("//div[@data-auto='rating-stars']");
}

public class FirstTest extends FWChromeDriver {


    // @Test обозначаем что это тест
    @Test
    public void Test(){
        // Открываем страницу маркета
        FW.openPage();
        // Открываем страницу "Смартфоны"
        FW.openSmartphonePage();
        // Открываем страницу "Все фильтры"
        FW.openAllFilters();
        // Открываем фильтр "Цена"
        FW.openOrCloseFilter("glprice","Open");
        // Вводим цену "ДО"
        FW.inputPriceMax("20000");
        // Открываем фильтр "Диагональ экрана (точно)"
        FW.openOrCloseFilter("14805766","Open");
        // Вводим "диагональ экрана (точно) ОТ"
        FW.inputScreenDiagonalMin("3");
        // Вводим "диагональ экрана (точно) ДО"
        FW.inputScreenDiagonalMax("5");
        // Открываем фильтр "Производитель"
        FW.openOrCloseFilter("7893318", "Open");
        // Выбираем первых 5 производителей
        FW.chooseManufacture(5);
        // Нажимаем кнопку "Показать # предложений"
        FW.click_button_show_results_filters();
        // Нажимаем кнопку "Показать # предложений"
        FW.click_button_show_results_filters();
        // Получаем информацию о телефоне
        List smartphone_data = List.of(FW.get_info_smartphone(1));
        // Находим телефон и нажимаем на него
        FW.click_name_smartphone((String) smartphone_data.get(0));
        // Записываем значение оценки
        String rating = FW.get_rating_smartphone();
    }
}

class FWChromeDriver {
    public static ChromeDriver driver;

    // @Before выполняется перед тестом
    @Before
    public void chromeDriverSetup(){
        // Автоматически работает с версией браузера и веб драйвера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Ожидание появления элемента на странице 10 секунд
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Разворачиваем на весь экран
        driver.manage().window().maximize();
    }

    // Выполняется после теста
    @After
    public void quit() {
        //Закрываем браузер
        driver.quit();
    }
    public static void clickElement(By locator){
        WebElement element = driver.findElement(locator);
        moveToElement(locator);
        element.click();
    }

    public static void moveToElement(By locator){
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
    }

    public static String getTagAttribute(By locator, String name_attribute){
        WebElement element = driver.findElement(locator);
        return element.getAttribute(name_attribute);
    }

    public static void send_keys(By locator, String name_text){
        WebElement element = driver.findElement(locator);
        element.sendKeys(name_text);
    }
    public static List find_elements(By locator){
        return driver.findElements(locator);
    }
    public static void scroll_to_element(By locator){
        WebElement element = driver.findElement(locator);
        driver.executeScript("arguments[0].scrollIntoView();",element);

    }

    public static String get_text_attribute(By locator){
        WebElement element = driver.findElement(locator);
        return element.getText();
    }
}
class FW {

    public static String name_page = "https://market.yandex.ru/";
    public static void openPage(){
        // Переходим настраницу маркета
        FWChromeDriver.driver.get(name_page);
    }

    public static void openSmartphonePage(){
        // Нажимаем на кнопку открытия католога
        FWChromeDriver.clickElement(Locator.button_open_catalogue);
        // Переводим курсор на кнопку "Электроника"
        FWChromeDriver.moveToElement(Locator.button_electronics_in_catalogue);
        // Нажимаем на кнопку "Смартфоны"
        FWChromeDriver.clickElement(Locator.button_smartphone_in_catalogue);
    }

    public static void openAllFilters() {
        FWChromeDriver.moveToElement(Locator.button_open_all_filters);
        FWChromeDriver.clickElement(Locator.button_open_all_filters);
    }

    public static void openOrCloseFilter(String name_data_filter_id, String open_or_close){
        By locator = By.xpath("//div[@data-filter-id='"+ name_data_filter_id + "']//button[@aria-controls='" + name_data_filter_id + "']");
        FWChromeDriver.scroll_to_element(locator);
        String tag_locator = FWChromeDriver.getTagAttribute(locator, "aria-expanded");
        if (open_or_close.equals("Open")) {
            if ((tag_locator).equals("false")) {
                // Нажимаем на кнопку открытия фильтра если он не открыт
                FWChromeDriver.clickElement(locator);
            }
        } else if (open_or_close.equals("Close")) {
            if (tag_locator.equals("true")) {
                // Нажимаем на кнопку закрытия фильтра если он открыт
                FWChromeDriver.clickElement(locator);
            }
        }
    }
    public static void inputPriceMax(String price){
        FWChromeDriver.send_keys(Locator.input_price_max, price);
    }
    public static void inputScreenDiagonalMin(String price){
        FWChromeDriver.scroll_to_element(Locator.input_screen_diagonal_min);
        FWChromeDriver.send_keys(Locator.input_screen_diagonal_min, price);
    }
    public static void inputScreenDiagonalMax(String price){
        FWChromeDriver.scroll_to_element(Locator.input_screen_diagonal_max);
        FWChromeDriver.send_keys(Locator.input_screen_diagonal_max, price);
    }

    public static void chooseManufacture(Integer count){
        for (int i = 1; i < count + 1; i++){
            By locator = By.xpath("(//button[@aria-controls='7893318']//..//label)" + "[" + i + "]");
            FWChromeDriver.clickElement(locator);
        }
    }
    public static void click_button_show_results_filters(){
        FWChromeDriver.driver.findElement(Locator.button_show_results_filters);
        FWChromeDriver.clickElement(Locator.button_show_results_filters);
    }

    public static String get_rating_smartphone(){
        return FWChromeDriver.getTagAttribute(Locator.rating_smartphone, "data-rating-value");
    }

    public static void click_name_smartphone(String name_phone){
        // Записываем количество телефонов на странице
        int count_name_smartphone = (FWChromeDriver.find_elements(Locator.count_name_smartphone_in_page)).size();
        for (int i = 1; i < count_name_smartphone + 1; i++) {
            // Локатор названия смартфона
            By locator_name_smartphone = By.xpath("//div[@data-item-index='" + i + "']//article//h3[@data-auto='snippet-title-header']//span");
            // получаем имя смартфона
            String name_smartphone = FWChromeDriver.get_text_attribute(locator_name_smartphone);
            if (name_phone.equals(name_smartphone)) {
                FWChromeDriver.clickElement(locator_name_smartphone);
                return;
            }
        }
    }

    public static String[] get_info_smartphone(int index_smartphone){
        String list_for_return[] = new String[]{"","",""};
        // Локатор названия смартфона
        By locator_name_smartphone = By.xpath("//div[@data-item-index='" + index_smartphone + "']//article//h3[@data-auto='snippet-title-header']//span");
        // Скролим к смартфону
        FWChromeDriver.scroll_to_element(locator_name_smartphone);
        // получаем имя смартфона
        String name_smartphone = FWChromeDriver.get_text_attribute(locator_name_smartphone);
        // записываем имя в list
        list_for_return[0] = name_smartphone;
        // локатор цены смартфона
        By locator_price = By.xpath("//div[@data-item-index='" + index_smartphone + "']//span[@data-auto='price-value']");
        // получаем цену смартфона
        String price_smartphone = FWChromeDriver.get_text_attribute(locator_price);
        // записываем цену в list
        list_for_return[1] = price_smartphone;
        // локатор названия магазина
        By locator_name_market = By.xpath("(//div[@data-zone-name='shop-name']//span)[" + index_smartphone + "]");
        // получаем название магазина
        String name_market = FWChromeDriver.get_text_attribute(locator_name_market);
        // записываем название магазина в list
        list_for_return[2] = price_smartphone;
        return list_for_return;
    }
}
