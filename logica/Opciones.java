package logica;

import java.util.Scanner;

public class Opciones {
    private String[] opts;
    private String nombre;
    private String format;

    public Opciones(String nombre, String[] opts, int d) {
        this.nombre = nombre;
        this.opts = opts.clone();
        format = String.format("[%%%dd] %%s\n", d);
    }

    public int length(){
        return opts.length;
    }

    public String getOpcion(int opt){
        if(esOpcionValida(opt))
            return opts[opt-1];
        return "";
    }

    public void printMenu() {
        for (int i = 0; i < opts.length; i++) {
            System.out.printf(format, i + 1, opts[i]);
        }
    }

    public int leerOpcion(Scanner scanner) {
        boolean valido = false;
        int opt = 0;
        while (!valido) {
            printMenu();
            System.out.printf("Seleccione una opción para %s: ", nombre);
            opt = leerInt(scanner);
            if (opt >= 0) {
                if (esOpcionValida(opt))
                    valido = true;
                else {
                    System.out.printf("Error, %d no es una opción para %s.\n", opt, nombre);
                }
            }
        }

        return opt;
    }

    public boolean esOpcionValida(int opt) {
        return 0 < opt && opt <= opts.length;
    }

    private static int leerInt(Scanner scanner) {
        String s = "";
        int opt = -1;
        try {
            s = scanner.next().trim();
            opt = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.printf("Error, %s no es una opción valida.\n", s);
        }
        return opt;
    }
}
