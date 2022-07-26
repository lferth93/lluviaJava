package logica;

import java.util.ArrayList;

public class Datos {
    private int[] datos;

    public Datos(int l) {
        this.datos = new int[l];
        for (int i = 0; i < this.datos.length; i++)
            this.datos[i] = -1;
    }

    public boolean esIndiceValido(int i) {
        return 0 <= i && i < datos.length;
    }

    public void updateDato(int i, int v) {
        if (0 <= i && i < this.datos.length)
            this.datos[i] = v;
    }

    public void update(int[] datos) {
        this.datos = new int[datos.length];
        System.arraycopy(datos, 0, this.datos, 0, this.datos.length);
    }

    public float promedio() {
        float p = 0;
        int c = 0;
        for (int v : this.datos)
            if (v != -1) {
                c++;
                p += v;
            }

        if (c > 0)
            return p / c;

        return -1;
    }

    public int length() {
        return this.datos.length;
    }

    public int getDato(int i) {
        if (esIndiceValido(i))
            return this.datos[i];
        return -1;
    }

    public void borrarDato(int i) {
        if (esIndiceValido(i))
            datos[i] = -1;
    }

    public boolean existe(int i) {
        return esIndiceValido(i) && datos[i] != -1;
    }

    public Integer[] getMaximo() {
        int max = -1;
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] >= 0) {
                if (datos[i] > max) {
                    max = datos[i];
                    res.clear();
                }
                if (datos[i] == max)
                    res.add(i);
            }
        }
        Integer arr[] = new Integer[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(5 * datos.length + 1);
        sb.append("|");
        for (int d : datos)
            sb.append(String.format("%4d|", d));

        return sb.toString();
    }

    public String getCSV() {
        StringBuilder sb = new StringBuilder(5 * datos.length);
        if (datos.length > 0)
            sb.append(String.format("%3d", datos[0]));
        for (int i = 1; i < datos.length; i++)
            sb.append(String.format(", %3d", datos[i]));

        return sb.toString();
    }
}
