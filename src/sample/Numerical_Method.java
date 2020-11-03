package sample;

public class Numerical_Method {
    protected Double[] x_array;
    protected Double[] y_array;
    protected Double[] y_exact_array;
    Double X, h;
    Integer N;
    Double[] error;
    Function function;
    Double maxError;
    public Numerical_Method(Integer N, Double X, Double x0, Double y0) {
        this.function = new Function(y0, x0);
        this.N = N;
        this.X = X;
        this.h = (X - x0) / N;
        x_array = new Double[N];
        y_array = new Double[N];
        x_array[0] = x0;
        y_array[0] = y0;
        y_exact_array = new Double[N];
        y_exact_array[0]=y0;
        for (int i = 1;i<N;i++){
            x_array[i]=x_array[i-1]+h;
            y_exact_array[i]=function.return_y_set3(x_array[i]);
        }
        error = new Double[N];
        error[0]=0.0;
        this.maxError=0.0;
    }


}

class Euler_Method extends Numerical_Method {
    public Euler_Method(Integer N, Double X, Double x0, Double y0) {
        super(N, X, x0, y0);
        applyMethod();
    }

    void applyMethod() {
        for (int i = 1; i < N; i++) {
            y_array[i] = y_array[i - 1] + h *function.return_f_of_x_y_set3(x_array[i - 1], y_array[i - 1]);
            error[i]=Math.abs(y_array[i]-y_exact_array[i]);
            if (error[i]>maxError)maxError=error[i];
        }
    }
}

class ImprovedEuler extends Numerical_Method {
    Double k1i,k2i;

    public ImprovedEuler(Integer N, Double X, Double x0, Double y0) {
        super(N, X, x0, y0);
        applyMethod();
    }

    void applyMethod() {
        //...........................this algorithm IEM was taken from.................................//
        //...https://math.libretexts.org/Courses/Monroe_Community_College/MTH_225_Differential_Equations/3%3A_Numerical_Methods/3.2%3A_The_Improved_Euler_Method_and_Related_Methods.................................//
        for (int i = 1; i < N; i++) {
            k1i=function.return_f_of_x_y_set3(x_array[i-1],y_array[i-1]);
            k2i=function.return_f_of_x_y_set3(x_array[i-1]+h,y_array[i-1]+h*k1i);
            y_array[i]=y_array[i-1]+h*(k1i+k2i)/2;
            x_array[i]=x_array[i-1]+h;
            error[i]=Math.abs(y_array[i]-y_exact_array[i]);
            if (error[i]>maxError)maxError=error[i];
            System.out.println("exact:"+y_exact_array[i]+" ie: "+y_array[i]+"step n:"+ N);
        }
    }
}

class Runge_Kutta extends Numerical_Method{
    public Runge_Kutta(Integer N, Double X, Double x0, Double y0){
        super(N,X,x0,y0);
        applyMethod();
    }
    double k1,k2,k3,k4;
    void applyMethod(){
        for (int i = 1; i < N; i++) {
            k1= function.return_f_of_x_y_set3(x_array[i-1],y_array[i-1]);
            k2= function.return_f_of_x_y_set3(x_array[i-1]+h/2,y_array[i-1]+h*k1/2);
            k3=function.return_f_of_x_y_set3(x_array[i-1]+h/2,y_array[i-1]+h*k2/2);
            k4=function.return_f_of_x_y_set3(x_array[i-1]+h,y_array[i-1]+h*k3);
            y_array[i]=y_array[i-1]+h*(k1+2*k2+2*k3+k4)/6;
            x_array[i]=x_array[i-1]+h;
            error[i]=Math.abs(y_array[i]-y_exact_array[i]);
            if (error[i]>maxError)maxError=error[i];
        }
    }
}
