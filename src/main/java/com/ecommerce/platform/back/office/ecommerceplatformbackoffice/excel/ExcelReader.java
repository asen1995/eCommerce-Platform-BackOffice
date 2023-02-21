package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.excel;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.ProductDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExcelReader {
    public static List<ProductDto> extractProductDtos(MultipartFile file) throws IOException {
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

            ProductDto productDto = new ProductDto(productName, category, description, quantity);
            products.add(productDto);
        }
        return products;
    }
}
