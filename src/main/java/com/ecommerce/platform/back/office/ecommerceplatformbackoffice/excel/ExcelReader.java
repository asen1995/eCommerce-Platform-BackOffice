package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.excel;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.ProductDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileNotContainProductsException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExcelReader {

    private ExcelReader() {}

    public static List<ProductDto> extractProductDtos(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next(); // Skip the header row

        List<ProductDto> products = new LinkedList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            String productName = row.getCell(0).getStringCellValue();
            String category = row.getCell(1).getStringCellValue();
            String description = row.getCell(2).getStringCellValue();
            int quantity = (int) row.getCell(3).getNumericCellValue();

            products.add(createProductDto(productName, category, description, quantity));
        }

        if (products.isEmpty()) {
            throw new FileNotContainProductsException("File does not contain products");
        }

        return products;
    }

    private static ProductDto createProductDto(String productName, String category, String description, int quantity) {
        ProductDto productDto = new ProductDto();
        productDto.setName(productName);
        productDto.setCategory(category);
        productDto.setDescription(description);
        productDto.setQuantity(quantity);

        return productDto;
    }
}
