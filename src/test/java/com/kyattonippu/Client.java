package com.kyattonippu;

import java.util.List;

public class Client {

    public String firstName;
    public String lastName;
    public String gender;
    public int age;
    public Address address;
    public List<String> contacts;

    public static class Address {
        public String country;
        public String city;
        public String region;
        public String postalCode;
    }
}
