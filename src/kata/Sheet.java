package kata;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Sheet extends XSSFWorkbook {
    private XSSFSheet sheet;
    private int columnIndex = 0;

    public Sheet(Location[] locations) throws IOException {
        this.sheet = this.createSheet("data");
        this.generateInitialColumn();

        // TODO: Iterate though locations
    }

    public void generateColumn(Location location) {
        // set row style for top row
    }

    private void generateInitialColumn() {
        final String[] cellDescriptions = new String[] {
            "",
            "Current Temperature",
            "Current Description",
            "Today's High",
            "Today's Low",
            "Bikeability",
            "Moon Phase",
            "Sunrise Time",
            "Sunset Time",
            "Tomorrow's High",
            "Tomorrow's Low",
            "Tomorrow's Description",
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

        sheet.setColumnWidth(0, width * 256 + 50);
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
    }
}
