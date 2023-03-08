


function goHome(){
    document.location.href = "/"; 
}

function goToLogin(bookName){
    document.location.href = `/login`; 
}

function goToRegister(bookName){
    document.location.href = `/register`; 
}

function goToBook(id){
    document.location.href = `/book?id=${id}`; 
}

function addBookButton(element){
    const button = document.createElement('button');
    button.classList.add("book-button");
    button.setAttribute("onclick", `goToBook(${element.id})`);

    const img = document.createElement('img');
    img.classList.add("book-button-img");
    img.setAttribute("src", `images/${element.title.toLowerCase().replace(" ", "_")}.jpg`);
    img.setAttribute("alt", "Book picture not found");

    button.appendChild(img);

    const homepage_books = document.getElementById("homepage-books");
    homepage_books.appendChild(button);
}