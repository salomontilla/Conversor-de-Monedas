package Logica;

import java.util.Scanner;

public class LogicaController {
    public void mostrarMenu(){
        Scanner sc = new Scanner(System.in);
        String seleccion;
        System.out.println("****** Menu *******");
        System.out.println("1) De dolar (USD) a Peso Argentino (ARS)");
        System.out.println("2) De Peso Argentino (ARS) a dolar (USD)");
        System.out.println("3) De dolar (USD) a Real Brasileño (BRL)");
        System.out.println("4) De Real Brasileño (BRL) a dolar (USD)");
        System.out.println("5) De dolar (USD) a Peso Colombiano (COP)");
        System.out.println("6) De Peso Colombiano (COP) a dolar (USD)");
        System.out.println("7) Salir");
    }
}
