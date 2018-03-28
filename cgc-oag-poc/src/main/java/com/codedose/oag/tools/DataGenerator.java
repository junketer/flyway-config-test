package com.codedose.oag.tools;

import com.github.javafaker.*;
import com.github.javafaker.Number;
import com.github.javafaker.service.RandomService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Generate csv fake data
 * RECORDS_COUNT - number of generated data
 * see javafaker generator page https://github.com/DiUS/java-faker
 *
 * */
public class DataGenerator {
    private static final String FILE_NAME = "data.csv";
    private static final List<String> HEADERS = new ArrayList<>(Arrays.asList("username", "firstName", "lastName", "fullAddress", "streetAddress", "productName", "price", "phoneNumber", "orderDate"));
    private static final int RECORDS_COUNT = 5_0;;



    private static void addHeaders(StringBuffer sb) {
        for (int i = 0; i < 45; i++) {
            HEADERS.add("B"+i);
        }

        for (int i = 0; i < 6; i++) {
            HEADERS.add("I"+i);
        }

        for (int i = 0; i < 20; i++) {
            HEADERS.add("S"+i);
        }
        for (String header: HEADERS) {
            sb.append(header+";");
        }
        sb.setLength(sb.length() -1);
        sb.append("\n");

    }

    private static final void append(StringBuffer sb, Object... objs) {
        for (Object o : objs) {
            sb.append(o.toString()+";");
        }
    }

    private static void flushToFile(StringBuffer sb) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)));
        pw.append(sb.toString());
        pw.close();
        sb.setLength(0);
    }

    public static void main(String[] args) throws ParseException, IOException {
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

        Date fromDate = format.parse("01-01-2014");
        Date now = new Date();

        Faker faker = new Faker();

        Name name = faker.name();
        Address address = faker.address();
        Commerce commerce = faker.commerce();
        Lorem lorem = faker.lorem();
        PhoneNumber phone = faker.phoneNumber();
        DateAndTime date = faker.date();
        Number number = faker.number();
        RandomService random = faker.random();


        StringBuffer sb = new StringBuffer();
        addHeaders(sb);
        for (int i = 0; i < RECORDS_COUNT; i++) {
            String username = name.username();
            String firstName = name.firstName();
            String lastName = name.lastName();
            String fullAddress = address.fullAddress();

            String streetAddress = address.streetAddress();
            String productName = commerce.productName();
            String price = commerce.price().replace(",", ".");
            String phoneNumber = phone.phoneNumber();
            Date orderDate = date.between(fromDate,now);

            append(sb, username, firstName, lastName, fullAddress, streetAddress, productName, price, phoneNumber, orderDate);



            for (int j = 0; j < 45; j++) {
                int bool = number.numberBetween(0, 2);
                append(sb, bool);
            }

            for (int j = 0; j < 6; j++) {
                long randomLong = random.nextLong();
                append(sb, randomLong);
            }

            for (int j = 0; j < 20; j++) {
                String randomString = lorem.characters(11);
                append(sb, randomString);
            }

            if(sb.length() > 0)
                sb.setLength(sb.length() -1);

            sb.append("\n");

            if(i % 100000 == 0 ) {
                System.out.println(i);
                flushToFile(sb);
            }

        }
    }


}
