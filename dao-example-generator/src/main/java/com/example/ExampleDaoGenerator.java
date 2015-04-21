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
        addFaceInfo(schema);
        addBrgInfo(schema);
        addPntInfo(schema);
        addPersonInfo(schema);
        addBasePntInfo(schema);
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

    private static void addFaceNews(Schema schema) {
        Entity section = schema.addEntity("FaceNews");
        section.addStringProperty("faceid").primaryKey();
        section.addStringProperty("facecode");
        section.addStringProperty("facename");
    }

    private static void addFaceInfo(Schema schema) {
        Entity section = schema.addEntity("FaceInfo");
        section.addStringProperty("faceid").primaryKey();
        section.addStringProperty("jointflag");
        section.addStringProperty("structtype");
        section.addStringProperty("structname");
        section.addStringProperty("structbase");
        section.addStringProperty("designatt");
        section.addStringProperty("piernum");
        section.addStringProperty("dkname");
        section.addStringProperty("dkilo");
        section.addStringProperty("dchain");
        section.addStringProperty("rkname");
        section.addStringProperty("rkilo");
        section.addStringProperty("rchain");
        section.addStringProperty("remark");
    }

    private static void addBrgInfo(Schema schema) {
        Entity section = schema.addEntity("BrgInfo");
        section.addStringProperty("faceid").primaryKey();
        section.addStringProperty("structname");
        section.addStringProperty("piernum");
        section.addStringProperty("beamspan");
        section.addStringProperty("beamtype");
        section.addStringProperty("remark");
    }

    private static void addPntInfo(Schema schema) {
        Entity section = schema.addEntity("PntInfo");
        section.addStringProperty("pointid").primaryKey();
        section.addStringProperty("pointnum");
        section.addStringProperty("designvalue");
        section.addStringProperty("designremark");
        section.addStringProperty("inbuiltdate");
        section.addStringProperty("seatcode");
        section.addStringProperty("remark");
        section.addStringProperty("pointcode");
        section.addStringProperty("name");
    }

    private static void addPersonInfo(Schema schema) {
        Entity section = schema.addEntity("PersonInfo");
        section.addStringProperty("userid").primaryKey();
        section.addStringProperty("username");
        section.addStringProperty("usertel");
    }

    private static void addBasePntInfo(Schema schema) {
        Entity section = schema.addEntity("BasePntInfo");
        section.addStringProperty("siteid").primaryKey();
        section.addStringProperty("sitename");
        section.addStringProperty("sitecode");
        section.addStringProperty("sitehigh");
        section.addStringProperty("sitenum");
        section.addStringProperty("sitevar");
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
