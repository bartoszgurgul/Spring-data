package pl.javastart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.javastart.model.Car;
import pl.javastart.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
public class SpringDataAppliaction {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringDataAppliaction.class);
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A4", "Audi", 49000.0));
        cars.add(new Car("Auris", "Toyota", 35000.0));
        cars.add(new Car("Insignia", "Opel", 29500.0));

        CarRepository bean = context.getBean(CarRepository.class);
        cars.forEach(bean::save);

        Car car = bean.findById(1L).get();
        bean.delete(car);


        bean.findAll().forEach(System.out::println);

        context.close();
    }
}
