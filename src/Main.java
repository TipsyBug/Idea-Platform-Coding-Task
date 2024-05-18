import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String FILE_NAME = "tickets.json";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    public static void main(String[] args) {
        try {
            JsonNode ticketsNode = readJsonFile(FILE_NAME);
            HashMap<String, Duration> minFlightDurations = calculateMinFlightDurations(ticketsNode);
            ArrayList<Integer> prices = collectPrices(ticketsNode);
            double averagePrice = calculateAverage(prices);
            double medianPrice = calculateMedian(prices);

            printResults(minFlightDurations, averagePrice, medianPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonNode readJsonFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(fileName));
        return rootNode.path("tickets");
    }

    private static HashMap<String, Duration> calculateMinFlightDurations(JsonNode ticketsNode) {
        HashMap<String, Duration> minFlightDurations = new HashMap<>();

        for (JsonNode ticketNode : ticketsNode) {
            String origin = ticketNode.path("origin").asText();
            String destination = ticketNode.path("destination").asText();

            if ("VVO".equals(origin) && "TLV".equals(destination)) {
                String carrier = ticketNode.path("carrier").asText();
                LocalTime depTime = LocalTime.parse(ticketNode.path("departure_time").asText(), TIME_FORMATTER);
                LocalTime arrTime = LocalTime.parse(ticketNode.path("arrival_time").asText(), TIME_FORMATTER);

                Duration flightDuration = Duration.between(depTime, arrTime);
                if (flightDuration.isNegative()) {
                    flightDuration = flightDuration.plusHours(24);
                }

                if (!minFlightDurations.containsKey(carrier) || minFlightDurations.get(carrier).compareTo(flightDuration) > 0) {
                    minFlightDurations.put(carrier, flightDuration);
                }
            }
        }
        return minFlightDurations;
    }

    private static ArrayList<Integer> collectPrices(JsonNode ticketsNode) {
        ArrayList<Integer> prices = new ArrayList<>();

        for (JsonNode ticketNode : ticketsNode) {
            String origin = ticketNode.path("origin").asText();
            String destination = ticketNode.path("destination").asText();

            if ("VVO".equals(origin) && "TLV".equals(destination)) {
                prices.add(ticketNode.path("price").asInt());
            }
        }
        return prices;
    }

    private static double calculateAverage(ArrayList<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    private static double calculateMedian(ArrayList<Integer> prices) {
        Collections.sort(prices);
        int size = prices.size();
        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }

    private static void printResults(HashMap<String, Duration> minFlightDurations, double averagePrice, double medianPrice) {
        System.out.println("The minimum flight time between the cities of Vladivostok and Tel Aviv for each airline:");
        for (Map.Entry<String, Duration> entry : minFlightDurations.entrySet()) {
            System.out.println("Airline: " + entry.getKey() + ", Flight time: " + entry.getValue().toHoursPart() + " hours " + entry.getValue().toMinutesPart() + " minutes");
        }
        System.out.println("The difference between the average price and the median price for flights between the cities of Vladivostok and Tel Aviv: " + (averagePrice - medianPrice));
    }
}
