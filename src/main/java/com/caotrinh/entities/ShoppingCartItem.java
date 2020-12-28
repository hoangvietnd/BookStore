package com.caotrinh.entities;

public class ShoppingCartItem {
    Object item;
    int quantity;

    public ShoppingCartItem(Object anItem) {
        item = anItem;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public Object getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	@Override
	public String toString() {
		return "ShoppingCartItem [item=" + item.toString() + ", quantity=" + quantity + "]";
	}
    
}
