package com.example.market_sytsem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class DebtController {

    @Autowired
    private DebtRepository repository;

    @Autowired
    private TelegramRateScraper rateScraper;

    @GetMapping("/Debt")
    public String showDebt(Model model , @RequestParam(required = false) String keyword){

        List<Debt> allDebtsFromDB;

        if(keyword != null && !keyword.isEmpty()){
           allDebtsFromDB = repository.findByCustomerNameContaining(keyword);
        }else{
            allDebtsFromDB = repository.findByIsArchivedFalse();
        }

        model.addAttribute("allDebts", allDebtsFromDB);

        double total = 0.0;

        for(Debt debtLists : allDebtsFromDB){

            if(debtLists.getCurrency().equals("USD")){
                total = debtLists.getAmount() * (rateScraper.getPrice() / 100);
            }else {
                total = (total + debtLists.getAmount());
            }
        }

        model.addAttribute("totalAmount", total);

        model.addAttribute("usdRate",rateScraper.getPrice());

        return "index";
    }

    @PostMapping("/saveDebt")
    public String saveNewDebt(@RequestParam String customerName, @RequestParam double amount, Model model , @RequestParam String phoneNumber , @RequestParam String currency){

        Debt currentDebt = new Debt(customerName, amount , phoneNumber , currency);
        repository.save(currentDebt);

        return "redirect:/Debt";
    }

    @GetMapping("/delete/{id}")
    public String archiveDebt(@PathVariable Long id){

        Debt debtToArchive = repository.findById(id).orElse(null);

        if(debtToArchive != null){
            debtToArchive.setArchived(true);

            repository.save(debtToArchive);
        }

        return "redirect:/Debt";
    }

    @Autowired
    PaymentRepository paymentRepository;
    @PostMapping("/addPayment")
    public String addPayment(@RequestParam Long debtId, @RequestParam double paymentAmount , @RequestParam String currency){

        Debt currentDebt = repository.findById(debtId).orElse(null);

        if(currentDebt != null){
            Payment newPayment = new Payment();

            newPayment.setAmount(paymentAmount);
            newPayment.setPaymentDate(java.time.LocalDate.now().toString());
            newPayment.setDebt(currentDebt);
            newPayment.setCurrency(currency);
            double autoExchangeRate = rateScraper.getPrice();

            double convertedAmount = 0;

            if(currentDebt.getCurrency().equals(currency)){

                convertedAmount = paymentAmount;
            }else if (currentDebt.getCurrency().equals("USD") && currency.equals("IQD")){

                convertedAmount = (paymentAmount / autoExchangeRate)* 100;
            }else if (currentDebt.getCurrency().equals("IQD") && currency.equals("USD")){
                convertedAmount = (paymentAmount * autoExchangeRate) / 100;
            }

            currentDebt.setAmount(currentDebt.getAmount() - convertedAmount);
            repository.save(currentDebt);

            paymentRepository.save(newPayment);
        }

        return "redirect:/Debt";
    }

    @GetMapping("/history")
    public String showPaymentHistory(Model model){
        List<Payment> allPayments = paymentRepository.findAll();

        model.addAttribute("allPayments", allPayments);

        return "payment-history";

    }

    @GetMapping("/")
    public String redirectToMain(){
        return "redirect:/Debt";
    }

    @GetMapping("/archives")
    public String archives(Model model){
        List<Debt> archivedList = repository.findByIsArchivedTrue();

        try{

            model.addAttribute("archives", archivedList);

            Double currentRate = rateScraper.getPrice();
            if(currentRate == null){
                currentRate = 153000.0;
            }

            model.addAttribute("usdRate", rateScraper.getPrice());

            return "archived-debts";
        } catch (Exception e) {
            System.out.println("Error in Archives: " + e.getMessage());
            return "archived-debts";
        }
    }

    @Autowired
    private SystemSettingRepository mySettings;

    @GetMapping("/settings")
    public String showSettings(Model model){
        mySettings.findById(1L).orElse(new SystemSetting());

        model.addAttribute("mySettings", mySettings);

        return "settings";
    }

    @PostMapping("/saveSettings")
    public String updateSettings(@RequestParam String storeName, @RequestParam Double customUsdRate, @RequestParam(required = false) Boolean useCustomRate){
        SystemSetting currentSetting = mySettings.findById(1L).orElse(new SystemSetting());
        currentSetting.setStoreName(storeName);
        currentSetting.setCustomUsdRate(customUsdRate);
        currentSetting.setUseCustomRate(useCustomRate);

        mySettings.save(currentSetting);

        return "redirect:/settings";
    }
}
