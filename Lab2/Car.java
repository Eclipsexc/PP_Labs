package Lab2;

public class Car {
    private int id;
    private String model;
    private int year;
    private double price;
    private String registrationNumber;

    public Car(int id, String model, int year, double price, String registrationNumber) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
        this.registrationNumber = registrationNumber;
    }

    public int getId() {return id;}

    public String getModel() {return model;}

    public int getYear() {return year;}

    public double getPrice() {return price;}

    public String getRegistrationNumber() {return registrationNumber;}


    public void setId(int id) {
        if(id >= 0){ this.id = id; }
        else {
            System.out.println("Некоректне значення для id");
        }
    }

    public void setModel(String model) {this.model = model;}

    public void setYear(int year) {
        if (year > 0) this.year = year;
        else{
            System.out.println("Значення року повинно бути додатнім");
        }
    }

    public void setPrice(double price) {
        if(price>0) this.price = price;
        else {
            System.out.println("Ціна не може бути від'ємною");
        }
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String toString() {
        return String.format(
                "Car:\n" +
                        "  ID: %d\n" +
                        "  Model: %s\n" +
                        "  Year: %d\n" +
                        "  Price: %.2f\n" +
                        "  Registration Number: %s\n",
                id, model, year, price, registrationNumber
        );
    }

}