import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

public class DashboardController {

    // Filters
    ComboBox<String> cbYear = new ComboBox<>();
    ComboBox<String> cbMonth = new ComboBox<>();
    ComboBox<String> cbCity = new ComboBox<>();
    ComboBox<String> cbPayment = new ComboBox<>();

    // KPIs
    Label lblTotalSales = new Label();
    Label lblAOV = new Label();
    Label lblCustomerCount = new Label();
    Label lblOrderCount = new Label();

    // Charts
    BarChart<String, Number> chartSalesByMonth;
    PieChart chartPayment;
    BarChart<String, Number> chartCity;
    BarChart<String, Number> chartAge;

    // Data
    List<SalesRecord> dataset = new ArrayList<>();

    public DashboardController() {
        seedData();
    }

    public Pane createUI() {

        // Top filters
        Label title = new Label("Smart Home Dashboard");
        title.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");

        HBox filters = new HBox(10,
                new Label("Year:"), cbYear,
                new Label("Month:"), cbMonth,
                new Label("City:"), cbCity,
                new Label("Payment:"), cbPayment
        );
        filters.setAlignment(Pos.CENTER_LEFT);

        VBox top = new VBox(8, title, filters);
        top.setPadding(new Insets(10));

        // KPIs
        HBox kpiBox = new HBox(12,
                createKpi("Total Sales", lblTotalSales),
                createKpi("AOV", lblAOV),
                createKpi("Customer Count", lblCustomerCount),
                createKpi("Order Count", lblOrderCount)
        );
        kpiBox.setPadding(new Insets(10));

        // Charts
        chartSalesByMonth = new BarChart<>(new CategoryAxis(), new NumberAxis());
        chartPayment      = new PieChart();
        chartCity         = new BarChart<>(new CategoryAxis(), new NumberAxis());
        chartAge          = new BarChart<>(new CategoryAxis(), new NumberAxis());

        chartSalesByMonth.setTitle("Total Sales by Month");
        chartCity.setTitle("Total Sales by City");
        chartPayment.setTitle("Payment Methods");
        chartAge.setTitle("Total Sales by Age");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        grid.add(chartSalesByMonth, 0, 0, 2, 1);
        grid.add(chartPayment,      2, 0);
        grid.add(chartCity,         0, 1);
        grid.add(chartAge,          1, 1);

        ColumnConstraints c0 = new ColumnConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c0.setPercentWidth(40);
        c1.setPercentWidth(30);
        c2.setPercentWidth(30);
        grid.getColumnConstraints().addAll(c0, c1, c2);

        VBox center = new VBox(10, kpiBox, grid);
        center.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        setupFilters();
        refresh();

        cbYear.setOnAction(e -> refresh());
        cbMonth.setOnAction(e -> refresh());
        cbCity.setOnAction(e -> refresh());
        cbPayment.setOnAction(e -> refresh());

        return root;
    }

    private VBox createKpi(String title, Label value) {
        Label t = new Label(title);
        t.setStyle("-fx-font-size:12px;");
        value.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");
        VBox box = new VBox(5, t, value);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color:#f0f0f0; -fx-border-color:#ccc;");
        box.setPrefWidth(200);
        return box;
    }

    private void setupFilters() {
        cbYear.setItems(FXCollections.observableArrayList("All", "2024"));
        cbYear.getSelectionModel().select(0);

        cbMonth.setItems(FXCollections.observableArrayList(
                "All", "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"
        ));
        cbMonth.getSelectionModel().select(0);

        cbCity.setItems(FXCollections.observableArrayList("All", "Cairo", "Alex", "Giza"));
        cbCity.getSelectionModel().select(0);

        cbPayment.setItems(FXCollections.observableArrayList("All", "Cash", "Card", "Wallet"));
        cbPayment.getSelectionModel().select(0);
    }

    private void refresh() {
        List<SalesRecord> filtered = filterData();

        updateKPIs(filtered);
        updateSalesByMonth(filtered);
        updateCity(filtered);
        updateAge(filtered);
        updatePayment(filtered);
    }

    private List<SalesRecord> filterData() {
        String y = cbYear.getValue();
        String m = cbMonth.getValue();
        String c = cbCity.getValue();
        String p = cbPayment.getValue();

        return dataset.stream().filter(r ->
                (y.equals("All") || r.year.equals(y)) &&
                (m.equals("All") || r.month.equals(m)) &&
                (c.equals("All") || r.city.equals(c)) &&
                (p.equals("All") || r.payment.equals(p))
        ).toList();
    }

    private void updateKPIs(List<SalesRecord> list) {
        double total = list.stream().mapToDouble(r -> r.amount).sum();
        int orders = list.size();
        double aov = orders == 0 ? 0 : total / orders;

        lblTotalSales.setText(String.format("%.2fK", total/1000));
        lblAOV.setText(String.format("%.2f", aov));
        lblCustomerCount.setText(String.valueOf(list.stream().map(r -> r.customerId).distinct().count()));
        lblOrderCount.setText(String.valueOf(orders));
    }

    private void updateSalesByMonth(List<SalesRecord> list) {
        chartSalesByMonth.getData().clear();

        Map<String, Double> map = list.stream().collect(
                Collectors.groupingBy(r -> r.month, Collectors.summingDouble(r -> r.amount))
        );

        XYChart.Series<String, Number> s = new XYChart.Series<>();
        for (String m : Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")) {
            s.getData().add(new XYChart.Data<>(m, map.getOrDefault(m, 0.0)/1000));
        }
        chartSalesByMonth.getData().add(s);
    }

    private void updateCity(List<SalesRecord> list) {
        chartCity.getData().clear();
        Map<String, Double> map = list.stream().collect(
                Collectors.groupingBy(r -> r.city, Collectors.summingDouble(r -> r.amount))
        );
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        map.forEach((k,v)-> s.getData().add(new XYChart.Data<>(k, v/1000)));
        chartCity.getData().add(s);
    }

    private void updateAge(List<SalesRecord> list) {
        chartAge.getData().clear();
        Map<String, Double> map = list.stream().collect(
                Collectors.groupingBy(r -> ageBucket(r.age), Collectors.summingDouble(r -> r.amount))
        );
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        for (String a : Arrays.asList("20","30","40","50","60","70")) {
            s.getData().add(new XYChart.Data<>(a, map.getOrDefault(a,0.0)/1000));
        }
        chartAge.getData().add(s);
    }

    private void updatePayment(List<SalesRecord> list) {
        chartPayment.getData().clear();
        Map<String, Double> map = list.stream().collect(
                Collectors.groupingBy(r -> r.payment, Collectors.summingDouble(r -> r.amount))
        );
        map.forEach((k,v)-> chartPayment.getData().add(new PieChart.Data(k, v)));
    }

    private String ageBucket(int age) {
        if (age < 30) return "20";
        if (age < 40) return "30";
        if (age < 50) return "40";
        if (age < 60) return "50";
        if (age < 70) return "60";
        return "70";
    }

    private void seedData() {
        Random r = new Random();
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String[] cities = {"Cairo","Alex","Giza"};
        String[] payments = {"Cash","Card","Wallet"};

        for (int i = 1; i <= 3000; i++) {
            dataset.add(new SalesRecord(
                    i,
                    "2024",
                    months[r.nextInt(12)],
                    cities[r.nextInt(cities.length)],
                    payments[r.nextInt(payments.length)],
                    18 + r.nextInt(60),
                    50 + r.nextDouble()*1500
            ));
        }
    }

    private static class SalesRecord {
        int customerId;
        String year, month, city, payment;
        int age;
        double amount;

        public SalesRecord(int customerId, String year, String month, String city, String payment, int age, double amount) {
            this.customerId = customerId;
            this.year = year;
            this.month = month;
            this.city = city;
            this.payment = payment;
            this.age = age;
            this.amount = amount;
        }
    }
}
