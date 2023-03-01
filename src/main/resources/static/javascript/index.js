function goToBook(bookName){
    document.location.href = `/book?title=${bookName}`; 
}

function addBookButton(title){
    const button = document.createElement('button');
    button.classList.add("book-button");
    button.setAttribute("onclick", `goToBook('${title}.jpg')`);

    const img = document.createElement('img');
    img.classList.add("book-button-img");
    img.setAttribute("src", `images/${title}.jpg`);
    img.setAttribute("alt", "Book picture not found");

    button.appendChild(img);

    const homepage_books = document.getElementById("homepage-books");
    homepage_books.appendChild(button);
}

document.addEventListener("DOMContentLoaded", () => {
    addBookButton("1984");
    addBookButton("the_mist");
    addBookButton("farenheit_451");
})
