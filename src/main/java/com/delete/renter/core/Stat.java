package com.delete.renter.core;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Stat {
    public void userCanLoginByUsername() {
        open("/login");
        $(By.name("username")).setValue("johny");
        $("#submit").click();
        $(".success-message").shouldHave(text("Hello, Johny!"));
    }
}
