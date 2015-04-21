package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "rwcjom.awit.com.rwcjo_m.dao");

        addSection(schema);
        addSite(schema);
        //addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addSection(Schema schema) {
        Entity section = schema.addEntity("SecNews");
        section.addStringProperty("sectid").primaryKey();
        section.addStringProperty("sectcode");
        section.addStringProperty("sectname");
    }

    private static void addSite(Schema schema) {
        Entity section = schema.addEntity("SiteNews");
        section.addStringProperty("siteid").primaryKey();
        section.addStringProperty("sitecode");
        section.addStringProperty("sitename");
        section.addStringProperty("startsite");
        section.addStringProperty("endsite");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
