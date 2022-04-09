function addToCart(id) {
	/*//PRIPREMA

	console.log(id);

	
	let cart = localStorage.getItem("cart");

	if (cart === null)
	{
		cart = {};		
	}
	else
	{
		cart = JSON.parse(cart);		
	}	

	if (!(id in cart)) {
		cart[id] = 0;
	}

	cart[id]++;
	localStorage.setItem("cart", JSON.stringify(cart));

	//PRIPREMA*/

	/*let value = localStorage.getItem(id);
	value = JSON.parse(value);
	localStorage.setItem(id, JSON.stringify(value+1));*/

	if( localStorage.getItem(id)===null)
	{
		localStorage.setItem(id,1);
	}
	else
	{
		let num = parseInt(localStorage.getItem(id),10)+1;
		localStorage.setItem(id,num);
	}


	refreshCartItems();
};

let getData = async function () {
	let response = await fetch("data/lab2.json");
	let data = await response.json();
	addCategories(data);
}

let addCategories = async function (data) {
	let categories = data.categories;
	let main = document.querySelector('main');
	let categoryTemplate = document.querySelector('#category-template');

	for (let index = 0; index < categories.length; index++) {
		let category = categoryTemplate.content.cloneNode(true);
		let categoryTitleElement = category.querySelector('.decorated-title > span');
		categoryTitleElement.textContent = categories[index].name;
		
		let products = data.products.filter(p => p.categoryId ==  categories[index].id);
		//PRIPREMA

		let gallery = category.querySelector(".gallery");
	let productTemplate = document.querySelector("#product-template");
	
	for (let index = 0; index < products.length; index++) {
		let product = productTemplate.content.cloneNode(true);		

		let productImageElement = product.querySelector(".photo-box-image");
		productImageElement.src = products[index].imageUrl;

		let productTitleElement = product.querySelector(".photo-box-title");
		productTitleElement.textContent = products[index].name;	
		
		let productID = products[index].id;	

		let productCartButton = product.querySelector(".cart-btn");		
		/*productCartButton.onclick = function() {addToCart(productID)};*/

		productCartButton.onclick = () => addToCart(productID);

		gallery.appendChild(product);
	}
		//PRIPREMA

		main.appendChild(category);
		
	}
};

getData();