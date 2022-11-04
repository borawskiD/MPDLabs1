package com.company;

public class Task {
    double v0 = 1;
    int k = 1;
    int N = 2;
    double epsilon = 0.0001;
    double[] P0 = new double[]{1, 1}; //punt startowy
    double[] Pz = new double[]{1, 1}; //punt zastepczy, do porownania
    double[] Pp = new double[]{1, 1}; //punt poprzedni, do odejmowania

    double F0 = f(P0);
    boolean secondTested = false;
    boolean nothingChanged = true;

    public void run() {
        do {
            prepareBeforeTaskOne();
            System.out.println("*******");
            System.out.println("Task 2");
            System.out.println("*******");
            taskTwo();
            System.out.println("Zakonczylem poszukiwania:");
            System.out.println("Biezacy punkt: [" + Pz[0] + ", " + Pz[1] + "]; f(x) wynosi: " + f(Pz));
            System.out.println("---");
        } while (!nothingChanged);

    }

    void prepareBeforeTaskOne() {
        nothingChanged = true;
        secondTested = false;
        k = 1;
        System.out.println("*******");
        System.out.println("Task 1");
        System.out.println("*******");
        taskOne();
        System.out.println("---");
        System.out.println("Ostatecznie wybrany punkt: [" + P0[0] + ", " + P0[1] + "] y = " + F0 + " a krok: " + v0);
    }

    void taskOne() {
        boolean foundBetter = false;
        double[] Pr = new double[]{1, 1};
        if (k == 1) Pr = new double[]{Pz[0] + v0, Pz[1]}; //P roboczy
        if (k == 2) Pr = new double[]{Pz[0], Pz[1] + v0}; //P roboczy dla 2 wymiaru
        double Fr = f(Pr);
        System.out.println("Aktualnie analizuje punkt dla " + k + " zmiennej: [" + Pr[0] + ";" + Pr[1] + "] dla ktorej f(x) wynosi: " + Fr + " a krok: " + v0);
        if (Fr < F0) {
            System.out.println("Sukces - ten punkt staje sie nowym punktem roboczym.");
            P0 = Pr;
            F0 = Fr;
            foundBetter = true;
            nothingChanged = false;
        }else{
            System.out.println("Nie wybieramy tego punktu (poprzednik jest lepszy).");
        }
        if (secondTested && !foundBetter) v0 = -1 * v0;
        if (!secondTested) {
            System.out.println("Sprawdzam przeciwny kierunek:");
            v0 = -1 * v0;
            secondTested = true;
            taskOne();
        }
        Pz = P0;
        if (k < N) {
            System.out.println("---");
            System.out.println("Sprawdzam druga zmienna");
            secondTested = false;
            k++;
            taskOne();
        }
    }

    void taskTwo() {
        double x1Diff = Pp[0] + 1.5 * (Pz[0] - Pp[0]);
        double x2Diff = Pp[1] + 1.5 * (Pz[1] - Pp[1]);
        double newValue = f(new double[]{x1Diff, x2Diff});
        System.out.println("Sprawdzam punkt: [" + x1Diff + "; " + x2Diff + "] dla ktorego f(x) wynosi: " + newValue);
        if (Math.abs((newValue - F0)) == 0) taskFour();
        if (newValue < F0 && (Math.abs((newValue - F0)) > epsilon)) {
            System.out.println("Ten punkt spelnia kryteria - jest mniejszy od poprzednika oraz roznica miesci sie w oczekiwanym przedziale.");
            System.out.println();
            Pp = Pz;
            Pz = new double[]{x1Diff, x2Diff};
            F0 = f(Pz);
            taskTwo();
        }
    }

    void taskFour() {
        if (v0 > epsilon) {
            System.out.println("Epsilon: " + epsilon);
            System.out.println("Stare v0: " + v0);
            v0 = 0.1 * v0;
            System.out.println("Nowe v0: " + v0);
            nothingChanged = false;
        } else {
            System.out.println("***");
            System.out.println("Przekroczono oczekiwana dokladnosc (" + epsilon + "). Algorytm konczy dzialanie");
            System.out.println("***");
            nothingChanged = true;
        }
    }

    double f(double[] args) {
        double x1 = args[0];
        double x2 = args[1];
        return -x1 * x1 - x1 * x1 * x2 * x2 + 3 * x1 * x2;
    }
}