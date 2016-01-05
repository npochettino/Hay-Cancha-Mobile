package sempait.haycancha.models;

import android.location.Location;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by martin on 26/10/15.
 */
public class Turn implements Serializable {

    private String date;
    private String horaDesde;
    private String horaHasta;
    private float distance;
    private String descripcionComplejo;
    private String imagenComplejo;
    private String codigoTipoCancha;
    private String descripcionCancha;
    private String descripcionTipoCancha;
    private String codigoComplejo;
    private String isFavorito;
    private String precio;
    private String direccion;
    private float puntajeComplejo;
    private Double latitud;
    private Double longitud;
    private String codigoCancha;
    private int codigoTurno;
    private int codigoEstado;
    private String descripcionEstado;


    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;

    }

    public String getHoraDesde() {

        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {


        this.horaDesde = horaDesde;
    }


    public String getHoraHasta() {

        return horaHasta;
    }


    public void setHoraHasta(String horaHasta) {

        this.horaHasta = horaHasta;

    }


    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {

        this.distance = distance;
    }


    public float calculateDistanceTo(Location location1) {

        if (location1 != null) {
            Location location2 = new Location("");
            location2.setLatitude(getLatitud());
            location2.setLongitude(getLongitud());
            float distance = location1.distanceTo(location2);
            this.distance = distance;
        } else
            this.distance = 0;


        return distance;


    }


    public String getImagenComplejo() {
        return "http://miguiaargentina.com.ar/Imagenes/m/784985453-1-ha-roes-fa-tbol-5.jpeg";
    }

    public void setImagenComplejo(String imagenComplejo) {
        this.imagenComplejo = "http://miguiaargentina.com.ar/Imagenes/m/784985453-1-ha-roes-fa-tbol-5.jpeg";
    }

    public String getCodigoTipoCancha() {
        return codigoTipoCancha;
    }

    public void setCodigoTipoCancha(String codigoTipoCancha) {
        this.codigoTipoCancha = codigoTipoCancha;
    }

    public String getDescripcionTipoCancha() {
        return descripcionTipoCancha;
    }

    public void setDescripcionTipoCancha(String descripcionTipoCancha) {
        this.descripcionTipoCancha = descripcionTipoCancha;
    }

    public String getCodigoComplejo() {
        return codigoComplejo;
    }

    public void setCodigoComplejo(String codigoComplejo) {
        this.codigoComplejo = codigoComplejo;
    }

    public String getIsFavorito() {
        return isFavorito;
    }

    public void setIsFavorito(String isFavorito) {
        this.isFavorito = isFavorito;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCodigoCancha() {
        return codigoCancha;
    }

    public void setCodigoCancha(String codigoCancha) {
        this.codigoCancha = codigoCancha;
    }

    public String getDescripcionComplejo() {
        return descripcionComplejo;
    }

    public void setDescripcionComplejo(String descripcionComplejo) {
        this.descripcionComplejo = descripcionComplejo;
    }

    public String getDescripcionCancha() {
        return descripcionCancha;
    }

    public void setDescripcionCancha(String descripcionCancha) {
        this.descripcionCancha = descripcionCancha;
    }

    public int getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(int codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public float getPuntajeComplejo() {
        return puntajeComplejo;
    }

    public void setPuntajeComplejo(float puntajeComplejo) {
        this.puntajeComplejo = puntajeComplejo;
    }
}
