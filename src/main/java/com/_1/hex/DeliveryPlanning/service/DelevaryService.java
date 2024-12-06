package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Delivery;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.model.Warehouse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DelevaryService {
    List<Intersection> selectedIntersections;
    Warehouse warehouse;
    Intersection startPoint;
    Intersection endPoint;
    Request request;
    int index=0;
    GraphService graphService;
    List<Long> solution = new ArrayList<>();
    public DelevaryService(GraphService graphService) {
        this.selectedIntersections = new ArrayList<Intersection>();
        this.graphService = graphService;
    }

    public int addInergection(Intersection intersection) {
        index++;
        if (index == 1 ){
            this.warehouse = new Warehouse(intersection,"Lyon");
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
        System.out.println("intersection added to delevary service from services package!" + intersection);
        return index;
    }
    public List<Long> computeGraph(){
        TspService tspService = new TspService();
        solution = tspService.searchSolution(100000,this.request,graphService);
        return solution;
    }




}
