package sample;

public class GTE {
    Double gte_euler[];
    Double gte_improved_euler[];
    Double gte_runge_kutta[];
    void apply(int N,Double X,Double x0, Double y0) {
        gte_euler=new Double[N];
        gte_improved_euler=new Double[N];
        gte_runge_kutta=new Double[N];
        for (int i = 1; i < N; i++) {
            Euler_Method euler = new Euler_Method(i,X,x0,y0);
            ImprovedEuler improvedEuler = new ImprovedEuler(i,X,x0,y0);
            Runge_Kutta runge_kutta = new Runge_Kutta(i,X,x0,y0);

            gte_euler[i]=euler.maxError;
            gte_improved_euler[i]=improvedEuler.maxError;
            gte_runge_kutta[i]=runge_kutta.maxError;

        }
    }
}
