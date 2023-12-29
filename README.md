# Mobile Test Automation Framework (MTAF) ReadMe

Welcome to the Mobile Test Automation Framework (MTAF) repository! This framework is designed to facilitate automated <u>
mobile</u> testing for your projects, utilizing various technologies and design patterns. Below, you'll find information
about the key components and technologies used in the framework.


## Features

MTAF is built with the following technologies and design patterns:

1. **Page Object Model (POM) Design Pattern**: MTAF follows the POM design pattern, which promotes the separation of
   test scripts and page-specific actions. This enhances maintainability and reusability by encapsulating the
   functionality of each page in dedicated Page Object classes.

2. **Appium 2.x**: Appium is utilized as a cross-platform mobile automation tool. With Appium 2.x, you can automate
   mobile applications on both Android and iOS platforms using a single codebase.

3. **OpenJDK 11**: MTAF is compatible with OpenJDK 11, ensuring that the framework can be used with a wide range of Java
   environments.

4. **TestNG**: TestNG is used as the testing framework. It offers powerful features for test management, parallel
   execution, and reporting, making it an ideal choice for organizing and running automated tests.

5. **Maven**: The project is managed using Maven, a popular build and dependency management tool. Maven simplifies
   project configuration, handles dependencies, and automates the build process.

6. **UiAutomator2**: UiAutomator2 is used for automating Android applications. It provides a robust API to interact with
   the user interface elements of Android apps, allowing for efficient test automation.

7. **XCUITest**: XCUITest is integrated for iOS application automation. This framework allows you to interact with and
   test iOS app interfaces effectively.
8. **Extent Report**: A Java-based reporting library often used for generating interactive and detailed reports in software testing. It's commonly used with test automation frameworks like Selenium or Appium.
9. **Log4j**: A widely used Java-based logging library that helps developers manage and output log messages in their applications.
10. **Image Comparison-Occurrences Lookup** : Performs images matching by template to find possible occurrence of the partial image in the full image
11. **Appium Server**: Programmatically startup the Appium server.
12. **Android Emulator**: Programmatically startup the emulator with the provided emulator name in properties file.

![a92f621e-ace0-4b67-8650-49b05c214f43](https://github.com/askhan-h/MTAF/assets/153196569/5e69bce1-a740-48ac-8e4a-bc6d2052b5a5)

## Getting Started

Follow these steps to get started with MTAF:

1. **Clone the Repository**: Clone this repository to your local machine using the following command:
   ```
   git clone git@github.com:askhan-h/MTAF.git
   ```

2. **Install Appium 2.x**: Follow the below links to install appium 2.0 and other dependencies
    1. [Appium 2.x](https://bitrise.io/blog/post/getting-started-with-appium-2-0-your-beginners-guide)
    2. [Appium Inspector](https://github.com/appium/appium-inspector)
    3. [Appium Doctor](https://github.com/appium/appium/tree/master/packages/doctor)
    4. [uiautomator2-driver](https://github.com/appium/appium-uiautomator2-driver)
    5. [xcuitest-driver](https://github.com/appium/appium-xcuitest-driver)
3. **Install Dependencies**: Ensure you
   have [OpenJDK 11](https://www.openlogic.com/openjdk-downloads?field_java_parent_version_target_id=406&field_operating_system_target_id=All&field_architecture_target_id=All&field_java_package_target_id=All)
   and Maven installed on your system. Install any additional dependencies mentioned in the `pom.xml` file using Maven.
4. **Configure TestNG**: Modify the TestNG XML configuration file(s) to specify the tests you want to run, test suites,
   parallel execution settings, and more.

5. **Implement Test Cases**: Create test scripts using TestNG annotations in conjunction with the Page Object classes
   provided by the framework. Follow the POM design pattern to keep your test scripts organized and maintainable.

6. **Install Image Driver plugin**: You need to install the images plugin to compare images

7. **Run Tests**: Execute your tests using Maven or your preferred IDE. TestNG's reporting features will provide you
   with detailed test results.

## Directory Structure

The framework repository is organized as follows:

```
- src
  - main
    - resources
      - apps
        - Android
        - iOS
      - log4j2.properties
  -test
      -java
         -android
            -androidDeviceDetails.properties
            -pageObjects
            -testCases
            -testsuites
         -iOS
            -iOSDeviceDetails.properties
            -pageObjects
            -testCases
            -testsuites            
         -base
            -appiumController
               -AndroidAppiumFactory.java
               -IOSAppiumFactory.java
            -constants
               -AndroidConstants.java
               -IOSConstants.java
            -pageBase
               -AndroidPageBase
               -IOSPageBase
               -MainPageBase
            -utils
               -log4j
                  -logs.java
               -testng
                  -AndroidTestngListners.java
                  -IOSTestngListners.java
               -AppiumServer.java
               -ExtentReporter.java
               
                  
- pom.xml               // Maven project configuration
- README.md             // Framework documentation
- .gitignore
```

Happy testing with Mobile Test Automation Framework!
