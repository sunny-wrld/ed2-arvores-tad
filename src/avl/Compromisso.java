//usei ia nessa parte

package avl;

public class Compromisso {
    private long timestamp;
    private String descricao;

    public Compromisso(long timestamp, String descricao) {
        this.timestamp = timestamp;
        this.descricao = descricao;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        String ts = String.format("%012d", timestamp);
        String ano = ts.substring(0, 4);
        String mes = ts.substring(4, 6);
        String dia = ts.substring(6, 8);
        String hora = ts.substring(8, 10);
        String minuto = ts.substring(10, 12);

        return String.format("%s/%s/%s %s:%s - %s", ano, mes, dia, hora, minuto, descricao);
    }
}

