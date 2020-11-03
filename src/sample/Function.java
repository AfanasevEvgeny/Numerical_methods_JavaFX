package sample;


public class Function {
    Double constant;
    public Function(Double y0,Double x0){
        this.constant=y0-Math.sin(x0)/Math.cos(x0);
    }
    public Double return_y_set3(Double x){
        return Math.sin(x)+constant*Math.cos(x);
    }
    public Double return_f_of_x_y_set3(Double x, Double y){
        return 1/Math.cos(x)-y*Math.tan(x);
    }
}
