package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Courrier;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.model.Warehouse;
import org.springframework.stereotype.Service;

@Service
public class Services {

    public Services() {
    }

    public Warehouse createWarehouse(StreetMap map, Long intersectionId, String name) {
        Intersection intersection = map.getIntersectionById(intersectionId);
        return new Warehouse(intersection, name);
    }

    public Courrier createCourier(String name, Warehouse wh) {
        return new Courrier(name, wh);
    }
}
