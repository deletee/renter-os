package com.delete.renter.core;


import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StatTest {

    @Test
    public void userCanLoginByUsername() {
        open("/login");
        $(By.name("username")).setValue("johny");
        $("#submit").click();
        $(".success-message").shouldHave(text("Hello, Johny!"));
    }
}
