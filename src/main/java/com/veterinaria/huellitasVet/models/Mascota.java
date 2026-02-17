package com.veterinaria.huellitasVet.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

@Entity
@Table(name = "tbl_mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(min = 2, max = 40, message = "La especie debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    @Size(min = 2, max = 40, message = "La raza debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String raza;

    @NotBlank(message = "El género es obligatorio")
    @Column(length = 10, nullable = false)
    private String genero;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(length = 500, nullable = false)
    private String descripcion;

    @Valid
    @ManyToOne
    @JsonIgnoreProperties("mascotas")
    @JoinColumn(name = "persona_id", nullable = false)
    @NotNull(message = "Debes asignar un dueño a la mascota")
    private Persona persona;

    public Mascota() {
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
