package sempait.haycancha.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by martin on 14/11/15.
 */
public class Comment implements Serializable {

    @SerializedName("fechaHoraValoracion")
    private String date;
    private String nombreApellidoUsuarioApp;
    @SerializedName("puntaje")
    private float raiting;
    @SerializedName("comentario")
    private String comment;
    @SerializedName("titulo")
    private String title;
    private String urlImagen;

    public Comment() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public float getRaiting() {
        return raiting;
    }

    public void setRaiting(float raiting) {
        this.raiting = raiting;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNombreApellidoUsuarioApp() {
        return nombreApellidoUsuarioApp;
    }

    public void setNombreApellidoUsuarioApp(String nombreApellidoUsuarioApp) {
        this.nombreApellidoUsuarioApp = nombreApellidoUsuarioApp;
    }
}
