import navbar from "../components/nav.js";

let catchElement = (val) => {
    return document.querySelector(val);
};

let createElement = (val) => {
    return document.createElement(val);
};

catchElement('.navbar').innerHTML = navbar();

const electronicsPage = catchElement(".category-finder>div:nth-child(6)");

electronicsPage.addEventListener("click", () => window.location.href = "/pages/electronics.html");
