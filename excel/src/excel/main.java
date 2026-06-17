package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class main {
	public static void main(String[] args) {
		    File fReport = new File(".xlsx");
		    XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(fReport));
		    XSSFSheet sheet = book.getSheet("");

		    XSSFSheet dummy = book.createSheet("dummy");

		    int lastRow = sheet.getLastRowNum();
		    for (int i = 8; i <= lastRow; i++) {
		        XSSFRow rowToClean = sheet.getRow(i);
		        XSSFCell cell = rowToClean.getCell(2);
		        System.out.println(i);
		        if (cell != null) {
		            cell.setCellFormula("'dummy'!A1");
		        }
		    }
		    book.removeSheetAt(book.getSheetIndex(dummy));
		    
		    for (int i = 8; i <= lastRow; i++) {
		        XSSFRow rowToClean = sheet.getRow(i);
		        XSSFCell cell = rowToClean.getCell(2);
		        System.out.println(i);
		        if (cell != null) {
		            cell.removeFormula();
		        }
		    }

		    book.write(new FileOutputStream(fReport));
		    book.close();

	}
	
}
