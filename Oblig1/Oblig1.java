/*
Algoritmer og Datastrukturer - Oblig 1

Laget av:
Tommy Pedersen - s306650 (Dataingeniør)
Eivind Solberg - s315324 (Dataingeniør)
*/

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class Oblig1 {

    ///// Oppgave 1 //////////////////////////////////////

    public static int min(int[] a) {

        // Gi feilmelding om tabellen er tom
        if (a.length < 1) {
            throw new NoSuchElementException("Tabellen er tom!");
        }

        // Variabel for største index-tall
        int m = a.length - 1;

        // Vi vil ha den minste verdien først, så vi teller tabellen motsatt vei
        for (int i = a.length - 1; i >= 0; i--) {
            // For-løkka må stoppe når vi kommer ned til det første index-tallet
            if (a[i] != a[0]) {
                // For hver gang tallet på venstre side er mindre enn tallet på høyre side
                if (a[m] < a[m - 1]) {
                    // Så bytter vi plass på verdien, slik vi får med oss den minste verdien først i tabellen
                    int min = a[m];
                    a[m] = a[m - 1];
                    a[m - 1] = min;
                }
                m--;
            }
        }

        // Returner den aller første verdien i den nye tabllen (dette skal være den minste verdien nå!)
        return a[0];
    }

    /*
    Ombyttinger teori-del

    I denne teori-delen har vi brukt randPerm metoden
    Selve sorterings-metoden (ombyttinger(int[] a) i dette tilfellet) er en bobblesorteringsmetode.
    Vi har sett at denne metoden er mindre effektiv enn kvikksortering og flettesortering i undervisningen.
    Til denne type oppgaver er metoden enkel å programmere, og den kjører kjapt på så små tabeller.

    Vi har tilsammen gjort 3 forsøk med tilfeldig genererte tabeller.

    FORSØK 1:
    Tabellstørrelse = 37
    Laveste verdi = 1
    Antall ombyttinger = 27

    FORSØK 2:
    Tabellstørrelse = 48
    Laveste verdi = 1
    Antall ombyttinger = 37

    FORSØK 3:
    Tabellstørrelse = 80
    Laveste verdi = 1
    Antall ombyttinger = 68

    Gjennomsnittet for ombyttinger i disse forsøkene finner vi da ved:
    (27 + 37 + 68) / 3 = 44

    Ved å skrive ut hele tabeller både før og etter selve koden har kjørt, observerer vi følgende:
    - En sortert stigende tabell gir ingen ombyttinger
    - En sortert synkende tabell gir flest ombyttinger
    - Så jo nærmere en sortert stigende tabell vi har, jo færre ombyttinger trenger vi
    */

    public static int ombyttinger(int[] a) {

        // Gi feilmelding om tabellen er tom
        if (a.length < 1) {
            throw new java.util.NoSuchElementException("Tabellen er tom!");
        }

        // Variabel for største index-tall og ombytt
        int m = a.length - 1;
        int ombytt = 0;

        // Vi bruker koden fra min-metoden over
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] != a[0]) {
                if (a[m] < a[m - 1]) {
                    int min = a[m];
                    a[m] = a[m - 1];
                    a[m - 1] = min;
                    // Her har vi lagt inn telleren
                    ombytt++;
                }
                m--;
            }
        }

        // Returnerer antall ombyttinger
        return ombytt;
    }

    ///// Oppgave 2 //////////////////////////////////////

    public static int modus1(int[] a) {

        // Sjekk for feilmeldinger
        for (int i = 0; i < a.length - 1; i++) {
            if (a.length <= 1) {
                throw new IllegalStateException("Tabellen er tom eller inneholder kun én verdi!");
            } else if (a[i] > a[i + 1]) {
                throw new IllegalStateException("Tabellen er usortert!");
            }
        }

        // Variabler for tellere og modus
        int modus = 0;
        int teller1 = 0;
        int teller2 = 0;

        // Vi søker igjennom hele tabellen to ganger
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                // For alle like verdier
                if (a[i] == a[j]) {
                    // Så oppdateres teller nr.1
                    teller1++;
                }
            }
            // Hvis teller nr.1 er større eller lik teller nr.2 (som starter på 0)
            if (teller1 >= teller2) {
                // Så oppdaterer vi modus og teller nr.2
                modus = a[i];
                teller2 = teller1;
            }
            // Til slutt nullstiller vi teller nr.1, så løkka over finner den neste verdien som er større enn den forrige
            teller1 = 0;
        }

        // Returnerer modus fra tabellen
        return modus;
    }

    ///// Oppgave 3 //////////////////////////////////////

    public static int modus2(int[] a) {

        // Kontroller at tabellen ikke er tom
        if (a.length < 1) {
            throw new IllegalStateException("Tabellen er tom!");
        }

        // Deklarerer maksVerdi og startVerdi, med verdi = 0;
        int maksVerdi = 0;
        int maksAntall = 0;

        //Tabellen søkes igjennom to ganger
        for (int i = 0; i < a.length; i++) {
            int teller = 0;
            for (int j = 0; j < a.length; j++) {
                //Hvis verdiene er like, vil telleren øke med 1.
                if (a[j] == a[i]) {
                    teller++;
                }
            }
            /*
            Hvis teller er større enn maksAntall, vil maksAntall ta verdien til telleren.
            maksVerdi tar verdien til a[i]
            */
            if (teller > maksAntall) {
                maksAntall = teller;
                maksVerdi = a[i];
            }
        }

        // Til slutt returneres modus/typetallet.
        return maksVerdi;
    }

    ///// Oppgave 4 //////////////////////////////////////

    public static void delsortering(int[] a) {

        // Variabler for venstre og høyre side av tabellen
        int v = 0;
        int h = a.length - 1;

        // Vi nøster denne løkka for å unngå en uendelig loop
        while (true) {
            // Mens venstre side er mindre eller lik høyre og modulus-rest ikke er lik null (oddetall)
            while (v <= h && a[v] % 2 != 0) {
                // Så legger vi til på venstre side
                v++;
            }
            // Mens høyre side er større eller lik venstre og modulus-rest er lik null (partall)
            while (v <= h && a[h] % 2 == 0) {
                // Så trekker vi ifra på høyre side
                h--;
            }
            // Når vi når midten (høyre side blir større eller lik venstre)
            if (v <= h) {
                // Bytter vi for å få skilleverdien på rett plass
                int temp = a[v];
                a[v] = a[h];
                a[h] = temp;
            } else {
                // Hvis ikke, så avslutter vi
                break;
            }
        }

        // Til slutt sorterer vi tallene på høyre og venstre side med Arrays.sort
        Arrays.sort(a, 0, v);
        Arrays.sort(a, v, a.length);
    }

    ///// Oppgave 5 //////////////////////////////////////

    public static void rotasjon(char[] a) {

        // Ingen handlinger ved null eller ett element
        if (!(a.length < 1)) {

            // Variabel som finner den siste bokstaven i tabellen
            char siste = a[a.length - 1];

            // Vi teller igjennom hele tabellen baklengs
            for (int i = a.length - 1; i > 0; i--) {
                // Vi har funnet siste bokstav, så da flytter vi alle andre en plass til høyre
                a[i] = a[i - 1];
            }

            // Til slutt plasserer vi den siste bokstaven først i tabellen
            a[0] = siste;
        }
    }

    ///// Oppgave 6 //////////////////////////////////////

    public static void rotasjon(char[] a, int k) {

        // Vi setter først n som lengden av hele tabellen
        int n = a.length;

        // Ingen handlinger ved null eller ett element
        if (n < 2) {
            return;
        }

        // Er verdien negativ, så snur vi den
        if ((k %= n) < 0) {
            k += n;
        }

        // Vi kopierer en hjelpetabell utifra verdiene som er oppgitt
        char[] hjelpetabell = Arrays.copyOfRange(a, n - k, n);

        // Så teller tabellen baklengs fra n, og slutter på større eller lik k
        for (int i = n - 1; i >= k; i--) {
            // Vi forskyver på samme måte som i oppgave 5
            a[i] = a[i - k];
        }

        // Til slutt lager vi en ny tabell med den kopierte hjelpetabellen som kilde
        System.arraycopy(hjelpetabell, 0, a, 0, k);
    }

    ///// Oppgave 7 //////////////////////////////////////

    public static String flett(String s, String t) {

        // Vi bruker Math.min for å finne den korteste strengen, og StringBuilder for fletting
        int n = Math.min(s.length(), t.length());
        StringBuilder fletting = new StringBuilder();

        // Først teller vi igjennom tabellen fra 0 og opp til tallet n
        for (int i = 0; i < n; i++) {
            // For hver bokstav vi teller, så fletter vi de sammen med StringBuilder
            fletting.append(s.charAt(i));
            fletting.append(t.charAt(i));
        }

        // Til slutt legger vi til de som er "til overs" etter n
        fletting.append(s.substring(n));
        fletting.append(t.substring(n));

        // Returnerer den flettede strengen
        return fletting.toString();
    }

    public static String flett(String... s) {

        // Vi bruker en hjelpevariabel for tallet n og StringBuilder for fletting
        int n = 0;
        StringBuilder fletting = new StringBuilder();

        // Først teller vi igjennom hele tabellen s
        for (int i = 0; i < s.length; i++) {
            // For hvert index-tall av tabellen s som er større enn tallet n
            if (s[i].length() > n) {
                // Setter vi tallet n til å være det gjeldene index-tallet fra tabellen s[i] og ut
                n = s[i].length();
            }
        }

        // Så teller vi igjennom tabellen to ganger. Første gang fra 0 til det nye tallet n
        for (int i = 0; i < n; i++) {
            // Deretter hele tabbellens lengde
            for (int j = 0; j < s.length; j++) {
                // Vi oppretter en hjelpevariabel for bokstaven på index-tallet til j
                String bokstav = s[j];
                // For hver gang bokstavenes lengde er større en vår første iterasjon i
                if (bokstav.length() > i) {
                    // Så fletter vi sammen bokstavene på dette index-tallet med StringBuilder
                    fletting.append(bokstav.charAt(i));
                }
            }
        }

        // Til slutt returneres den nye strengen etter fletting
        return fletting.toString();
    }
}
