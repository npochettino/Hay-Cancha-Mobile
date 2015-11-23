package sempait.haycancha.models;

import java.io.Serializable;

/**
 * Created by martin on 21/11/15.
 */
public class Invitation implements Serializable {

    private int codigoSolicitud;
    private int codigoEstadoSolicitud;
    private String descripcionEstadoSolicitud;
    private int codigoCancha;
    private String descripcionCancha;
    private int codigoComplejo;
    private String descripcionComplejo;
    private String horaDesde;
    private String horaHasta;
    private String fecha;
    private String imagenUsuario;
    private String nombreApellidoUsuario;
    private Boolean isCreator;
    private String direction;
    private Float puntaje;
    private String codigoTelefono;
    private Float precio;
    private Boolean isHeader;
    private String headerText;


    public int getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(int codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public int getCodigoEstadoSolicitud() {
        return codigoEstadoSolicitud;
    }

    public void setCodigoEstadoSolicitud(int codigoEstadoSolicitud) {
        this.codigoEstadoSolicitud = codigoEstadoSolicitud;
    }

    public String getDescripcionEstadoSolicitud() {
        return descripcionEstadoSolicitud;
    }

    public void setDescripcionEstadoSolicitud(String descripcionEstadoSolicitud) {
        this.descripcionEstadoSolicitud = descripcionEstadoSolicitud;
    }

    public int getCodigoCancha() {
        return codigoCancha;
    }

    public void setCodigoCancha(int codigoCancha) {
        this.codigoCancha = codigoCancha;
    }

    public String getDescripcionCancha() {
        return descripcionCancha;
    }

    public void setDescripcionCancha(String descripcionCancha) {
        this.descripcionCancha = descripcionCancha;
    }

    public int getCodigoComplejo() {
        return codigoComplejo;
    }

    public void setCodigoComplejo(int codigoComplejo) {
        this.codigoComplejo = codigoComplejo;
    }

    public String getDescripcionComplejo() {
        return descripcionComplejo;
    }

    public void setDescripcionComplejo(String descripcionComplejo) {
        this.descripcionComplejo = descripcionComplejo;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(String imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }

    public String getNombreApellidoUsuario() {
        return nombreApellidoUsuario;
    }

    public void setNombreApellidoUsuario(String nombreApellidoUsuario) {
        this.nombreApellidoUsuario = nombreApellidoUsuario;
    }

    public Boolean getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Boolean isCreator) {
        this.isCreator = isCreator;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader) {
        this.isHeader = isHeader;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(String codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }
}
