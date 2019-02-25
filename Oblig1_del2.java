/*
Algoritmer og Datastrukturer - Oblig 1

Laget av:
Tommy Pedersen - s306650 (Dataingeniør)
Eivind Solberg - s315324 (Dataingeniør)
*/

import java.util.Arrays;
import java.util.Random;

public class Oblig1_del2 {

    public static void main(String[] args) {

        // Generer en random tabell
        int n = (int) Math.floor(Math.random() * 100000);
        int[] a1 = randPerm(n);

        // Kjør tidsortering (oppgave 8)
        System.out.println("Oppgave 8");
        tidSorteringer(a1);

        // Summen av to tall er lik X? (oppgave 9)
        System.out.println("\nOppgave 9");
        int a2[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int x = 29;
        System.out.println("X = " + x);
        System.out.println("sumX = " + Arrays.toString(sumX(a2, x)));
    }

    ///// Oppgave 8 //////////////////////////////////////

    /*
    I denne oppgaven har vi brukt randPerm som beskrevet i obligen.
    Etter å ha generert en random tabell a, klonet vi denne til b, c og d
    Da hadde vi fire like tabeller (en til hver metode).
    På disse kjørte vi så sorteringsmetodene og tok tiden i millisekunder.

    Her er funnene våre:

    Tabellstørrelse: 100000
    a) Utvalgssortering (1777 ms)
    b) Innsettingssortering (982 ms)
    c) Kvikksortering (15 ms)
    d) Flettesortering (9 ms)

    Tabellstørrelse: 87700
    a) Utvalgssortering (1432 ms)
    b) Innsettingssortering (797 ms)
    c) Kvikksortering (13 ms)
    d) Flettesortering (7 ms)

    Tabellstørrelse: 51374
    a) Utvalgssortering (540 ms)
    b) Innsettingssortering (493 ms)
    c) Kvikksortering (14 ms)
    d) Flettesortering (7 ms)

    Tabellstørrelse: 14614
    a) Utvalgssortering (49 ms)
    b) Innsettingssortering (56 ms)
    c) Kvikksortering (4 ms)
    d) Flettesortering (2 ms)

    Gjennomsnittet for disse forsøkene finner vi da ved:
    a) (1777 + 1432 + 540 + 49) / 4 = 949 ms
    b) (982 + 797 + 493 + 56) / 4 = 582 ms
    c) (15 + 13 + 14 + 4) / 4 = 11 ms
    d) (9 + 7 + 7 + 2) / 4 = 6 ms

    Her ser vi at det er en enorm forskjell i ytelsen på de forskjellige metodene!
    For morroskyld, forsøkte vi med en tabell på én million permutasjoner.
    Da brukte utvalgssorteringen over 2.3 minutter, mens flettesortering gjorde den samme jobben på 0.1 sekund...

    Tabellstørrelse: 1000000
    a) Utvalgssortering (138382 ms)
    b) Innsettingssortering (111672 ms)
    c) Kvikksortering (112 ms)
    d) Flettesortering (107 ms)
    */

    public static void tidSorteringer(int[] a) {

        // Vi kloner tabellen
        int[] b = a.clone();
        int[] c = a.clone();
        int[] d = a.clone();

        System.out.println("Tabellstørrelse: " + a.length);

        // Utvalgssortering
        // Starter klokka NÅ!!!
        long tid1 = System.currentTimeMillis();
        // Kjører sortering
        utvalgssortering(a);
        // Stopper klokka her...
        tid1 = System.currentTimeMillis() - tid1;
        // Skriver ut svaret
        System.out.println("a) Utvalgssortering (" + tid1 + " ms)");

        // Innsettingssortering
        long tid2 = System.currentTimeMillis();
        innsettingssortering(b);
        tid2 = System.currentTimeMillis() - tid2;
        System.out.println("b) Innsettingssortering (" + tid2 + " ms)");

        // Kvikksortering
        long tid3 = System.currentTimeMillis();
        kvikksortering(c);
        tid3 = System.currentTimeMillis() - tid3;
        System.out.println("c) Kvikksortering (" + tid3 + " ms)");

        // Flettesortering
        long tid4 = System.currentTimeMillis();
        kvikksortering(d);
        tid4 = System.currentTimeMillis() - tid4;
        System.out.println("d) Flettesortering (" + tid4 + " ms)");
    }

    ///// Oppgave 9 //////////////////////////////////////

    /*
    I main-metoden har vi laget en tabell for denne med følgende verdier:
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]

    Dette skriver ut:

    X = 29
    sumX = [9, 20]
    */

    public static int[] sumX(int[] a, int x) {

        // Ny tabell for verdiene som skal summeres
        int[] sum = {0, 0};

        // Vi looper igjennom med to iterasjoner så vi kan summere tallene med hverandre
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                // Når vi treffer to tall som utgjør summen av x
                if (a[i] + a[j] == x) {
                    // Så fører vi opp disse tallene i den nye tabellen sum, og avslutter
                    sum[0] = a[j];
                    sum[1] = a[i];
                    break;
                }
            }
        }

        // Så returnerer vi den nye tabellen med de to tallene som kan summeres for x
        return sum;

        // Skulle vi heller returnert dette i tabellen a?!
        //a = sum.clone();
        //return a;
    }

    ///// Sorteringsmetoder //////////////////////////////////////

    public static void utvalgssortering(int[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            bytt(a, i, min(a, i, a.length));
        }
    }

    public static void innsettingssortering(int[] a) {

        for (int i = 1; i < a.length; i++) {
            int verdi = a[i], j = i - 1;
            for (; j >= 0 && verdi < a[j]; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = verdi;
        }
    }

    public static void kvikksortering(int[] a) {

        kvikksortering0(a, 0, a.length - 1);
    }

    private static void flettesortering(int[] a, int[] b, int fra, int til) {

        if (til - fra <= 1) {
            return;
        }

        int m = (fra + til) / 2;
        flettesortering(a, b, fra, m);
        flettesortering(a, b, m, til);

        if (a[m - 1] > a[m]) {
            flett(a, b, fra, m, til);
        }
    }

    ///// Hjelpemetoder //////////////////////////////////////

    private static void kvikksortering0(int[] a, int v, int h) {

        if (v >= h) {
            return;
        }

        int k = sParter0(a, v, h, (v + h) / 2);
        kvikksortering0(a, v, k - 1);
        kvikksortering0(a, k + 1, h);
    }

    private static int sParter0(int[] a, int v, int h, int indeks) {

        bytt(a, indeks, h);
        int pos = parter0(a, v, h - 1, a[h]);
        bytt(a, pos, h);

        return pos;
    }

    private static int parter0(int[] a, int v, int h, int skilleverdi) {

        while (true) {
            while (v <= h && a[v] < skilleverdi) {
                v++;
            }
            while (v <= h && a[h] >= skilleverdi) {
                h--;
            }
            if (v < h) {
                bytt(a, v++, h--);
            } else {
                return v;
            }
        }
    }

    private static void flett(int[] a, int[] b, int fra, int m, int til) {

        int n = m - fra;
        System.arraycopy(a, fra, b, 0, n);
        int i = 0, j = m, k = fra;

        while (i < n && j < til) {
            a[k++] = b[i] <= a[j] ? b[i++] : a[j++];
        }

        while (i < n) {
            a[k++] = b[i++];
        }
    }

    public static int min(int[] a, int fra, int til) {

        if (fra < 0 || til > a.length || fra >= til) {
            throw new IllegalArgumentException("Illegalt intervall!");
        }

        int m = fra;
        int minverdi = a[fra];

        for (int i = fra + 1; i < til; i++) {
            if (a[i] < minverdi) {
                m = i;
                minverdi = a[m];
            }
        }

        return m;
    }


    //Tilfeldig permutasjoner

    public static int[] randPerm(int n) {

        Random r = new Random();
        int[] a = new int[n];
        Arrays.setAll(a, i -> i + 1);

        for (int k = n - 1; k > 0; k--) {
            int i = r.nextInt(k + 1);
            bytt(a, k, i);
        }

        return a;
    }

    public static void bytt(int[] a, int i, int j) {

        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
