package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDelivery {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String genDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void appSuccessfulPathManually() {
        String date = genDate(4, "dd.MM.yyyy");
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void appSelectingCityList() {
        String date = genDate(4, "dd.MM.yyyy");

        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);

        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void appChoosingDateWeekAheadАlternative() {
        String date = genDate(7, "dd.MM.yyyy");

        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();

        if ( !genDate(7, "MM").equals(genDate(3, "MM")) ) {
            $("[class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);

        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
