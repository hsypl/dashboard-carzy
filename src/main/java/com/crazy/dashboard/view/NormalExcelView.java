package com.crazy.dashboard.view;

import com.crazy.code.poi.HSSFUtils;
import com.crazy.code.poi.SheetColumnMeta;
import com.crazy.code.poi.SheetMeta;
import org.apache.poi.ss.usermodel.*;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 通用Excel文件视图具体实现类
 *  借助 SpEL 自动将java对象装配到excel文件中。
 * Created by wanghongwei on 12/01/15.
 */
public class NormalExcelView extends AbstractXlsView {

    @Override
    public void buildExcelDocument(
            Map<String, Object> map, Workbook workbook,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        SheetMeta sheetMeta = (SheetMeta)map.get("sheetMeta");
        List data = (List)map.get("data");
        Sheet sheet = workbook.createSheet(sheetMeta.getSheetName());
        sheet.setDefaultColumnWidth(12);

        List<SheetColumnMeta> columnMetaList = sheetMeta.getColumns();
        CellStyle headCellStyle = HSSFUtils.createHeadStyle(workbook);
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < columnMetaList.size(); i++) {
            SheetColumnMeta columnMeta = columnMetaList.get(i);
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(columnMeta.getName());
        }
        ExpressionParser parser = new SpelExpressionParser();
        CellStyle dataCellStyle = HSSFUtils.createRightStyle(workbook);
        for (int i = 0; i < data.size(); i++) {
            Object rowObj = data.get(i);
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < columnMetaList.size(); j++) {
                SheetColumnMeta columnMeta = columnMetaList.get(j);
                Expression exp = parser.parseExpression(columnMeta.getProperty());
                Cell dataCell = dataRow.createCell(j);
                dataCell.setCellStyle(dataCellStyle);
                dataCell.setCellValue(exp.getValue(rowObj).toString());
            }
        }
    }
}
