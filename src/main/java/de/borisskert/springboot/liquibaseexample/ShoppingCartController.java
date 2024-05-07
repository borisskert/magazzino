package de.borisskert.springboot.liquibaseexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/search")
    public Page<ShoppingCartSearchResult> search(ShoppingCartSearch search, Pageable pageable) {
        return shoppingCartService.search(search, pageable)
                .map(ShoppingCartSearchResult::fromEntity);
    }
}
