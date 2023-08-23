package com.kyattonippu;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@DisplayName("Тесты на чтение и проверку содержимого файлов из zip-архива")
public class ZipTest {

    private final ClassLoader cl = ZipTest.class.getClassLoader();

    @Test
    @DisplayName("Парсинг PDF файла из zip-архива")
    void zipPdfTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ZipTest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("pdf-example")) {
                    PDF pdf = new PDF(zs);
                    Assertions.assertEquals(30, (pdf.numberOfPages));
                    Assertions.assertTrue((pdf.text).contains("Lorem ipsum"));
                }
            }
        }
    }

    @Test
    @DisplayName("Парсинг CSV файла из zip-архива")
    void zipCsvTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ZipTest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("csv-example")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"5;Nereida;Magwood;Female;United States;58;16/08/2016;2468"}, content.get(5));
                }
            }
        }
    }

    @Test
    @DisplayName("Парсинг XLS файла из zip-архива")
    void zipXlsTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ZipTest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("xls-example")) {
                    XLS xls = new XLS(zs);
                    Assertions.assertTrue(
                            xls.excel.getSheetAt(0).getRow(5).getCell(2).getStringCellValue().contains("Magwood")
                    );
                }
            }
        }
    }
}
