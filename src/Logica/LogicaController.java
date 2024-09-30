package Logica;

import Modulos.MonedaApi;
import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

public class LogicaController {
    int seleccion;
    private void generarOpciones(){
        System.out.println("****** Menu *******");
        System.out.println("1) De dolar (USD) a Peso Argentino (ARS)");
        System.out.println("2) De Peso Argentino (ARS) a dolar (USD)");
        System.out.println("3) De dolar (USD) a Real Brasileño (BRL)");
        System.out.println("4) De Real Brasileño (BRL) a dolar (USD)");
        System.out.println("5) De dolar (USD) a Peso Colombiano (COP)");
        System.out.println("6) De Peso Colombiano (COP) a dolar (USD)");
        System.out.println("7) Ver Historial de búsquedas");
        System.out.println("8) Salir");
    }

    public void mostrarMenu(){
        seleccion = 0;
        while(true){
            generarOpciones();
            seleccion = seleccion();
            if(seleccion == 8){
                System.out.println("Saliendo...");
                break;
            }else{
                ejecutarSeleccion(seleccion);
            }
        }
    }

    private int seleccion(){
        System.out.println("**************\nIngrese una opcion: ");
        Scanner sc = new Scanner(System.in);
        try{
            return sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Ingresa un valor valido!\n");
            mostrarMenu();
            return 0;
        }
    }
    private void ejecutarSeleccion(int opcion){
        switch(opcion){
            case 1:
                conversion("USD", "ARS", false);
                break;
            case 2:
                conversion("ARS", "USD", true);
                break;
            case 3:
                conversion("USD", "BRL", false);
                break;
            case 4:
                conversion("BRL", "USD", true);
                break;
            case 5:
                conversion("USD", "COP", false);
                break;
            case 6:
                conversion("COP", "USD",true);
                break;
            case 7:
                mostrarHistorial();
                break;
            default:
                System.out.println("Ingresa un valor entre 1 y 8!");
                break;
        }
    }
    private Map<String, Double> hacerPeticion(){
        try{
        HttpClient client = HttpClient.newBuilder().build();
        String url = "https://v6.exchangerate-api.com/v6/d065df8ca8ea08d75badb2a8/latest/USD";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
            MonedaApi moneda = gson.fromJson(json, MonedaApi.class);
            return moneda.conversion_rates();
        }catch (JsonSyntaxException e) {
            System.err.println("Error en la sintaxis del JSON: " + e.getMessage());
        } catch (JsonIOException e) {
            System.err.println("Error durante la lectura del JSON: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Error al acceder a los datos del JSON: " + e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    DecimalFormat df = new DecimalFormat("#.00");
    private void conversion(String deMoneda, String paraMoneda, boolean divide){

        Scanner sc = new Scanner(System.in);
        Map<String, Double> monedas = hacerPeticion();
        double valorDe = monedas.get(deMoneda), valorPara = monedas.get(paraMoneda), conversion = 0;
        try{
            System.out.println("***** Conversion ****\nIngresa el valor en " + deMoneda +
                    " para convertir a " + paraMoneda);
            double valor = sc.nextDouble();
            if(divide){
                conversion = valor / valorDe;
                System.out.println(paraMoneda + ": " + df.format(conversion));
            }else{
                conversion = valor * valorPara;
                System.out.println(paraMoneda + ": " + df.format(conversion));
            }
            guardarConversion(deMoneda, paraMoneda, conversion, valor);
        }catch (InputMismatchException e){
            System.out.println("Ingresa un valor válido!\n");
            mostrarMenu();
        }
    }
    List<String> listaConversiones = new ArrayList<>();
    private void guardarConversion(String valorDe, String valorPara, double resultadoConver, double valor) {

        String conversion =  valor + valorDe + " --> " + df.format(resultadoConver)+ valorPara + " : "
                + getCurrentTime();
        listaConversiones.add(conversion);
    }

    private String getCurrentTime(){
        LocalDateTime lt = LocalDateTime.now();
        String fecha = lt.getDayOfMonth() + "/" +
                lt.getMonthValue() + "/" +  lt.getYear() + " a las " + lt.getHour() + ":" + lt.getMinute();
        return fecha;
    }
    private void mostrarHistorial(){
        while (true) {

            System.out.println("***** Historial *****");
            if(listaConversiones.isEmpty()){
                System.out.println("No hay nada que mostrar!");
            }else{
                for(int i = 0; i < listaConversiones.size(); i++) {
                    System.out.println(i+1 +".)" + listaConversiones.get(i)+"\n");
                }
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Presiona cualquier numero para volver: ");
            try{
                if(sc.hasNextInt()){break;}
            }catch (InputMismatchException e){mostrarHistorial();}
        }
    }
}
