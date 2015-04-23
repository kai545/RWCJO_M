package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;



public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "rwcjom.awit.com.rwcjo_m.dao");

//        addSection(schema);
//        addSite(schema);
//        addFaceNews(schema);
        addLineBw(schema);
        addSectionSite(schema);
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

    private static void addSectionSite(Schema schema) {
        Entity section = schema.addEntity("SecNews");
        section.addStringProperty("sectid").primaryKey();
        section.addStringProperty("sectcode");
        section.addStringProperty("sectname");

        Entity site = schema.addEntity("SiteNews");
        site.addStringProperty("siteid").primaryKey();
        site.addStringProperty("sitecode");
        site.addStringProperty("sitename");
        site.addStringProperty("startsite");
        site.addStringProperty("endsite");
        Property f_sectionid = site.addStringProperty("f_sectionid").notNull().getProperty();
        site.addToOne(section, f_sectionid);

        ToMany customerToOrders = section.addToMany(site, f_sectionid);
    }


        private static void addLineBw(Schema schema){
            Entity line = schema.addEntity("Line");
            line.addStringProperty("lc").primaryKey();
            line.addStringProperty("ln");

            Entity bw = schema.addEntity("BwInfo");
            bw.addLongProperty("bwid").primaryKey().autoincrement();
            bw.addStringProperty("id");
            bw.addStringProperty("od");
            bw.addStringProperty("ty");
            Property f_lineid=bw.addStringProperty("f_lineid").notNull().getProperty();
            bw.addToOne(line, f_lineid);

            ToMany lineToBws = line.addToMany(bw, f_lineid);
    }
}

