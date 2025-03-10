package com.iess.guidelines.entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the responsibilities database table.
 * 
 */
@Schema
@Entity
@Table(name="guidelines")
@NamedQuery(name="Guideline.findAll", query="SELECT g FROM Guideline g")
public class Guideline implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="necesidad", length=300)
    private String necesidad;

    @Column(name="lineamiento", length=1000)
    private String lineamiento;

    @Column(name="mecanismo", length=300)
    private String mecanismo;

    @Column(name="observacion", length=500)
    private String observacion;

    public Guideline() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNecesidad() {
        return necesidad;
    }

    public void setNecesidad(String necesidad) {
        this.necesidad = necesidad;
    }

    public String getLineamiento() {
        return lineamiento;
    }

    public void setLineamiento(String lineamiento) {
        this.lineamiento = lineamiento;
    }

    public String getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(String mecanismo) {
        this.mecanismo = mecanismo;
    }

	public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
