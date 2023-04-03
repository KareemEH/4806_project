
document.addEventListener("DOMContentLoaded", () => {
    fetch("/frontPageBooks")
    .then((payload) => payload.json())
    .then(json => {

       if (sessionStorage.getItem("userId")!=null){
            getRecommendedBooks().then((recommendedBooks) => {
                if (recommendedBooks.length > 0) {
                    console.log(recommendedBooks);
                    generateBookTable(recommendedBooks, "recommendedBooks", false);
                }
            });
        }
        console.log(json);
        generateBookTable(json, "books", true);

    });

    if(sessionStorage.username === "Admin"){
        document.getElementById("add-book-btn").toggleAttribute("hidden");
    }
});

