package com.kyattonippu;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты на файлы")
public class FilesTest {


    @Test
    @DisplayName("Имя файла отображается после загрузки")
    void filenameShouldDisplayedAfterUploadActionFromClasspathTest() {
        Selenide.open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("testimage.jpg");
        $("div.qq-file-info").shouldHave(Condition.text("testimage.jpg"));
        $("span.qq-upload-file-selector").shouldHave(
                Condition.attribute("title", "testimage.jpg")
        );
    }

    @Test
    @DisplayName("Скачивание текстового файла и проверка его содержимого")
    void txtFileDownloadTest() throws IOException {
        Selenide.open("https://filesamples.com/formats/txt");
        File download = $("a[href*='sample1.txt']").download();
        String fileContent = IOUtils.toString(new FileReader(download));

        assertTrue(fileContent.contains("Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
    }

    @Test
    @DisplayName("Скачивание PDF файла и проверка его содержимого")
    void pdfFileDownloadTest() throws IOException {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedPdf = $("a[href='junit-user-guide-5.10.0.pdf']").download();
        PDF content = new PDF(downloadedPdf);

        assertThat(content.text).contains("JUnit 5 User Guide");
        assertEquals(191, content.numberOfPages);
    }

    @Test
    @DisplayName("Скачивание XLS файла и проверка его содержимого")
    void xlsFileDownloadTest() throws IOException {
        Selenide.open("https://nadezhda-nv.ru/docs/price.php");
        File xlsFile = Selenide.$("a[href*='/docs/Price2.xlsx']").download();
        XLS parsedXls = new XLS(xlsFile);
        boolean checkPassed =parsedXls.excel
                .getSheetAt(0)
                .getRow(10)
                .getCell(2)
                .getStringCellValue()
                .contains("Доставка и установка расходного материала");

        assertTrue(checkPassed);
    }

    @Test
    @DisplayName("Парсинг CSV файла")
    void parseCsvFileTest() throws IOException, CsvException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("csv.csv");
             Reader reader = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> strings = csvReader.readAll();

            assertEquals(100, strings.size());
        }
    }
}
