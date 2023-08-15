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
    void appSuccessfulPath() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Москва");
        form.$("[class='icon-button__text']").click();
        $$("[data-day]").first().click();
        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + $("[data-day]").getText()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void appSelectingCityList() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();
        $$("[data-day]").first().click();
        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + $("[data-day]").getText()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void appChoosingDateWeekAhead() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();

        Integer nextWeek = Integer.valueOf(genDate(7, "d"));

        if (nextWeek >= 31 || nextWeek >= 30 || nextWeek >= 29) {
            Integer lastDay = Integer.valueOf($$("[data-day]").last().getText());
            Integer res = null;

            if (nextWeek == lastDay) {
                res = lastDay;
            } else {
                res = nextWeek - lastDay;
                $("[class='calendar__arrow calendar__arrow_direction_right']").click();
            }

            $(byText(String.valueOf(res))).click();

        } else {
            String nextWeekRes = nextWeek.toString();
            $(byText(nextWeekRes)).click();
        }
        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + $("[data-day]").getText()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void appChoosingDateWeekAheadАlternative() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();

        if ($$("[data-day]").last().getText().equals(genDate(7, "d"))) {
            $("[class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $$("[data-day]").findBy(text(genDate(7, "d"))).click();

        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + $("[data-day]").getText()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
