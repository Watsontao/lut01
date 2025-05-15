package com.vincent.EnvironmentData;
import com.vincent.EnvironmentData.pojo.EnvironmentData;
import com.vincent.EnvironmentData.service.EnvironmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class EnvironmentDataSimulator {

    @Autowired
    private EnvironmentDataService environmentDataService;

    private Random random = new Random();


//    @Scheduled(cron = "*/5 * * * * ?") // 每5秒执行一次
    public void generateMockData() {
        EnvironmentData data = new EnvironmentData();

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String timeStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 温度模拟：日间高，夜间低
        double baseTemp = (hour >= 6 && hour <= 18) ? 20 : 15;
        double temperature = baseTemp + Math.sin(System.currentTimeMillis() / 100000.0) * 2 + random.nextDouble() * 1.5;

        // 湿度模拟：夜间略高
        double humidity = (hour >= 20 || hour <= 6) ? 65 : 55;
        humidity += Math.cos(System.currentTimeMillis() / 80000.0) * 5 + random.nextDouble() * 3;

        // 土壤湿度：轻微波动
        double soilMoisture = 40 + Math.sin(System.currentTimeMillis() / 90000.0) * 3 + random.nextDouble();

        // 光照强度：白天有值，晚上为0
        double lightIntensity = (hour >= 6 && hour <= 18) ? (Math.sin(Math.PI * (hour - 6) / 12) * 300 + random.nextDouble() * 50) : 0;

        // 写入数据
        data.setGreenhouseId(8);
        data.setRecordDate(timeStr);
        data.setTemperature((float) temperature);
        data.setHumidity((float) humidity);
        data.setSoilMoisture((float) soilMoisture);
        data.setLightIntensity((float) lightIntensity);

        environmentDataService.insertEnvironmentData(data);
    }

}
