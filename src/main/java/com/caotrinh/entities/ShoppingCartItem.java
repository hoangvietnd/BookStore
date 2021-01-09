package com.caotrinh.entities;

public class ShoppingCartItem {
    Book item;
    int quantity;

    public ShoppingCartItem(Book anItem) {
        item = anItem;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public Book getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemTotal() {
    	return roundOff(quantity*item.getPrice());
    }
    
    private double roundOff(double x) {
		long val = Math.round(x * 100); // cents
		return val / 100.0;
	}
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	@Override
	public String toString() {
		return "ShoppingCartItem [item=" + item.toString() + ", quantity=" + quantity + "]";
	}
    
}
