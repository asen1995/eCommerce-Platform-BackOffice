package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.excel;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.ProductDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileNotContainProductsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ExcelReaderTest {

    @Test
    public void testLoadingExcelFileAndExtractingProducts() throws Exception {

        String filePath = "templates/back-office-products-template.xlsx";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filePath, inputStream);

        List<ProductDto> productDtos = ExcelReader.extractProductDtos(mockMultipartFile);
        assertNotNull(productDtos);
        assertEquals(8, productDtos.size());
    }

    @Test(expected = FileNotContainProductsException.class)
    public void testLoadingExcelFileWithNoProducts() throws Exception {

        String filePath = "templates/back-office-no-products-template.xlsx";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filePath, inputStream);

        ExcelReader.extractProductDtos(mockMultipartFile);
    }
}
