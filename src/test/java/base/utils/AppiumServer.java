package base.utils;

import base.utils.log4j.logs;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

public class AppiumServer {
    static AppiumDriverLocalService server;

    public static void setServerInstance() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();

        builder
//                .withAppiumJS(new File(System.getProperty("user.home") + "/.appium/node_modules/appium/build/lib/main.js"))
//                .usingDriverExecutable(new File("/opt/homebrew/bin/node"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(GeneralServerFlag.USE_PLUGINS, "images")
                .withLogFile(new File("src/main/resources/logs/appium.log"))
                .withIPAddress("0.0.0.0");

        server = AppiumDriverLocalService.buildService(builder);

    }

    public static AppiumDriverLocalService getServerInstance() {
        if (server == null) {
            setServerInstance();
        }
        return server;
    }

    public static void startServer() {
        getServerInstance().start();
        logs.info("Appium Server Started");
        logs.info("Appium Server URL : " + server.getUrl());
        logs.info("Is Appium Server Running : " + String.valueOf(server.isRunning()).toUpperCase());

    }

    public static void stopServer() {
        if (server != null) {
            getServerInstance().stop();
            logs.info("Appium Server Stopped");
        }

    }
}
