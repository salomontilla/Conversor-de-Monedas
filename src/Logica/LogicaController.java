package Logica;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LogicaController {
    int seleccion;
    public void mostrarMenu(){
        while(true){
            System.out.println("****** Menu *******");
            System.out.println("1) De dolar (USD) a Peso Argentino (ARS)");
            System.out.println("2) De Peso Argentino (ARS) a dolar (USD)");
            System.out.println("3) De dolar (USD) a Real Brasileño (BRL)");
            System.out.println("4) De Real Brasileño (BRL) a dolar (USD)");
            System.out.println("5) De dolar (USD) a Peso Colombiano (COP)");
            System.out.println("6) De Peso Colombiano (COP) a dolar (USD)");
            System.out.println("7) Salir");
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
                System.out.println("Hola");
                break;
            case 2:
                System.out.println("hola2");
                break;
            case 3:
                System.out.println("hola3");
                break;
            case 4:
                System.out.println("hola4");
                break;
            case 5:
                System.out.println("hola5");
                break;
            case 6:
                System.out.println("hola6");
                break;
            default:
                System.out.println("Ingresa un valor entre 1 y 7!");
                break;
        }
    }


}
