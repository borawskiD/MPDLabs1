package com.company;

public class Task {
    double previousPoint[] = new double[]{1,1};
    double valueOfPreviousPoint = F(previousPoint[0], previousPoint[1]);
    double currentPoint[] = new double[]{1,1};
    double valueOfCurrentPoint = F(currentPoint[0], currentPoint[1]);
    boolean timeToStop = false;
    final double EPSILON = 0.0001;
    double v = 1;
    public void run(){
        do{
            findNewPoint();
            moveForward();
            if (v < EPSILON) timeToStop = true;
            else{
                //System.out.println("Wartosc kroku przekracza dokladnosc (epsilon). Algorytm konczy dzialanie");
                System.out.println("Krok: " + v);
                System.out.println("Epsilon: " + EPSILON);
            }

        }while(!timeToStop);
    }

    private void moveForward() {
        valueOfCurrentPoint = F(currentPoint[0], currentPoint[1]);
        if (valueOfCurrentPoint < valueOfPreviousPoint){
            double x1 = previousPoint[0] + 1.5 * (currentPoint[0] - previousPoint[0]);
            double x2 = previousPoint[1] + 1.5 * (currentPoint[1] - previousPoint[1]);
            System.out.println("Punkt po przesunieciu: [" + x1 + ", " + x2 + "]");
            previousPoint = currentPoint;
            valueOfPreviousPoint = valueOfCurrentPoint;
            currentPoint = new double[]{x1, x2};
        }
        else{
            v = 0.2 * v;
        }
    }

    public void findNewPoint(){
        System.out.println("");
        System.out.println("======");
        System.out.println("Wartosc kroku: " + v);
        //first side
        System.out.println("Analiza punktu - 1 wymiar:");
        double[] newValue = checkPoints(currentPoint[0] + v, currentPoint[1]);
        System.out.println("Ostateczny punkt po sprawdzeniu 1. wymiaru: [" + newValue[0] + ",  " + newValue[1]+"] gdzie f(x) = " + F(newValue[0], newValue[1]));
        //second side
        System.out.println("---");
        System.out.println("Analiza punktu - 2 wymiar:");
        newValue = checkPoints(currentPoint[0], currentPoint[1] + v);
        System.out.println("Ostateczny punkt po sprawdzeniu 2. wymiaru: [" + newValue[0] + ",  " + newValue[1]+"] gdzie f(x) = " + F(newValue[0], newValue[1]));
        if (newValue == currentPoint) v = 1.5 * v;
        else{
            System.out.println("Znaleziony punkt okazuje sie najlepszym - koniec dzialania programu");
            timeToStop = true;
        }
    }
    double[] checkPoints(double x1, double x2){
        //doing full analysis for 1d
        double[] newPoint = new double[]{x1, x2};
        double fNP = F(newPoint[0], newPoint[1]);
        System.out.println("Aktualnie analizuje punkt: [" + newPoint[0] + ", " + newPoint[1] + "]" + " gdzie f(x) = " + fNP);
        if (fNP < valueOfCurrentPoint){
            currentPoint = newPoint;
            System.out.println("Jest lepszy od poprzednika, zatem staje sie najlepszym punktem.");
        }else{
            System.out.println("Nie jest lepszy od poprzednika, zatem analizuje drugi punkt:");
            newPoint[0] = newPoint[0] - 2 * v;
            fNP = F(newPoint[0], newPoint[1]);
            System.out.println("Aktualnie analizuje punkt: [" + newPoint[0] + ", " + newPoint[1] + "]" + " gdzie f(x) = " + fNP);
            if (fNP < valueOfCurrentPoint){
                currentPoint = newPoint;
                System.out.println("Jest lepszy od poprzednikow, zatem staje sie najlepszym punktem");
            }else{
                System.out.println("Nie lepszy od poprzednikow, nic nie zmieniam");
            }
        }
        return newPoint;
    }
    double F(double x1, double x2){
        return  -x1 * x1 - x1 * x1 * x2 * x2 + 3 * x1 * x2;
    }
}
