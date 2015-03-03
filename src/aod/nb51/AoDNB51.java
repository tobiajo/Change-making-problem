package aod.nb51;

import java.util.Scanner;

/**
 * Växlingsproblemet.
 * 
 * @author Tobias Johansson <tobias@johansson.xyz>
 */
public class AoDNB51 {

    private static int bredd;
    private static int[] mynt;
    private static int[][] lista;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int belopp = input();
        output(belopp, vaxel2(belopp));
    }

    public static int input() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Hur många olika valörer: ");
        int valorer = sc.nextInt();
        bredd = valorer + 1;                                // <-- int bredd
        mynt = new int[bredd];                              // <-- int[] mynt
        for (int i = 1; i < mynt.length; i++) {
            System.out.print("Värde valör " + i + ": ");
            mynt[i] = sc.nextInt();
        }

        System.out.print("Belopp: ");
        int belopp = sc.nextInt();
        lista = new int[bredd][belopp + 1];                 // <-- int[][] lista

        sc.close();
        return belopp;
    }

    public static void output(int belopp, int[] vaxel) {
        System.out.println("---\nMinst bredd mynt och sedlar: "
                + vaxel2(belopp)[0] + "\n---\n[Antal]\t[Valör]");
        for (int i = 1; i < bredd; i++) {
            System.out.println(vaxel[i] + "\t" + mynt[i]);
        }
    }

    /**
     * Lösning av växlingsproblemet med söndra och härska. Returnerar minsta
     * antalet mynt och sedlar som krävs för att ge växel för ett givet belopp,
     * samt växeln. 
     * 
     * Indata är: int belopp, int bredd, int[] mynt och int[][] lista
     *
     * @param belopp beloppet
     * @return {Minsta totalt, Valör 1, Valör 2, ..., Antal valörer}
     */
    public static int[] vaxel2(int belopp) {
        if (lista[0][belopp] != 0) {
            return lista(belopp);
        }

        // min ← belopp
        int[] min = new int[bredd];
        min[0] = belopp;

        // for i ← 1 to antal (bredd - 1)
        for (int i = 1; i < bredd; i++) {
            if (mynt[i] == belopp) {
                min[myntIndex(belopp)] = 1;
                min[0] = 1;
                return min;
            }
        }

        // for i ← 1 to belopp/2
        for (int i = 1; i <= belopp / 2; i++) {
            // n ← vaxel2(i) + vaxel2(belopp − i)
            int[] n = sum(vaxel2(i), vaxel2(belopp - i));
            if (n[0] < min[0]) {
                min = n;
            }
        }

        // lista[belopp] ← min
        for (int i = 0; i < bredd; i++) {
            lista[i][belopp] = min[i];
        }

        return min;
    }

    private static int[] lista(int belopp) {
        int[] ints = new int[bredd];
        for (int i = 0; i < bredd; i++) {
            if (lista[i][belopp] != 0) {
                ints[i] = lista[i][belopp];
            }
        }
        return ints;
    }

    private static int myntIndex(int belopp) {
        for (int i = 1; i < bredd; i++) {
            if (mynt[i] == belopp) {
                return i;
            }
        }
        return - 1;
    }

    private static int[] sum(int[] a, int[] b) {
        if (a.length != bredd || b.length != bredd) {
            throw new RuntimeException();
        }
        int[] ints = new int[bredd];
        for (int i = 0; i < bredd; i++) {
            ints[i] = a[i] + b[i];
        }
        return ints;
    }
}
