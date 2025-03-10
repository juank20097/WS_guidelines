package com.iess.guidelines.utilities;

public class Utility {
	
	public String reemplazarProjectName(String textoOriginal, String nuevoNombreProyecto) {
		String textoModificado = textoOriginal.replaceFirst("<<project_name>>", nuevoNombreProyecto);
		textoModificado = textoModificado.replace("<<project_name>>", nuevoNombreProyecto.toLowerCase());
		return textoModificado;
    }
	
	public String reemplazarGitlabName(String textoOriginal, String nuevoNombre) {
		return textoOriginal.replace("<<url_gitlab>>", nuevoNombre);
	}

}
