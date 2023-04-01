
document.addEventListener("DOMContentLoaded", () => {
    fetch("/frontPageBooks")
    .then((payload) => payload.json())
    .then(json => {
        if (sessionStorage.getItem("userId")!=null){
            getRecommendedBooks().then((recommendedBooks) => {
                if (recommendedBooks.length > 0) {
                    // document.append(document.createElement("p").innerHTML="Here's What Users Similar To You Have Ordered!");
                    generateBookTable(recommendedBooks, "recommendedBooks", false);
                }
            });
        }
        generateBookTable(json, "books", true);

    });
});

