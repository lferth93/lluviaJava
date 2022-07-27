package logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintStream;

public class Pais {
    private static final String header = "| id| Entidad Federativa  | Prom. |   1|   2|   3|   4|   5|   6|   7|   8|   9|  10|  11|  12|";
    private static final String row = "+---+---------------------+-------+----+----+----+----+----+----+----+----+----+----+----+----+";
    private Estado[] estados;
    private File archivo;

    private Pais() {
        int ne = Estado.estado.length();
        estados = new Estado[ne];

        for (int i = 0; i < ne; i++)
            estados[i] = new Estado(i + 1);
    }

    public Pais(File f) {
        this();
        this.archivo = f;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            Scanner s = new Scanner(reader);
            while (s.hasNext()) {
                String linea = s.nextLine();
                String[] campos = linea.split(",");
                int[] datos = new int[12];
                for (int i = 0; i < 12; i++) {
                    datos[i] = Integer.parseInt(campos[3 + i].trim());
                }
                int id = Integer.parseInt(campos[0].trim());
                estados[id - 1].updateDatos(datos);
            }
            s.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.printf("No se encontró el archivo %s, se creó uno nuevo.\n", archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        guardar();
    }

    public float getPromedio(int e) {
        return estados[e - 1].getPromedio();
    }

    public void borrarDato(int e, int m) {
        estados[e - 1].borrarDato(m);
        guardar();
    }

    public void guardar() {
        try {
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Estado e : estados)
                bw.write(e.getCSV() + "\n");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tabla(PrintStream out) {
        out.println(row);
        out.println(header);
        out.println(row);
        for (Estado e : estados)
            out.println(e);
        out.println(row);
    }

    public String minimo(int mes) {
        int min = -1;
        int c = 0;
        StringBuilder sb = new StringBuilder();
        String mName = Estado.mes.getOpcion(mes);
        for (Estado e : estados) {
            int act = e.getDato(mes);
            if (0 <= act) {
                if (act == min)
                    c++;
                if (act < min || min == -1) {
                    min = act;
                    c = 1;
                }

                System.out.println("C:" + c);
            }
        }
        if (c == 0)
            return String.format("Ninguna entidad federativa tiene datos para el mes %s.", mName);
        sb.append(String.format("La precipitación minima de los datos capturados que corresponde al mes %s son las ",
                mName));
        if (c > 1)
            sb.append("de los estados ");
        else
            sb.append("del estado ");
        sb.append(getEstados(mes, min));
        sb.append(String.format(" con un valor de %dmm.", min));
        return sb.toString();
    }

    public String maximo(int e) {
        return estados[e - 1].maximo();
    }

    public String getEstados(int mes, int dato) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean primero = true;
        for (Estado e : estados) {
            if (e.getDato(mes) == dato) {
                if (!primero)
                    sb.append(',');
                sb.append(e.getNombre());
                primero = false;
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public String consultarDato(int id, int mes) {
        if (!Estado.estado.esOpcionValida(id))
            return String.format("El id %d no corresponde a un estado valido.");
        return estados[id - 1].consultarMes(mes);
    }

    public boolean existeDato(int e, int m) {
        return estados[e - 1].existeDato(m);
    }

    public void insertarDato(int e, int m, int dato) {
        estados[e - 1].updateDato(m, dato);
        guardar();
    }
}
