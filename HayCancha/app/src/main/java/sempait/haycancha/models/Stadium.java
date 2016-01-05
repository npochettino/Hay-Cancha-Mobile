package sempait.haycancha.models;

import java.io.Serializable;

/**
 * Created by martin on 12/11/15.
 */
public class Stadium implements Serializable {


    private int codigoComplejo;
    private String descripcion;
    private String direccion;
    private String horaApertura;
    private String horaCierre;
    private float latitud;
    private float longitud;
    private String mail;
    private String telefono;
    private float puntajeComplejo;

    public int getCodigoComplejo() {
        return codigoComplejo;
    }

    public void setCodigoComplejo(int codigoComplejo) {
        this.codigoComplejo = codigoComplejo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public float getPuntajeComplejo() {
        return puntajeComplejo;
    }

    public void setPuntajeComplejo(float puntajeComplejo) {
        this.puntajeComplejo = puntajeComplejo;
    }
}
