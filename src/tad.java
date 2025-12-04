public class tad {
    class Nodo {
        int chave;
        Nodo esq, dir;
        int altura;

        Nodo(int c) {
            chave = c;
            altura = 1;
        }
    }

    class AVL {

        Nodo raiz;

        int altura(Nodo p) {
            if (p == null) return 0;
            return p.altura;
        }

        int fb(Nodo p) {
            return altura(p.esq) - altura(p.dir);
        }

        Nodo rotDir(Nodo y) {
            Nodo x = y.esq;
            Nodo t2 = x.dir;
            x.dir = y;
            y.esq = t2;
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            return x;
        }

        Nodo rotEsq(Nodo x) {
            Nodo y = x.dir;
            Nodo t2 = y.esq;
            y.esq = x;
            x.dir = t2;
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            return y;
        }

        Nodo inserirRec(Nodo p, int chave) {
            if (p == null) return new Nodo(chave);

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

        Nodo buscar(Nodo p, int chave) {
            if (p == null) return null;
            if (chave == p.chave) return p;
            if (chave < p.chave) return buscar(p.esq, chave);
            return buscar(p.dir, chave);
        }

        boolean contem(int chave) {
            return buscar(raiz, chave) != null;
        }

        Nodo menor(Nodo p) {
            while (p.esq != null) p = p.esq;
            return p;
        }

        Nodo removerRec(Nodo p, int chave) {
            if (p == null) return null;

            if (chave < p.chave)
                p.esq = removerRec(p.esq, chave);
            else if (chave > p.chave)
                p.dir = removerRec(p.dir, chave);
            else {
                if (p.esq == null || p.dir == null) {
                    Nodo t = (p.esq != null) ? p.esq : p.dir;
                    p = t;
                } else {
                    Nodo suc = menor(p.dir);
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

        void emOrdem(Nodo p) {
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
