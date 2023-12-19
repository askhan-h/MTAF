package iOS.testcases;

import android.pageObjects.AndroidPageLogin;
import base.appiumController.IOSAppiumFactory;
import iOS.pageObjects.IOSPageLogin;
import org.testng.annotations.Test;

public class IOSTestcaseLogin extends IOSAppiumFactory {

    IOSPageLogin pageLogin;

    @Test(description = "Login to app")
    public void userLogin() {
        pageLogin = new IOSPageLogin(driver);

        pageLogin.sampleMethod();

        //Test Steps

    }
}
