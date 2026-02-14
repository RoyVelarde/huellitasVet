package com.veterinaria.huellitasVet.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tbl_persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 75, message = "Los apellidos deben tener entre 2 y 75 caracteres")
    @Column(length = 75, nullable = false)
    private String apellidos;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    @Digits(integer = 8, fraction = 0, message = "El DNI solo debe contener números")
    @Column(length = 8, nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "El celular es obligatorio")
    @Size(min = 9, max = 9, message = "El celular debe tener 9 dígitos")
    @Digits(integer = 9, fraction = 0, message = "El número de celular solo debe contener números")
    @Column(name = "numero_celular", length = 9, nullable = false, unique = true)
    private String numeroCelular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Column(length = 100, nullable = true, unique = true)
    private String correo;

    @OneToMany(mappedBy = "persona")
    private List<Mascota> mascotas;

    public Persona() {
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
