
document.addEventListener("DOMContentLoaded", () => {
    fetch("/frontPageBooks")
    .then((payload) => payload.json())
    .then(json => {
        console.log(json);
        generateBookTable(json);
    });
})
