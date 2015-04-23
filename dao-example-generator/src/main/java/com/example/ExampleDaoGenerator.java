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
        addFaceNews(schema);
        //addLineBw(schema);
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

    private static void addFaceNews(Schema schema) {
        Entity section = schema.addEntity("FaceNews");
        section.addStringProperty("faceId").primaryKey();
        section.addStringProperty("faceCode");
        section.addStringProperty("faceName");
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

    private static void addLineBw(Schema schema) {
        Entity line = schema.addEntity("Line");
        line.addIdProperty().primaryKey().autoincrement();
        line.addStringProperty("lc");
        line.addStringProperty("ln");

        Entity bw = schema.addEntity("Bw");
        bw.addLongProperty("Bwid").autoincrement();
        bw.addStringProperty("id");
        bw.addStringProperty("od");
        bw.addStringProperty("ty");
        Property lineid=bw.addIntProperty("lineid").notNull().getProperty();
        bw.addToOne(line, lineid);

        ToMany lineToBws = line.addToMany(bw, lineid);
    }
}

