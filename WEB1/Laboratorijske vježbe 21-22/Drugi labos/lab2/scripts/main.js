function refreshCartItems(){
	//PRIPREMA

	let cartItemsCounter = document.querySelector("#cart-items");
	let cart = localStorage.getItem("cart");
	cart = JSON.parse(cart);	

	if (cart === null)
	{
		cartItemsCounter.textContent = "0 items";
	}
	else if(Object.keys(cart).length === 1)
	{
		cartItemsCounter.textContent = "1 item";
	}
	else
	{		
		cartItemsCounter.textContent = Object.values(cart).reduce((a, b) => a + b) + " items";
	}

	console.log(Object.values(cart));
	//PRIPREMA
}



refreshCartItems();