// disabled
//package wad;
//
//import org.junit.Assert;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//
//
//
//public class ServletTest {
//    private String port;
//    private WebDriver driver;
//    private String baseUrl;
//    
//    @Before
//    public void setUp() throws Exception {
//        driver = new HtmlUnitDriver();
//        port = System.getProperty("jetty.port", "8080");
//        baseUrl = "http://localhost:" + port;
//    }
//    
//
//    @Test
//    public void doesServletStartCorrectly() {
//        
//            driver.get(baseUrl);
//            String source = driver.getPageSource();
//            Assert.assertTrue(source.contains("Flavour of the Now"));
//    }
//    
//    @Test
//    public void doesServletStartCorrectly2() {
//        
//            driver.get(baseUrl + "/history");
//            String source = driver.getPageSource();
//            Assert.assertTrue(source.contains("Flavour of the Month"));
//    }
//    
//    @Test
//    public void doesServletStartCorrectly3() {
//        
//            driver.get(baseUrl + "/about");
//            String source = driver.getPageSource();
//            Assert.assertTrue(source.contains("Today I will choose..."));
//    }
//    
//}
