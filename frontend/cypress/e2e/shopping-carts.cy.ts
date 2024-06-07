describe('Search Shopping Carts spec', () => {
  beforeEach(() => cy.intercept(
      'GET',
      'http://localhost:8080/api/shopping-cart/search?page=0&size=10',
      {fixture: 'api/shopping-cart/search/200-get-page0&size10.json'}
    )
      .as('defaultSearchShoppingCarts')
  )

  beforeEach(
    () => cy.visit('http://localhost:4200')
      .then(() => cy.wait('@defaultSearchShoppingCarts'))
  )

  it('should show 10 shopping carts', () => {
    cy.get('mat-row').should('have.length', 10)
  })
})
