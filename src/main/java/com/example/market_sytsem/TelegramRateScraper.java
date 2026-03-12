package com.example.market_sytsem;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.jsoup.nodes.Document;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class TelegramRateScraper {
    private double price;

    @Scheduled(fixedRate = 3600000)
    public void fetchDollarRate(){
        try{
            Document doc = Jsoup.connect("https://t.me/s/bazari_dolaraka").get();
            Elements messages = doc.select(".tgme_widget_message_text");

            for(int i = messages.size() - 1; i >= 0; i--){
                String messageText = messages.get(i).text();

                Pattern pattern = Pattern.compile("100\\$\\s*=\\s*(\\d{3})[,\\.]?(\\d{3})");
                Matcher matcher = pattern.matcher(messageText);

                if(matcher.find()){
                    String numberString = matcher.group(1) + matcher.group(2);
                    double rawPrice = Double.parseDouble(numberString);
                    this.price = rawPrice / 1000.0;
                    System.out.println("نرخی نوێی دۆلار وەرگیرا: " + this.price);
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Please check if the internet is available");
        }
    }

    public double getPrice(){
        return this.price;
    }
}
