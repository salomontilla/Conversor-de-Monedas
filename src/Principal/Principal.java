package Principal;

import Logica.LogicaController;

public class Principal {
    public static void main(String[] args) {
        LogicaController lg = new LogicaController();
        //lg.mostrarMenu();
        try {
            System.out.println(lg.hacerPeticion());
        }catch (Exception e) {e.printStackTrace();}
    }
}
