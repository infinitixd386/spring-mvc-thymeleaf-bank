package spring.mvc.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.mvc.bank.domain.BankCard;
import spring.mvc.bank.model.BankCardModel;
import spring.mvc.bank.service.DataService;
import spring.mvc.bank.transformer.BankCardTransformer;

import javax.validation.Valid;

@Controller
public class BankCardDetailsController {

    private static final Logger LOG = LoggerFactory.getLogger(BankCardDetailsController.class);
    private static final String REQUEST_PATH = "/bankcard-details";

    @Autowired
    private DataService dataService;
    @Autowired
    private BankCardTransformer bankCardTransformer;

    @ModelAttribute("bankCardModel")
    public BankCardModel createBankCardModel(@RequestParam("bankCardId") long bankCardId) {
        BankCard bankCard = dataService.findBankCardById(bankCardId);
        if (bankCard == null) {
            throw new IllegalArgumentException("Bank Card not found by id: " + bankCardId);
        }
        return bankCardTransformer.transformBankCardToBankCardModel(bankCard);
    }

    @GetMapping(value = REQUEST_PATH, params = "bankCardId")
    public String showBankCardDetails(@RequestParam long bankCardId) {
        LOG.info("bankCardId: {}", bankCardId);
        return "bankcard-details";
    }

    @PostMapping(value = REQUEST_PATH, params = "bankCardId")
    public String updateBankCard(@RequestParam long bankCardId, @Valid BankCardModel bankCardModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String result;
        BankCard bankCard = dataService.findBankCardById(bankCardId);
        if (bindingResult.hasErrors()) {
            bankCardModel.setBalance(bankCard.getBalance());
            result = "bankcard-details";
        } else {
            bankCard.setBalance(bankCardModel.getBalance());
            redirectAttributes.addFlashAttribute("successMessage", "Bank card balance updated successfully");
            result = "redirect:bankcard-details?bankCardId=" + bankCardId;
        }
        return result;
    }

}
