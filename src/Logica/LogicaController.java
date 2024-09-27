package Logica;

import Modulos.MonedaApi;
import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

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
        System.out.println("7) Salir");
    }

    public void mostrarMenu(){
        while(true){
            generarOpciones();
            seleccion = seleccion();
            if(seleccion == 7){
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
            System.out.println("Ingrese un numero valido!\n");
            mostrarMenu();
            return 0;
        }
    }
    private void ejecutarSeleccion(int opcion){
        switch(opcion){
            case 1:
                conversion("USD", "ARS");
                break;
            case 2:
                conversion("ARS", "USD");
                break;
            case 3:
                conversion("USD", "BRL");
                break;
            case 4:
                conversion("BRL", "USD");
                break;
            case 5:
                conversion("USD", "COP");
                break;
            case 6:
                conversion("COP", "USD");
                break;
            default:
                System.out.println("Ingresa un valor entre 1 y 7!");
                break;
        }
    }
    public Map<String, Double> hacerPeticion(){
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
    private void conversion(String deMoneda, String paraMoneda){
        Scanner sc = new Scanner(System.in);
        System.out.println("***** Conversion ****\nIngresa el valor en " + deMoneda +
                " para convertir a:" + paraMoneda);
        double valor = sc.nextDouble();
        Map<String, Double> monedas = hacerPeticion();
        double valorPara = monedas.get(paraMoneda);
        System.out.println(paraMoneda + ": " + valor * valorPara);
    }
}
