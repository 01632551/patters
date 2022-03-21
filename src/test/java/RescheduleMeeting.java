import com.codeborne.selenide.Configuration;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RescheduleMeeting {
    private Faker faker;

    @BeforeEach
    void setUpAll(){
        faker = new Faker(new Locale("ru"));
    }

    String generateDate(int days) {

        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }

    // TEST 1: DATE ON THE BOARD OF ALLOWABLE VALUE (+3 days after delivery date)
    @Test
    public void shouldLoadFormV1() {
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();

        int days = 3;

        open("http://localhost:7777/");
        $("[data-test-id=city] input").setValue("Майкоп");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").doubleClick().setValue(generateDate(days));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.ownText(generateDate(days)));
    }

    // TEST 2: A LONG TIME AFTER DATE OF DELIVERY (+ one year after delivery date)
    @Test
    public void shouldLoadFormV2() {
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();

        int days = 365;

        open("http://localhost:7777/");
        $("[data-test-id=city] input").setValue("Улан-Удэ");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").doubleClick().setValue(generateDate(days));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.ownText(generateDate(days)));
    }

    // TEST 3: AVERAGE TIME AFTER A DATE OF DELIVERY (+ 2 month after delivery date)
    @Test
    public void shouldLoadFormV3() {
        Configuration.holdBrowserOpen = true;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.phoneNumber().phoneNumber();

        int days = 61;

        open("http://localhost:7777/");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").doubleClick().setValue(generateDate(days));
        $("[data-test-id=name] input").setValue(firstName + lastName);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.ownText(generateDate(days)));
    }
}
