package oblig3;

/*
Algoritmer og Datastrukturer - Oblig 3

Laget av:
Tommy Pedersen - s306650 (Dataingeniør)
Eivind Solberg - s315324 (Dataingeniør)
 */

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public final boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        // Har kun lagt til q som forelder her.
        p = new Node<>(verdi, q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            // Mod 1
            if (b != null) {
                b.forelder = q;
            }
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else {
                s.høyre = r.høyre;
                // Mod 2
                r.høyre.forelder = s;
            }
            // Mod 3
            r.forelder = s; // 3
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {

        int ant = 0;

        while (fjern(verdi)) {
            ant++;
        }

        return ant;
    }

    @Override
    public int antall() {
        return antall;
    }

    public int antall(T verdi) {

        Node<T> p = rot;
        int returVerdi = 0;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.høyre;
            } else {
                returVerdi++;
                p = p.høyre;
            }
        }

        return returVerdi;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {

        Node<T> n = rot;
        Node<T> p = n;

        if (n == null) {
            return;
        }

        while (n.venstre != null) {
            n = n.venstre;
        }

        while (n != null) {
            n = nesteInorden(n);
            p.verdi = null;
            p = n;
        }

        antall = 0;
        rot = null;
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {

        Node<T> forelder = p.forelder;

        if (p.høyre != null) {
            p = p.høyre;
            while (p.venstre != null) {
                p = p.venstre;
            }
            return p;
        } else {
            while (forelder != null && forelder.høyre == p) {
                p = forelder;
                forelder = forelder.forelder;
            }
            return forelder;
        }
    }

    @Override
    public String toString() {

        Node<T> p = rot;
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (antall != 0) {
            if (p.venstre != null) {
                while (p.venstre != null) {
                    p = p.venstre;
                }
            }
            sb.append(p.verdi);
            for (int i = 0; i < antall - 1; i++) {
                p = nesteInorden(p);
                sb.append(", ").append(p.verdi);
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public String omvendtString() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String høyreGren() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String lengstGren() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String[] grener() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String bladnodeverdier() {

        Node<T> p = rot;
        StringBuilder sb = new StringBuilder();

        if (tom()) {
            return "[]";
        }

        sb.append("[");
        bladnodeverdier(p, sb);
        sb.append("]");

        // Fix for å slette ekstra ", " som blir lagt til på slutten av strengen
        if (!Objects.equals(sb.toString(), "[]")) {
            sb.delete(sb.length() - 3, sb.length() - 1);
        }

        return sb.toString();
    }

    private static <T> void bladnodeverdier(Node<T> p, StringBuilder sb) {

        if (p.venstre == null && p.høyre == null) {
            sb.append(p.verdi).append(", ");
        }

        if (p.venstre != null) {
            bladnodeverdier(p.venstre, sb);
        }

        if (p.høyre != null) {
            bladnodeverdier(p.høyre, sb);
        }
    }

    public String postString() {

        Node<T> p = postStringIterativ(rot); // Iterativ
        //Node<T> p = rot; // Rekursiv
        StringBuilder sb = new StringBuilder();

        if (tom()) {
            return "[]";
        }

        sb.append("[");
        //postStringRekursiv(p, sb); // Rekursiv

        // Iterativ
        while (true) {
            sb.append(p.verdi).append(", ");
            if (p.forelder == null) {
                break;
            }
            Node<T> q = p.forelder;
            if (p == q.høyre || q.høyre == null) {
                p = q;
            } else {
                p = postStringIterativ(q.høyre);
            }
        }

        sb.append("]");

        // Fix for å slette ekstra ", " som blir lagt til på slutten av strengen
        if (!Objects.equals(sb.toString(), "[]")) {
            sb.delete(sb.length() - 3, sb.length() - 1);
        }

        return sb.toString();
    }

    // Iterativ postString
    private static <T> Node<T> postStringIterativ(Node<T> p) {

        if (p == null) {
            return p;
        }

        while (true) {
            if (p.venstre != null) {
                p = p.venstre;
            } else if (p.høyre != null) {
                p = p.høyre;
            } else {
                return p;
            }
        }
    }

    // Rekursiv postString
    private void postStringRekursiv(Node p, StringBuilder sb) {

        if (p == null) {
            return;
        } else {
            postStringRekursiv(p.venstre, sb);
            postStringRekursiv(p.høyre, sb);
            sb.append(p.verdi).append(", ");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()  // konstruktør
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext() {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

    } // BladnodeIterator

} // ObligSBinTre