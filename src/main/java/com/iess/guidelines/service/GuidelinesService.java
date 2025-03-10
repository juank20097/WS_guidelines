package com.iess.guidelines.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.iess.guidelines.entity.Guideline;
import com.iess.guidelines.entity.Information;
import com.iess.guidelines.repo.GuidelinesRepo;
import com.iess.guidelines.utilities.Utility;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Service
public class GuidelinesService {

	@Autowired
	private GuidelinesRepo guidelineRepo;

	private Utility utility = new Utility();

	private String DocumentName = "";

	public void generateExcel(String numDoc, Information info) throws IOException {
		DocumentName = "PAS-MLT-" + numDoc + " " + info.getTicket();

		ClassPathResource resource = new ClassPathResource("guideline_base.xlsx");
		// Abrir el archivo Excel base
		InputStream fileInputStream = resource.getInputStream();
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);

		String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		// Escribir el nombre del documento en la celda C6
		Row dateRow = sheet.getRow(5); // Fila 6 (índice 5)
		Cell dateCell = dateRow.getCell(2); // Columna C (índice 2)
		dateCell.setCellValue(DocumentName);

		Row dateRow1 = sheet.getRow(30); // Fila 31 (índice 30)
		Cell dateCell1 = dateRow1.getCell(4); // Columna E (índice 4)
		dateCell1.setCellValue(date);

		Row dateRow2 = sheet.getRow(31); // Fila 32 (índice 31)
		Cell dateCell2 = dateRow2.getCell(4); // Columna E (índice 4)
		dateCell2.setCellValue(date);

		Row dateRow3 = sheet.getRow(34); // Fila 35 (índice 34)
		Cell dateCell3 = dateRow3.getCell(4); // Columna E (índice 4)
		dateCell3.setCellValue(date);

		Row dateRow4 = sheet.getRow(37); // Fila 38 (índice 37)
		Cell dateCell4 = dateRow4.getCell(4); // Columna E (índice 4)
		dateCell4.setCellValue(date);

		List<Guideline> list = this.Search(info);

		// Escribir los permisos en las filas correspondientes
		int startRow = 7; // Comienza en la fila 8 (índice 7)
		Integer item = 1;
		for (Guideline aligment : list) {
			// Crear una nueva fila
			Row row = sheet.createRow(startRow);

			// Crear una fuente con tamaño 8
			Font font = sheet.getWorkbook().createFont();
			font.setFontHeightInPoints((short) 8); // Establecer tamaño de fuente en 9

			// Crear un estilo que ajuste el texto
			CellStyle style = sheet.getWorkbook().createCellStyle();
			style.setWrapText(true); // Habilita el ajuste de texto
			// Asignar la fuente al estilo de celda
			style.setFont(font);
			// Alinear el texto en el centro (horizontal y verticalmente)
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			// Establecer bordes en negro
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);

			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());

			Cell cell = row.createCell(0);
			cell.setCellValue(item);
			cell.setCellStyle(style);

			Cell cell1 = row.createCell(1);
			cell1.setCellValue("Juan Carlos Estévez");
			cell1.setCellStyle(style);

			Cell cell2 = row.createCell(2);
			cell2.setCellValue("SW");
			cell2.setCellStyle(style);

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(aligment.getNecesidad());
			cell3.setCellStyle(style);

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(utility.reemplazarProjectName(aligment.getLineamiento(), info.getTipo_project_name()));
			cell4.setCellStyle(style);

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(aligment.getMecanismo());
			cell5.setCellStyle(style);

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(date);
			cell6.setCellStyle(style);

			Cell cell7 = row.createCell(7);
			if (aligment.getObservacion() != null) {
				cell7.setCellValue(utility.reemplazarGitlabName(aligment.getObservacion(), info.getTipo_url_gitlab()));
			} else {
				cell7.setCellValue(aligment.getObservacion());
			}
			cell7.setCellStyle(style);

			item++;
			startRow++;
		}

		String outputDirectoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "doc";
		String outputFilePath = outputDirectoryPath + File.separator + DocumentName + ".xlsx";

		File directory = new File(outputDirectoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		// Guardar el archivo Excel
		FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();
		fileInputStream.close();

		System.out.println("Archivo Excel generado y guardado en: " + outputFilePath);
	}

	private List<Guideline> Search(Information info) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		List<Guideline> list = new ArrayList<>();
		if (info.getSoftware().equals("web") || info.getSoftware().equals("hibrido")) {
			if (info.getTipo().equals("Nuevo")) {
				l.add(1);
			}
			if (info.getTipo().equals("Antiguo")) {
				l.add(2);
				l.add(3);
			}
			if (info.getTipo().equals("Actual")) {
				l.add(3);
			}
			if (info.getTipo().equals("Migrar")) {
				l.add(4);
				l.add(1);
			}
			if (info.getAuditoria()==true) {
				l.add(13);
			}
		}
		if (info.getSoftware().equals("ws") || info.getSoftware().equals("hibrido")) {
			if (info.getWs_tipo().equals("request")) {
				l.add(6);
				l.add(11);
				if (info.getWso2() == true) {
					l.add(10);
				}
				if (info.getWso2() == false) {
					l.add(9);
				}
			}
			if (info.getWs_tipo().equals("response")) {
				if (info.getWs_tipo_categoria().equals("soap")) {
					l.add(7);
					l.add(12);
				}
				if (info.getWs_tipo_categoria().equals("rest")) {
					l.add(8);
					l.add(12);
				}
			}
			if (info.getAuditoria()==true) {
				l.add(19);
			}
		}
		if (info.getReportes()== true) {
			l.add(15);
		}
		if (info.getFirmaec()==true) {
			l.add(16);
		}
		if (info.getKeycloak()==true) {
			l.add(17);
		}
		if(info.getNotificaciones()==true) {
			l.add(18);
		}
		l.add(5);
		l.add(14);
		list = guidelineRepo.findAllById(l);
		System.out.println(list.size());
		return list;
	}
}
