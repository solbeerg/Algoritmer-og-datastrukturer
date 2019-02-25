package Oblig2;

/*
Algoritmer og Datastrukturer - Oblig 2
 
Laget av:
Tommy Pedersen - s306650 (Dataingeniør)
Eivind Solberg - s315324 (Dataingeniør)
 */
/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T> {

    private static final class Node<T> // en indre nodeklasse
    {

        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste) // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi) // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {

        Node<T> node;

        if (indeks < antall / 2) {
            node = hode;
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
        } else {
            node = hale;
            for (int i = antall - 1; i > indeks; i--) {
                node = node.forrige;
            }
        }

        return node;
    }

    // konstruktør
    @SuppressWarnings("unchecked")
    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {

        Objects.requireNonNull(a, "Tabellen a er null!");

        for (T a1 : a) {
            if (!(a1 == null)) {
                if (antall == 0) {
                    hale = hode = new Node<>(a1, hode, hale);
                } else {
                    hale = new Node<>(a1, hale, null);
                    hale.forrige.neste = hale;
                }
                antall++;
            }
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {

        fratilKontroll(antall(), fra, til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();
        Node<T> node = finnNode(fra);

        for (int i = fra; i < til; i++) {
            liste.leggInn(node.verdi);
            node = node.neste;
        }

        return liste;
    }

    private static void fratilKontroll(int antall, int fra, int til) {

        if (fra < 0) // fra er negativ
        {
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > antall) // til er utenfor tabellen
        {
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }

        if (fra > til) // fra er større enn til
        {
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    @Override
    public int antall() {

        return antall;
    }

    @Override
    public boolean tom() {

        return (antall == 0);
    }

    @Override
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null, null);
        } else {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }

        endringer++;
        antall++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

        if (verdi == null) {
            throw new NullPointerException("Null-verdier er ikke tillatt!");
        } else if (indeks < 0) {
            throw new IndexOutOfBoundsException("Indeks er mindre enn null!");
        } else if (indeks > antall) {
            throw new IndexOutOfBoundsException("Indeks er større enn antall!");
        }

        if ((indeks == 0) && (antall == 0)) {
            hode = new Node<>(verdi, null, hode);
            hale = hode;
            endringer++;
            antall++;
        } else if ((indeks == 0) && (antall > 0)) {
            Node<T> node = hode;
            hode = new Node<>(verdi, null, node);
            node.forrige = hode;
            endringer++;
            antall++;
        } else if (indeks == antall) {
            Node<T> node = hale;
            hale = new Node<>(verdi, hale, null);
            node.neste = hale;
            endringer++;
            antall++;
        } else {
            Node<T> node1 = null;
            Node<T> node2 = hode;
            while (indeks > 0) {
                node1 = node2;
                node2 = node2.neste;
                indeks--;
            }
            Node<T> node = new Node<>(verdi, node1, node2);
            endringer++;
            antall++;
            if (!(node1 == null)) {
                node1.neste = node;
            }
            node2.forrige = node;
        }
    }

    @Override
    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {

        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {

        int indeks = -1;
        Node<T> node = hode;

        if (verdi == null) {
            indeks = -1;
        } else if (node == null) {
            indeks = -1;
        } else {
            for (int i = 0; i < antall; i++) {
                if (verdi.equals(node.verdi)) {
                    indeks = i;
                    break;
                } else {
                    node = node.neste;
                }
            }
        }

        return indeks;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi, "Null-verdier er ikke tillatt!");

        Node<T> node = finnNode(indeks);
        T verdi = node.verdi;
        node.verdi = nyverdi;
        endringer++;

        return verdi;
    }

    @Override
    public boolean fjern(T verdi) {

        if (verdi == null) {
            return false;
        } else if (antall == 0) {
            return false;
        }

        Node<T> node1 = hode;
        Node<T> node2 = node1.neste;

        if (hode.verdi.equals(verdi)) {
            node1 = hode;
            hode = hode.neste;
            if (!(hode == null)) {
                hode.forrige = null;
            } else {
                hale = null;
            }
            node1.neste = null;
            endringer++;
            antall--;
            return true;
        } else if (hale.verdi.equals(verdi)) {
            hale = hale.forrige;
            hale.neste = null;
            endringer++;
            antall--;
            return true;
        }

        node1 = hode;

        for (int i = 0; i < antall - 1; i++) {
            if (node2.verdi.equals(verdi)) {
                node1.neste = node2.neste;
                node2.neste.forrige = node1;
                endringer++;
                antall--;
                return true;
            }
            node1 = node1.neste;
            node2 = node1.neste;
        }

        return false;
    }

    @Override
    public T fjern(int indeks) {

        if (indeks < 0) {
            throw new IndexOutOfBoundsException("Indeks er mindre enn 0!");
        } else if (indeks >= antall) {
            throw new IndexOutOfBoundsException("Indeks er større eller lik antall!");
        }

        Node<T> verdi = hode;
        Node<T> node1 = null;
        Node<T> node2 = hale;

        if (indeks == 0) {
            hode = hode.neste;
            if (!(hode == null)) {
                hode.forrige = null;
            } else {
                hale = null;
            }
            node2.neste = null;
            endringer++;
            antall--;
            return node2.verdi;
        } else if (indeks == antall - 1) {
            hale = hale.forrige;
            hale.neste = null;
            endringer++;
            antall--;
            return node2.verdi;
        }

        while (indeks > 0) {
            node1 = verdi;
            verdi = verdi.neste;
            indeks--;
        }

        verdi.neste.forrige = node1;
        node1.neste = verdi.neste;
        endringer++;
        antall--;

        return verdi.verdi;
    }

    @Override
    public void nullstill() {

        for (int i = 0; i < antall; i++) {
            fjern(0);
        }

        hode = hale = null;
        endringer++;
        antall = 0;
    }

    @Override
    public String toString() {

        if (antall == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        Node<T> node = hode;
        sb.append("[").append(node.verdi);
        node = node.neste;

        while (node != null) {
            sb.append(", ").append(node.verdi);
            node = node.neste;
        }

        sb.append("]");

        return sb.toString();
    }

    public String omvendtString() {

        if (antall == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        Node<T> node = hale;
        sb.append("[").append(node.verdi);
        node = node.forrige;

        while (node != null) {
            sb.append(", ").append(node.verdi);
            node = node.forrige;
        }

        sb.append("]");

        return sb.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
 
        for (int i = liste.antall(); i > 0; i--) {
            Iterator<T> iterator = liste.iterator();
            T maksverdi = iterator.next();
            int m = 0;
            for (int j = 1; j < i; j++) {
                T verdi = iterator.next();
                if (c.compare(verdi, maksverdi) < 0) {
                    m = j;
                    maksverdi = verdi;
                }
            }
            liste.fjern(m);
            liste.leggInn(maksverdi);
        }
    }

    @Override
    public Iterator<T> iterator() {

        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {

        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {

        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {

            indeksKontroll(indeks, false);

            denne = hode;

            fjernOK = false;
            iteratorendringer = endringer;

            for (int i = 0; i < indeks; i++) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {

            if (!(iteratorendringer == endringer)) {
                throw new ConcurrentModificationException("Endringer og iteratorendringer er forskjellige!");
            } else if (!(hasNext())) {
                throw new NoSuchElementException("Dette elementet finnes ikke!");
            } else if (denne == null) {
                throw new NoSuchElementException("Dette elementet finnes ikke!");
            }

            fjernOK = true;
            T verdi = denne.verdi;
            denne = denne.neste;

            return verdi;
        }

        @Override
        public void remove() {

            if (!(fjernOK)) {
                throw new IllegalStateException("Ugyldig tilstand!");
            } else if (!(endringer == iteratorendringer)) {
                throw new ConcurrentModificationException("Endringer og iteratorendringer er forskjellige!");
            }

            fjernOK = false;

            if (antall == 1) {
                hode = hale = null;
            } else if (denne == null) {
                hale = hale.forrige;
                hale.neste = null;
            } else if (denne.forrige == hode) {
                hode = hode.neste;
                hode.forrige = null;
            } else {
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }

            iteratorendringer++;
            endringer++;
            antall--;
        }
    } // DobbeltLenketListeIterator
} // DobbeltLenketListe
