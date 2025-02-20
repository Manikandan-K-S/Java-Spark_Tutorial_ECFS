package main.java;

import static spark.Spark.*;
import freemarker.template.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        port(8080);

        Configuration freeMarkerConfig = new Configuration();
        try {
            freeMarkerConfig.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        } catch (Exception e) {
            System.out.println("Error setting template directory: " + e.getMessage());
            return;
        }
        freeMarkerConfig.setDefaultEncoding("UTF-8");
        freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(freeMarkerConfig);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("passengers", Airline.join_tables());
            model.put("flights",Airline.flights());
            return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
        });

        post("/create-flight", (req, res) -> {
            Airline.create_flight(req.queryParams("number"),req.queryParams("destination"));
            res.redirect("/");
            return null;
        });
        post("/create-passenger", (req, res) -> {
            Airline.create_passenger(req.queryParams("name"),req.queryParams("email"),Integer.parseInt(req.queryParams("flight_id")));
            res.redirect("/");
            return null;
        });


        post("/update-destination", (req, res) -> {
            Airline.update_destination( Integer.parseInt(req.queryParams("id")),req.queryParams("new-dest"));
            res.redirect("/");
            return null;

        });


        post("/delete-flight", (req, res) -> {
            Airline.delete_flight(Integer.parseInt(req.queryParams("id")));
            res.redirect("/");
            return null;
        });

        post("/delete-passenger", (req, res) -> {
            Airline.delete_passenger(Integer.parseInt(req.queryParams("id")));
            res.redirect("/");
            return null;
        });
    }


}
