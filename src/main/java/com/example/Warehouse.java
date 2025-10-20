package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Warehouse {
    private final List<Product> wares = new ArrayList<>();
    private final List<Product> changedWares = new ArrayList<>();
    private static final Map<String, Warehouse> warehouses = new HashMap<>();
    private final String name;

    private Warehouse(String name) {
        this.name = name.trim();
    }

    private static Warehouse setAndOrGet(String name) {
        warehouses.putIfAbsent(name, new Warehouse(name));
        return warehouses.get(name);
    }

    public static Warehouse getInstance(String name) {
        return setAndOrGet(name.trim());
    }

    public static Warehouse getInstance(){
        return getInstance("example");
    }

    public void addProduct(Product p){
        if(p == null){
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if(!getProductById(p.uuid()).equals(Optional.empty())){
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        wares.add(p);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(wares);
    }

    public Optional<Product> getProductById(UUID id){
        return wares.stream().filter(p -> p.uuid().equals(id)).findFirst();
    }

    public void updateProductPrice(UUID id, BigDecimal price){
        Product p = getProductById(id).orElseThrow(() ->new NoSuchElementException("Product not found with id:" + id));
        p.price(price);
        changedWares.add(p);
    }

    public List<Perishable> expiredProducts() {
        return wares.stream().filter(p -> p instanceof Perishable).map(p -> (Perishable) p).filter(Perishable::isExpired).toList();
    }

    public List<Shippable> shippableProducts() {
        return wares.stream().filter(p -> p instanceof Shippable).map(p -> (Shippable) p).toList();
    }

    public void remove(UUID id){
        wares.removeIf(p -> p.uuid().equals(id));
    }

    public boolean isEmpty(){
        return wares.isEmpty();
    }

    public void clearProducts(){
        wares.clear();
        changedWares.clear();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return wares.stream().collect(Collectors.toMap(k -> k.category(), v1 -> List.of(v1), (v1, v2) -> Stream.concat(v1.stream(), v2.stream()).toList()));
    }
}
