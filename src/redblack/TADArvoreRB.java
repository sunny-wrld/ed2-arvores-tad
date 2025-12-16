package redblack;

public class TADArvoreRB {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private No raiz;
    private No Nil;

    public TADArvoreRB() {
        Nil = new No(0, PRETO);
        Nil.esquerda = Nil.direita = Nil.p = Nil;
        raiz = Nil;
    }

    private void rotacaoEsquerda(No x) {
        No y = x.direita;
        x.direita = y.esquerda;

        if (y.esquerda != Nil) {
            y.esquerda.p = x;
        }

        y.p = x.p;

        if (x.p == Nil) {
            raiz = y;
        } else if (x == x.p.esquerda) {
            x.p.esquerda = y;
        } else {
            x.p.direita = y;
        }

        y.esquerda = x;
        x.p = y;
    }

    private void rotacaoDireita(No x) {
        No y = x.esquerda;
        x.esquerda = y.direita;

        if (y.direita != Nil) {
            y.direita.p = x;
        }

        y.p = x.p;

        if (x.p == Nil) {
            raiz = y;
        } else if (x == x.p.direita) {
            x.p.direita = y;
        } else {
            x.p.esquerda = y;
        }

        y.direita = x;
        x.p = y;
    }

    public void inserir(int chave) {
        No z = new No(chave);
        z.esquerda = Nil;
        z.direita = Nil;
        z.p = Nil;

        No y = Nil;
        No x = raiz;

        while (x != Nil) {
            y = x;
            if (z.chave < x.chave) {
                x = x.esquerda;
            } else {
                x = x.direita;
            }
        }

        z.p = y;

        if (y == Nil) {
            raiz = z;
        } else if (z.chave < y.chave) {
            y.esquerda = z;
        } else {
            y.direita = z;
        }

        z.esquerda = Nil;
        z.direita = Nil;
        z.cor = VERMELHO;
        rbInsertFixup(z);
    }

    private void rbInsertFixup(No z) {
        while (z.p.cor == VERMELHO) {
            if (z.p == z.p.p.esquerda) {
                No y = z.p.p.direita;

                if (y.cor == VERMELHO) {
                    z.p.cor = PRETO;
                    y.cor = PRETO;
                    z.p.p.cor = VERMELHO;
                    z = z.p.p;
                } else {
                    if (z == z.p.direita) {
                        z = z.p;
                        rotacaoEsquerda(z);
                    }

                    z.p.cor = PRETO;
                    z.p.p.cor = VERMELHO;
                    rotacaoDireita(z.p.p);
                }
            } else {
                No y = z.p.p.esquerda;

                if (y.cor == VERMELHO) {
                    z.p.cor = PRETO;
                    y.cor = PRETO;
                    z.p.p.cor = VERMELHO;
                    z = z.p.p;
                } else {
                    if (z == z.p.esquerda) {
                        z = z.p;
                        rotacaoDireita(z);
                    }
                    z.p.cor = PRETO;
                    z.p.p.cor = VERMELHO;
                    rotacaoEsquerda(z.p.p);
                }
            }
        }
        raiz.cor = PRETO;
    }

    private void rbTransplant(No u, No v) {
        if (u.p == Nil) {
            raiz = v;
        } else if (u == u.p.esquerda) {
            u.p.esquerda = v;
        } else {
            u.p.direita = v;
        }
        v.p = u.p;
    }

    private No treeMinimum(No x) {
        while (x.esquerda != Nil) {
            x = x.esquerda;
        }
        return x;
    }

    public void remover(int chave) {
        No z = buscar(raiz, chave);
        if (z == Nil) {
            System.out.println("Chave " + chave + " não encontrada!");
            return;
        }

        No y = z;
        boolean yCorOriginal = y.cor;
        No x;

        if (z.esquerda == Nil) {
            x = z.direita;
            rbTransplant(z, z.direita);
        } else if (z.direita == Nil) {
            x = z.esquerda;
            rbTransplant(z, z.esquerda);
        } else {
            y = treeMinimum(z.direita);
            yCorOriginal = y.cor;
            x = y.direita;

            if (y.p == z) {
                x.p = y;
            } else {
                rbTransplant(y, y.direita);
                y.direita = z.direita;
                y.direita.p = y;
            }

            rbTransplant(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.p = y;
            y.cor = z.cor;
        }

        if (yCorOriginal == PRETO) {
            rbRemoveFixup(x);
        }
    }

    private void rbRemoveFixup(No x) {
        while (x != raiz && x.cor == PRETO) {
            if (x == x.p.esquerda) {
                No w = x.p.direita;

                if (w.cor == VERMELHO) {
                    w.cor = PRETO;
                    x.p.cor = VERMELHO;
                    rotacaoEsquerda(x.p);
                    w = x.p.direita;
                }

                if (w.esquerda.cor == PRETO && w.direita.cor == PRETO) {
                    w.cor = VERMELHO;
                    x = x.p;
                } else {
                    if (w.direita.cor == PRETO) {
                        w.esquerda.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacaoDireita(w);
                        w = x.p.direita;
                    }

                    w.cor = x.p.cor;
                    x.p.cor = PRETO;
                    w.direita.cor = PRETO;
                    rotacaoEsquerda(x.p);
                    x = raiz;
                }
            } else {
                No w = x.p.esquerda;

                if (w.cor == VERMELHO) {
                    w.cor = PRETO;
                    x.p.cor = VERMELHO;
                    rotacaoDireita(x.p);
                    w = x.p.esquerda;
                }

                if (w.direita.cor == PRETO && w.esquerda.cor == PRETO) {
                    w.cor = VERMELHO;
                    x = x.p;
                } else {
                    if (w.esquerda.cor == PRETO) {
                        w.direita.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacaoEsquerda(w);
                        w = x.p.esquerda;
                    }
                    w.cor = x.p.cor;
                    x.p.cor = PRETO;
                    w.esquerda.cor = PRETO;
                    rotacaoDireita(x.p);
                    x = raiz;
                }
            }
        }
        x.cor = PRETO;
    }

    private No buscar(No x, int chave) {
        if (x == Nil || chave == x.chave) {
            return x;
        }

        if (chave < x.chave) {
            return buscar(x.esquerda, chave);
        } else {
            return buscar(x.direita, chave);
        }
    }

    public void emOrdem() {
        emOrdemAux(raiz);
        System.out.println();
    }

    private void emOrdemAux(No no) {
        if (no != Nil) {
            emOrdemAux(no.esquerda);
            if (no.cor) {
                System.out.print(no.chave + "V ");
            } else {
                System.out.print(no.chave + "P ");
            }
            emOrdemAux(no.direita);
        }
    }

    public void exibirArvore() {
        exibirArvoreAux(raiz, 0);
    }

    private void exibirArvoreAux(No no, int nivel) {
        if (no != Nil) {
            exibirArvoreAux(no.direita, nivel + 1);

            for (int i = 0; i < nivel; i++) {
                System.out.print("");
            }

            if (no.cor) {
                System.out.println(no.chave + "V");
            } else {
                System.out.println(no.chave + "P");
            }

            exibirArvoreAux(no.esquerda, nivel + 1);
        }
    }
}


