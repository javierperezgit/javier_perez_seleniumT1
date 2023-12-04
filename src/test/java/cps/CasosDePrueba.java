package cps;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class CasosDePrueba {

    public static String corregirEncoding(String textoIncorrecto) {
        // Convertir la cadena incorrecta de la codificación ISO-8859-1 a UTF-8
        byte[] bytes = textoIncorrecto.getBytes(StandardCharsets.ISO_8859_1);
        String textoCorregido = new String(bytes, StandardCharsets.UTF_8);

        return textoCorregido;
    }
    //Atributos
    WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    public void preCondiciones(){
        String rutaProyecto = System.getProperty("user.dir");

        String rutaDriver = rutaProyecto + "\\src\\test\\resources\\drivers\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", rutaDriver);

        driver = new ChromeDriver();

        js = (JavascriptExecutor) driver;

        driver.get("https://apimarket.bci.cl/es/home");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.manage().window().maximize();
    }

    @AfterEach
    public void postCondiciones(){
        driver.close();
    }

    @Test
    public void CP001_Login_fallido()throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        By localizadorBtnRegistrarse = By.xpath("//a[contains(text(),'Iniciar Sesi')]");
        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrarse);
        btnRegistrarse.click();
        driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("Perrito");
        driver.findElement(By.xpath("//input[@id='edit-pass']")).sendKeys("1234");
        driver.findElement(By.id("edit-submit")).click();
        String resultadoMSG = driver.findElement(By.xpath("//*[@id=\"page\"]/div[1]/aside/div[2]/div")).getText();
        String[] salida = resultadoMSG.split("\\n");
        String resultadoEsperado = corregirEncoding("Usuario o contraseña no reconocidos. ¿Olvidaste tu contraseña?");
        Assertions.assertEquals(salida[2],resultadoEsperado);
}

    @Test
    public void CP002_Login_correcto()throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        By localizadorBtnLogearse = By.xpath("//a[contains(text(),'Iniciar Sesi')]");
        WebElement btnLogearse = driver.findElement(localizadorBtnLogearse);
        btnLogearse.click();
        driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("roscoeibm");
        driver.findElement(By.xpath("//input[@id='edit-pass']")).sendKeys("Selenium2023%");
        driver.findElement(By.id("edit-submit")).click();
        String validaIngreso = driver.findElement(By.xpath("//li/a[@data-drupal-link-system-path='user/logout']")).getText();
        String resultadoEsperado = corregirEncoding("Cerrar sesión");
        Assertions.assertEquals(validaIngreso.toLowerCase(),resultadoEsperado.toLowerCase());
    }

    @Test
    public void CP003_ReglaCrearAplicacion(){
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        By localizadorBtnLogearse = By.xpath("//a[contains(text(),'Iniciar Sesi')]");
        WebElement btnLogearse = driver.findElement(localizadorBtnLogearse);
        btnLogearse.click();

        driver.findElement(By.xpath("//input[@id='edit-name']")).clear();
        driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("roscoeibm");
        driver.findElement(By.xpath("//input[@id='edit-pass']")).clear();
        driver.findElement(By.xpath("//input[@id='edit-pass']")).sendKeys("Selenium2023%");
        driver.findElement(By.id("edit-submit")).click();

        By localizadorBtnCrearApp = By.xpath("//a[@class='btn btn-secondary']");
        WebElement btnCrearApp = driver.findElement(localizadorBtnCrearApp);
        btnCrearApp.click();
        String Msj1= driver.findElement(By.xpath("//p[contains(text(), 'Para consumir nuestros API Products en ambiente Productivo, es decir, recibir data real, primero debes:')]")).getText();
        Assertions.assertEquals(Msj1,"Para consumir nuestros API Products en ambiente Productivo, es decir, recibir data real, primero debes:");
    }

    @Test
    public void CP004_ConsultarDocApiSinLogin()throws InterruptedException{
        By localizadorCatalogoApis = By.xpath("//a[@class='nav-link']");
        WebElement btnCataApis = driver.findElement(localizadorCatalogoApis);
        btnCataApis.click();
        driver.findElement(By.id("edit-label")).sendKeys("Widget Hipotecario");
        driver.findElement(By.xpath("//button[contains(@class,'button js-form-submit')]")).click();
        js.executeScript("window.scrollBy(0,1000)");
        driver.findElement(By.xpath("//a[@class='btn-card button-product']")).click();
        driver.findElement(By.xpath("//a[@class='btn hipotecario-section-header__documentacion-btn']")).click();
        Thread.sleep(2000);
        String Msj1= driver.findElement(By.xpath("//div/p[@class='mb-5 col-md-12 texto-permisos mx-auto']")).getText();
        String Msj2= corregirEncoding("Para acceder a la documentación de cada API, debes registrarte.");
        Assertions.assertEquals(Msj1,Msj2);
    }

    @Test
    public void CP005_ConsultarDocApiConLogin(){
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        By localizadorBtnLogearse = By.xpath("//a[contains(text(),'Iniciar Sesi')]");
        WebElement btnLogearse = driver.findElement(localizadorBtnLogearse);
        btnLogearse.click();

        driver.findElement(By.xpath("//input[@id='edit-name']")).clear();
        driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("roscoeibm");
        driver.findElement(By.xpath("//input[@id='edit-pass']")).clear();
        driver.findElement(By.xpath("//input[@id='edit-pass']")).sendKeys("Selenium2023%");
        driver.findElement(By.id("edit-submit")).click();

        By localizadorCatalogoApis = By.xpath("//a[@class='nav-link']");
        WebElement btnCataApis = driver.findElement(localizadorCatalogoApis);
        btnCataApis.click();
        driver.findElement(By.id("edit-label")).sendKeys("Widget Hipotecario");
        driver.findElement(By.xpath("//button[contains(@class,'button js-form-submit')]")).click();
        js.executeScript("window.scrollBy(0,1000)");
        driver.findElement(By.xpath("//a[@class='btn-card button-product']")).click();
        driver.findElement(By.xpath("//a[@class='btn hipotecario-section-header__documentacion-btn']")).click();
        String Mensaje1 = driver.findElement(By.xpath("(//p[@class='doc-widget-hipotecario__subtitle'])[2]")).getText();
        String Mensaje2 = corregirEncoding("Este API Product les da la posibilidad a todos tus clientes de simular y recibir en línea una aprobación comercial o pre-aprobación del crédito hipotecario Bci.");
        Assertions.assertEquals(Mensaje1,Mensaje2);
    }


}
