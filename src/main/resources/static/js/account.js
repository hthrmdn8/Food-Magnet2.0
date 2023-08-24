document.addEventListener("DOMContentLoaded", function () {

    //swap between info, favorites, and reviews
    const showButtons = document.querySelectorAll('.show-content');
    const contentDivs = document.querySelectorAll('.content');

    showButtons.forEach((button, index) => {
        button.addEventListener('click', () => {
            contentDivs.forEach((div, divIndex) => {
                if (index === divIndex) {
                    div.style.display = 'flex';
                } else {
                    div.style.display = 'none';
                }
            });
        });
    });

    //open & close edit review form
    const editButtons = document.querySelectorAll(".edit-review");
    const deleteButtons = document.querySelectorAll(".delete-review");

    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            const review = button.closest(".review");
            const editContent = review.querySelector(".edit-review-content");
            const displayContent = review.querySelector(".display-review-content");

            if(editContent.style.display === "none") {
                editContent.style.display = "block";
                displayContent.style.display = "none";
                button.classList.add("edit-active");
            } else {
                editContent.style.display = "none";
                displayContent.style.display = "block";
                button.classList.remove("edit-active");
            }
        });
    });

    deleteButtons.forEach(button => {
        button.addEventListener("mousedown", function() {
            button.classList.add("delete-active");
        });

        button.addEventListener("mouseup", function() {
            button.classList.remove("delete-active");
        });

    });
});