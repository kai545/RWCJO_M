package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;



public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "rwcjom.awit.com.rwcjo_m.dao");
        schema.enableKeepSectionsByDefault();
        addFaceNews(schema);
        addFaceInfo(schema);
        addBrgInfo(schema);
        addPntInfo(schema);
        addPersonInfo(schema);
        addBasePntInfo(schema);
        addLineBw(schema);
        addSectionSite(schema);
        addLineMeasure(schema);
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
        section.addStringProperty("objstate");
        section.addStringProperty("f_faceid");
    }

    private static void addPersonInfo(Schema schema) {
        Entity section = schema.addEntity("PersonInfo");
        section.addStringProperty("userid").primaryKey();
        section.addStringProperty("username");
        section.addStringProperty("usertel");
        section.addStringProperty("ptype");
        section.addStringProperty("f_sectid");
    }

    private static void addBasePntInfo(Schema schema) {
        Entity section = schema.addEntity("BasePntInfo");
        section.addStringProperty("basepntid").primaryKey();
        section.addStringProperty("basepntname");
        section.addStringProperty("basepntcode");
        section.addStringProperty("basepnthigh");
        section.addStringProperty("basepntnum");
        section.addStringProperty("basepntvar");
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
        site.addStringProperty("sitetype");
        Property f_sectionid = site.addStringProperty("f_sectionid").notNull().getProperty();
        site.addToOne(section, f_sectionid);

        ToMany customerToOrders = section.addToMany(site, f_sectionid);
    }


    private static void addLineBw(Schema schema) {
        Entity line = schema.addEntity("Line");
        line.implementsSerializable();
        line.addStringProperty("lc").primaryKey();
        line.addStringProperty("ln");
        line.addStringProperty("f_sectid");

        Entity bw = schema.addEntity("BwInfo");
        bw.implementsSerializable();
        bw.addLongProperty("bwid").primaryKey().autoincrement();
        bw.addStringProperty("id");
        bw.addStringProperty("od");
        bw.addStringProperty("ty");
        Property f_lineid = bw.addStringProperty("f_lc").notNull().getProperty();
        bw.addToOne(line, f_lineid);
        
        ToMany lineToBws = line.addToMany(bw, f_lineid);
    }

    private static void addLineMeasure(Schema schema) {
        Entity line_extra = schema.addEntity("LineExtra");
        line_extra.implementsSerializable();
        line_extra.addStringProperty("lc").primaryKey();//线路编号

        line_extra.addStringProperty("ltype");//线路类型
        line_extra.addStringProperty("mtype");//观测类型


        line_extra.addStringProperty("devBrand");//设备品牌
        line_extra.addStringProperty("devType");//设备型号
        line_extra.addStringProperty("devSN");//设备序列号

        line_extra.addStringProperty("stuffid");//司镜id
        line_extra.addStringProperty("stuff_name");//司镜帐号
        line_extra.addStringProperty("stuff_pwd");//司镜密码

        line_extra.addStringProperty("temp");//温度
        line_extra.addStringProperty("weather");//天气
        line_extra.addStringProperty("air");//气压

        line_extra.addStringProperty("bpntsq");//基点序列
        line_extra.addStringProperty("mdate");//测量日期 YYMMDD



        //bclass
        Entity ori = schema.addEntity("OriData");
        ori.addLongProperty("id").primaryKey().autoincrement();
        ori.addStringProperty("bffb");//前后视标记
        ori.addStringProperty("bfpcode");//点号：P.B.Z
        ori.addStringProperty("bfpl");//视距
        ori.addStringProperty("bfpvalue");//读数
        ori.addStringProperty("mtime");//测量时间 毫秒

        Property f_lineid = ori.addStringProperty("f_lc").notNull().getProperty();
        ori.addToOne(line_extra, f_lineid);

        line_extra.addToMany(ori, f_lineid);

        //stations
        Entity station = schema.addEntity("LineStation");
        station.addLongProperty("sno").primaryKey().autoincrement();//测站自动编号
        station.addStringProperty("sb");//后点
        station.addStringProperty("sf");//前点
        station.addStringProperty("shd_diff");//测站视距差，两次平均
        station.addStringProperty("shd_diff_all");//累计视距差
        station.addStringProperty("sr_diff");//测站高差 两次平均
        station.addStringProperty("sr_diff_diff");//两次高差之差
        station.addStringProperty("sr_diff_all");//累计高差

        Property f_lineid_station = station.addStringProperty("f_lc").notNull().getProperty();
        station.addToOne(line_extra, f_lineid_station);

        line_extra.addToMany(station, f_lineid_station);

    }

}