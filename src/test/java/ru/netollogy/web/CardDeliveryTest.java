package ru.netollogy.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:7777");
    }

    private String generateDate (int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

        @Test
        void shouldCardDelivery() {
        String plannigDate = generateDate(3,"dd.MM.yyyy");

            $("[data-test-id='city'] input").setValue("Казань");
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id='date'] input").setValue(plannigDate);
            $("[data-test-id='name'] input").setValue("Петров Иван");
            $("[data-test-id='phone'] input").setValue("+79201117788");
            $("[data-test-id='agreement']").click();
            $$("button").findBy(Condition.text("Забронировать")).click();
            $("[data-test-id='notification']").shouldBe(Condition.visible,
                    Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + plannigDate));
    }

}
