package com.miku.minadevelop.modules.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class HttpUtils {
    public static void main(String[] args) {
        // 设置 ChromeDriver 的路径
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        // 创建 WebDriver 实例
        WebDriver driver = new ChromeDriver();
        try {
            // 访问目标网站
            driver.get("https://www.baidu.com");

            // 添加 Cookie
            Cookie cookie = new Cookie("SESSDATA", "82e6f58d%2C1737858552%2Cdf3c0%2A71CjDOC9IDlUZqSbXrUxFTIcTgNGOGKpc2aJa0sXEzEWB0226XbKt1yb9BfFysfuJzyV8SVmVSVzU4dXAzeHJVRTBJTnQ3WVI2YXI2cWRTcFhhdUZJSDFDMndab1E3SUdMNmlXSTc0UHg1amhsT01GNmNJcUFFTnlYbW5STjFaUDZ3ckJWaVI5aDJnIIEC");
            driver.manage().addCookie(cookie);

            // 重新加载页面以应用 Cookie
            driver.get("https://game.bilibili.com/tool/pcr/search?name=%E5%92%96%E5%95%A1%E9%A6%86&ts=1722830087350&nonce=e2b1e739-fa94-4ede-8a3b-feb257d8d099&appkey=f07288b7ef7645c7a3997baf3d208b62&sign=26c529a07ad45699f3775a4cc497b1d3");

            // 等待页面加载完成
            Thread.sleep(5000);

            // 查找目标元素
            WebElement div = driver.findElement(By.xpath("//div[contains(@class, 'num01137')]"));
            if (div != null) {
                System.out.println("Found element: " + div.getText());
            } else {
                System.out.println("Element not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 WebDriver
            driver.quit();
        }
    }
}