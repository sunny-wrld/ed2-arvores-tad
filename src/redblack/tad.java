package redblack;

public class tad {

    public static class RubroNegra {
        private static final boolean VERMELHO = true;
        private static final boolean PRETO = false;

        private final No NULO;
        private No raiz;

        private class No {
            int chave;
            boolean cor;
            No esq, dir, pai;

            No(int chave, boolean cor) {
                this.chave = chave;
                this.cor = cor;
                this.esq = null;
                this.dir = null;
                this.pai = null;
            }
        }

        public RubroNegra() {
            NULO = new No(0, PRETO);
            NULO.esq = NULO.dir = NULO.pai = NULO;
            raiz = NULO;
        }

        public void inserir(int chave) {
            No z = new No(chave, VERMELHO);
            No y = NULO;
            No x = raiz;

            while (x != NULO) {
                y = x;
                if (z.chave < x.chave) x = x.esq;
                else x = x.dir;
            }

            z.pai = y;

            if (y == NULO) raiz = z;
            else if (z.chave < y.chave) y.esq = z;
            else y.dir = z;

            z.esq = NULO;
            z.dir = NULO;
            z.cor = VERMELHO;

            corrigirInsercao(z);
        }

        private void corrigirInsercao(No z) {
            while (z.pai.cor == VERMELHO) {
                if (z.pai == z.pai.pai.esq) {
                    No y = z.pai.pai.dir;

                    if (y.cor == VERMELHO) {
                        z.pai.cor = PRETO;
                        y.cor = PRETO;
                        z.pai.pai.cor = VERMELHO;
                        z = z.pai.pai;
                    } else {
                        if (z == z.pai.dir) {
                            z = z.pai;
                            rotacaoEsquerda(z);
                        }
                        z.pai.cor = PRETO;
                        z.pai.pai.cor = VERMELHO;
                        rotacaoDireita(z.pai.pai);
                    }
                } else {
                    No y = z.pai.pai.esq;

                    if (y.cor == VERMELHO) {
                        z.pai.cor = PRETO;
                        y.cor = PRETO;
                        z.pai.pai.cor = VERMELHO;
                        z = z.pai.pai;
                    } else {
                        if (z == z.pai.esq) {
                            z = z.pai;
                            rotacaoDireita(z);
                        }
                        z.pai.cor = PRETO;
                        z.pai.pai.cor = VERMELHO;
                        rotacaoEsquerda(z.pai.pai);
                    }
                }
            }
            raiz.cor = PRETO;
        }

        public void remover(int chave) {
            No z = buscarNo(raiz, chave);
            if (z == NULO) return;

            No y = z;
            No x;
            boolean corOriginal = y.cor;

            if (z.esq == NULO) {
                x = z.dir;
                transplante(z, z.dir);
            } else if (z.dir == NULO) {
                x = z.esq;
                transplante(z, z.esq);
            } else {
                y = minimo(z.dir);
                corOriginal = y.cor;
                x = y.dir;

                if (y.pai == z) x.pai = y;
                else {
                    transplante(y, y.dir);
                    y.dir = z.dir;
                    y.dir.pai = y;
                }

                transplante(z, y);
                y.esq = z.esq;
                y.esq.pai = y;
                y.cor = z.cor;
            }

            if (corOriginal == PRETO) corrigirRemocao(x);
        }

        private void corrigirRemocao(No x) {
            while (x != raiz && x.cor == PRETO) {
                if (x == x.pai.esq) {
                    No w = x.pai.dir;

                    if (w.cor == VERMELHO) {
                        w.cor = PRETO;
                        x.pai.cor = VERMELHO;
                        rotacaoEsquerda(x.pai);
                        w = x.pai.dir;
                    }

                    if (w.esq.cor == PRETO && w.dir.cor == PRETO) {
                        w.cor = VERMELHO;
                        x = x.pai;
                    } else {
                        if (w.dir.cor == PRETO) {
                            w.esq.cor = PRETO;
                            w.cor = VERMELHO;
                            rotacaoDireita(w);
                            w = x.pai.dir;
                        }

                        w.cor = x.pai.cor;
                        x.pai.cor = PRETO;
                        w.dir.cor = PRETO;
                        rotacaoEsquerda(x.pai);
                        x = raiz;
                    }

                } else {
                    No w = x.pai.esq;

                    if (w.cor == VERMELHO) {
                        w.cor = PRETO;
                        x.pai.cor = VERMELHO;
                        rotacaoDireita(x.pai);
                        w = x.pai.esq;
                    }

                    if (w.dir.cor == PRETO && w.esq.cor == PRETO) {
                        w.cor = VERMELHO;
                        x = x.pai;
                    } else {
                        if (w.esq.cor == PRETO) {
                            w.dir.cor = PRETO;
                            w.cor = VERMELHO;
                            rotacaoEsquerda(w);
                            w = x.pai.esq;
                        }

                        w.cor = x.pai.cor;
                        x.pai.cor = PRETO;
                        w.esq.cor = PRETO;
                        rotacaoDireita(x.pai);
                        x = raiz;
                    }
                }
            }
            x.cor = PRETO;
        }

        private void transplante(No u, No v) {
            if (u.pai == NULO) raiz = v;
            else if (u == u.pai.esq) u.pai.esq = v;
            else u.pai.dir = v;

            v.pai = u.pai;
        }

        private No minimo(No x) {
            while (x.esq != NULO) x = x.esq;
            return x;
        }

        private void rotacaoEsquerda(No x) {
            No y = x.dir;
            x.dir = y.esq;

            if (y.esq != NULO) y.esq.pai = x;

            y.pai = x.pai;

            if (x.pai == NULO) raiz = y;
            else if (x == x.pai.esq) x.pai.esq = y;
            else x.pai.dir = y;

            y.esq = x;
            x.pai = y;
        }

        private void rotacaoDireita(No x) {
            No y = x.esq;
            x.esq = y.dir;

            if (y.dir != NULO) y.dir.pai = x;

            y.pai = x.pai;

            if (x.pai == NULO) raiz = y;
            else if (x == x.pai.dir) x.pai.dir = y;
            else x.pai.esq = y;

            y.dir = x;
            x.pai = y;
        }

        private No buscarNo(No node, int chave) {
            while (node != NULO && chave != node.chave) {
                if (chave < node.chave) node = node.esq;
                else node = node.dir;
            }
            return node;
        }

        public boolean contem(int chave) {
            return buscarNo(raiz, chave) != NULO;
        }

        public void imprimir() {
            imprimirInOrdem(raiz);
            System.out.println();
        }

        private void imprimirInOrdem(No node) {
            if (node == NULO) return;

            imprimirInOrdem(node.esq);
            System.out.print(node.chave + (node.cor == VERMELHO ? "R " : "P "));
            imprimirInOrdem(node.dir);
        }
    }
}}
