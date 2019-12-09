package magic.utils;

import java.io.*;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Excel处理Utils
 * @author : Narcssus
 * @date : 2019/11/27 21:58
 */
public class ExcelUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\test.xlsx";
        if (args.length > 0) {
            filePath = args[0];
        }
        modifyExcelWithThousandMark(filePath);
    }

    /**
     * 修改Excel文件中每个单元格中的数字，添加千分符
     *
     * @param filePath Excel文件路径
     */
    private static void modifyExcelWithThousandMark(String filePath) {
        File file = new File(filePath);
        FileOutputStream out = null;
        try {
            Workbook wb = getWorkbook(file);
            Font font = wb.createFont();
            CellStyle cellStyle = wb.createCellStyle();
            font.setColor(IndexedColors.RED.index);
            cellStyle.setFont(font);
            for (int index = 0; index < wb.getNumberOfSheets(); index++) {
                Sheet sheet = wb.getSheetAt(index);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (CellType.STRING.equals(cell.getCellType())) {
                            String a = cell.getStringCellValue();
                            String b = MyStringUtils.addThousandMark(a);
                            if (!a.equals(b)) {
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(b);
                            }
                            continue;
                        }
                        if (CellType.NUMERIC.equals(cell.getCellType())) {
                            String a = String.valueOf(cell.getNumericCellValue());
                            if (a.contains("E")) {
                                String[] tmp2 = a.split("E");
                                BigDecimal aa = new BigDecimal(tmp2[0]);
                                int bb = Integer.parseInt(tmp2[1]);
                                a = aa.multiply(new BigDecimal(10).pow(bb)).stripTrailingZeros().toPlainString();
                            }
                            String b = MyStringUtils.addThousandMark(a);
                            if (!a.equals(b)) {
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(b);
                            }
                        }
                    }
                }
            }
            out = new FileOutputStream(file);
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断Excel的版本,获取Workbook
     */
    private static Workbook getWorkbook(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


}
