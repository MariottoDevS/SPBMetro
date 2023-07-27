import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculateTest extends TestCase {


    public void testCalculateDuration(){

        List<Station> route = new ArrayList<>();

        Line line1 = new Line(1,"Красная");
        Line line2 = new Line(2,"Синия");

        Station station1 = new Station("Петровская",line1);
        Station station2 = new Station("Малиновка",line1);
        Station station3 = new Station("Петроушчына",line2);
        Station station4 = new Station("Центральная",line2);

        route.add(station1);
        route.add(station2);
        route.add(station3);
        route.add(station4);
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;
        assertEquals(expected,actual);
    }

    public void testGetShortestRoute(){
        Line line1 = new Line(1,"Красная");
        Line line2 = new Line(2,"Синяя");
        Line line3 = new Line(3,"Фиолетовая");

        Station from = new Station("Петровская",line1);
        Station station2 = new Station("Малиновка",line1);
        Station station3 = new Station("Петроушчына",line1);

        Station station2_1 = new Station("Институт Культуры",line2);
        Station station2_2 = new Station("Центральная",line2);
        Station station2_3 = new Station("Партизанская",line2);

        Station station3_1 = new Station("Фрунзенская",line3);
        Station station3_2 = new Station("Московская",line3);
        Station to =  new Station("Минская", line3);

        line1.addStation(from);
        line1.addStation(station2);
        line1.addStation(station3);

        line2.addStation(station2_1);
        line2.addStation(station2_2);
        line2.addStation(station2_3);

        line3.addStation(station3_1);
        line3.addStation(station3_2);
        line3.addStation(to);

        StationIndex stationIndex = new StationIndex();
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        stationIndex.addStation(from);
        stationIndex.addStation(station2);
        stationIndex.addStation(station3);
        stationIndex.addStation(station2_1);
        stationIndex.addStation(station2_2);
        stationIndex.addStation(station2_3);
        stationIndex.addStation(station3_1);
        stationIndex.addStation(station3_2);
        stationIndex.addStation(to);

        stationIndex.addConnection(List.of(station3,station2_1));
        stationIndex.addConnection(List.of(station2_3,station3_1));

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> actual = routeCalculator.getShortestRoute(from,to);
        List<Station> expected = List.of(from, station2,station3,station2_1,station2_2,station2_3,station3_1,station3_2,to);
        assertEquals(expected,actual);
    }

    public void testGetRouteOnTheLine(){
        Line line = new Line(1, "Красная");
        Station from = new Station("Петровская",line);
        Station station2 = new Station("Малиновка",line);
        Station to = new Station("Петроушчына",line);

        StationIndex stationIndex = new StationIndex();
        stationIndex.addLine(line);

        stationIndex.addStation(from);
        stationIndex.addStation(station2);
        stationIndex.addStation(to);
        line.addStation(from);
        line.addStation(station2);
        line.addStation(to);

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> actual = routeCalculator.getShortestRoute(from,to);
        List<Station> expected = List.of(from, station2,to);
        assertEquals(expected,actual);
    }

    public void testGetRouteWithOneConnection(){
        Line line = new Line(1, "Красная");
        Line line1 = new Line(2, "Синяя");

        Station from = new Station("Петровская",line);
        Station station1 = new Station("Малиновка",line);
        Station station2 = new Station("Фрунзенская", line1);
        Station station3 = new Station("Китайская", line1);
        Station to = new Station("Петроушчына",line1);

        StationIndex stationIndex = new StationIndex();
        stationIndex.addLine(line);
        stationIndex.addLine(line1);

        stationIndex.addStation(from);
        stationIndex.addStation(station1);
        stationIndex.addStation(station2);
        stationIndex.addStation(station3);
        stationIndex.addStation(to);

        line.addStation(from);
        line.addStation(station1);
        line1.addStation(station2);
        line1.addStation(station3);
        line1.addStation(to);

        stationIndex.addConnection(List.of(station1,station3));

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> actual = routeCalculator.getShortestRoute(from,to);
        List<Station> expected = List.of(from, station1,station3, to);
        assertEquals(expected,actual);
    }


    public void testGetRouteWithTwoConnections(){
        Line line1 = new Line(1,"Красная");
        Line line2 = new Line(2,"Синяя");
        Line line3 = new Line(3,"Фиолетовая");

        Station from = new Station("Петровская",line1);
        Station station2 = new Station("Малиновка",line1);
        Station station3 = new Station("Петроушчына",line1);

        Station station2_1 = new Station("Институт Культуры",line2);
        Station station2_2 = new Station("Центральная",line2);
        Station station2_3 = new Station("Партизанская",line2);

        Station station3_1 = new Station("Фрунзенская",line3);
        Station station3_2 = new Station("Московская",line3);
        Station to =  new Station("Минская", line3);

        StationIndex stationIndex = new StationIndex();
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        stationIndex.addStation(from);
        stationIndex.addStation(station2);
        stationIndex.addStation(station3);

        stationIndex.addStation(station2_1);
        stationIndex.addStation(station2_2);
        stationIndex.addStation(station2_3);

        stationIndex.addStation(station3_1);
        stationIndex.addStation(station3_2);
        stationIndex.addStation(to);


        line1.addStation(from);
        line1.addStation(station2);
        line1.addStation(station3);

        line2.addStation(station2_1);
        line2.addStation(station2_2);
        line2.addStation(station2_3);

        line3.addStation(station3_1);
        line3.addStation(station3_2);
        line3.addStation(to);


        stationIndex.addConnection(List.of(station2,station2_1));
        stationIndex.addConnection(List.of(station2_2,station3_1));

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);

        List<Station> actual = routeCalculator.getShortestRoute(from,to);
        List<Station> expected = List.of(from, station2,station2_1,station2_2,station3_1,station3_2,to);
        assertEquals(expected,actual);
    }

}
