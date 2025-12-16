package redblack;

public class No {
    int chave;
    No esquerda, direita, p;
    boolean cor;

    public No(int chave) {
        this.chave = chave;
        this.cor = true;
    }

    public No(int chave, boolean cor) {
        this.chave = chave;
        this.cor = cor;
    }
}

