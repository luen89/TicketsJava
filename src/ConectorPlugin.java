/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

import com.fasterxml.jackson.jr.ob.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.lang.String;

/**
 *
 * @author parzibyte
 */
public class ConectorPlugin {

    public static String URL_PLUGIN_POR_DEFECTO = "http://localhost:8000";
    public static float TAMANO_IMAGEN_NORMAL = 0;
    public static float TAMANO_IMAGEN_DOBLE_ANCHO = 1;
    public static float TAMANO_IMAGEN_DOBLE_ALTO = 2;
    public static float TAMANO_IMAGEN_DOBLE_ANCHO_Y_ALTO = 3;
    public static float ALINEACION_IZQUIERDA = 0;
    public static float ALINEACION_CENTRO = 1;
    public static float ALINEACION_DERECHA = 2;
    public static float RECUPERACION_QR_BAJA = 0;
    public static float RECUPERACION_QR_MEDIA = 1;
    public static float RECUPERACION_QR_ALTA = 2;
    public static float RECUPERACION_QR_MEJOR = 3;

    public ArrayList<OperacionPlugin> operaciones;
    public String urlPlugin;
    public String serial;

    public ConectorPlugin(String urlPlugin, String serial) {
        this.operaciones = new ArrayList<>();
        this.urlPlugin = urlPlugin;
        this.serial = serial;
    }

    public ConectorPlugin(String urlPlugin) {
        this.operaciones = new ArrayList<>();
        this.urlPlugin = urlPlugin;
        this.serial = "";
    }

    public ConectorPlugin() {
        this.urlPlugin = URL_PLUGIN_POR_DEFECTO;
        this.operaciones = new ArrayList<>();
    }

    private void agregarOperacion(OperacionPlugin operacion) {
        this.operaciones.add(operacion);
    }

    public ConectorPlugin CargarImagenLocalEImprimir(String ruta, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(ruta);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPlugin("CargarImagenLocalEImprimir", argumentos));
        return this;
    }

    public ConectorPlugin Corte(float lineas) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(lineas);
        this.agregarOperacion(new OperacionPlugin("Corte", argumentos));
        return this;
    }

    public ConectorPlugin CorteParcial() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("CorteParcial", argumentos));
        return this;
    }

    public ConectorPlugin DefinirCaracterPersonalizado(String caracterRemplazoComoCadena, String matrizComoCadena) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(caracterRemplazoComoCadena);
        argumentos.add(matrizComoCadena);
        this.agregarOperacion(new OperacionPlugin("DefinirCaracterPersonalizado", argumentos));
        return this;
    }

    public ConectorPlugin DescargarImagenDeInternetEImprimir(String urlImagen, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(urlImagen);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPlugin("DescargarImagenDeInternetEImprimir", argumentos));
        return this;
    }

    public ConectorPlugin DeshabilitarCaracteresPersonalizados() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("DeshabilitarCaracteresPersonalizados", argumentos));
        return this;
    }

    public ConectorPlugin DeshabilitarElModoDeCaracteresChinos() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("DeshabilitarElModoDeCaracteresChinos", argumentos));
        return this;
    }

    public ConectorPlugin EscribirTexto(String texto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(texto);
        this.agregarOperacion(new OperacionPlugin("EscribirTexto", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerAlineacion(float alineacion) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(alineacion);
        this.agregarOperacion(new OperacionPlugin("EstablecerAlineacion", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerEnfatizado(boolean enfatizado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(enfatizado);
        this.agregarOperacion(new OperacionPlugin("EstablecerEnfatizado", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerFuente(float fuente) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(fuente);
        this.agregarOperacion(new OperacionPlugin("EstablecerFuente", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerImpresionAlReves(boolean alReves) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(alReves);
        this.agregarOperacion(new OperacionPlugin("EstablecerImpresionAlReves", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerImpresionBlancoYNegroInversa(boolean invertir) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(invertir);
        this.agregarOperacion(new OperacionPlugin("EstablecerImpresionBlancoYNegroInversa", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerRotacionDe90Grados(boolean rotar) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(rotar);
        this.agregarOperacion(new OperacionPlugin("EstablecerRotacionDe90Grados", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerSubrayado(boolean subrayado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(subrayado);
        this.agregarOperacion(new OperacionPlugin("EstablecerSubrayado", argumentos));
        return this;
    }

    public ConectorPlugin EstablecerTamanoFuente(float multiplicadorAncho, float multiplicadorAlto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(multiplicadorAncho);
        argumentos.add(multiplicadorAlto);
        this.agregarOperacion(new OperacionPlugin("EstablecerTamañoFuente", argumentos));
        return this;
    }

    public ConectorPlugin Feed(float lineas) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(lineas);
        this.agregarOperacion(new OperacionPlugin("Feed", argumentos));
        return this;
    }

    public ConectorPlugin HabilitarCaracteresPersonalizados() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("HabilitarCaracteresPersonalizados", argumentos));
        return this;
    }

    public ConectorPlugin HabilitarElModoDeCaracteresChinos() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("HabilitarElModoDeCaracteresChinos", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasCodabar(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasCodabar", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasCode128(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasCode128", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasCode39(String contenido, boolean incluirSumaDeVerificacion,
            boolean modoAsciiCompleto, float alto, float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(incluirSumaDeVerificacion);
        argumentos.add(modoAsciiCompleto);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasCode39", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasCode93(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasCode93", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasEan(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasEan", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasEan8(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasEan8", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasPdf417(String contenido, float nivelSeguridad, float alto,
            float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(nivelSeguridad);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasPdf417", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasTwoOfFiveITF(String contenido, boolean intercalado, float alto,
            float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(intercalado);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasTwoOfFiveITF", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasUpcA(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasUpcA", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoDeBarrasUpcE(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoDeBarrasUpcE", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirCodigoQr(String contenido, float anchoMaximo, float nivelRecuperacion,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(anchoMaximo);
        argumentos.add(nivelRecuperacion);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPlugin("ImprimirCodigoQr", argumentos));
        return this;
    }

    public ConectorPlugin ImprimirImagenEnBase64(String imagenCodificadaEnBase64, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(imagenCodificadaEnBase64);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPlugin("ImprimirImagenEnBase64", argumentos));
        return this;
    }

    public ConectorPlugin Iniciar() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPlugin("Iniciar", argumentos));
        return this;
    }

    public ConectorPlugin Pulso(float pin, float tiempoEncendido, float tiempoApagado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(pin);
        argumentos.add(tiempoEncendido);
        argumentos.add(tiempoApagado);
        this.agregarOperacion(new OperacionPlugin("Pulso", argumentos));
        return this;
    }

    public ConectorPlugin TextoSegunPaginaDeCodigos(float numeroPagina, String pagina, String texto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(numeroPagina);
        argumentos.add(pagina);
        argumentos.add(texto);
        this.agregarOperacion(new OperacionPlugin("TextoSegunPaginaDeCodigos", argumentos));
        return this;
    }

    public boolean imprimirEn(String impresora) throws Exception, IOException, InterruptedException {
        ImpresionConNombrePlugin impresionConNombre = new ImpresionConNombrePlugin(this.operaciones, impresora, this.serial);
        String postEndpoint = this.urlPlugin + "/imprimir";
        String inputJson = JSON.std.asString(impresionConNombre);
        var request = HttpRequest.newBuilder().uri(URI.create(postEndpoint)).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("código de respuesta distinto a 200. Verifique que el plugin se está ejecutando");
        }
        String respuesta = response.body();
        if (respuesta.equals("true\n")) {
            return true;
        } else {
            throw new Exception("petición ok pero error en el servidor: " + respuesta);
        }
    }

    public static String[] obtenerImpresoras(String urlPlugin) throws InterruptedException, IOException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlPlugin + "/impresoras"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().replace("[", "").replace("]", "").replace("\n", "").replace("\"", "").split(",");
    }

    public static String[] obtenerImpresoras() throws InterruptedException, IOException {
        return obtenerImpresoras(URL_PLUGIN_POR_DEFECTO);
    }

}