package utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import pojo.WriteBackData;


public class ExcelUtils {
    
    public static ArrayList<WriteBackData> wbdlist = new ArrayList<WriteBackData>();

    //            定义              使用                                                                                                                       传入
    public static <T> List<T> readExcel(String srcPath, int sheetIndex, Class<T> clazz) {


	List<T> list = null;

	FileInputStream fis = null;
	try {
	    // 1.加载Excel流对象
	    fis = new FileInputStream(srcPath);
	    ImportParams params = new ImportParams(); // 导入参数设置类
	    params.setStartSheetIndex(sheetIndex);
	    params.setNeedVerfiy(true);

	    list = ExcelImportUtil.importExcel(fis, clazz, params);
	    return list;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close(fis);
	}
	return null;
    }

    
    public static void write(String srcPath, int sheetIndex) {
	FileInputStream fis = null;
	FileOutputStream fos = null;
	try {
	    //加载Excel流对象
	    fis = new FileInputStream(srcPath);
	    Workbook wb = WorkbookFactory.create(fis);
	    
	    Sheet sheet = wb.getSheetAt(sheetIndex);
	    
	    
	    //循环wbdlist集合,取出wbd对象,获取到行号、列号、内容
	    for (WriteBackData wbd : wbdlist) {
		
		Row row = sheet.getRow(wbd.getRowNum());
		
		Cell cell = row.getCell(wbd.getCellNum(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		
		cell.setCellType(CellType.STRING);
		
		//修改内容
		cell.setCellValue(wbd.getContent());
	    }
	    
	    
	    fos = new FileOutputStream(srcPath);
	    
	    wb.write(fos);
	    fis.close();
	    fos.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    public static void close(Closeable stream) {
	try {
	    if (stream != null) {
		stream.close();
	    }
	} catch (Exception e2) {
	    e2.printStackTrace();
	}
    }

}
