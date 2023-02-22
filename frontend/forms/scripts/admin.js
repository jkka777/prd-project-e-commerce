
import navbar from "../../components/nav.js";

let catchElement = (val) => {
    return document.querySelector(val);
};

let createElement = (val) => {
    return document.createElement(val);
};

catchElement('.navbar').innerHTML = navbar();


