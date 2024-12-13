package com._1.hex.DeliveryPlanning.utils;

import com._1.hex.DeliveryPlanning.model.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveRouteToFile(Route route, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), route);
    }

    public static Route readRouteFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Route.class);
    }
}
