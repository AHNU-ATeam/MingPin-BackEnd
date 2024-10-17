package com.chuanglian.mingpin.entity.attendance.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelUtil {

    // 根据用户对象生成 Excel
    public static <T> ByteArrayOutputStream createExcel(List<T> dataList, Class<T> clazz, String sheetName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建表头
        Row headerRow = sheet.createRow(0);
        int headerIndex = 0;

        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                Cell cell = headerRow.createCell(headerIndex++);
                cell.setCellValue(excelColumn.value());  // 使用注解中的列名
            }
        }

        // 填充数据
        int rowNum = 1;
        for (T item : dataList) {
            Row row = sheet.createRow(rowNum++);
            int cellIndex = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    try {
                        field.setAccessible(true);  // 确保可以访问私有字段
                        Object value = field.get(item);
                        row.createCell(cellIndex++).setCellValue(value != null ? value.toString() : "");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 将 Excel 内容写入输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}
