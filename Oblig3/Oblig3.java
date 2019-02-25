/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oblig3;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author eivind
 */
public class Oblig3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder()); 
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
        for (char c : verdier) {
            tre.leggInn(c);
        }

        System.out.println(tre.bladnodeverdier(p.høyre));
        // Utskrift: [D, G, K, N, Q, S

    }

}
