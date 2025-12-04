public class tad {
    class Ponteiro {
        int chave;
        Ponteiro esq, dir;
        int altura;

        Ponteiro(int c) {
            chave = c;
            altura = 1;
        }
    }

    class AVL {

        Ponteiro raiz;

        int altura(Ponteiro p) {
            if (p == null) return 0;
            return p.altura;
        }

        int fb(Ponteiro p) {
            return altura(p.esq) - altura(p.dir);
        }

        Ponteiro rotDir(Ponteiro y) {
            Ponteiro x = y.esq;
            Ponteiro t2 = x.dir;
            x.dir = y;
            y.esq = t2;
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            return x;
        }

        Ponteiro rotEsq(Ponteiro x) {
            Ponteiro y = x.dir;
            Ponteiro t2 = y.esq;
            y.esq = x;
            x.dir = t2;
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            return y;
        }

        Ponteiro inserirRec(Ponteiro p, int chave) {
            if (p == null) return new Ponteiro(chave);

            if (chave < p.chave)
                p.esq = inserirRec(p.esq, chave);
            else if (chave > p.chave)
                p.dir = inserirRec(p.dir, chave);
            else
                return p;

            p.altura = 1 + Math.max(altura(p.esq), altura(p.dir));
            int f = fb(p);

            if (f > 1 && chave < p.esq.chave) return rotDir(p);
            if (f < -1 && chave > p.dir.chave) return rotEsq(p);
            if (f > 1 && chave > p.esq.chave) {
                p.esq = rotEsq(p.esq);
                return rotDir(p);
            }
            if (f < -1 && chave < p.dir.chave) {
                p.dir = rotDir(p.dir);
                return rotEsq(p);
            }

            return p;
        }

        void inserir(int chave) {
            raiz = inserirRec(raiz, chave);
        }

        Ponteiro buscar(Ponteiro p, int chave) {
            if (p == null) return null;
            if (chave == p.chave) return p;
            if (chave < p.chave) return buscar(p.esq, chave);
            return buscar(p.dir, chave);
        }

        boolean contem(int chave) {
            return buscar(raiz, chave) != null;
        }

        Ponteiro menor(Ponteiro p) {
            while (p.esq != null) p = p.esq;
            return p;
        }

        Ponteiro removerRec(Ponteiro p, int chave) {
            if (p == null) return null;

            if (chave < p.chave)
                p.esq = removerRec(p.esq, chave);
            else if (chave > p.chave)
                p.dir = removerRec(p.dir, chave);
            else {
                if (p.esq == null || p.dir == null) {
                    Ponteiro t = (p.esq != null) ? p.esq : p.dir;
                    p = t;
                } else {
                    Ponteiro suc = menor(p.dir);
                    p.chave = suc.chave;
                    p.dir = removerRec(p.dir, suc.chave);
                }
            }

            if (p == null) return null;

            p.altura = 1 + Math.max(altura(p.esq), altura(p.dir));
            int f = fb(p);

            if (f > 1 && fb(p.esq) >= 0) return rotDir(p);
            if (f > 1 && fb(p.esq) < 0) {
                p.esq = rotEsq(p.esq);
                return rotDir(p);
            }
            if (f < -1 && fb(p.dir) <= 0) return rotEsq(p);
            if (f < -1 && fb(p.dir) > 0) {
                p.dir = rotDir(p.dir);
                return rotEsq(p);
            }

            return p;
        }

        void remover(int chave) {
            raiz = removerRec(raiz, chave);
        }

        void emOrdem(Ponteiro p) {
            if (p == null) return;
            emOrdem(p.esq);
            System.out.print(p.chave + " ");
            emOrdem(p.dir);
        }

        void imprimir() {
            emOrdem(raiz);
            System.out.println();
        }
    }

}
