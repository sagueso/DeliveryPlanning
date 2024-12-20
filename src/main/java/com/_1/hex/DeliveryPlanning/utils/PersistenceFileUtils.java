package com._1.hex.DeliveryPlanning.utils;
import com._1.hex.DeliveryPlanning.MainDrawMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com._1.hex.DeliveryPlanning.model.Courrier;
import com._1.hex.DeliveryPlanning.model.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PersistenceFileUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void saveRouteToFile(Route route, String filePath) throws IOException {
        List<Route> routes;

        Path currentPath;
        Path fileP;
        try {
            currentPath = Paths.get(MainDrawMap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            System.out.println("Working Directory: " + currentPath.toString());
            fileP = currentPath.resolve(filePath);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        // Read existing routes from the file
        File file = new File(fileP.toString());
        if (file.exists() && file.length() > 0) {
            routes = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Route.class));
        } else {
            routes = new ArrayList<>();
        }

        // Check if a route with the same id exists
        boolean updated = false;
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getId() == route.getId()) {
                routes.set(i, route); // Update the existing route
                updated = true;
                break;
            }
        }

        // Add the route if it doesn't exist
        if (!updated) {
            routes.add(route);
        }

        // Write the updated list back to the file
        objectMapper.writeValue(file, routes);
    }

    public static Route readRouteFromFile(String filePath, int idRoute) throws IOException {

        Path currentPath;
        Path fileP;
        try {
            currentPath = Paths.get(MainDrawMap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            System.out.println("Working Directory: " + currentPath.toString());
            fileP = currentPath.resolve(filePath);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        List<Route> routes = objectMapper.readValue(
                new File(fileP.toString()),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Route.class)
        );


        for (Route route : routes) {
            if (route.getId() == idRoute) {
                return route; // Return the matching route
            }
        }

        return null; // Or throw new IllegalArgumentException("Route with id " + idRoute + " not found");
    }

    public static void saveCouriersToFile(List<Courrier> courriers, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), courriers);
    }

    public static List<Courrier> readCouriersFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Courrier>>() {});
    }
}
