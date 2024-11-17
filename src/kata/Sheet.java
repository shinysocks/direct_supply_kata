package kata;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Sheet with special methods to fill
 * columns based on Location[] data
 */
class Sheet extends XSSFWorkbook {
    private XSSFSheet sheet;
    private int columnIndex = 0;

    public Sheet(Location[] locations) throws IOException {
        this.sheet = this.createSheet("data");
        this.generateInitialColumn();

        for (Location location : locations) {
            if (location.HAS_RESULTS) {
                final XSSFCellStyle style = this.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);

                writeToColumn(location.getData(), style);
            }
        }
    }

    private void generateInitialColumn() {
        final String[] cellDescriptions = new String[] {
            "Location",
            "Current Temperature",
            "Current Description",
            "Today's High",
            "Today's Low",
            "Should I go biking?",
            "Sunrise Time",
            "Sunset Time",
        };

        final XSSFCellStyle style = this.createCellStyle();
        final XSSFFont font = this.createFont();
        font.setBold(true);
        style.setFont(font);

        this.writeToColumn(cellDescriptions, style);
    }

    private void writeToColumn(String[] data, XSSFCellStyle style) {
        int width = 0;
        for (int i = 0; i < data.length; i++) {
            XSSFRow row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
            XSSFCell cell = row.createCell(this.columnIndex);
            String d = data[i];

            if (d.length() > width) {
                width = d.length();
            }

            cell.setCellValue(d);
            cell.setCellStyle(style);
        }

        sheet.setColumnWidth(columnIndex, width * 256 + 50);
        this.columnIndex++;
    }


    /**
     * write this workbook to the specified file
     * @param out
     * @throws IOException
     */
    public void writeToFile(FileOutputStream out) throws IOException {
        this.write(out);
        out.close();
        Log.info("Wrote sheet to 'weather.xlsx'");
    }
}
