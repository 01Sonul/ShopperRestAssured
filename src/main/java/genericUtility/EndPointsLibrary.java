package genericUtility;

public interface EndPointsLibrary {

	String login = "/users/login";
	String getAllProducts = "/products/alpha";
	String addNewAddress = "/shoppers/{shopperId}/address";
	String getAllAddress = "/shoppers/{shopperId}/address";
	String getPerticularAddress = "/shoppers/{shopperId}/address/{addressId}";
	String updateAddress = "/shoppers/{shopperId}/address/{addressId}";
	String deleteAddress = "/shoppers/{shopperId}/address/{addressId}";
	String addProductToWishlist = "/shoppers/{shopperId}/wishlist";
	String getProductFromWishlist = "/shoppers/{shopperId}/wishlist";
	String deleteFromWishlist = "/shoppers/{shopperId}/wishlist/{productId}";
	String addProductToCart = "/shoppers/{shopperId}/carts";
	String getAllProductsFromCart = "/shoppers/{shopperId}/carts";
	String updateProductsInCart = "/shoppers/{shopperId}/carts/{itemId}";
	String deleteProductsFromCart = "/shoppers/{shopperId}/carts/{productId}";
	String placeOrder = "/shoppers/{shopperId}/orders";
	String displayOrders = "/shoppers/{shopperId}/orders";
	String updateOrderStatus = "/shoppers/{shopperId}/orders/{orderId}";
	String generateInvoice = "/shoppers/{shopperId}/orders/{orderId}/invoice";
	
}
