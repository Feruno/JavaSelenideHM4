package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDelivery {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
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
    }

    @Test
    void appChoosingDateWeekAhead() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();

        Integer nextWeek = Integer.valueOf($$("[data-day]").first().getText());
        nextWeek += 7;

        if(nextWeek >= 29 || nextWeek >= 30 || nextWeek >= 31 ){
            Integer lastDay = Integer.valueOf($$("[data-day]").last().getText());
            String res = String.valueOf(nextWeek - lastDay);
            $("[class='calendar__arrow calendar__arrow_direction_right']").click();
            $(byText(res)).click();
        }else {
            String nextWeekRes = nextWeek.toString();
            $(byText(nextWeekRes)).click();
        }

        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void appChoosingDate3WeekAhead() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ка");
        $(byText("Казань")).click();
        form.$("[class='icon-button__text']").click();

        Integer nextWeek = Integer.valueOf($$("[data-day]").first().getText());
        nextWeek += 21;

        if(nextWeek >= 29 || nextWeek >= 30 || nextWeek >= 31 ){
            Integer lastDay = Integer.valueOf($$("[data-day]").last().getText());
            String res = String.valueOf(nextWeek - lastDay);
            $("[class='calendar__arrow calendar__arrow_direction_right']").click();
            $(byText(res)).click();
        }else {
            String nextWeekRes = nextWeek.toString();
            $(byText(nextWeekRes)).click();
        }

        $("span [name='name']").setValue("Алекс");
        form.$("span [name='phone']").setValue("+79012345678");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
