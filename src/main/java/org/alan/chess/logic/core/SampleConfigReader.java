package org.alan.chess.logic.core;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ģ�����ö�ȡ��
 * <p>
 * Created on 2017/7/10.
 *
 * @author Alan
 * @since 1.0
 */
public class SampleConfigReader {

    public static String SAMPLE_PKG = "org.zhmj.%s.sample.%s";

    public static Logger log = LoggerFactory.getLogger(SampleConfigReader.class);


    public static boolean readWorkbook(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            log.warn("�Ҳ���ָ�����ļ���filename={}", filename);
            return false;
        }
        return readWorkbook(file);
    }

    /**
     * ��ȡ excel �ļ�
     *
     * @param file
     * @return
     */
    public static boolean readWorkbook(File file) {
        //String pkg = from.getParent().replace(rootPath, "").replace(File.separator, ".");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            int size = workbook.getNumberOfSheets();
            for (int i = 0; i < size; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                readSheet(file, sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("", e);
            return false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    public static void readSheet(File file, XSSFSheet sheet) {
        //��ǩ��
        String sheetName = sheet.getSheetName();
        //��ȡ����
        int num = sheet.getLastRowNum();
        if (num <= 4) {
            log.warn("�ļ�{}����{}��ҳ�������������㣬�޷�ӳ�����...", file.getName(), sheetName);
            return;
        }
        //�ڶ���Ϊ����
        XSSFRow nameRow = sheet.getRow(2);

        int lastCellNum = nameRow.getLastCellNum();
        List<String> cellNames = readRow(nameRow, lastCellNum);
        List<List<String>> cellValuesList = new ArrayList<>();
        for (int i = 4; i <= num; i++) {
            XSSFRow valueRow = sheet.getRow(i);
            try {
                List<String> values = readRow(valueRow, lastCellNum);
                if (!values.isEmpty()) {
                    cellValuesList.add(values);
                }
            } catch (Exception e) {
                log.warn("�ж�ȡ����fileName={},sheetName={},row={},cell={}", file.getName(), sheetName, i, e.getMessage(), e);
            }

        }
        String fileName = file.getName();
        String pkg = fileName.substring(0, fileName.indexOf('.'));
        String className = String.format(SAMPLE_PKG, pkg, sheetName);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.warn("", e);
            return;
        }
        List<Sample> samples = SampleReflectHelper.resolveSample(clazz, cellNames, cellValuesList);
        try {
            Field field = clazz.getField("factory");
            SampleFactory<Sample> factory = (SampleFactory<Sample>) field.get(null);
            factory.reloadSamples(samples);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("", e);
        }
    }

    public static List<String> readRow(XSSFRow row, int lastCellNum) {
        List<String> values = new ArrayList<>();
        for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
            try {
                XSSFCell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String name = getCellValue(cell);
                values.add(name);
            } catch (Exception e) {
                log.warn("", e);
                throw new RuntimeException("" + cellNum);
            }
        }
        return values;
    }

    public static String getCellValue(Cell cell) {
        String value;
        switch (cell.getCellTypeEnum()) {
            case STRING: // �ַ���
                value = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case NUMERIC: // ��ʽ
                value = cell.getNumericCellValue() + "";
                break;
            case BLANK: // ��ֵ
            default:
                value = "";
                break;
        }
        return value;
    }

}
