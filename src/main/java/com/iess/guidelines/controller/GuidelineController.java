package com.iess.guidelines.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iess.guidelines.entity.Information;
import com.iess.guidelines.service.GuidelinesService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Guideline", description = "Guidelines API")
@RestController
@RequestMapping("/api/guideline")
public class GuidelineController {

	@Autowired
	private GuidelinesService guidelineService;

	@Operation(description = "This service generates a guidelines doc to IESS, and then creates an Excel file.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON data containing form and permissions information.", required = true, content = @Content(schema = @Schema(example = "[{\"ipOrigin\": \"192.168.1.1\", \"descriptionOrigin\": \"Descripción de origen 1\", \"areaOrigin\": \"Área de origen 1\", \"ipDestination\": \"192.168.1.2\", \"descriptionDestination\": \"Descripción de destino 1\", \"areaDestination\": \"Área de destino 1\", \"protocol\": \"TCP\", \"ports\": \"80,443\", \"duration\": \"3600\"}]"))), responses = {
			@ApiResponse(responseCode = "200", description = "Form generated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"El archivo Excel ha sido generado exitosamente.\"}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Error al generar el archivo Excel: mensaje del error\"}"))) })
	@CrossOrigin(origins = "*")
	@PostMapping("/excel/{numDoc}")
	public ResponseEntity<Map<String, String>> generateDoc(@PathVariable("numDoc") String numDoc,@RequestBody Information info) {
		Map<String, String> response = new HashMap<>();
		try {
			guidelineService.generateExcel(numDoc, info);
			response.put("message", "El archivo Excel ha sido generado exitosamente.");
			response.put("document", "PAS-MLT-"+numDoc+"_"+info.getTicket()+".xlsx");
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			response.put("error", "Error al generar el archivo Excel: " + e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
}
