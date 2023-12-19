package android.testcases;

import android.pageObjects.AndroidPageLogin;
import base.appiumController.AndroidAppiumFactory;
import org.testng.annotations.Test;

public class AndroidTestcaseLogin extends AndroidAppiumFactory {

    AndroidPageLogin pageLogin;

    @Test(description = "Login to app")
    public void userLogin() {
        pageLogin = new AndroidPageLogin(driver);

        pageLogin.sampleMethod();

        //Test Steps

    }
}
