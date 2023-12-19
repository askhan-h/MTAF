package base.utils;

import base.utils.log4j.logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmulatorManager {

    public static void startEmulator(String emulatorName) {
        if (!isEmulatorRunning(emulatorName)) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c");
                processBuilder.environment().put("EMULATOR_NAME", emulatorName);
                processBuilder.command(System.getProperty("user.dir") + "/src/test/java/base/utils/start_emulator.sh");
                processBuilder.start();
                Thread.sleep(15000);
                logs.info(emulatorName + " Emulator is started.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            logs.info("Emulator '" + emulatorName + "' is already running.");
        }
    }

    public static boolean isEmulatorRunning(String emulatorName) {
        try {
            Process process = Runtime.getRuntime().exec("ps -A");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(emulatorName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void killEmulator(String emulatorName) {
        String killCommand = "pkill -f " + emulatorName;
        try {
            Runtime.getRuntime().exec(killCommand);
            logs.info("Emulator '" + emulatorName + "' killed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
