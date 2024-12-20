package com._1.hex.DeliveryPlanning.controller;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.utils.PersistenceFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class Controller {
    private StreetMap streetMap;
    private List<Intersection> selectedIntersections;
    private List<Intersection> listRoute;
    private int nbPanel = 0; //0 - select deliverer, 1 - controlPanel, 2-....
    private Courrier person;
    private List<Courrier> couriers;
    private Intersection startPoint;
    Intersection endPoint;
    private Request request;
    private int index=0;
    private final GraphService graphService;

    @Autowired
    private TspService tspService ;

    public Controller(GraphService graphService, TspService tspService) {
        this.selectedIntersections = new ArrayList<>();
        this.couriers = new ArrayList<>();
        this.graphService = graphService;
        this.tspService = tspService;
    }

    public List<Intersection> getListRoute() {
        return listRoute;
    }

    public void addCourier(Courrier courrier) {
        couriers.add(courrier);
    }

    /**
     * Get the list of couriers if it is empty, read the couriers from the file
     * @return the list of couriers
     */
    public List<Courrier> getCouriers()  {
        if (couriers.isEmpty()) {
            try{
                couriers = PersistenceFileUtils.readCouriersFromFile("COURIER-JSON-FILE");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        return couriers;
    }

    public void setPerson(Courrier person) {
        this.person = person;
    }

    public int getNbPanel() {
        return nbPanel;
    }

    public void setNbPanel(int nbPanel) {
        this.nbPanel = nbPanel;
    }

    /**
     * Add the street map to the graph service
     * @param streetMap the street map
     */
    public void addStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
        graphService.addMap(streetMap);
    }

    public StreetMap getStreetMap() {
        return this.streetMap;
    }

    public void reinitializeListIntersection() {
        this.selectedIntersections = new ArrayList<>();
        index = 0;
    }

    /**
     * Add the intersection to the delivery service creates the request and each delivery after getting the start and end points
     * @param intersection the intersection
     */
    public void addIntersection(Intersection intersection) {
        index++;
        if (index == 1 ){
            Warehouse warehouse = new Warehouse(intersection, "Lyon");
            this.request = new Request(warehouse);
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
        this.selectedIntersections.add(intersection);
        //System.out.println("intersection added to delevary service from services package!" + intersection.getId());
    }

    public List<Intersection> getSelectedIntersections() {
        return selectedIntersections;
    }

    /**
     * Call tspService to compute the TSP solution for the request and gets the route of intersections
     * @param streetMap the street map
     * @return the list of intersections
     */
    public List<Intersection> computeGraph(StreetMap streetMap) {
        System.out.println("the actual courier"+this.person.getName());
        List<Long> l = tspService.searchSolution(100000, this.request, graphService);
        List<Intersection> listRoute = new ArrayList<>();
        if (l!=null && !l.isEmpty()){
            listRoute.add(streetMap.getIntersectionById(l.get(0)));
            for (int j = 1; j < l.size(); j++) {
                Intersection inter = streetMap.getIntersectionById(l.get(j));
                if (listRoute.get(listRoute.size() - 1) != inter) {
                    listRoute.add(inter);
                }
            }
        }
        System.out.println("route: " + listRoute);
        this.listRoute = listRoute;
        return listRoute;
    }

    /**
     * Save the route to the file
     */
    public void saveRouteToFile(){
        try {
            //int id = this.person.getName();
            int CurrentCourierId = this.person.getId();
            PersistenceFileUtils.saveRouteToFile(new Route(CurrentCourierId,this.listRoute ,this.selectedIntersections),"ROUTE-JSON-FILE");
            PersistenceFileUtils.saveCouriersToFile(this.couriers,"COURIER-JSON-FILE");
        }catch (Exception e){
            //System.out.println(e);
        }

    }

    /**
     * Get the route from the file
     */
    public List<Integer> getRouteInt(){
        return tspService.getNodes();
    }

    public List<Double> getDistances(){
        return tspService.getDistances();
    }

    /**
     * Get the delivery duration by searching the delivery in the request corresponding to the end point
     * @param endPoint the end point
     * @return the delivery duration
     */
    private Double getDeliveryDuration(Intersection endPoint){
        for(Delivery delivery: request.getTrip()){
            if(delivery.getDestinationPoint().equals(endPoint)){
                return delivery.getPickupDuration();
            }
            if(delivery.getStartPoint().equals(endPoint)){
                return null;
            }
        }
        return null;
    }

    /**
     * Get the pickup times for the selected intersections
     * @return the list of pickup times with null if the intersection is a start point in the order of the selected intersections
     */
    public List<Double> getPickUpTimes()
    {
        List<Double> pickUpTimes = new ArrayList<>();
        for(Intersection intersection: this.selectedIntersections){
            pickUpTimes.add(getDeliveryDuration(intersection));
        }
        return pickUpTimes;
    }

    /**
     * Load the route from the file
     */
    public void loadRouteFromFile(){
        try {
            Route route = PersistenceFileUtils.readRouteFromFile("ROUTE-JSON-FILE",this.person.getId());
            assert route != null;
            this.listRoute =  route.getIntersections();
            this.selectedIntersections = route.getSelectedIntersections();
        }catch (Exception e){
            //System.out.println(e);
        }
    }

}
