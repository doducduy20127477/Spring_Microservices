package programming;
import java.util.ArrayList;
import java.util.List;


public class FP01Exercises {
    public static void main (String[] args) {

//        printOddNumbersInListFunctional(List.of(12, 9, 13, 4, 6, 2, 4, 12,15));
        List<String> courses = List.of("Spring", "Spring Boot", "API", "Microservice", "AWS"
                            , "PCF", "Azure", "Docker", "Kubernetes");
//        courses.stream()
//                .forEach(System.out::println);

//                courses.stream()
//                        .filter(course -> course.contains("Spring"))
//                        .forEach(System.out::println);

//        courses.stream()
//                .filter(course -> course.length() >= 4)
//                .forEach(System.out::println);

//        printCubesOfOddNumbersInListFunctional(List.of(12, 9, 13, 4, 6, 2, 4, 12,15));

//                courses.stream()
//                    .forEach(course -> System.out.println(course + " " + course.length()));

        courses.stream()
                .map(course -> course + " " + course.length())
                .forEach(System.out::println);
    }

    private static void printAllNumbersInListFunctional (List<Integer> numbers) {
        numbers.stream()
                .forEach(System.out::println);

    }
    private static void printOddNumbersInListFunctional (List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 != 0)
                .forEach(System.out::println);

    }
    private static void printCubesOfOddNumbersInListFunctional (List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * number * number)
                .forEach(System.out::println);

    }
}
