**First steps to set the project**

**What are we automating?**

Sauce Lab's demo mobile app
GitHub: https://github.com/saucelabs/sample-app-mobile
Releases (Latest APKs and IPAs): https://github.com/saucelabs/sample-app-mobile/releases


This is a project created based on lectures 1-140 from the course https://www.udemy.com/course/the-complete-appium-course-for-ios-and-android/
* Create project 
* Add to git ignore .idea/ 
* Add java client dependency 

**Important:** _No need for more dependencies (as Selenium) if the class for driver session creation is in /src/test/java
If the code is not in the test package you need to add and selenium dependencies_

**Important:**  You have 2 ways to create driver - using DesiredCapabilities or using Options
                For Android app if you like to start app , you can use capability to start it
                There is a second option to call a specific package and specific activity

If you like to open specific app package and app activity or you are in case when Appium can't retrieve appPackage and appActivity
from manifest.xml , you need to appPackage and appActivity to desired capabilities

How to take App package  and App activity

Run app - it needs to be on focus
Then execute in the terminal

adb shell dumpsys window | grep -E mCurrentFocus

Then use them in 


The result is something like this:
mCurrentFocus=Window{55a399e u0 io.appium.android.apis/io.appium.android.apis.ApiDemos}

Here: app package is on the beggining (io.appium.android.apis)
app activity is after it.   (o.appium.android.apis.ApiDemos)

**Native locator strategies**

Android -  https://developer.android.com/reference/androidx/test/uiautomator/BySelector
iOS - https://learnautomation.medium.com/improving-appium-ios-automation-performance-by-replacing-xpaths-1f5d0217d9a , https://appium.github.io/appium-xcuitest-driver/4.19/ios-predicate/

**Mobile gestures automation**
Android - https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
iOS - https://appium.readthedocs.io/en/latest/en/writing-running-appium/ios/ios-xctest-mobile-gestures/


**Interacting with apps** - install, activate, remove, put in background, uninstall, take state
iOS -  https://appium.readthedocs.io/en/latest/en/writing-running-appium/ios/ios-xctest-mobile-apps-management/
       https://www.udemy.com/course/the-complete-appium-course-for-ios-and-android/ lecture 118
Android - https://github.com/appium/appium-uiautomator2-driver (Platform-Specific Extensions)
          https://www.udemy.com/course/the-complete-appium-course-for-ios-and-android/ lecture 121

**Log4j2 logging**
Implementation:
Add log4j dependency ( check POM.xml here)
Create log4j2.properties or log4j2.xml file under src/main/resources folder. This file defines all the required configurations like loggers, appenders, layouts, etc. in the form of key value pair.
(check file in the current repo)

Add
static  Logger log = (Logger) LogManager.getLogger(LoginTests.class);


where class name is passed (in the row above the name of the class in LoginTests)

In a different class (in Example.class) the code will looks like :
static Logger log = Logger.getLogger(ExampleClass.class.getName());

Wit this you are ready to use logger in the class where you initialized 


Links:
Documentation: https://logging.apache.org/log4j/2.x/
Configurations: https://logging.apache.org/log4j/2.x/manual/configuration.html 
Pattern Layouts: https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/ PatternLayout.html
Write to separate logs: https://logging.apache.org/log4j/2.0/ faq.html#separate_log_files

**Extent reports**

- In pom.xml add dependencies for Extentreports

<!-- https://mvnrepository.com/artifact/com.aventstack/extentreports -->
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.1</version>
</dependency>



- Create testng.xml file to point your tests
- Add in POM.xml the following code to be able to run tests through command line

<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
        </plugins>
    </build>


- In src/test/java create a package extentreports
- In the package src/test/java/extentreports create a class ExtentManager

- In src/test/java create a package testlisteners
- In the package src/test/java/testlisteners create a class TestListener that i
  mplements implements ITestListener
- In testng.xml add

  <listeners>
  <listener class-name ="testlisteners.TestListener"></listener>
  </listeners>


Documentation
https://www.extentreports.com/

**DeepLinks** 

Deep link is a technology that launches an app and opens a specific page once the user clicks a URL on a web page or another app.

To implement deep links use the DeepLinks.java class from utils/deepLinks