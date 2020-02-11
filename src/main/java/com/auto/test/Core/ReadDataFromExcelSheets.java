package com.auto.test.Core;

import static com.auto.test.Core.GC.objectRepository;
import static com.auto.test.Core.GC.userDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ReadDataFromExcelSheets {

	public static void main(String[] args) throws Exception {
		objectRepository = getDataFromExcelSheets("Object_Repository.xlsx", "ObjectRepository");
	}

	public static HashMap<String, String> getDataFromExcelSheets(String fileName, String sheetName) {
		HashMap<String, String> excelData = new HashMap<String, String>();
		try {
			File excelFile = new File(userDirectory + "//DataDriven//" + fileName);
			FileInputStream fis = new FileInputStream(excelFile);
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			Sheet sheet = workBook.getSheet(sheetName);
			int numberOfRows = sheet.getLastRowNum();
			for (int i = 1; i <= numberOfRows; i++) {
				// This is to handle Null pointer exception while row is empty
				try {
					Row row = sheet.getRow(i);
					int numberOfColumns = row.getLastCellNum();
					String key = "";
					String value = "";
					for (int j = 0; j < numberOfColumns; j++) {
						if (j >= 4)
							break;
						Cell cell = row.getCell(j);
						if (cell == null)
							continue;
						cell.setCellType(CellType.STRING);
						if (j == 0)
							key = cell.toString().trim();
						else {
							if (value.isEmpty())
								value = cell.toString();
							else
								value += "==" + cell.toString();
						}

					}
					// System.out.println(key + ":" + value);
					excelData.put(key, value);
				} catch (Exception e) {

				}

			}
			workBook.close();
			fis.close();
		} catch (Exception e) {
			System.out.println("Exception Occured While Reading Data From Excel Sheet....!");
		}

		return excelData;
	}

}
