package kata;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Sheet extends XSSFWorkbook {
    private XSSFSheet sheet;
    private final FileOutputStream out = new FileOutputStream("weather.xlsx");

    public Sheet(String name) throws IOException {
        sheet = this.createSheet("data");
        sheet.createRow(0).createCell(0);
        sheet.getRow(0).getCell(0).setCellValue(name);
        this.write(out);
        out.close();
        this.close();
    }
}
