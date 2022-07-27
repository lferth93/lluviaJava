package logica;

public class Estado {
    public static final Opciones estado = new Opciones("estado", new String[] {
            "Aguascalientes",
            "Baja California",
            "Baja California Sur",
            "Campeche",
            "Coahuila",
            "Colima",
            "Chiapas",
            "Chihuahua",
            "Ciudad de México",
            "Durango",
            "Guanajuato",
            "Guerrero",
            "Hidalgo",
            "Jalisco",
            "Estado de México",
            "Michoacán",
            "Morelos",
            "Nayarit",
            "Nuevo León",
            "Oaxaca",
            "Puebla",
            "Querétaro",
            "Quintana Roo",
            "San Luis Potosi",
            "Sinaloa",
            "Sonora",
            "Tabasco",
            "Tamaulipas",
            "Tlaxcala",
            "Veracruz",
            "Yucatán",
            "Zacatecas"
    }, 2);

    public static final Opciones mes = new Opciones("mes", new String[] {
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
    }, 2);

    private int id;
    private float promedio;
    private Datos meses;

    public Estado(int id) {
        this.meses = new Datos(mes.length());
        this.id = id;
        this.updatePromedio();
    }

    public String getNombre() {
        return estado.getOpcion(id);
    }

    public void updatePromedio() {
        this.promedio = this.meses.promedio();
    }

    public float getPromedio() {
        return promedio;
    }

    public void borrarDato(int m) {
        meses.borrarDato(m - 1);
        updatePromedio();
    }

    public void updateDato(int mes, int v) {
        if (0 < mes && mes <= this.meses.length()) {
            this.meses.updateDato(mes - 1, v);
            this.updatePromedio();
        }
    }

    public String consultarMes(int m) {
        if (!mes.esOpcionValida(m))
            return String.format("%d no es un mes valido.", m);

        if (meses.getDato(m - 1) == -1)
            return String.format("No hay datos para el mes de %s en %s", mes.getOpcion(m), estado.getOpcion(id));

        return String.format("Precipitación de lluvias del mes de %s en %s es %dmm", mes.getOpcion(m),
                estado.getOpcion(id), meses.getDato(m - 1));
    }

    public String maximo() {
        Integer[] max = meses.getMaximo();
        if (max.length == 0)
            return String.format("No hay datos registrados para el estado %s", getNombre());
        StringBuilder sb = new StringBuilder();
        sb.append("La pecipitación maxima de los datos capturados corresponde ");
        if (max.length > 1)
            sb.append("a los meses ");
        else
            sb.append("al mes");
        sb.append('[');
        for (int i = 0; i < max.length; i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(mes.getOpcion(max[i] + 1));
        }
        sb.append(String.format("] con un valor de %dmm.", meses.getDato(max[0])));
        return sb.toString();
    }

    public void updateDatos(int[] d) {
        this.meses.update(d);
        updatePromedio();
    }

    public int getDato(int m) {
        return this.meses.getDato(m - 1);
    }

    public boolean existeDato(int m) {
        return meses.existe(m - 1);
    }

    public String toString() {
        return String.format("|%3d| %-20s| %6.2f%s", id, estado.getOpcion(id), promedio, meses);
    }

    public String getCSV() {
        return String.format("%2d, %-20s, %6.2f, %s", id, estado.getOpcion(id), promedio, meses.getCSV());
    }

}
