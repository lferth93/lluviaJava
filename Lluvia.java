import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

import logica.*;

class Lluvia {
    private static Pais pais;
    private static Scanner scanner;
    private static String[] confirm = { "si", "s", "yes", "y" };
    private static Opciones funciones = new Opciones("realizar", new String[] {
            "Ingresar un dato.", // 1
            "Mostrar tabla.", // 2
            "Consultar por entidad fedrativa y mes.", // 3
            "Consultar el minimo por mes.", // 4
            "Consultar el maximo por entidad federativa.", // 5
            "Consultar promedio por entidad federativa.", // 6
            "Borrar un dato.", // 7
            "Salir del programa"// 8
    }, 1);
    private static Random random = new Random();

    public static void main(String[] args) {
        InputStreamReader r = new InputStreamReader(System.in);
        scanner = new Scanner(r);
        String datos = "datos.txt";
        if (args.length > 0)
            datos = args[0];
        pais = new Pais(new File(datos));
        ejecutar();
        scanner.close();
        try {
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ejecutar() {
        boolean salir = false;
        while (!salir) {
            int opt = funciones.leerOpcion(scanner);
            System.out.println("\n" + funciones.getOpcion(opt));
            switch (opt) {
                case 1:
                    funcionInsertar();
                    break;
                case 2:
                    pais.tabla(System.out);
                    break;
                case 3:
                    funcionConsultar();
                    break;
                case 4:
                    funcionMinimo();
                    break;
                case 5:
                    funcionMaximo();
                    break;
                case 6:
                    funcionPromedio();
                    break;
                case 7:
                    funcionBorrar();
                    break;
                case 8:
                    salir = funcionSalir();
                    break;
            }
            System.out.println();
        }
    }

    private static void funcionInsertar() {
        int estado = Estado.estado.leerOpcion(scanner);
        String eNombre = Estado.estado.getOpcion(estado);
        int mes = Estado.mes.leerOpcion(scanner);
        String mNombre = Estado.mes.getOpcion(mes);
        System.out.printf("Entidad federativa: %s, Mes: %s\n", eNombre, mNombre);
        if (pais.existeDato(estado, mes)) {
            System.out.println("Ya existe un dato, Quiere sobrescribirlo?");
            boolean confirm = leerConfir();
            if (!confirm) {
                System.out.println("Operación cancelada.");
                return;
            }
        }
        int r = random.nextInt(751);
        pais.insertarDato(estado, mes, r);
        System.out.printf("Se inserto %d en el mes %s para la entidad federativa %s.\n", r, mNombre, eNombre);
    }

    private static void funcionConsultar() {
        int estado = Estado.estado.leerOpcion(scanner);
        int mes = Estado.mes.leerOpcion(scanner);
        System.out.println(pais.consultarDato(estado, mes));
    }

    private static void funcionMinimo() {
        int mes = Estado.mes.leerOpcion(scanner);
        String res = pais.minimo(mes);
        System.out.println(res);
    }

    private static void funcionMaximo() {
        int estado = Estado.estado.leerOpcion(scanner);
        String res = pais.maximo(estado);
        System.out.println(res);
    }

    public static void funcionPromedio() {
        int e = Estado.estado.leerOpcion(scanner);
        String eName = Estado.estado.getOpcion(e);
        float prom = pais.getPromedio(e);
        if (prom < 0.0) {
            System.out.println(String.format("No hay datos para la entidad federativa %s.", eName));
            return;
        }

        System.out.printf("La media de la entidad federativa %d (%s) es de %6.2fmm.\n", e, eName, prom);
    }

    public static void funcionBorrar() {
        int e = Estado.estado.leerOpcion(scanner);
        String eName = Estado.estado.getOpcion(e);
        int m = Estado.mes.leerOpcion(scanner);
        String mName = Estado.mes.getOpcion(m);
        if (!pais.existeDato(e, m)) {
            System.out.printf("No hay algun dato para el mes %s en la entidad federativa %s.\n", mName, eName);
            return;
        }
        System.out.printf("Seguro que desea borrar el dato para el mes %s en la entidad federativa %s?\n", mName,
                eName);
        if (leerConfir())
            pais.borrarDato(e, m);
    }

    private static boolean funcionSalir() {
        System.out.println("Esta seguro que desea salir?");
        return leerConfir();
    }

    private static boolean leerConfir() {
        System.out.printf("Escriba [%s] para confirmar: ", String.join(",", confirm));
        String opt = scanner.next().trim().toLowerCase();
        for (String s : confirm) {
            if (opt.equals(s))
                return true;
        }
        System.out.println("Operación canccelada.");
        return false;
    }
}