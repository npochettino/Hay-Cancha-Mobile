package sempait.haycancha.models;

import java.io.Serializable;

/**
 * Created by martin on 02/11/15.
 */
public class User implements Serializable {
    private int codigoUsuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String mail;
    private String contraseña;
    private int codigoPosicion;
    private String descripcionPosicion;


    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;

    }

    public String getApellido() {

        return apellido;
    }

    public void setApellido(String apellido) {

        this.apellido = apellido;

    }

    public String getDni() {

        return dni;
    }

    public void setDni(String dni) {


        this.dni = dni;
    }


    public String getTelefono() {

        return telefono;
    }


    public void setTelefono(String telefono) {

        this.telefono = telefono;

    }

    public String getMail() {

        return mail;
    }


    public void setMail(String mail) {

        this.mail = mail;
    }

    public String getContraseña() {
        return contraseña;

    }

    public void setContraseña(String contraseña) {

        this.contraseña = contraseña;
    }


    public int getCodigoUsuario() {
        return codigoUsuario;

    }

    public void SetCodigoUsuario(int codigoUsuario) {

        this.codigoUsuario = codigoUsuario;
    }

    public String getDescPosicion() {
        return descripcionPosicion;

    }

    public void setDescPosicion(String descPosicion) {

        this.descripcionPosicion = descPosicion;
    }


    public int getCodigoPosicion() {
        return codigoPosicion;

    }

    public void setCodigoPosicion(int codigoPosicion) {

        this.codigoPosicion = codigoPosicion;
    }
}
