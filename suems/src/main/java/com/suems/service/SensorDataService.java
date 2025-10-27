    package com.suems.service;


    import com.opencsv.CSVWriter;
    import com.suems.dto.SummaryDto;
    import com.suems.model.SensorData;
    import com.suems.model.User;
    import com.suems.repository.SensorDataRepository;
    import org.springframework.stereotype.Service;

    import java.io.IOException;
    import java.io.StringWriter;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.DoubleSummaryStatistics;
    import java.util.List;

    @Service
    public class SensorDataService {

        private final SensorDataRepository repo;
        private final UserEnergyConfigService cfgService;

        public SensorDataService(SensorDataRepository repo, UserEnergyConfigService cfgService) {
            this.repo = repo;
            this.cfgService = cfgService;
        }

        // ✅ Save anonymous or generic sensor data
        public SensorData save(SensorData data) {
            return repo.save(data);
        }


        // Save new sensor data with cost (simulator or ingestion)
        public SensorData saveWithCost(SensorData s, User user) {
            if (user == null || user == null) {
                throw new IllegalArgumentException("User is not authenticated or missing.");
            }

            s.setUserId(user); // maps correctly via @ManyToOne
            double unit = cfgService.getUnitCostOrDefault(user.getId());
            s.setCost(s.getTotalConsumption() * unit);

            return repo.save(s);
        }

        // ✅ Fetch last 100 readings for a user
        public List<SensorData> findRecentTop100(User user) {
            return repo.findTop100ByUserIdOrderByTimestampDesc(user);
        }

        public List<SensorData> findAfter(User user, LocalDateTime ts) {
            return repo.findByUserIdAndTimestampAfterOrderByTimestampAsc(user, ts);
        }

        public List<SensorData> between(User user, LocalDateTime from, LocalDateTime to) {
            return repo.findByUserIdAndTimestampBetweenOrderByTimestampAsc(user, from, to);
        }

        public long deleteHour(User user, LocalDateTime hour) {
            LocalDateTime start = hour.withMinute(0).withSecond(0).withNano(0);
            LocalDateTime end = start.plusHours(1);
            return repo.deleteByUserIdAndTimestampBetween(user, start, end);
        }

        public Summary calculateSummary(User user, String range, LocalDate fromDate, LocalDate toDate) {
            LocalDateTime from;
            LocalDateTime to;

            switch ((range == null ? "day" : range).toLowerCase()) {
                case "week":
                    from = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
                    to = from.plusDays(7);
                    break;
                case "month":
                    from = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                    to = from.plusMonths(1);
                    break;
                case "custom":
                    from = fromDate.atStartOfDay();
                    to = toDate.plusDays(1).atStartOfDay();
                    break;
                case "day":
                default:
                    from = LocalDate.now().atStartOfDay();
                    to = from.plusDays(1);
            }

            List<SensorData> list = between(user, from, to);
            return summarize(list);
        }

        public Summary summarize(List<SensorData> list) {
            DoubleSummaryStatistics solar = list.stream().mapToDouble(SensorData::getSolarPower).summaryStatistics();
            DoubleSummaryStatistics wind  = list.stream().mapToDouble(SensorData::getWindPower).summaryStatistics();
            DoubleSummaryStatistics grid  = list.stream().mapToDouble(SensorData::getGridUsage).summaryStatistics();
            DoubleSummaryStatistics cons  = list.stream().mapToDouble(SensorData::getTotalConsumption).summaryStatistics();
            DoubleSummaryStatistics cost  = list.stream().mapToDouble(SensorData::getCost).summaryStatistics();

            Summary s = new Summary();
            s.setSamples(list.size());
            s.setAvgSolar(solar.getAverage());
            s.setAvgWind(wind.getAverage());
            s.setAvgGrid(grid.getAverage());
            s.setAvgConsumption(cons.getAverage());
            s.setAvgCost(cost.getAverage());
            s.setTotalSolar(solar.getSum());
            s.setTotalWind(wind.getSum());
            s.setTotalGrid(grid.getSum());
            s.setTotalConsumption(cons.getSum());
            s.setTotalCost(cost.getSum());
            return s;
        }

        // CSV Export (from..to inclusive)
        public String exportCsv(User user, LocalDateTime from, LocalDateTime to) throws IOException {
            List<SensorData> list = between(user, from, to);
            StringWriter sw = new StringWriter();
            try (CSVWriter w = new CSVWriter(sw)) {
                w.writeNext(new String[]{"id","timestamp","solarPower","windPower","gridUsage","totalConsumption","cost"});
                for (SensorData s : list) {
                    w.writeNext(new String[]{
                            String.valueOf(s.getId()),
                            String.valueOf(s.getTimestamp()),
                            String.valueOf(s.getSolarPower()),
                            String.valueOf(s.getWindPower()),
                            String.valueOf(s.getGridUsage()),
                            String.valueOf(s.getTotalConsumption()),
                            String.valueOf(s.getCost())
                    });
                }
            }
            return sw.toString();
        }

        public static class Summary {
            private long samples;
            private double avgSolar, avgWind, avgGrid, avgConsumption, avgCost;
            private double totalSolar, totalWind, totalGrid, totalConsumption, totalCost;

            // getters/setters
            public long getSamples() { return samples; }
            public double getAvgSolar() { return avgSolar; }
            public double getAvgWind() { return avgWind; }
            public double getAvgGrid() { return avgGrid; }
            public double getAvgConsumption() { return avgConsumption; }
            public double getAvgCost() { return avgCost; }
            public double getTotalSolar() { return totalSolar; }
            public double getTotalWind() { return totalWind; }
            public double getTotalGrid() { return totalGrid; }
            public double getTotalConsumption() { return totalConsumption; }
            public double getTotalCost() { return totalCost; }

            public void setSamples(long v) { this.samples = v; }
            public void setAvgSolar(double v) { this.avgSolar = v; }
            public void setAvgWind(double v) { this.avgWind = v; }
            public void setAvgGrid(double v) { this.avgGrid = v; }
            public void setAvgConsumption(double v) { this.avgConsumption = v; }
            public void setAvgCost(double v) { this.avgCost = v; }
            public void setTotalSolar(double v) { this.totalSolar = v; }
            public void setTotalWind(double v) { this.totalWind = v; }
            public void setTotalGrid(double v) { this.totalGrid = v; }
            public void setTotalConsumption(double v) { this.totalConsumption = v; }
            public void setTotalCost(double v) { this.totalCost = v; }
        }
    }