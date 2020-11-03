package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Controller {

    Double x0, y0, X;
    Integer n;
    @FXML
    private TextField input_N;

    @FXML
    private TextField input_x0;

    @FXML
    private TextField input_X;

    @FXML
    private TextField input_y0;
    @FXML
    LineChart<Double, Double> exactGraph;
    @FXML
    LineChart<Double, Double> errorsGraph;
    @FXML
    LineChart<Double, Double> gteGraph;

    public void setGraphOfMethods(javafx.event.ActionEvent actionEvent) {
        //.........Get information from text Fields.............. ....//
        x0 = Double.parseDouble(input_x0.getText());
        y0 = Double.parseDouble(input_y0.getText());
        n = Integer.parseInt(input_N.getText());
        X = Double.parseDouble(input_X.getText());
        //...................Initialize methods........................//
        Euler_Method eu1 = new Euler_Method(n, X, x0, y0);
        ImprovedEuler ie1 = new ImprovedEuler(n,X,x0,y0);
        Runge_Kutta rk1=new Runge_Kutta(n,X,x0,y0);
        //...................Build functions.....................//
        buildMethodsAndExact(eu1,ie1,rk1);
        buildErrors(eu1,ie1,rk1);


    }
    public void setGTE(javafx.event.ActionEvent actionEvent) {
        buildGTE();
    }

    void buildGTE(){
        GTE gte = new GTE();
        gte.apply(n, X, x0, y0);
        XYChart.Series<Double, Double> euler_gte = new XYChart.Series<Double, Double>();
        euler_gte.setName("euler GTE");
        XYChart.Series<Double, Double> i_euler_gte = new XYChart.Series<Double, Double>();
        i_euler_gte.setName("improved euler GTE");
        XYChart.Series<Double, Double> rk_gte = new XYChart.Series<Double, Double>();
        rk_gte.setName("runge kutta GTE");
        for (int i = 1; i < n; i++) {
            euler_gte.getData().add(new XYChart.Data(i,gte.gte_euler[i]));
            i_euler_gte.getData().add(new XYChart.Data(i,gte.gte_improved_euler[i]));
            rk_gte.getData().add(new XYChart.Data(i,gte.gte_runge_kutta[i]));

        }
        gteGraph.getData().clear();
        gteGraph.setCreateSymbols(false);
        gteGraph.getData().addAll(euler_gte,i_euler_gte,rk_gte);
    }

    void buildErrors(Euler_Method eu1,ImprovedEuler ie1,Runge_Kutta rk1){
        errorsGraph.getData().clear();
        errorsGraph.setCreateSymbols(false);
        XYChart.Series<Double, Double> euler_error = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            euler_error.getData().add(new XYChart.Data(eu1.x_array[i],eu1.error[i]));
        }
        euler_error.setName("Euler error");
        XYChart.Series<Double, Double> improved_euler_error = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            improved_euler_error.getData().add(new XYChart.Data(ie1.x_array[i],ie1.error[i]));
        }
        improved_euler_error.setName("Improved euler error");
        XYChart.Series<Double, Double> rk_error = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            rk_error.getData().add(new XYChart.Data(rk1.x_array[i],rk1.error[i]));
        }
        rk_error.setName("Runge-Kutta error");
        errorsGraph.getData().addAll(euler_error,improved_euler_error,rk_error);
    }

    void buildMethodsAndExact(Euler_Method eu1,ImprovedEuler ie1,Runge_Kutta rk1){
        exactGraph.getData().clear();
        Function analytic_solution = new Function(y0, x0);
        exactGraph.setCreateSymbols(false);
        //................implement series...........................//
        XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        series.setName("exact solution");
        //...................Euler graph.............................//
        XYChart.Series<Double, Double> euler_series = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            euler_series.getData().add(new XYChart.Data(eu1.x_array[i], eu1.y_array[i]));
        }
        euler_series.setName("euler method");
        //....................Improved euler graph....................//
        XYChart.Series<Double, Double> improved_euler_series = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            improved_euler_series.getData().add(new XYChart.Data(ie1.x_array[i], ie1.y_array[i]));
        }
        improved_euler_series.setName("improved euler");
        //...................Runge-Kutta graph........................//
        XYChart.Series<Double, Double> runge_kutta_series = new XYChart.Series<Double, Double>();
        for (int i = 0; i < n; i++) {
            runge_kutta_series.getData().add(new XYChart.Data(rk1.x_array[i], rk1.y_array[i]));
        }
        runge_kutta_series.setName("Runge-Kutta");
        //....................exact graph.............................//
        for (double x = x0; x < X; x += 0.01) {
            series.getData().add(new XYChart.Data<Double, Double>(x, analytic_solution.return_y_set3(x)));
        }
        exactGraph.getData().addAll(series, euler_series,improved_euler_series,runge_kutta_series);
    }



}
