package com.example;

import java.util.TooManyListenersException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;



public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "rwcjom.awit.com.rwcjo_m.dao");
        addFaceNews(schema);
        addFaceInfo(schema);
        addBrgInfo(schema);
        addPntInfo(schema);
        addPersonInfo(schema);
        addBasePntInfo(schema);
        addLineBw(schema);
        addSectionSite(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
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
        section.addStringProperty("f_siteid");
        section.addStringProperty("f_faceid");
    }

    private static void addBrgInfo(Schema schema) {
        Entity section = schema.addEntity("BrgInfo");
        section.addStringProperty("faceid").primaryKey();
        section.addStringProperty("structname");
        section.addStringProperty("piernum");
        section.addStringProperty("beamspan");
        section.addStringProperty("beamtype");
        section.addStringProperty("remark");
        section.addStringProperty("f_siteid");
        section.addStringProperty("f_faceid");
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
        section.addStringProperty("f_faceid");
    }

    private static void addPersonInfo(Schema schema) {
        Entity section = schema.addEntity("PersonInfo");
        section.addStringProperty("userid").primaryKey();
        section.addStringProperty("username");
        section.addStringProperty("usertel");
        section.addStringProperty("f_sectid");
    }

    private static void addBasePntInfo(Schema schema) {
        Entity section = schema.addEntity("BasePntInfo");
        section.addStringProperty("siteid").primaryKey();
        section.addStringProperty("sitename");
        section.addStringProperty("sitecode");
        section.addStringProperty("sitehigh");
        section.addStringProperty("sitenum");
        section.addStringProperty("sitevar");
        section.addStringProperty("f_sectid");
    }

    private static void addFaceNews(Schema schema) {
        Entity section = schema.addEntity("FaceNews");
        section.addStringProperty("faceId").primaryKey();
        section.addStringProperty("faceCode");
        section.addStringProperty("faceName");
        section.addStringProperty("f_siteid");
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


    private static void addLineBw(Schema schema) {
        Entity line = schema.addEntity("Line");
        line.addStringProperty("lc").primaryKey();
        line.addStringProperty("ln");

        Entity bw = schema.addEntity("BwInfo");
        bw.addLongProperty("bwid").primaryKey().autoincrement();
        bw.addStringProperty("id");
        bw.addStringProperty("od");
        bw.addStringProperty("ty");
        Property f_lineid = bw.addStringProperty("f_lineid").notNull().getProperty();
        bw.addToOne(line, f_lineid);
        
        ToMany lineToBws = line.addToMany(bw, f_lineid);
    }
}