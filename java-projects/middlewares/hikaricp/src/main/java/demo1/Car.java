package demo1;

/**
 * @author agui93
 * @since 2021/11/17
 */
public class Car {

    private Long Id;
    private String name;
    private int price;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" + "Id=" + Id + ", name=" + name + ", price=" + price + '}';
    }
}
