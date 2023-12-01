package spring.mvc.bank.transformer;

import org.springframework.stereotype.Component;
import spring.mvc.bank.domain.BankCard;
import spring.mvc.bank.domain.CardType;
import spring.mvc.bank.model.BankCardModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankCardTransformer {

    public List<BankCardModel> transformBankCardsListToBankCardModelsList(List<BankCard> bankCards) {
        List<BankCardModel> bankCardModelList = new ArrayList<>();
        for (BankCard bankCard : bankCards) {
            bankCardModelList.add(transformBankCardToBankCardModel(bankCard));
        }
        return bankCardModelList;
    }

    public BankCard transformBankCardModelToBankCard(BankCardModel bankCardModel) {
        BankCard bankCard = new BankCard();
        bankCard.setId(bankCardModel.getId());
        bankCard.setValidity(bankCardModel.getValidity());
        bankCard.setCardType(CardType.valueOf(bankCardModel.getCardType()));
        bankCard.setCardNumber(bankCardModel.getCardNumber());
        bankCard.setBalance(bankCardModel.getBalance());
        return bankCard;
    }

    public BankCardModel transformBankCardToBankCardModel(BankCard bankCard) {
        BankCardModel bankCardModel = new BankCardModel();
        bankCardModel.setId(bankCard.getId());
        bankCardModel.setValidity(bankCard.getValidity());
        bankCardModel.setCardType(bankCard.getCardType().toString());
        bankCardModel.setCardNumber(bankCard.getCardNumber());
        bankCardModel.setBalance(bankCard.getBalance());
        return bankCardModel;
    }
}
