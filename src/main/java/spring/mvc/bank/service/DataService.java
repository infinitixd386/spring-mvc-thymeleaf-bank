package spring.mvc.bank.service;

import org.springframework.stereotype.Service;
import spring.mvc.bank.domain.BankCard;
import spring.mvc.bank.domain.CardType;
import spring.mvc.bank.domain.Role;
import spring.mvc.bank.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {

    private List<User> users;
    private List<BankCard> bankCards;

    public DataService() {
        populateUsers();
        populateBankCards();
    }

    private void populateUsers() {
        users = new ArrayList<>();
        users.add(new User(1, "user1", "123", Role.USER));
        users.add(new User(2, "admin1", "123", Role.ADMIN));
    }

    private void populateBankCards() {
        bankCards = new ArrayList<>();
        bankCards.add(new BankCard(1, users.get(0), LocalDate.of(2023, 10, 7), CardType.MASTER_CARD, "32525", 42000));
        bankCards.add(new BankCard(2, users.get(1), LocalDate.of(2023, 7, 10), CardType.VISA, "56232", 98000));
    }


    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    public List<BankCard> findBankCardsByUser(User user) {
        return bankCards.stream()
                .filter(bankCard -> bankCard.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public BankCard findBankCardById(long id) {
        return bankCards.stream()
                .filter(bankCard -> bankCard.getId() == id)
                .findAny().orElse(null);
    }

    public void addBankCard(BankCard bankCard) {
        long currentMax = bankCards.stream()
                .map(BankCard::getId)
                .max(Long::compare)
                .orElse(0L);
        bankCard.setId(currentMax + 1);
        bankCards.add(bankCard);
    }

}
