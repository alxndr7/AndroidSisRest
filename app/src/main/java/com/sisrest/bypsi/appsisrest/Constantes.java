package com.sisrest.bypsi.appsisrest;

/**
 * Created by Alexander on 6/12/2016.
 */

public class Constantes {

    /**
     * Transici�n Home -> Detalle
     */
    private static int CODIGO_USUARIO;
    private static String NOMBRE_USUARIO;

    private static String DNI_DEFAULT;

    /**
     * Transici�n Detalle -> Actualizaci�n
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexi�n.
     * Dejalo en blanco si no has configurado esta car�cteristica.
     */
    private static final String PUERTO_HOST = ":8080";
    /**
     * Direcci�n IP de genymotion o AVD
     */
    private static final String IP = "192.168.0.102";
    /**
     * URLs del Web Service
     */

    public static final String LOGIN = "http://" + IP + PUERTO_HOST + "/tesis0.0/public/index.php/servValidarLogin/";
    public static final String GET_INFO = "http://" + IP + PUERTO_HOST + "/tesis0.0/public/index.php/wsultimosConsumos/";
    public static final String UPDATE = "http://" + IP + PUERTO_HOST + "/I%20Wish/actualizar_meta.php";
    public static final String DELETE = "http://" + IP + PUERTO_HOST + "/I%20Wish/borrar_meta.php";
    public static final String INSERT_USUARIO = "http://" + IP + PUERTO_HOST + "/tesis0.0/public/index.php/wscrearusuario/";

    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";


    public static void setCodigoUsuario(int codigoUsuario) {
        CODIGO_USUARIO = codigoUsuario;
    }

    public static int getCodigoUsuario() {
        return CODIGO_USUARIO;
    }

    public static String getNombreUsuario() {
        return NOMBRE_USUARIO;
    }

    public static void setNombreUsuario(String nombreUsuario) {
        NOMBRE_USUARIO = nombreUsuario;
    }

    public static String getDniDefault() {
        return DNI_DEFAULT;
    }

    public static void setDniDefault(String dniDefault) {
        DNI_DEFAULT = dniDefault;
    }
}