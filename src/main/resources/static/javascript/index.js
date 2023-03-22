
document.addEventListener("DOMContentLoaded", () => {
    fetch("/frontPageBooks").
    then((payload) => payload.json()).
    then((json) => json.forEach(element => {
        addBookButton(element);        
    }));
})
