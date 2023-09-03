package com.example.lakshmiproject;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Optionals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class OptionalsTest {
    @Test // ways to create optionals
    public void testOptionals(){
        // empty is used to empty optional. the optional will not have any value.
        Optional<Integer> age = Optional.empty();
        // orElse method is part of optionals. it will return the value in optional if the value is not null
        //or the value defined in orElse if the optional is empty.
        System.out.println("empty optional"+age.orElse(23));
        // of is used to create an optional with value , if the value is null it will throw null pointer exception.
        Optional<String> name = Optional.of("Lakshmi");
        System.out.println("my name non-empty optional"+name.orElse("sonu"));
        try{
            Optional<Long> salary = Optional.of(null);
        }
        catch (Exception e){
            System.out.println("this exception is caused as the optional is empty"+e);
        }
        //3 way ofNullable accepts both null and not null. If the value is null it returns empty optional,
        // if not it will use of() internally to create optional
        Optional<String> nonemptyOptional = Optional.ofNullable("Lakshmi");
        System.out.println(nonemptyOptional.orElse("emptyOptional"));
        Optional<String> emptyOptional = Optional.ofNullable(null);
        System.out.println(emptyOptional.orElse("emptyOptional"));
    }

    @Test // how to check whether optionals have any value
    public void testOptionalEmptiness(){
        // isPresent and isEmpty returns boolean values to check for the optionalsEmptiness.
        Optional<String> emptyOptional = Optional.ofNullable(null);
        System.out.println("optionalemptiness using isEmpty --- "+emptyOptional.isEmpty());
        System.out.println("optionalemptiness using isPresent --- "+emptyOptional.isPresent());
    }

    @Test // how to handle the Scenarios of Optionals being Empty
    //orElse - orElseGet - ifPresentOrElse
    public void test5(){
        Optional<Integer> pincodeOptional = Optional.empty();
        // or else can accept a default or a function call that genereates default value.
        int pin = pincodeOptional.orElse(45);

        Optional<Integer> demo = Optional.of(75);
        // or else can accept a default or a function call that genereates default value.
        int demo2 = pincodeOptional.orElse(normalFunction());

        Optional<Integer> customerNo = Optional.empty();
        // or elseGet will accept function definition or the functional call that should be called if the data is null
        // and will be called only if the data is null.
        int no = customerNo.orElseGet(() -> {
            System.out.println("I am only called if the optional is empty, initialize value to this optional you done see me getting called");
            return 30;
        });
       // Orelse only accepts values or function calls that determine value – whether orElseget accepts methods, I mean. We have a scenario where we have to assign
       // defalut value if optional is empty. Then we use orElse. If the scenarios is if the optional is empty to want to
       // make db call or some other method to calculate default value we use orElseGet() , so that corresponding method
       // id called only during null scenario.

        Optional<Integer> customerNos = Optional.ofNullable(45);
        // ifPresentOrElse - if the value is not empty it will be passed to the first method impelmentation ,
        // empty to second one unlike others they don't need to return anything. they just get called
        customerNos.ifPresentOrElse((i)-> System.out.println("Non-empty optional handled in ifPresentOrelse" + i),
                ()-> System.out.println("empty optional handled in ifPresentOrelse"));

    }
    public int  normalFunction(){
        System.out.println("I am getting called by OrElse eventhough the optionals has the value");
        return 20;
    }

    @Test
    // filter accepts a condition in the form of predicate or ananymous function and
    // returns an empty optional or the acutal optional depending on condition.
    public void processingOfOptionalsUsingFilter(){
        Optional<List<Integer>> orderIds = Optional.of(Arrays.asList(23,45,67));
        // if the customer has placed more than 5 orders print valid for offer. if not print not valid for offer.
        System.out.println(orderIds.filter((i) -> i.size() >5));
        //predicate and <input to predicate> and on right side anonymous function that returns boolean;
        Predicate<List<Integer>> filterOrdersForOffers = (i) -> i.size() > 5;
        System.out.println("using predicate and filter"+orderIds.filter(filterOrdersForOffers));
        Optional<List<Integer>> orderIds2 = Optional.of(Arrays.asList(23,45,67,99,78,67,90,78));
        orderIds2.filter(filterOrdersForOffers).ifPresentOrElse((i)-> System.out.println("customer  eligible for Offer and order ids are" + i),
                () -> System.out.println("customer not eligible for Offer"));
    }

}
