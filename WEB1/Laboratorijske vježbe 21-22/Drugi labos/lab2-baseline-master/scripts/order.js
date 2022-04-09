function addToCart(id) {
	// INSERT CODE HERE --> PRIPREMA

	// END INSERT --> PRIPREMA
	refreshCartItems();
}

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
		// INSERT CODE HERE --> PRIPREMA

		// END INSERT --> PRIPREMA

		main.appendChild(category);
	}
};
getData();