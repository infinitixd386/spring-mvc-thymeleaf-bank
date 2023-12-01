package spring.mvc.bank.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.mvc.bank.domain.BankCard;
import spring.mvc.bank.model.BankCardsListModel;
import spring.mvc.bank.security.UserLoginDetailsService;
import spring.mvc.bank.service.DataService;
import spring.mvc.bank.transformer.BankCardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import spring.mvc.bank.model.BankCardModel;

import javax.validation.Valid;

@Controller
public class BankCardListController {
    private static final String REQUEST_PATH = "/bankcard-list";

    @Autowired
    private DataService dataService;

    @Autowired
    private UserLoginDetailsService userLoginDetailsService;

    @Autowired
    private BankCardTransformer bankCardTransformer;

    @ModelAttribute("bankCardModel")
    public BankCardModel getBankCardModel() {
        return new BankCardModel();
    }

    @ModelAttribute("bankCardListModel")
    public BankCardsListModel createBankCardListModel() {
        return new BankCardsListModel(bankCardTransformer.transformBankCardsListToBankCardModelsList(
                dataService.findBankCardsByUser(
                        dataService.findUserByUsername(
                                userLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @PostMapping(value = REQUEST_PATH)
    public String createCard(@Valid BankCardModel bankCardModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String result;
        if (bindingResult.hasErrors()) {
            result = "bankcard-list";
        } else {
            BankCard bankCard = bankCardTransformer.transformBankCardModelToBankCard(bankCardModel);
            bankCard.setUser(dataService.findUserByUsername(userLoginDetailsService.loadAuthenticatedUsername()));
            dataService.addBankCard(bankCard);
            redirectAttributes.addFlashAttribute("successMessage", "Bank card added successfully");
            result = "redirect:bankcard-list";
        }
        return result;
    }

    @GetMapping(value = REQUEST_PATH)
    public String showBankCardList() {
        return "bankcard-list";
    }
}
