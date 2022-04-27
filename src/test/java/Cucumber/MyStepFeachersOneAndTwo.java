package Cucumber;

import io.cucumber.java.After;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.Затем;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static Cucumber.WebDriverFactory.setupDriver;

public class MyStepFeachersOneAndTwo {

    public String baseUrl = "https://otus.ru/";

    public static EventFiringWebDriver driver;
    protected WebDriverWait wait;

    @FindBy(css = "a.lessons__new-item .lessons__new-item-title")
    private List<WebElement> coursesNameAll;

    @FindBy(css = "a.lessons__new-item")
    private List<WebElement> courses;

    @FindBy(css = ".lessons__new-item-start")
    private List<WebElement> datePopularCourse;

    @FindBy(css = ".lessons__new-item-time")
    private List<WebElement> dateSpecialCourse;




    @Допустим("Открытие {string}")
    public void openSite( String browser) {
        driver = new EventFiringWebDriver(setupDriver(browser));
        driver.register(new SelectClickListener());

        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(baseUrl);
        PageFactory.initElements(driver, this);
    }

    @After
    public void teamDown(){
        driver.quit();
    }


    @Затем("Поиск указанного курса {string}")
    public void searchCourse(String course) {
        Actions actions = new Actions(driver);

        try {
            for (int i = 0;; i++) {
                String courseName = coursesNameAll.get(i).getText();

                if (courseName.contains(course)){
                    System.out.println("Ваш курс: " + courseName);
                    actions
                            .moveToElement(courses.get(i))
                            .pause(1000)
                            .click()
                            .pause(3000)
                            .build()
                            .perform();


                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Курс с таким названием не найден");
        }

    }

    @Затем("Вывод курсов с требуемой даты {string}")
    public void outputOfCoursesFromDate(String date) {
        String format = "Курс - %s! \nСтартует - %s\n\n";
        for (int i = 0; i < 3; i++) {
            String dateCourse = datePopularCourse.get(i).getText();
            String[] words = dateCourse.split("[\\s]+");

            if (words[0].matches("[С]") && inputDate(date).isBefore(popularDate(words))) {
                System.out.printf(format, coursesNameAll.get(i).getText(), datePopularCourse.get(i).getText());
            }
        }
        for (int i = 3; i < 23; i++) {
            String dateCourse = dateSpecialCourse.get(i).getText();
            String[] words = dateCourse.split("[\\s]+");

            if (words[0].matches("[0-9]{2}") && inputDate(date).isBefore(specialDate(words))){
                System.out.printf(format,coursesNameAll.get(i).getText(), dateSpecialCourse.get(i).getText());
            }

            if (words[0].matches("[В]") && inputDate(date).isBefore(LocalDate.of(2022,4,1))){
                System.out.printf(format,coursesNameAll.get(i).getText(), "В марте");
            }
        }
    }

    public LocalDate inputDate(String date) {
        if (date.matches("[0-2][0-9][- /.][0-1][0-9][- /.][20]\\d\\d")) {
            System.out.println("Введён неверный формат даты ");
            Assert.fail();
        }
        String[] words = date.split("[- /.]+");
        int dd = Integer.parseInt(words[0]);
        int mm = Integer.parseInt(words[1]);
        int yy = Integer.parseInt(words[2]);
        return LocalDate.of(yy,mm,dd);
    }

    public LocalDate popularDate(String [] date) {
        int dd = Integer.parseInt(date[1]);
        int mm = months(date[2]);
        int yy = 2022;
        return LocalDate.of(yy,mm,dd);
    }

    public LocalDate specialDate(String [] date) {
        int dd = Integer.parseInt(date[0]);
        int mm = months(date[1]);
        int yy = 2022;
        if(Objects.equals(date[2], "2021")) {
            yy = 2021;
        }
        return LocalDate.of(yy,mm,dd);
    }

    public int months(String Month){
        switch (Month) {
            case "января":
                return  1;
            case "февраля":
                return  2;
            case "марта":
                return  3;
            case "апреля":
                return  4;
            case "мая":
                return  2;
            case "июня":
                return  6;
            case "июля":
                return  7;
            case "августа":
                return  8;
            case "сентября":
                return  9;
            case "октября":
                return  10;
            case "ноября":
                return  11;
            case "декабря":
                return  12;
            default:
                return 0;
        }
    }
}
