package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import utils.properties.ConfigPropertyManager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.time.Duration;

public class AppiumServerManager {
    static AppiumDriverLocalService service;
    static int port;
    static Logger log = (Logger) LogManager.getLogger(AppiumServerManager.class);

    public static void Start() {
        int count = 0;
        boolean isStarted = false;
        if (service == null || !service.isRunning()) {
            while (!isStarted && count < 3) {
                try {
                    getInstance().start();
                    isStarted = true;
                    count = 3;
                    //System.out.println("APPIUM SERVER IS STARTED");
                    log.info("APPIUM SERVER IS STARTED");
                } catch (Exception e) {
                    //System.out.println("APPIUM SERVER IS NOT STARTED " + e.getMessage());
                    log.info("APPIUM SERVER IS NOT STARTED ON " + (count + 1) + " TRY");
                    if (count == 2) {
                        log.error("APPIUM SERVER IS NOT STARTED ON " + (count + 1) + " TRY");
                    }
                }
                count++;
            }
        }

    }

    public static void Stop() {
        String[] command = {"/usr/bin/killall", "-KILL", "node"};
        try {
            Runtime.getRuntime().exec(command);
            //System.out.println("APPIUM SERVER IS STOPPED");
            log.info("APPIUM SERVER IS STOPPED");
        } catch (IOException e) {
            //System.out.println("APPIUM SERVER IS NOT STOPPED" + e.getMessage());
            log.error("APPIUM SERVER IS NOT STOPPED" + e.getMessage());
        }
    }

    public static AppiumDriverLocalService getInstance() {
        port = getPort();

        String dirPath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "appium_server_logs";

        File appiumServerLogsDir = new File(dirPath);

            if (!appiumServerLogsDir.exists()) {
                System.out.println("Directory for server logs doesn't exist ");
                appiumServerLogsDir.mkdirs();
            }


        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .usingPort(port)
                .withArgument(GeneralServerFlag.BASEPATH, "/")
                .withArgument(GeneralServerFlag.USE_PLUGINS, "execute-driver")
                .withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload")
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withTimeout(Duration.ofSeconds(60L))
                // Set LOG_LEVEL
                // Log level. Value must be one of: info, info:debug, info:info, info:warn, info:error, warn, warn:debug, warn:info, warn:warn, warn:error, error, error:debug, error:info, error:warn, error:error, debug, debug:debug, debug:info, debug:warn, debug:error)
                // it can be set in the config file (in this case on the next row you should use getProperty method to take it form the config) or here  as "error"
                .withArgument(GeneralServerFlag.LOG_LEVEL, ConfigPropertyManager.getAppiumLogLevel())
                // Set log file
                .withLogFile(new File(appiumServerLogsDir + File.separator + "appiumLogs_"
                        + TestUtils.getCurrentDateAndTime() + ".txt"))
                .withArgument(GeneralServerFlag.LOG_TIMESTAMP);

        //AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        service = AppiumDriverLocalService.buildService(builder);

        // If we don't like to see Appium log displayed in the console
        // Logs related
        if (!ConfigPropertyManager.getShowAppiumLogInTheConsole()) {
        //boolean showAppiumLogInTheConsole=true;
        //if (!showAppiumLogInTheConsole) {
            service.clearOutPutStreams();
        }

        return service;
    }

    public static int getPort() {
        int port = 4723;
        try {
            ServerSocket socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }

    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
