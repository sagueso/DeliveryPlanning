package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DelevaryService {
    StreetMap streetMap;
    List<Intersection> selectedIntersections;
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

    public void addStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
        graphService.addMap(streetMap);
    }

    public StreetMap getStreetMap() {
        return this.streetMap;
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
    public List<Long> computeGraph(){
        solution = tspService.searchSolution(100000,this.request,graphService);
        return solution;
    }

}
