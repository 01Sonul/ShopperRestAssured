package pojoLibrary;

public class Pojo_ShopperOrder {

	Pojo_Address address;
	String paymentMode;

	public Pojo_ShopperOrder(Pojo_Address address, String paymentMode) {

		this.address = address;
		this.paymentMode = paymentMode;
	}

	public Pojo_Address getAddress() {
		return address;
	}

	public void setAddress(Pojo_Address address) {
		this.address = address;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
