package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.utils.PersistenceFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DelevaryService {
    List<Intersection> selectedIntersections;
    List<Intersection> listRoute;
    Warehouse warehouse;
    Intersection startPoint;
    Intersection endPoint;
    Request request;
    int index=0;
    GraphService graphService;
    @Autowired
    TspService tspService ;
    List<Long> solution = new ArrayList<>();
    public DelevaryService(GraphService graphService,TspService tspService) {
        this.selectedIntersections = new ArrayList<Intersection>();
        this.graphService = graphService;
        this.tspService = tspService;
    }

    public int addInergection(Intersection intersection) {
        index++;
        if (index == 1 ){
            this.warehouse = new Warehouse(intersection,"Lyon");
            this.request = new Request(this.warehouse);

        }
        else {

            if (index%2==0){
                this.startPoint = intersection;


            }
            else {
                this.endPoint = intersection;
                this.request.addDelivery(new Delivery(startPoint,endPoint));
            }
        }
        /*
        if (this.startPoint != null && this.endPoint == null) {
            this.endPoint = warehouse;
            this.request.addDelivery(new Delivery(startPoint,endPoint));
        }*/


        this.selectedIntersections.add(intersection);
        System.out.println("intersection added to delevary service from services package!" + intersection.getId());
        return index;
    }

    public List<Intersection> getSelectedIntersections() {
        return selectedIntersections;
    }

    public List<Intersection> computeGraph(StreetMap streetMap) {
        List<Long> l = tspService.searchSolution(100000, this.request, graphService);
        List<Intersection> listRoute = new ArrayList<>();
        listRoute.add(streetMap.getIntersectionById(l.get(0)));
        for (int j = 1; j < l.size(); j++) {
            Intersection inter = streetMap.getIntersectionById(l.get(j));
            if (listRoute.get(listRoute.size() - 1) != inter) {
                listRoute.add(inter);
            }
        }
        System.out.println("route: " + listRoute);
        this.listRoute = listRoute;
        return listRoute;
    }
    public void saveRouteToFile(){
        try {
            PersistenceFileUtils.saveRouteToFile(new Route(this.listRoute),"ROUTE-JSON-FILE");
        }catch (Exception e){System.out.println(e);}

    }

    public List<Intersection> loadRouteFromFile(){
        List<Intersection> intersectionList = new ArrayList<>();
        try {
            Route route = PersistenceFileUtils.readRouteFromFile("ROUTE-JSON-FILE");
            intersectionList = route.getIntersections();
        }catch (Exception e){System.out.println(e);}
        return intersectionList;
    }

}
