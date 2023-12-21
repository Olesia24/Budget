package family_budget;

import family_budget.dao.BudgetImpl;
import family_budget.model.Product;
import family_budget.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetImplTest {
    List<Purchase> purchaseList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    List<Product> productList1 = new ArrayList<>();
    List<Product> productList2 = new ArrayList<>();
    List<Product> productList3 = new ArrayList<>();
    List<Product> productList4 = new ArrayList<>();

    BudgetImpl budget = new BudgetImpl(purchaseList ,0);

    @BeforeEach
    void setUp() {
        productList = List.of(
                new Product("milk", 1.0, 2)
        );
        productList1 = List.of(
                new Product("wine", 3.0, 1)
        );
        productList2 = List.of(
                new Product("bred", 1.5, 3)
        );
        productList3 = List.of(
                new Product("chocolate", 0.5, 2)
        );
        productList4 = List.of(
                new Product("sofa", 900, 1)
        );
        purchaseList = List.of(
                new Purchase(1,LocalDate.of(2023,12,10),10,"Penny","Mama",productList1),
                new Purchase(2, LocalDate.of(2023,12,11), 23,"Aldi", "Mama", productList2),
                new Purchase(3, LocalDate.of(2023,12,01), 55,"Penny", "Mama", productList1),
                new Purchase(4, LocalDate.of(2023,12,12),  65,"Action", "Tolik", productList3),
                new Purchase(5, LocalDate.of(2023, 12, 20), 70,"Ikea", "Dad", productList4)
        );
        for (Purchase p : purchaseList) {
            budget.addPurchase(p);
        }
        double b = budget.calcBudget();
        System.out.println(b);
    }

    @Test
    void addPurchaseTest() {
        assertFalse(budget.addPurchase(null));
        assertTrue(budget.addPurchase(purchaseList.get(0)));//проверка на дубликат
        Purchase p = new Purchase(6, LocalDate.of(2023,10,11), 5.0,"Aldi", "Mama", productList2);
        assertTrue(budget.addPurchase(p));//проверка прошла т.к они разные
    }

    @Test
    void budgetGetPersonTest() {
        assertEquals(88.0, budget.budgetGetPerson("Mama"));
    }

    @Test
    void sortByPersonTest() {
        List<Purchase> sortedList = budget.sortByPerson("Dad");
        assertFalse( sortedList.isEmpty());
    }

    @Test
    void budgetByStoreTest() {
        assertEquals( 70.0,budget.budgetByStore("Ikea"));
    }

    @Test
    void sortByStoreTest() {
        List<Purchase> sortedList = budget.sortByStore("Penny");
        assertFalse(sortedList.isEmpty());
    }

    @Test
    void budgetByPeriodTest() {
        assertEquals(168, budget.budgetByPeriod(LocalDate.of(2023, 12, 01), LocalDate.of(2023, 12, 21)));
    }

    @Test
    void sortByPeriodTest() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 22);
        List<Purchase> sortedList = budget.sortByPeriod(from, to);
        assertFalse(sortedList.isEmpty());
    }

    @Test
    void calcBudgetTest() {
        assertEquals(223.0, budget.calcBudget());
    }

    @Test
    void checkBudgetTest() {
        assertEquals(-223.0, budget.checkBudget());//минус потому что мы уже потратили эту сумму
    }

    @Test
    void removePurchaseTest() {
        assertEquals(purchaseList.get(0),budget.removePurchase(1));
        assertEquals(4, budget.collect().size());
    }

    @Test
    void printTest() {
        budget.print();
    }

    @Test
    void collectTest() {
        budget.collect().forEach(System.out::println);
    }
}
