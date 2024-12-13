package com._1.hex.DeliveryPlanning.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.Services;
import com._1.hex.DeliveryPlanning.service.XmlParser;

import javax.xml.stream.XMLStreamException;


public class Mvp {

    private final GraphService graphService;
    private final XmlParser xmlParser;
    Services services;
    List<Warehouse> warehouses = new ArrayList<>();
    List<Courrier> courriers = new ArrayList<>();
    List<Delivery> deliveries = new ArrayList<>();

    public Mvp() {
        this.graphService = new GraphService();
        this.xmlParser = new XmlParser();
        this.services = new Services();
    }

    public void launch() throws IOException, XMLStreamException {

        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        StreetMap map = xmlParser.parse(xmlPath);
        graphService.addMap(map);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            System.out.println("1- Rentrer un entrepot\n2- Rentrer un livreur\n3- Rentrer une livraison" +
                    "\n4-Attribuer une livraison a un livreur\n");

            int choise = Integer.parseInt(reader.readLine());
            switch (choise) {
                case 1:
                    System.out.println("Entrez le nom de l'entrepot:\n");
                    String name = reader.readLine();
                    System.out.println("Entrez l'id de l'intersection:\n");
                    Long intersectionId = Long.parseLong(reader.readLine());
                    Warehouse wh = services.createWarehouse(map, intersectionId, name);
                    warehouses.add(wh);
                    System.out.println("Entrepot crée\n");
                    break;
                case 2:
                    System.out.println("Entrez le nom du livreur:\n");
                    String nameLivreur = reader.readLine();
                    System.out.println("Entrez le nom de l'entrepot:\n");
                    String warehouseName = reader.readLine();
                    for (Warehouse warehouse : warehouses) {
                        if (warehouse.getName().equals(warehouseName)) {
                            courriers.add(services.createCourier(nameLivreur, warehouse));
                            System.out.println("Livreur crée\n");
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Entrez le point d'enlevement :\n");
                    Long enlevementId = Long.parseLong(reader.readLine());
                    System.out.println("Entrez le point de livraison:\n");
                    Long livraisonId = Long.parseLong(reader.readLine());
                    Intersection enlevement = map.getIntersectionById(enlevementId);
                    Intersection livraison = map.getIntersectionById(livraisonId);
                    deliveries.add(new Delivery(enlevement, livraison));
                    System.out.println("Livraison crée\n");
                    break;
                case 4:
                    System.out.println("Entrez le nom du livreur:\n");
                    String livName = reader.readLine();
                    System.out.println("Entrez le point d'enlevement :\n");
                    Long enlevementId2 = Long.parseLong(reader.readLine());
                    List<Long> shortestPath = new ArrayList<>();
                    for (Delivery delivery : deliveries) {
                        if (delivery.getStartPoint().getId().equals(enlevementId2)) {
                            for (Courrier courrier : courriers) {
                                if (courrier.getName().equals(livName)) {
                                    List<Long> trip1 = graphService.computeTheShortestPath(courrier.getWarehouse(), delivery.getStartPoint()).getFirst();
                                    Double distance1 = graphService.computeTheShortestPath(courrier.getWarehouse(), delivery.getStartPoint()).getSecond();
                                    List<Long> trip2 = graphService.computeTheShortestPath(delivery.getStartPoint(), delivery.getDestinationPoint()).getFirst();
                                    Double distance2 = graphService.computeTheShortestPath(delivery.getStartPoint(), delivery.getDestinationPoint()).getSecond();
                                    List<Long> trip3 = graphService.computeTheShortestPath(delivery.getDestinationPoint(), courrier.getWarehouse()).getFirst();
                                    Double distance3 = graphService.computeTheShortestPath(delivery.getDestinationPoint(), courrier.getWarehouse()).getSecond();
                                    Double totalTime = 60 * (distance1 + distance2 + distance3) / 15000;
                                    for(int i =0;i<trip1.size();i++){
                                        shortestPath.add(map.getIntersectionById(trip1.get(i)).getId());
                                    }
                                    for(int i=1 ; i<trip2.size();i++){
                                        shortestPath.add(map.getIntersectionById(trip2.get(i)).getId());
                                    }
                                    for(int i=1 ; i<trip3.size();i++){
                                        shortestPath.add(map.getIntersectionById(trip3.get(i)).getId());
                                    }
                                    System.out.println("total Time: " + totalTime);
                                    System.out.println("shortest path: " + shortestPath);
                                    if(totalTime > 5) {
                                        System.out.println("Livraison non attribuée: temps exédé\n");
                                        break;
                                    }
                                    else{
                                        courrier.setDelivery(delivery);
                                        System.out.println("Livraison attribuée\n");
                                    }

                                    break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choise");
                    break;
            }
        }
    }



}
